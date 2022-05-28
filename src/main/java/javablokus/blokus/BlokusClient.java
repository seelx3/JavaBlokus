package javablokus.blokus;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BlokusClient {
    final int PORT = 8090;
    InetAddress addr;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    int playerId;
    private String playerName;

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
        playerId = Integer.parseInt(in.readLine());
        System.out.println("Set playerID to : " + playerId);
    }

    public void waitForStart() {
        // TODO: サーバーからの応答を受け取ってゲーム画面に遷移
        // TODO: Jackson周りの実装をついか

        // 自分のターンであればサーバーへ更新後のオブジェクトを送信
        // そうでなければつぎのサーバーからの受信を待つ
    }

    void disconnect() throws IOException {
        socket.close();
    }
}
