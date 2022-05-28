package javablokus.blokus;

import java.util.Arrays;

public class Communication {
    public static int turn;
    public static int[][] board;
    public static boolean giveup;
    public static boolean finished;
    public static String whowon;

    public static void setCom(int tn, int[][] bd, boolean gu, boolean fin, String ww){
        turn = tn;
        board = bd;
        giveup = gu;
        finished = fin;
        whowon = ww;
    }

    @Override
    public String toString() {
        return "{\nturn: " + turn + "\nboard: " + Arrays.deepToString(board) +
                "\ngiveup: " + giveup + "\nfinished: " + finished + "\nwhowon: " + whowon + "\n}\n";
    }
}
