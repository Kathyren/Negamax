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
package connect4.AI.Controller.Deprecated;

import Connect4.AI.Controller.IRatingMechanism;
import connect4.AI.Model.PossibleMove;
import connect4.AI.Model.Rating;
import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

/**
 * This mechanism returns a high negative (<code>BLOCK</code>)
 * rating if the move of the ki would result in creating a winning
 * possibility for the enemy.
 * WARNING: do not use. Not working!
 */
// TODO when (or rather if...) minimax is working, delete this class
public class MDontCreateWin implements IRatingMechanism {

    @Override
    public int getValue() {
        return -Rating.BLOCK;
    }

    @Override
    public boolean isFullfilled(final PossibleMove move, final ModelInterface model, Token color) {

        Token enemyColor = Token.EMPTY;
        if (color == Token.BLUE) {
            enemyColor = Token.RED;
        } else {
            enemyColor = Token.BLUE;
        }

        // check enemy moves in same column as move for win:
        boolean couldWin = false;

        if (nInARow(4, model, move, enemyColor, move) ||
                    nInAColumn(4, model, move, enemyColor, move)||
                    nDiagonal(4, model, move, enemyColor, move)) {
                couldWin = true;
        }

        // debug
        if (couldWin) {
        System.out.println(move.getMove() + " creates winning possibility for enemy!!!!!!!!!!!!");
        }
        return couldWin;
    }



























    /**
     * finds the row in which the stone would go with the given field and move.
     * Returns -1 if the column is full.
     *
     * @param field field
     * @param move move
     * @return row row
     */
    private int findRowForColumn(ModelInterface field, PossibleMove moveEnemy, PossibleMove moveKI) {
        int returnValue;

        for (int i = 0; i < field.getHeight(); i++) {

            if (field.getFieldStatus(moveEnemy.getMove(), i) != Token.EMPTY) {
                returnValue = i - 1;
                // if the ki move would go here, subtract one to simulate that it actually performed that move
                if (i == moveKI.getMove()) {
                    returnValue--;
                }
                return returnValue;
            }
        }
        returnValue = field.getHeight() - 1;
        if (field.getHeight() - 1 == moveKI.getMove()) {
            returnValue--;
        }
        return returnValue;
    }

    /**
     * checks whether or not there are n connecting stones.

     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a row
     * @param field field
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     * @param what determines if the method checks diagonally, vertically or horizontally
     *
     * @return are there n stones in an X?
     */
    private boolean nInAnX(int n, ModelInterface field, PossibleMove move, Token color, GetMyFieldStatus what, PossibleMove moveKI) {
        if (n < 2 || n > 4) {
            return false;
        }

        // the row in which a stone would be placed with the given move
        int placedRow = findRowForColumn(field, move, moveKI);

        if (placedRow < 0) {
            return false;
        }

        int countColor = 0;
        // check in the given row from n to the left to n to the right
        for (int i = 0; i < field.getWidth(); i++) {

            if (what.getFieldStatusAt(i, field, placedRow, move.getMove()) == color) {
                countColor++;
            } else if (i == 0) {
                countColor++;
            } else if (countColor != n) {

                // is it enough to remove this line?
                countColor = 0;
            }
            // done
            if (countColor == n) {
                break;
            }
        }

        if (countColor >= n) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks whether or not there are n stones in a vertical row.

     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a row
     * @param field
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return
     */
    public boolean nInARow(int n, ModelInterface field, PossibleMove move, Token color, PossibleMove moveKI) {

            return nInAnX(n, field, move, color, new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn + loopCounter, placedRow);
            }
        }, moveKI);


    }

    /**
     * checks whether or not there are n stones in a horizontal row.

     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a row
     * @param field
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return
     */
    public boolean nInAColumn(int n, ModelInterface field, PossibleMove move, Token color, PossibleMove moveKI) {

            return nInAnX(n, field, move, color, new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn, placedRow + loopCounter);
            }
        }, moveKI);
    }

    /**
     * checks whether or not there are n stones in a diagonal row.

     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a row
     * @param field
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return
     */
    public boolean nDiagonal(int n, ModelInterface field, PossibleMove move, Token color, PossibleMove moveKI) {

        GetMyFieldStatus upperLeftToLowerRight = new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn + loopCounter, placedRow + loopCounter);
            }
        };

        GetMyFieldStatus lowerLeftToUpperRight = new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn + loopCounter, placedRow - loopCounter);
            }
        };

        return nInAnX(n, field, move, color, upperLeftToLowerRight, moveKI) ||
                nInAnX(n, field, move, color, lowerLeftToUpperRight, moveKI);

    }

    /**
     * This interface is used to avoid copy-pasting for the methods above.
     */
    private interface GetMyFieldStatus {

        /**
         * returns the relevant field status depending on the currently passed
         * loopCounter as well as the placed row and column.
         *
         * @param loopCounter
         * @param field
         * @return
         */
        Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn);
    }

}
