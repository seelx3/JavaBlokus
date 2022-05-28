package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartController {
    public static BlokusClient bc;

    void connectToServer() {
        try{
            bc = new BlokusClient();
            bc.setPlayerName(playername.getText());
            bc.Init();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Connection Failed!");
            // TODO: 接続やりなおし
        }

        // waiting-view に遷移
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("waiting-view.fxml"));
            Scene scene = new Scene(parent, 800, 600);
            JavaBlokus.changeView(scene);
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Change View to Waiting-view");
        }

        // 名前をサーバーに送信
        bc.sendPlayerName();

        // IDをサーバーから取得
        try {
            bc.getPlayerId();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Get playerID Failed!");
        }

        // TODO: 待ち合わせ状態に遷移(サーバーからの応答を待つ)
        bc.waitForStart();
    }

    @FXML
    private TextField playername;

    @FXML
    protected void onStartButtonClick() {

        // TODO: playernameが空白ならば入力を促す

        connectToServer();
    }
}