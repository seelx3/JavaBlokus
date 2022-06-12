package javablokus.blokus.pieces;

public class PieceU extends AbstractPiece{
    public PieceU() {
        super(
                20,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 3, 2, 1, 2, 3, 0},
                {0, 2, 1, 1, 1, 2, 0},
                {0, 3, 2, 1, 2, 3, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
