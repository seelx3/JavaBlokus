package javablokus.blokus;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlokusClient {
    final int PORT = 8090;
    InetAddress addr;
    Socket socket;
    BufferedReader in;
    static PrintWriter out;
    static int playerId; // 0 or 1　/ 共有
    private String playerName;
    static Communication comObj; // 共有
    static ObjectMapper mapper;
    static Scene currentScene;
    static Boolean gameFinished;

    static Node fxmlnode;
    static Group asgnedBlocks;
    private static final double BLOCK_HEIGHT = 30.44;
    private static final double BLOCK_WIDTH = 30;
    private static final int COL = 14;
    private static final int ROW = 14;
    private static final int LX = 187;
    private static final int LY = 53;
    private static final int BLUE = 2;
    private static final int RED = 3;

    BlokusClient (String pn) {
        setPlayerName(pn);
    }

    PlayController pc;

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

        pc = new PlayController();
        asgnedBlocks = new Group();
        gameFinished = false;
    }

    public void setPlayerName(String name) {
        playerName = name;
        System.out.println("Set the playerName to :" + playerName);
    }

    public void sendPlayerName() {
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

    public void getPlayerId() throws IOException {
        // サーバーからplayerIdを受け取る
        playerId = Integer.parseInt(in.readLine());
        System.out.println("Set playerID to : " + playerId);
    }

    public void waitForStart() {
        // サーバーからの応答を受け取ってゲーム画面に遷移

        System.out.println("wait for start");

        // Communicationオブジェクトの情報をサーバーから取得をするタスク
        // 取得後にplay-viewに画面遷移
        Task<Void> waitFor2ndPlayer = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(!in.ready()) { ; }
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

    public void waitForNextTurn() {

        System.out.println("Wait for Next Turn");
        Task<Void> waitNext = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true) {
                    while(!in.ready() && !gameFinished) { ; }
                    if(gameFinished) break;

                    String msg = null;
                    try {
                        msg = in.readLine();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    if(msg == null) {
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

                        setAsgnedBlocks();
                        Group root = new Group();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-view.fxml"));
                        try {
                            fxmlnode = fxmlLoader.load();
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                        root.getChildren().add(fxmlnode);
                        PlayController pc = fxmlLoader.getController(); // 使用中のコントローラーを取得

                        if(comObj.finished) {
                            clearButton(pc);
                            pc.playerName.setText(comObj.whowon);
                            pc.label1.setText("won!");
                            pc.goToTitle.setVisible(true);
                            gameFinished = true;
                        }

                        pc.playerName.setText(comObj.players[comObj.turn]);
                        if(comObj.turn != playerId) clearButton(pc);
                        else setButtonVisible(pc);

                        root.getChildren().add(asgnedBlocks);
                        currentScene = new Scene(root, 800, 600);
                        JavaBlokus.setView(currentScene);

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

    void connectToServer() {
        try{
            Init();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Connection Failed!");
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
    }

    void disconnect() throws IOException {
        socket.close();
    }

    static void setAsgnedBlocks() {
        asgnedBlocks.getChildren().removeAll();
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Rectangle r = new Rectangle(LX + j * BLOCK_WIDTH, LY + i * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
                if(comObj.board[i][j] == BLUE) {
                    r.setFill(Color.DODGERBLUE);
                    r.setStroke(Color.BLACK);
                    asgnedBlocks.getChildren().add(r);
                }else if(comObj.board[i][j] == RED) {
                    r.setFill(Color.DARKRED);
                    r.setStroke(Color.BLACK);
                    asgnedBlocks.getChildren().add(r);
                }
            }
        }
    }

    static void setButtonVisible(PlayController pc) {
        if(comObj.usedPiece[playerId][0])  pc.AButton.setVisible(false);
        if(comObj.usedPiece[playerId][1])  pc.BButton.setVisible(false);
        if(comObj.usedPiece[playerId][2])  pc.CButton.setVisible(false);
        if(comObj.usedPiece[playerId][3])  pc.DButton.setVisible(false);
        if(comObj.usedPiece[playerId][4])  pc.EButton.setVisible(false);
        if(comObj.usedPiece[playerId][5])  pc.FButton.setVisible(false);
        if(comObj.usedPiece[playerId][6])  pc.GButton.setVisible(false);
        if(comObj.usedPiece[playerId][7])  pc.HButton.setVisible(false);
        if(comObj.usedPiece[playerId][8])  pc.IButton.setVisible(false);
        if(comObj.usedPiece[playerId][9])  pc.JButton.setVisible(false);
        if(comObj.usedPiece[playerId][10]) pc.KButton.setVisible(false);
        if(comObj.usedPiece[playerId][11]) pc.LButton.setVisible(false);
        if(comObj.usedPiece[playerId][12]) pc.MButton.setVisible(false);
        if(comObj.usedPiece[playerId][13]) pc.NButton.setVisible(false);
        if(comObj.usedPiece[playerId][14]) pc.OButton.setVisible(false);
        if(comObj.usedPiece[playerId][15]) pc.PButton.setVisible(false);
        if(comObj.usedPiece[playerId][16]) pc.QButton.setVisible(false);
        if(comObj.usedPiece[playerId][17]) pc.RButton.setVisible(false);
        if(comObj.usedPiece[playerId][18]) pc.SButton.setVisible(false);
        if(comObj.usedPiece[playerId][19]) pc.TButton.setVisible(false);
        if(comObj.usedPiece[playerId][20]) pc.UButton.setVisible(false);
    }

    static void clearButton(PlayController pc){
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