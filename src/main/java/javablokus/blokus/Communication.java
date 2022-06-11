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
        String boardStat = "";
        for (int i = 0; i < 14; i++) {
            boardStat += Arrays.toString(board[i]) + "\n";
        }
        return "{\nplayers: " + Arrays.deepToString(players) + "\nturn: " + turn + "\nboard:\n" + boardStat +
                "giveup: " + giveup + "\nfinished: " + finished + "\nwhowon: " + whowon + "\nusedPiece: " +
                Arrays.deepToString(usedPiece) + "\n}\n";
    }
}
