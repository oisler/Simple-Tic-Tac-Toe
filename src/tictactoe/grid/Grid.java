package tictactoe.grid;

import tictactoe.common.TicTacToeConstants;
import tictactoe.engine.Engine;

import java.util.Scanner;

public final class Grid {

    private static final Scanner scanner = new Scanner(System.in);

    private final char[][] grid;
    private final Engine engine;

    public Grid() {
        this.grid = initEmptyGrid();
        this.engine = new Engine();
    }

    private static char[][] initEmptyGrid() {
        final char[][] grid = new char[TicTacToeConstants.THREE][TicTacToeConstants.THREE];

        for (int i = TicTacToeConstants.ZERO; i < grid.length; i++) {
            for (int j = TicTacToeConstants.ZERO; j < grid.length; j++) {
                grid[i][j] = TicTacToeConstants.EMPTY_CELL;
            }
        }

        return grid;
    }

    public void print() {
        System.out.println(TicTacToeConstants.HORIZONTAL_BORDER);
        for (char[] row : this.grid) {
            StringBuilder stringBuilder = new StringBuilder(TicTacToeConstants.VERTICAL_BORDER);
            for (int index = TicTacToeConstants.ZERO; index < this.grid.length; index++) {
                stringBuilder.append(String.format(" %s", row[index]));
            }
            stringBuilder.append(String.format(" %s", TicTacToeConstants.VERTICAL_BORDER));
            System.out.println(stringBuilder);
        }
        System.out.println(TicTacToeConstants.HORIZONTAL_BORDER);
    }

    public void run() {
        print();

        char player = TicTacToeConstants.PLAYER_TWO;
        Engine.Result result;
        do {
            String[] userInput;
            boolean isValidInput;
            do {
                userInput = scanner.nextLine().split(TicTacToeConstants.SPACE);
                isValidInput = isValidInput(userInput);
            } while (!isValidInput);
            int firstCoordinate = Integer.parseInt(userInput[TicTacToeConstants.ZERO]);
            int secondCoordinate = Integer.parseInt(userInput[TicTacToeConstants.ONE]);
            this.grid[--firstCoordinate][--secondCoordinate] = player;

            result = engine.isGameFinished(this.grid);
            player = changePlayer(player);

            print();
        } while (!result.finished());

        System.out.println(result.status());
    }

    private static char changePlayer(final char player) {
        return player == TicTacToeConstants.PLAYER_TWO ? TicTacToeConstants.PLAYER_ONE : TicTacToeConstants.PLAYER_TWO;
    }

    private boolean isValidInput(String[] s) {
        if (s.length != TicTacToeConstants.TWO) {
            System.out.println(TicTacToeConstants.NUMBER_FORMAT_ERROR);
            return false;
        }

        int firstNumber;
        int secondNumber;
        try {
            firstNumber = Integer.parseInt(s[TicTacToeConstants.ZERO]);
            secondNumber = Integer.parseInt(s[TicTacToeConstants.ONE]);
        } catch (NumberFormatException e) {
            System.out.println(TicTacToeConstants.NUMBER_FORMAT_ERROR);
            return false;
        }

        if ((firstNumber < TicTacToeConstants.ONE || firstNumber > TicTacToeConstants.THREE) ||
                (secondNumber < TicTacToeConstants.ONE || secondNumber > TicTacToeConstants.THREE)) {
            System.out.printf(TicTacToeConstants.COORDINATE_BOUNDARIES_ERROR, TicTacToeConstants.ONE, TicTacToeConstants.THREE);
            return false;
        }

        if (this.grid[(char) --firstNumber][(char) --secondNumber] != TicTacToeConstants.EMPTY_CELL) {
            System.out.println(TicTacToeConstants.OCCUPIED_CELL_ERROR);
            return false;
        }

        return true;
    }
}