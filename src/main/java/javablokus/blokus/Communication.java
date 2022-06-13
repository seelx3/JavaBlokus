package javablokus.blokus;

import java.util.Arrays;

public class Communication {
    public String[] players;
    public int turn; // 0 or 1
    public int[][] board;
    public boolean giveup;
    public boolean finished;
    public String whowon;

    public boolean[][] usedPiece;

    @Override
    public String toString() {
        StringBuilder boardStat = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            boardStat.append(Arrays.toString(board[i])).append("\n");
        }
        return "{\nplayers: " + Arrays.deepToString(players) + "\nturn: " + turn + "\nboard:\n" + boardStat +
                "giveup: " + giveup + "\nfinished: " + finished + "\nwhowon: " + whowon + "\nusedPiece: " +
                Arrays.deepToString(usedPiece) + "\n}\n";
    }
}
