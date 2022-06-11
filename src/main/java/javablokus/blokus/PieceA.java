package javablokus.blokus;

public final class PieceA extends AbstractPiece {
    // 1 : ピースの実体
    // 2 : ピースが辺で隣接するマス
    // 3 : ピースが角で接するマス

    PieceA() {
        super(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 2, 1, 2, 0, 0},
                {0, 0, 3, 2, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        });
    }

}
