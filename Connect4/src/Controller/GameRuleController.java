/*    
    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package connect4.Controller;

import connect4.Model.GameField;
import connect4.Model.GameState;
import manager.interfaces.Token;

/**
 * this class provides methods to check the rules of 
 * the game "4 Gewinnt".
 */
public class GameRuleController {

    /**
     * makes a new controller to check the game rules
     * @param field the field to check to
     */
    public GameRuleController(GameField field) {
        this.field = field;
    }
    private GameField field;

    /**
     * checks if the game is undecided.
     * @return true if undecided, else false
     */
    public boolean isDraw() {
        int width = field.getWidth();
        for (int currentColumn = 0; currentColumn < width; currentColumn++) {
            if (field.getFieldStatus(currentColumn, 0) == Token.EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks the game state and return it.
     * @return a enum <code> GameState </code>
     */
    public GameState getGameState(int row, int column, Token color) {
        if (won(row, column, color)) {
            return GameState.WON;
        }
        if (isDraw()) {
            return GameState.DRAW;
        } else {
            return GameState.PLAYING;
        }
    }

    /**
     * check if a move to a column could be make.
     * @param column the column to check
     * @return true if colmn is not full, else false
     */
    public boolean isLegalMove(int column) {
        return field.getFieldStatus(column, 0) == Token.EMPTY;
    }

    /**
     * Check if the game is won.
     * @return true if won, else false
     */
    public boolean won(int row, int column, Token color) {
        return fourInARow(row, column, color)
                || fourDiagonal(row, column, color)
                || fourInAColumn(row, column, color);
    }

    /**
     * checks if there are four stones in a row.
     *
     * @param row
     * @param column
     * @param color
     * @return
     */
    private boolean fourInARow(int row, int column, Token color) {
        int countColor = 0;
        // check in the given row from for to the left to four to the right
        for (int i = -4; i <= 4; i++) {

            if (field.getFieldStatus(column + i, row) == color) {
                countColor++;

            } else if (countColor < 4) {
                countColor = 0;
            }
        }

        if (countColor >= 4) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * checks if there are four stones in a column.
     *
     * @param row
     * @param column
     * @param color
     * @return
     */
    private boolean fourInAColumn(int row, int column, Token color) {
        int countColor = 0;
        // check in the given column from for to the left to four to the right
        for (int i = -4; i <= 4; i++) {

            if (field.getFieldStatus(column, row + i) == color) {
                countColor++;

            } else if (countColor < 4) {
                countColor = 0;
            }
        }

        if (countColor >= 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if there are four stones diagonally.
     *
     * @param row
     * @param column
     * @param color
     * @return
     */
    private boolean fourDiagonal(int row, int column, Token color) {
        int countColor = 0;
        // check diagonal going from lower left to upper right
        for (int i = -4; i <= 4; i++) {

            if (field.getFieldStatus(column + i, row + i) == color) {
                countColor++;
            } else if (countColor < 4) {
                countColor = 0;
            }


        }
        if (countColor >= 4) {
            return true;
        }

        countColor = 0;
        // check diagonal going from upp left to lower right
        for (int i = -4; i <= 4; i++) {

            if (field.getFieldStatus(column + i, row - i) == color) {
                countColor++;

            } else if (countColor < 4) {
                countColor = 0;
            }

        }

        if (countColor >= 4) {
            return true;
        }
        return false;
    }
}
