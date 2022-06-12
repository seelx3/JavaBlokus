package javablokus.blokus.pieces;

public class PieceO extends AbstractPiece{
    public PieceO() {
        super(
                14,
                new int[][]{
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
