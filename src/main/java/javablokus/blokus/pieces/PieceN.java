package javablokus.blokus.pieces;

public class PieceN extends AbstractPiece{
    public PieceN() {
        super(
                13,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 3, 2, 2, 3, 0, 0},
                {0, 2, 1, 1, 2, 0, 0},
                {0, 3, 2, 1, 2, 0, 0},
                {0, 2, 1, 1, 2, 0, 0},
                {0, 3, 2, 2, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
