package javablokus.blokus;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class BlokusClient {
    final int PORT = 8090;
    InetAddress addr;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    int playerId; // 0 or 1
    private String playerName;

    Communication comObj;

    ObjectMapper mapper;

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
    }

    public void setPlayerName(String name) {
        playerName = name;
        System.out.println("Set the playerName to :" + playerName);
    }

    public void sendPlayerName() {
        out.println(playerName);
        System.out.println("Send player name : " + playerName);
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
        // turn % 2 == id ならばクライアントはturnを+1してComObjをサーバに送信
        // サーバからComObjを受信

        System.out.println("Wait for Next Turn");
        Task<Void> waitNext = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true) {
                    while(!in.ready()) { ; }
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

                        // TODO: playerName(誰のターン)や盤面を更新
                        Group root = new Group();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-view.fxml"));
                        try {
                            root.getChildren().add(fxmlLoader.load());
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                        PlayController pc = fxmlLoader.getController(); // 使用中のコントローラーを取得
                        pc.playerName.setText("aaa");
                        Scene scn = new Scene(root, 800, 600);
                        JavaBlokus.setView(scn);

                    });
                }

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
}

