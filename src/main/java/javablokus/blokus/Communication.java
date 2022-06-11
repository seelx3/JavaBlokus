package javablokus.blokus;

import java.util.Arrays;

public class Communication {
    public String[] players;
    public int turn;
    public int[][] board;
    public boolean giveup;
    public boolean finished;
    public String whowon;

    public boolean[][] availablePieces;

    @Override
    public String toString() {
        String boardStat = "";
        for (int i = 0; i < 14 + 6; i++) {
            boardStat += Arrays.toString(board[i]) + "\n";
        }
        return "{\nplayers: " + Arrays.deepToString(players) + "\nturn: " + turn + "\nboard:\n" + boardStat +
                "giveup: " + giveup + "\nfinished: " + finished + "\nwhowon: " + whowon + "\navailablePieces: " +
                Arrays.deepToString(availablePieces) + "\n}\n";
    }
}
