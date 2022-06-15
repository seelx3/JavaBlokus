package javablokus.blokus;

import javablokus.blokus.pieces.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static javablokus.blokus.BlokusClient.*;

public class PlayController {
    private static final double BLOCK_SIZE = 30;
    private static final int PIECE_SIZE = 7;
    private static final int BOARD_SIZE = 14;
    private static final int LX = 187;
    private static final int LY = 53;
    private static final Color[] col = {Color.DEEPSKYBLUE, Color.CRIMSON};
    private static final int[] boardNum = {2, 3};
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int MIN_COR = -3;
    private static final int MAX_COR = 10;

    static int xCor; // ピースの左上のマスのX座標に対応 0 <= xCor <= 13
    static int yCor; // ピースの左上のマスのY座標に対応 0 <= yCor <= 13
    static AbstractPiece currentPiece;
    static Group blocks;

    @FXML public Label playerName; // ターンのプレイヤー
    @FXML public Button GiveUpButton;
    @FXML public Button ConfirmButton;
    @FXML public Button UpButton;
    @FXML public Button DownButton;
    @FXML public Button RightButton;
    @FXML public Button LeftButton;
    @FXML public Button SpinButton;
    @FXML public Button ReverseButton;

    /* Button 宣言部 */
    @FXML Button AButton;
    @FXML Button BButton;
    @FXML Button CButton;
    @FXML Button DButton;
    @FXML Button EButton;
    @FXML Button FButton;
    @FXML Button GButton;
    @FXML Button HButton;
    @FXML Button IButton;
    @FXML Button JButton;
    @FXML Button KButton;
    @FXML Button LButton;
    @FXML Button MButton;
    @FXML Button NButton;
    @FXML Button OButton;
    @FXML Button PButton;
    @FXML Button QButton;
    @FXML Button RButton;
    @FXML Button SButton;
    @FXML Button TButton;
    @FXML Button UButton;
    @FXML Label label1;
    @FXML Button goToTitle;

    /* Button onAction 宣言部 */
    @FXML  protected void onAButtonClick() { currentPiece = new PieceA(); setPos(); }
    @FXML  protected void onBButtonClick() { currentPiece = new PieceB(); setPos(); }
    @FXML  protected void onCButtonClick() { currentPiece = new PieceC(); setPos(); }
    @FXML  protected void onDButtonClick() { currentPiece = new PieceD(); setPos(); }
    @FXML  protected void onEButtonClick() { currentPiece = new PieceE(); setPos(); }
    @FXML  protected void onFButtonClick() { currentPiece = new PieceF(); setPos(); }
    @FXML  protected void onGButtonClick() { currentPiece = new PieceG(); setPos(); }
    @FXML  protected void onHButtonClick() { currentPiece = new PieceH(); setPos(); }
    @FXML  protected void onIButtonClick() { currentPiece = new PieceI(); setPos(); }
    @FXML  protected void onJButtonClick() { currentPiece = new PieceJ(); setPos(); }
    @FXML  protected void onKButtonClick() { currentPiece = new PieceK(); setPos(); }
    @FXML  protected void onLButtonClick() { currentPiece = new PieceL(); setPos(); }
    @FXML  protected void onMButtonClick() { currentPiece = new PieceM(); setPos(); }
    @FXML  protected void onNButtonClick() { currentPiece = new PieceN(); setPos(); }
    @FXML  protected void onOButtonClick() { currentPiece = new PieceO(); setPos(); }
    @FXML  protected void onPButtonClick() { currentPiece = new PieceP(); setPos(); }
    @FXML  protected void onQButtonClick() { currentPiece = new PieceQ(); setPos(); }
    @FXML  protected void onRButtonClick() { currentPiece = new PieceR(); setPos(); }
    @FXML  protected void onSButtonClick() { currentPiece = new PieceS(); setPos(); }
    @FXML  protected void onTButtonClick() { currentPiece = new PieceT(); setPos(); }
    @FXML  protected void onUButtonClick() { currentPiece = new PieceU(); setPos(); }

    public void keyInit() {
        scn.setOnKeyPressed(this::keyPressed);
    }

    private void keyPressed(KeyEvent ke) {
        switch (ke.getCode()) {
            case ENTER -> confirmAction();
            case SPACE -> spinAction();
            case UP -> upAction();
            case DOWN -> downAction();
            case RIGHT -> rightAction();
            case LEFT -> leftAction();
        }
    }

    @FXML protected void DownClick() { downAction(); }
    @FXML protected void UpClick() { upAction(); }
    @FXML protected void LeftClick() { leftAction(); }
    @FXML protected void RightClick() { rightAction(); }

    @FXML protected void ReverseClick() { reverseAction(); }
    @FXML protected void SpinClick() { spinAction(); }

