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

        // 名前をサーバーに送信
        bc.sendPlayerName();

        // IDをサーバーから取得
        try {
            bc.getPlayerId();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Get playerID Failed!");
        }

        // TODO:  waiting-viewが表示されない原因を探って解決する
        // waiting-view に遷移
        try {
            bc.changeView("waiting-view.fxml");
        } catch (IOException e) {
            System.err.println(e);
        }

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