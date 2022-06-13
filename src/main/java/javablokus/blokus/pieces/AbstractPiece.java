package javablokus.blokus.pieces;

abstract public class AbstractPiece {
    private static final int PIECE_SIZE = 7;
    public int id;
    public int[][] piece;

    AbstractPiece(int pceid, int[][] pce) {
        id = pceid;
        piece = pce;
    }

    public int[][] getPiece() {
        return piece;
    }

    public void reversePiece() {
        int[][] tmp = new int[7][7];
        for(int i = 0; i < PIECE_SIZE; i++) {
            tmp[i] = piece[i].clone();
        }
        for(int i = 0; i < PIECE_SIZE; i++) {
            for (int j = 0; j < PIECE_SIZE; j++) {
                piece[i][j] = tmp[i][PIECE_SIZE-1-j];
            }
        }
    }

    public void spinPiece() {
        reversePiece();
        int[][] tmp = new int[7][7];
        for(int i = 0; i < PIECE_SIZE; i++) {
            tmp[i] = piece[i].clone();
        }
        for(int i = 0; i < PIECE_SIZE; i++) {
            for (int j = 0; j < PIECE_SIZE; j++) {
                piece[i][j] = tmp[j][i];
            }
        }
    }

    /* デバッグ用 プリント出力 */
//    void checkCurrentPiece(int[][] pce) {
//        for (int i = 0; i < PIECE_SIZE; i++) {
//            for (int j = 0; j < PIECE_SIZE; j++) {
//                System.out.print(pce[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
}
