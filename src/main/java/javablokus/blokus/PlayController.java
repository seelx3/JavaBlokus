package javablokus.blokus;

import javablokus.blokus.pieces.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static javablokus.blokus.JavaBlokus.stg;

public class PlayController {
    private static final double BLOCK_SIZE = 30;
    private static final int PIECE_SIZE = 7;
    private static final int BOARD_SIZE = 14;
    private static final int LX = 187;
    private static final int LY = 53;
    private static final Color col[] = {Color.DEEPSKYBLUE, Color.CRIMSON};
    private static final int boardNum[] = {2, 3};
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int MIN_COR = -3;
    private static final int MAX_COR = 10;

    static int xCor; // ピースの左上のマスのX座標に対応 0 <= xCor <= 13
    static int yCor; // ピースの左上のマスのY座標に対応 0 <= yCor <= 13
    static AbstractPiece currentPiece;
    static Group root, blocks;

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
    @FXML  protected void onAButtonClick(ActionEvent ev) { currentPiece = new PieceA(); setPos(); }
    @FXML  protected void onBButtonClick(ActionEvent ev) { currentPiece = new PieceB(); setPos(); }
    @FXML  protected void onCButtonClick(ActionEvent ev) { currentPiece = new PieceC(); setPos(); }
    @FXML  protected void onDButtonClick(ActionEvent ev) { currentPiece = new PieceD(); setPos(); }
    @FXML  protected void onEButtonClick(ActionEvent ev) { currentPiece = new PieceE(); setPos(); }
    @FXML  protected void onFButtonClick(ActionEvent ev) { currentPiece = new PieceF(); setPos(); }
    @FXML  protected void onGButtonClick(ActionEvent ev) { currentPiece = new PieceG(); setPos(); }
    @FXML  protected void onHButtonClick(ActionEvent ev) { currentPiece = new PieceH(); setPos(); }
    @FXML  protected void onIButtonClick(ActionEvent ev) { currentPiece = new PieceI(); setPos(); }
    @FXML  protected void onJButtonClick(ActionEvent ev) { currentPiece = new PieceJ(); setPos(); }
    @FXML  protected void onKButtonClick(ActionEvent ev) { currentPiece = new PieceK(); setPos(); }
    @FXML  protected void onLButtonClick(ActionEvent ev) { currentPiece = new PieceL(); setPos(); }
    @FXML  protected void onMButtonClick(ActionEvent ev) { currentPiece = new PieceM(); setPos(); }
    @FXML  protected void onNButtonClick(ActionEvent ev) { currentPiece = new PieceN(); setPos(); }
    @FXML  protected void onOButtonClick(ActionEvent ev) { currentPiece = new PieceO(); setPos(); }
    @FXML  protected void onPButtonClick(ActionEvent ev) { currentPiece = new PieceP(); setPos(); }
    @FXML  protected void onQButtonClick(ActionEvent ev) { currentPiece = new PieceQ(); setPos(); }
    @FXML  protected void onRButtonClick(ActionEvent ev) { currentPiece = new PieceR(); setPos(); }
    @FXML  protected void onSButtonClick(ActionEvent ev) { currentPiece = new PieceS(); setPos(); }
    @FXML  protected void onTButtonClick(ActionEvent ev) { currentPiece = new PieceT(); setPos(); }
    @FXML  protected void onUButtonClick(ActionEvent ev) { currentPiece = new PieceU(); setPos(); }

    @FXML
    protected void DownClick(ActionEvent ev) {
        if(yCor >= MAX_COR) return;
        yCor += 1;
        changePos();
    }

    @FXML
    protected void UpClick(ActionEvent ev) {
        if(yCor <= MIN_COR) return;
        yCor -= 1;
        changePos();
    }

    @FXML
    protected void LeftClick(ActionEvent ev) {
        if(xCor <= MIN_COR) return;
        xCor -= 1;
        changePos();
    }

    @FXML
    protected void RightClick(ActionEvent ev) {
        if(xCor >= MAX_COR) return;
        xCor += 1;
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
        BlokusClient.comObj.giveup = true;
        BlokusClient.sendComObj();
    }

    @FXML
    void onConfirmClick(ActionEvent ev) {
        if(!isPlaceable()) return;
        updateComObj();
        BlokusClient.sendComObj();
    }

    @FXML
    void onGoToTitleButton(ActionEvent ev) {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaBlokus.class.getResource("start-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
            stg.setScene(scene);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        stg.show();
    }

    void setPos() {
        // ボード上の(7, 7)に対応
        xCor = 4;
        yCor = 4;

        root = new Group();
        blocks = new Group();
        root.getChildren().add(BlokusClient.fxmlnode);

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

        root.getChildren().add(BlokusClient.asgnedBlocks);
        root.getChildren().add(blocks);
        Scene scn = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stg.setScene(scn);
    }

    void changePos() {

        if(currentPiece == null) {
            System.err.println("currentPiece == null !");
            return;
        }
        root = new Group();
        blocks = new Group();
        root.getChildren().add(BlokusClient.fxmlnode);

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

        root.getChildren().add(BlokusClient.asgnedBlocks);
        root.getChildren().add(blocks);
        Scene scn = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        playerName.setText(BlokusClient.comObj.players[BlokusClient.comObj.turn]);
        stg.setScene(scn);
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
        Boolean noDuplicates = true; // 重複するピースが存在しない
        Boolean existInACorner = false; // (4,4),(9,9)のいずれかを埋める
        Boolean touchingAtTheCorner = false; // 同じ色のピースと角で接する
        Boolean noPieceOnEdge = true; // 同じ色のピースと辺で接しない

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

        if(noDuplicates && (existInACorner || touchingAtTheCorner) && noPieceOnEdge) return true;
        else return false;
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
