package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartController {

    @FXML
    private TextField playername;

    @FXML
    public Label starttext;

    @FXML
    protected void onStartButtonClick() {

        // TODO: playernameが空白ならば入力を促す

        starttext.setText("Waiting...");

        BlokusClient bc = new BlokusClient(starttext.getText());
        bc.connectToServer();
    }

}