    @FXML
    protected void onGiveUpClick() {
        BlokusClient.comObj.giveup = true;
        BlokusClient.sendComObj();
    }

    @FXML protected void onConfirmClick() { confirmAction(); }

    @FXML
    void onGoToTitleButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaBlokus.class.getResource("start-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
            JavaBlokus.setView(scene);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        JavaBlokus.showView();
    }

    void setPos() {
        // ボード上の(7, 7)に対応
        xCor = 4;
        yCor = 4;

        root.getChildren().remove(blocks);
        blocks = new Group();

        int[][] piece = currentPiece.getPiece();

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                Rectangle r = new Rectangle(LX + (xCor +j) * BLOCK_SIZE , LY + (yCor +i) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                if(piece[i][j] == 1) {
                    r.setFill(col[BlokusClient.playerId]);
                    r.setStroke(Color.BLACK);
                    blocks.getChildren().add(r);
                }
            }
        }

        root.getChildren().add(blocks);
    }

    void changePos() {

        if(currentPiece == null) {
            System.err.println("currentPiece == null !");
            return;
        }

        root.getChildren().remove(blocks);
        blocks = new Group();

        int[][] piece = currentPiece.getPiece();

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                Rectangle r = new Rectangle(LX + (xCor +j) * BLOCK_SIZE , LY + (yCor +i) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                if(piece[i][j] == 1) {
                    r.setFill(col[BlokusClient.playerId]);
                    r.setStroke(Color.BLACK);
                    blocks.getChildren().add(r);
                }
            }
        }

        root.getChildren().add(blocks);
    }

    static void updateComObj() {
        // boardの更新
        int[][] piece = currentPiece.getPiece();

        debugCheckPiece(piece);

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                int X = xCor +j;
                int Y = yCor +i;
                if(Y >= 0 && Y < BOARD_SIZE && X >= 0 && X < BOARD_SIZE && piece[i][j] == 1) {
                    BlokusClient.comObj.board[Y][X] = boardNum[BlokusClient.playerId];
                }
            }
        }

        // usedPieceの更新
        BlokusClient.comObj.usedPiece[BlokusClient.playerId][currentPiece.id] = true;
    }

    static Boolean isPlaceable(){
        boolean noDuplicates = true; // 重複するピースが存在しない
        boolean existInACorner = false; // (4,4),(9,9)のいずれかを埋める
        boolean touchingAtTheCorner = false; // 同じ色のピースと角で接する
        boolean noPieceOnEdge = true; // 同じ色のピースと辺で接しない

        int[][] piece = currentPiece.getPiece();

        for(int i=0;i<PIECE_SIZE;i++) {
            for(int j=0;j<PIECE_SIZE;j++) {
                int X = xCor +j;
                int Y = yCor +i;
                if(Y >= 0 && Y < BOARD_SIZE && X >= 0 && X < BOARD_SIZE) {
                    // 重複排除
                    if(piece[i][j] == 1 && BlokusClient.comObj.board[Y][X] != 0) noDuplicates = false;
                    // (4,4),(9,9) or 角
                    if(piece[i][j] == 1 && ((Y==4 && X==4) || (Y==9 && X==9))) existInACorner = true;
                    if(piece[i][j] == 3 && BlokusClient.comObj.board[Y][X] == boardNum[BlokusClient.playerId]) touchingAtTheCorner = true;
                    // 辺
                    if(piece[i][j] == 2 && BlokusClient.comObj.board[Y][X] == boardNum[BlokusClient.playerId]) noPieceOnEdge = false;
                } else {
                    if(piece[i][j] == 1) noDuplicates = false;
                }
            }
        }

        return noDuplicates && (existInACorner || touchingAtTheCorner) && noPieceOnEdge;
    }

    private void upAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        if(yCor <= MIN_COR) return;
        yCor -= 1;
        changePos();
    }

    private void downAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        if(yCor >= MAX_COR) return;
        yCor += 1;
        changePos();
    }

    private void rightAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        if(xCor >= MAX_COR) return;
        xCor += 1;
        changePos();
    }

    private void leftAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        if(xCor <= MIN_COR) return;
        xCor -= 1;
        changePos();
    }

    private void spinAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        currentPiece.spinPiece();
        changePos();
    }

    private void reverseAction() {
        if(comObj.usedPiece[playerId][currentPiece.id]) return;
        currentPiece.reversePiece();
        changePos();
    }

    private void confirmAction() {
        if(!isPlaceable()) return;
        updateComObj();
        BlokusClient.sendComObj();
    }

    static void debugCheckPiece(int[][] piece) {
        System.err.println("Xpos: " + xCor + ", Ypos: " + yCor);
        for(int i = 0; i < PIECE_SIZE; i++) {
            for(int j = 0; j < PIECE_SIZE; j++) {
                System.err.print(piece[i][j] + " ");
            }
            System.err.println();
        }
    }
}
