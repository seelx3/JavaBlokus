package javablokus.blokus.pieces;

public class PieceG extends AbstractPiece{
    public PieceG() {
        super(
                6,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 3, 0},
                {0, 0, 2, 1, 1, 2, 0},
                {0, 0, 2, 1, 2, 3, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
