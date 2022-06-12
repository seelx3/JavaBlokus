package javablokus.blokus.pieces;

public class PieceL extends AbstractPiece{
    public PieceL() {
        super(
                11,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 0, 0},
                {0, 3, 2, 1, 2, 0, 0},
                {0, 2, 1, 1, 2, 0, 0},
                {0, 2, 1, 2, 3, 0, 0},
                {0, 3, 2, 3, 0, 0, 0}
        });
    }
}
