package javablokus.blokus;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlokusClient {
    final int PORT = 8090;
    InetAddress addr;
    Socket socket;
    BufferedReader in;
    private static PrintWriter out;
    public static int playerId; // 0 or 1　/ 共有
    private String playerName;
    public static Communication comObj; // 共有
    private static ObjectMapper mapper;
    private static Boolean gameFinished;
    private static PlayController pCtrl;

    private static Node fxmlnode;
    public static Group root;
    private static Group asgnedBlocks;
    public static Scene scn;
    private static final double BLOCK_SIZE = 30;
    private static final int BOARD_SIZE = 14;
    private static final int LX = 187;
    private static final int LY = 53;
    private static final int BLUE = 2;
    private static final int RED = 3;

    BlokusClient (String pn) {
        setPlayerName(pn);
    }

    public void Init() throws IOException {
        addr = InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        socket = new Socket(addr, PORT);
        System.out.println("socket = " + socket);

        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream())); // データ受信用バッファの設定

        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())),
                true);                      // 送信バッファ設定
        mapper = new ObjectMapper();

        pCtrl = new PlayController();
        root = new Group();
        asgnedBlocks = new Group();
        gameFinished = false;
    }

    private void setPlayerName(String name) {
        playerName = name;
        System.out.println("Set the playerName to :" + playerName);
    }

    private void sendPlayerName() {
        out.println(playerName);
        System.out.println("Send player name : " + playerName);
    }

    public static void sendComObj() {
        try {
            out.println(mapper.writeValueAsString(comObj));
        } catch (JsonProcessingException jpe) {
            System.err.println(jpe);
        }
    }

    private void getPlayerId() throws IOException {
        // サーバーからplayerIdを受け取る
        playerId = Integer.parseInt(in.readLine());
        System.out.println("Set playerID to : " + playerId);
    }

    private void waitForStart() {
        // サーバーからの応答を受け取ってゲーム画面に遷移

        System.out.println("wait for start");

        // Communicationオブジェクトの情報をサーバーから取得をするタスク
        // 取得後にplay-viewに画面遷移
        Task<Void> waitFor2ndPlayer = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!in.ready()) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ie) {
                        System.err.println(ie);
                    }
                }
                Platform.runLater(() -> {
                    try {
                        String msg = in.readLine();
                        System.out.println(msg);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    waitForNextTurn();
                });
                return null;
            }
        };

        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(waitFor2ndPlayer);
        ex.shutdown();
    }

    private void waitForNextTurn() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-view.fxml"));
        try {
            fxmlnode = fxmlLoader.load();
        } catch (Exception e) {
            System.err.println(e);
        }
        root.getChildren().add(fxmlnode);
        pCtrl = fxmlLoader.getController(); // 使用中のコントローラーを取得

        scn = new Scene(root, 800, 600);
        pCtrl.keyInit();
        JavaBlokus.setView(scn);

        System.out.println("Wait for Next Turn");
        Task<Void> waitNext = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    while (!in.ready() && !gameFinished) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ie) {
                            System.err.println(ie);
                        }
                    }
                    if (gameFinished) break;

                    String msg = null;
                    try {
                        msg = in.readLine();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    if (msg == null) {
                        System.out.println();
                        break;
                    }
                    final String objmsg = msg;

                    Platform.runLater(() -> {
                        try {
                            comObj = mapper.readValue(objmsg, Communication.class); // comObjの更新
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        System.out.println(comObj);

                        root.getChildren().remove(asgnedBlocks);
                        setAsgnedBlocks();
                        root.getChildren().add(asgnedBlocks);

                        pCtrl.playerName.setText(comObj.players[comObj.turn]);
                        if (comObj.turn != playerId) clearButton(pCtrl);
                        else setButtonVisible(pCtrl);

                        if (comObj.finished) {
                            clearButton(pCtrl);
                            pCtrl.playerName.setText(comObj.whowon);
                            pCtrl.label1.setText("won!");
                            pCtrl.goToTitle.setVisible(true);
                            gameFinished = true;
                        }
                    });

                }
                System.out.println("exit Task");
                return null;
            }
        };

        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(waitNext);
        ex.shutdown();

    }

    public int connectToServer() {
        try{
            Init();
        } catch (IOException e) {
            System.err.println(e);
            return -1;
        }

        // 名前をサーバーに送信
        sendPlayerName();

        // IDをサーバーから取得
        try {
            getPlayerId();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Get playerID Failed!");
        }

        waitForStart();
        return 0;
    }

    private static void setAsgnedBlocks() {
        asgnedBlocks.getChildren().removeAll();
        Group base = new Group();
        Group block = new Group();
        Group startPoint = new Group();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Rectangle r = new Rectangle(LX + j * BLOCK_SIZE, LY + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                base.getChildren().add(r);
                if((i==4 && j==4) || (i==9 && j==9)) {
                    r.setFill(Color.LIGHTGRAY);
                    startPoint.getChildren().add(r);
                }
                if(comObj.board[i][j] == BLUE) {
                    r.setFill(Color.DODGERBLUE);
                    block.getChildren().add(r);
                }else if(comObj.board[i][j] == RED) {
                    r.setFill(Color.DARKRED);
                    block.getChildren().add(r);
                }
            }
        }
        asgnedBlocks.getChildren().add(base);
        asgnedBlocks.getChildren().add(startPoint);
        asgnedBlocks.getChildren().add(block);
    }

    private static void setButtonVisible(PlayController pc) {
        if(!comObj.usedPiece[playerId][0])  pc.AButton.setVisible(true);
        if(!comObj.usedPiece[playerId][1])  pc.BButton.setVisible(true);
        if(!comObj.usedPiece[playerId][2])  pc.CButton.setVisible(true);
        if(!comObj.usedPiece[playerId][3])  pc.DButton.setVisible(true);
        if(!comObj.usedPiece[playerId][4])  pc.EButton.setVisible(true);
        if(!comObj.usedPiece[playerId][5])  pc.FButton.setVisible(true);
        if(!comObj.usedPiece[playerId][6])  pc.GButton.setVisible(true);
        if(!comObj.usedPiece[playerId][7])  pc.HButton.setVisible(true);
        if(!comObj.usedPiece[playerId][8])  pc.IButton.setVisible(true);
        if(!comObj.usedPiece[playerId][9])  pc.JButton.setVisible(true);
        if(!comObj.usedPiece[playerId][10]) pc.KButton.setVisible(true);
        if(!comObj.usedPiece[playerId][11]) pc.LButton.setVisible(true);
        if(!comObj.usedPiece[playerId][12]) pc.MButton.setVisible(true);
        if(!comObj.usedPiece[playerId][13]) pc.NButton.setVisible(true);
        if(!comObj.usedPiece[playerId][14]) pc.OButton.setVisible(true);
        if(!comObj.usedPiece[playerId][15]) pc.PButton.setVisible(true);
        if(!comObj.usedPiece[playerId][16]) pc.QButton.setVisible(true);
        if(!comObj.usedPiece[playerId][17]) pc.RButton.setVisible(true);
        if(!comObj.usedPiece[playerId][18]) pc.SButton.setVisible(true);
        if(!comObj.usedPiece[playerId][19]) pc.TButton.setVisible(true);
        if(!comObj.usedPiece[playerId][20]) pc.UButton.setVisible(true);

        pc.GiveUpButton.setVisible(true);
        pc.ConfirmButton.setVisible(true);
        pc.UpButton.setVisible(true);
        pc.DownButton.setVisible(true);
        pc.RightButton.setVisible(true);
        pc.LeftButton.setVisible(true);
        pc.SpinButton.setVisible(true);
        pc.ReverseButton.setVisible(true);
    }

    private static void clearButton(PlayController pc){
        pc.AButton.setVisible(false);
        pc.BButton.setVisible(false);
        pc.CButton.setVisible(false);
        pc.DButton.setVisible(false);
        pc.EButton.setVisible(false);
        pc.FButton.setVisible(false);
        pc.GButton.setVisible(false);
        pc.HButton.setVisible(false);
        pc.IButton.setVisible(false);
        pc.JButton.setVisible(false);
        pc.KButton.setVisible(false);
        pc.LButton.setVisible(false);
        pc.MButton.setVisible(false);
        pc.NButton.setVisible(false);
        pc.OButton.setVisible(false);
        pc.PButton.setVisible(false);
        pc.QButton.setVisible(false);
        pc.RButton.setVisible(false);
        pc.SButton.setVisible(false);
        pc.TButton.setVisible(false);
        pc.UButton.setVisible(false);

        pc.GiveUpButton.setVisible(false);
        pc.ConfirmButton.setVisible(false);
        pc.UpButton.setVisible(false);
        pc.DownButton.setVisible(false);
        pc.RightButton.setVisible(false);
        pc.LeftButton.setVisible(false);
        pc.SpinButton.setVisible(false);
        pc.ReverseButton.setVisible(false);
    }
}