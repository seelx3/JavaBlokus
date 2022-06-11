package javablokus.blokus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static javablokus.blokus.JavaBlokus.stg;

public class PlayController {
    private static final double BLOCK_HEIGHT = 30.44;
    private static final double BLOCK_WIDTH = 30;
    private static final int COL = 14;
    private static final int ROW = 14;
    private static final int PIECE_SIZE = 7;
    private static final int LX = 187;
    private static final int LY = 53;
    private static final Color col[] = {Color.BLUE, Color.RED};
    private static final int BLUE = 2;
    private static final int RED = 3;

    static int Xpos;
    static int Ypos;
    static AbstractPiece currentPiece;
    static Group root, blocks;

    @FXML public  Label playerName; // ターンのプレイヤー

    @FXML
    protected void onAButtonClick(ActionEvent ev) { currentPiece = new PieceA(); setPos(); }
    @FXML
    protected void onBButtonClick(ActionEvent ev) { currentPiece = new PieceB(); setPos(); }
    @FXML
    protected void onCButtonClick(ActionEvent ev) { currentPiece = new PieceC(); setPos(); }
    @FXML
    protected void onDButtonClick(ActionEvent ev) { currentPiece = new PieceD(); setPos(); }
    @FXML
    protected void onEButtonClick(ActionEvent ev) { currentPiece = new PieceE(); setPos(); }
    @FXML
    protected void onFButtonClick(ActionEvent ev) { currentPiece = new PieceF(); setPos(); }
    @FXML
    protected void onGButtonClick(ActionEvent ev) { currentPiece = new PieceG(); setPos(); }
    @FXML
    protected void onHButtonClick(ActionEvent ev) { currentPiece = new PieceH(); setPos(); }
    @FXML
    protected void onIButtonClick(ActionEvent ev) { currentPiece = new PieceI(); setPos(); }
    @FXML
    protected void onJButtonClick(ActionEvent ev) { currentPiece = new PieceJ(); setPos(); }
    @FXML
    protected void onKButtonClick(ActionEvent ev) { currentPiece = new PieceK(); setPos(); }
    @FXML
    protected void onLButtonClick(ActionEvent ev) { currentPiece = new PieceL(); setPos(); }
    @FXML
    protected void onMButtonClick(ActionEvent ev) { currentPiece = new PieceM(); setPos(); }
    @FXML
    protected void onNButtonClick(ActionEvent ev) { currentPiece = new PieceN(); setPos(); }
    @FXML
    protected void onOButtonClick(ActionEvent ev) { currentPiece = new PieceO(); setPos(); }
    @FXML
    protected void onPButtonClick(ActionEvent ev) { currentPiece = new PieceP(); setPos(); }
    @FXML
    protected void onQButtonClick(ActionEvent ev) { currentPiece = new PieceQ(); setPos(); }
    @FXML
    protected void onRButtonClick(ActionEvent ev) { currentPiece = new PieceR(); setPos(); }
    @FXML
    protected void onSButtonClick(ActionEvent ev) { currentPiece = new PieceS(); setPos(); }
    @FXML
    protected void onTButtonClick(ActionEvent ev) { currentPiece = new PieceT(); setPos(); }
    @FXML
    protected void onUButtonClick(ActionEvent ev) { currentPiece = new PieceU(); setPos(); }

    @FXML
    protected void DownClick(ActionEvent ev) {
        Ypos += 1;
        changePos();
    }

    @FXML
    protected void UpClick(ActionEvent ev) {
        Ypos -= 1;
        changePos();
    }

    @FXML
    protected void LeftClick(ActionEvent ev) {
        Xpos -= 1;
        changePos();
    }

    @FXML
    protected void RightClick(ActionEvent ev) {
        Xpos += 1;
        changePos();
    }

    @FXML
    void ReverseClick(ActionEvent ev) {
        currentPiece.reversePiece();
        changePos();
    }

    @FXML
    void SpinClick(ActionEvent ev) {
        currentPiece.spinPiece();
        changePos();
    }

    @FXML
    void onGiveUpClick(ActionEvent ev) {

    }

    @FXML
    void onConfirmClick(ActionEvent ev) {
        // TODO: placableかどうかの判定

        // TODO: comObjを更新
        updateComObj();

        // TODO: サーバーに送信
        BlokusClient.sendComObj();
    }

    void setPos() {
        Xpos = -3;
        Ypos = -3;

        root = new Group();
        blocks = new Group();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-view.fxml"));
        try {
            root.getChildren().add(fxmlLoader.load());
        } catch (Exception e) {
            System.err.println(e);
        }
        PlayController pc = fxmlLoader.getController();

        int[][] piece = currentPiece.getPiece();

        // (x, y)にピースを配置
        // -3 <= x, y <= 10
        // ピースの中心の座標は(x+3, y+3)

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                Rectangle r = new Rectangle(LX + (Xpos+j) * BLOCK_WIDTH , LY + (Ypos+i) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);

                if(piece[i][j] == 1) {
                    r.setFill(col[BlokusClient.playerId]);
                    r.setStroke(Color.BLACK);
                    blocks.getChildren().add(r);
                }
            }
        }

        root.getChildren().add(BlokusClient.asgnedBlocks);
        root.getChildren().add(blocks);
        Scene scn = new Scene(root, 800, 600);
        stg.setScene(scn);
    }

    void changePos() {

        if(currentPiece == null) {
            System.err.println("currentPiece == null !");
            return;
        }
        root = new Group();
        blocks = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-view.fxml"));

        try {
            int[][] piece = currentPiece.getPiece();
            root.getChildren().add(fxmlLoader.load());
        } catch (Exception e) {
            System.out.println(e);
        }

        int[][] piece = currentPiece.getPiece();

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                Rectangle r = new Rectangle(LX + (Xpos+j) * BLOCK_WIDTH , LY + (Ypos+i) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);

                if(piece[i][j] == 1) {
                    r.setFill(col[BlokusClient.playerId]);
                    r.setStroke(Color.BLACK);
                    blocks.getChildren().add(r);
                }
            }
        }

        root.getChildren().add(BlokusClient.asgnedBlocks);
        root.getChildren().add(blocks);
        Scene scn = new Scene(root, 800, 600);
        stg.setScene(scn);
    }

    static void updateComObj() {
        int[][] piece = currentPiece.getPiece();
        System.err.println("Xpos: " + Xpos + ", Ypos: " + Ypos);
        for(int i = 0; i < PIECE_SIZE; i++) {
            for(int j = 0; j < PIECE_SIZE; j++) {
                System.err.print(piece[i][j] + " ");
            }
            System.err.println();
        }

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                if(Ypos+i >= 0 && Ypos < 14 && Xpos+j >= 0 && Xpos+j < 14 && piece[i][j] == 1) {
                    if(BlokusClient.playerId == 0) BlokusClient.comObj.board[Ypos+i][Xpos+j] = BLUE;
                    else if(BlokusClient.playerId == 1) BlokusClient.comObj.board[Ypos+i][Xpos+j] = RED;
                }
            }
        }
    }
}
