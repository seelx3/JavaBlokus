package javablokus.blokus.pieces;

public class PieceQ extends AbstractPiece{
    public PieceQ() {
        super(
                16,
                new int[][]{
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 0, 0},
                {0, 0, 2, 1, 2, 2, 3},
                {0, 0, 2, 1, 1, 1, 2},
                {0, 0, 3, 2, 2, 2, 3},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
