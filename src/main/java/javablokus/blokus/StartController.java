package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartController {
    private BlokusClient bc;

    @FXML
    private TextField playername;

    @FXML
    public Label starttext;

    @FXML
    public Button startButton;

    @FXML
    protected void onStartButtonClick() {
        if(playername.getText().equals("")){
            starttext.setText("Please enter your name!");
            return;
        }

        starttext.setText("Connecting...");
        startButton.setVisible(false);

        bc = new BlokusClient(playername.getText());
        if(bc.connectToServer() == -1){
            starttext.setText("Connection Failed!");
            startButton.setVisible(true);
        }
    }

}