package Controllers;

/*
 *   This class contains the board representation in memory, and handles the logic, such as checking if someone won, and placing a piece.
 */

public class BoardLogic {
    private final int BOARD_WIDTH = 7;
    private final int BOARD_HEIGHT = 6;

    private int[][] boardMap;
    private int pieces;

    public BoardLogic() {
        boardMap = new int[BOARD_HEIGHT][BOARD_WIDTH];
    }

    private void set(int x, int y, int i) {
        boardMap[y][x] = i;
    }

    public int get(int x, int y) {
        return boardMap[y][x];
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            System.out.print(" /"+ (i + 1) + "\\");
        }

        System.out.println();

        System.out.println("| - - - - - - - - - - - - - |");

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            System.out.print("| ");
            for (int x = 0; x < BOARD_WIDTH; x++) {
                System.out.print(get(x, y) + " | ");
            }

            System.out.println();
        }
    }

    public int place(int x, int player) {
        for (int y = BOARD_HEIGHT - 1; y != -1; y--) {
            if (get(x, y) == 0) {
                set(x, y, player);
                pieces++;

                return y + 1;
            }
        }

        return -1;
    }

    /*
     * -1: no win
     * keep playing
     *
     * 0: board is full
     * 1: player 1 win
     * 2: player 2 win
     */
    public int checkWin() {
        int[] seq = { 0, 0, 0, 0 };

        // check if board full.
        if (pieces == BOARD_WIDTH * BOARD_HEIGHT) {
            return 0;
        }

        // check horizontal four
        // loop through every y
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            // every row has 4 possibilities
            for (int k = 0; k < 4; k++) {
                for (int x = 0; x < 4; x++) {
                    seq[x] = get(k + x, y);
                }

                int won = checkSequence(seq);

                if (won != 0) {
                    return won;
                }
            }
        }

        // check vertical four
        // loop through every x
        for (int y = 0; y < BOARD_WIDTH; y++) {
            // every column has 3 possibilities
            for (int col = 0; col < 3; col++) {
                for (int k = 0; k < 4; k++) {
                    seq[k] = get(y, col + k);
                }

                int won = checkSequence(seq);

                if (won != 0) {
                    return won;
                }
            }
        }

        // check diagonal left to right
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++) {
                int begX = x;
                int begY = y;

                for (int k = 0; k < 4; k++) {
                    seq[k] = get(begX++, begY++);
                }

                int won = checkSequence(seq);

                if (won != 0) {
                    return won;
                }
            }
        }

        // check diagonal right to left
        for (int y = 5; y != 2; y--) {
            for (int x = 0; x < 4; x++) {
                int begY = y;
                int begX = x;

                for (int k = 0; k < seq.length; k++) {
                    seq[k] = get(begX++, begY--);
                }

                int won = checkSequence(seq);

                if (won != 0) {
                    return won;
                }
            }
        }

        return -1;
    }

    /*
     * Function checks if all 4 ints in the array are equal, but not equal to 0.
     */
    private int checkSequence(int[] seq) {
        // TODO: Make his better with a for loop
        if (seq[0] != 0) {
            if (seq[0] == seq[1]) {
                if (seq[1] == seq[2]) {
                    if (seq[2] == seq[3]) {
                        // return player who won
                        return seq[0];
                    }
                }
            }
        }

        return 0;
    }
}
