package javablokus.blokus;

public class PieceP extends AbstractPiece{
    PieceP() {
        super(
                15,
                new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 0, 0},
                {0, 3, 2, 1, 2, 3, 0},
                {0, 2, 1, 1, 1, 2, 0},
                {0, 3, 2, 2, 2, 3, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }
}
