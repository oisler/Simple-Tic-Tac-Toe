package tictactoe.engine;

import tictactoe.common.TicTacToeConstants;

public final class Engine {

    public Result isGameFinished(char[][] grid) {
        if (hasImpossibleState(grid)) {
            return new Result(true, TicTacToeConstants.IMPOSSIBLE);
        }

        boolean playerOneIsTheWinner = isPlayerTheWinner(grid, TicTacToeConstants.PLAYER_ONE);
        boolean playerTwoIsTheWinner = isPlayerTheWinner(grid, TicTacToeConstants.PLAYER_TWO);
        if (playerOneIsTheWinner && playerTwoIsTheWinner) {
            return new Result(true, TicTacToeConstants.IMPOSSIBLE);
        } else if (playerOneIsTheWinner) {
            return new Result(true, TicTacToeConstants.PLAYER_ONE + " wins");
        } else if (playerTwoIsTheWinner) {
            return new Result(true, TicTacToeConstants.PLAYER_TWO + " wins");
        } else if (hasEmptyCells(grid)) {
            return new Result(false, TicTacToeConstants.GAME_NOT_FINISHED);
        } else {
            return new Result(true, TicTacToeConstants.DRAW);
        }
    }

    /**
     * Checks if a player is a winner.
     * A player is a winner if he has occupied three cells in either a horizontal, a vertical or a diagonal row.
     *
     * @param grid   - the field of the game.
     * @param symbol - the symbol of a player.
     * @return true, if a player has occupied three cells in either a horizontal, a vertical or a diagonal row.
     */
    private static boolean isPlayerTheWinner(char[][] grid, char symbol) {
        return hasThreeInAHorizontalRow(grid, symbol) || hasThreeInAVerticalRow(grid, symbol) || hasThreeInADiagonalRow(grid, symbol);
    }

    /**
     * Checks if grid is in an impossible state or not.
     * A state is impossible if the amount of occupied cells per player is greater than one or both win.
     *
     * @param grid - the field of the game.
     * @return true, if the amount of occupied cells per player is greater than one or both win.
     */
    private static boolean hasImpossibleState(char[][] grid) {
        int countPlayerOne = TicTacToeConstants.ZERO;
        int countPlayerTwo = TicTacToeConstants.ZERO;

        for (char[] row : grid) {
            for (char symbol : row) {
                if (symbol == TicTacToeConstants.PLAYER_ONE) {
                    countPlayerOne++;
                }
                if (symbol == TicTacToeConstants.PLAYER_TWO) {
                    countPlayerTwo++;
                }
            }
        }

        int difference = countPlayerOne - countPlayerTwo;
        return Math.abs(difference) > TicTacToeConstants.ONE;
    }

    /**
     * Checks if a player has three symbols in a diagonal row.
     *
     * @param grid   - the field of the game.
     * @param player - the symbol of player one or player two.
     * @return true, if a player has occupied three cells in a diagonal row.
     */
    private static boolean hasThreeInADiagonalRow(char[][] grid, char player) {
        boolean a = grid[TicTacToeConstants.ZERO][TicTacToeConstants.ZERO] == player
                && grid[TicTacToeConstants.ONE][TicTacToeConstants.ONE] == player
                && grid[TicTacToeConstants.TWO][TicTacToeConstants.TWO] == player;

        boolean b = grid[TicTacToeConstants.ZERO][TicTacToeConstants.TWO] == player
                && grid[TicTacToeConstants.ONE][TicTacToeConstants.ONE] == player
                && grid[TicTacToeConstants.TWO][TicTacToeConstants.ZERO] == player;

        return a || b;
    }

    /**
     * Checks if a player has three symbols in a vertical row.
     *
     * @param grid   - the field of the game.
     * @param player - the symbol of player one or player two.
     * @return true, if a player has occupied three cells in a vertical row.
     */
    private static boolean hasThreeInAVerticalRow(char[][] grid, char player) {
        boolean hasThreeInARow = false;

        for (int index = TicTacToeConstants.ZERO; index < grid.length; index++) {
            int equalityCounter = TicTacToeConstants.ZERO;
            for (char[] symbol : grid) {
                equalityCounter += symbol[index] == player ? TicTacToeConstants.ONE : TicTacToeConstants.ZERO;
            }
            if (equalityCounter == TicTacToeConstants.THREE) {
                hasThreeInARow = true;
                break;
            }
        }
        return hasThreeInARow;
    }

    /**
     * Checks if a player has three symbols in a horizontal row.
     *
     * @param grid   - the field of the game.
     * @param player - the symbol of player one or player two.
     * @return true, if a player has occupied three cells in a horizontal row.
     */
    private static boolean hasThreeInAHorizontalRow(char[][] grid, char player) {
        boolean hasThreeInARow = false;

        for (char[] row : grid) {
            int equalityCounter = TicTacToeConstants.ZERO;
            for (char symbol : row) {
                equalityCounter += symbol == player ? TicTacToeConstants.ONE : TicTacToeConstants.ZERO;
            }
            if (equalityCounter == TicTacToeConstants.THREE) {
                hasThreeInARow = true;
                break;
            }
        }
        return hasThreeInARow;
    }

    /**
     * Checks if a field still has empty cells.
     *
     * @param grid - the field of the game;
     * @return true, if there is at least one empty cell.
     */
    private static boolean hasEmptyCells(final char[][] grid) {
        int emptyCellCounter = TicTacToeConstants.ZERO;

        for (char[] row : grid) {
            for (char symbol : row) {
                emptyCellCounter += symbol == TicTacToeConstants.EMPTY_CELL ? TicTacToeConstants.ONE : TicTacToeConstants.ZERO;
            }
        }

        return emptyCellCounter > TicTacToeConstants.ZERO;
    }

    public record Result(boolean finished, String status) {
    }
}