package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    public static BlokusClient bc;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");

        // TODO: ボタンが押されたときに通信を確立する
        bc = new BlokusClient();
        bc.Init();
    }
}