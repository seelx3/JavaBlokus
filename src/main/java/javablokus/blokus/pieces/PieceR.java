package javablokus.blokus.pieces;

public class PieceR extends AbstractPiece{
    public PieceR() {
        super(
                17,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 3, 2, 2, 3, 0, 0},
                {0, 2, 1, 1, 2, 3, 0},
                {0, 3, 2, 1, 1, 2, 0},
                {0, 0, 3, 2, 1, 2, 0},
                {0, 0, 0, 3, 2, 3, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
