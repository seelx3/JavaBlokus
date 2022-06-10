package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartController {

    @FXML
    private TextField playername;

    @FXML
    public Label starttext;

    @FXML
    protected void onStartButtonClick() {

        if(playername.getText().equals("")){
            starttext.setText("Please enter your name!");
            return;
        }

        starttext.setText("Connecting...");

        BlokusClient bc = new BlokusClient(playername.getText());
        bc.connectToServer();
    }

}