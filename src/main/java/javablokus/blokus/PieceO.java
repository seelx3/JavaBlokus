package javablokus.blokus;

public class PieceO extends AbstractPiece{
    PieceO() {
        super(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 3, 0},
                {0, 0, 2, 1, 1, 2, 0},
                {0, 0, 2, 1, 2, 3, 0},
                {0, 0, 2, 1, 2, 0, 0},
                {0, 0, 3, 2, 3, 0, 0}
        });
    }
}
