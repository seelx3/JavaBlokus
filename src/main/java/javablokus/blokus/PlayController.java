package javablokus.blokus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PlayController {
    @FXML private Button aButton;
    @FXML private Button bButton;
    @FXML private Button cButton;

    @FXML public  Label playerName;

    private List<Button> buttonList = new ArrayList<>();

    @FXML private void initialize() {
        System.out.println("button Panel initialize");

        this.buttonList = List.of(
                this.aButton,
                this.bButton,
                this.cButton
        );

        for(int i=0; i<buttonList.size(); i++) {
//            final int j = i;
            PieceA piece = new PieceA();
            buttonList.get(i).setOnAction(event -> {
//                System.out.println(buttonList.get(j).getText());
                // TODO: ピースボタンが押されたときに盤面に表示


            });
        }
    }

}
