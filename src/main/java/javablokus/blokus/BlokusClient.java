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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class BlokusClient {
    final int PORT = 8090;
    InetAddress addr;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    int playerId;
    private String playerName;

    Communication comObj;

    ObjectMapper mapper;

    ViewController vc;

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

        vc = new ViewController();
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
                        comObj = mapper.readValue(msg, Communication.class);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    System.out.println(comObj);
                    try {
                        vc.changeView("play-view.fxml");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                });
                return null;
            }
        };

        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(waitFor2ndPlayer);
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

