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

package Connect4.AI.Controller;

import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

import connect4.AI.Model.PossibleMove;

/**
 * This class contains general methods to check for the state of a field used
 * by more than one rating mechanism.
 * For example, it may check, whether or not a move leads to a given number
 * of stones in a row.
 */
public abstract class AbstractRatingMechanism {

    /**
     * finds the row in which the stone would go with the given field and move.
     * Returns -1 if the column is full.
     *
     * @param field the field to check on
     * @param move the move to make
     * @return the row which the stone where be placed
     */
    protected int findRowForColumn(ModelInterface field, PossibleMove move) {
        for (int i = 0; i < field.getHeight(); i++) {

            if (field.getFieldStatus(move.getMove(), i) != Token.EMPTY) {
                return i - 1;
            }
        }
        return field.getHeight() - 1;
    }

    /**
     * checks whether or not there are n connecting stones.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of connecting stones
     * @param field the field to count on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     * @param what determines if the method checks diagonally, vertically or horizontally
     * @return true, if n connecting stones are found, else false
     */
    private boolean nConnectedInAnX(int n, ModelInterface field, PossibleMove move, Token color, GetMyFieldStatus what) {
        if (n < 2 || n > 4) {
            return false;
        }

        // the row in which a stone would be placed with the given move
        int placedRow = findRowForColumn(field, move);

        if (placedRow < 0) {
            return false;
        }

        int countColor = 0;
        // check in the given row from n to the left to n to the right
        for (int i = -n; i <= n; i++) {

            if (what.getFieldStatusAt(i, field, placedRow, move.getMove()) == color) {
                countColor++;

            } else if (i == 0) {
                countColor++;

            } else if (countColor != n) {
                countColor = 0;
            }
            // done
            if (countColor == n) {
                break;
            }
        }

        return countColor >= n;
    }

    /**
     * this method checks if there are n stones in a line.
     * In this case, in a line means that x stones may be extended
     * to 4 stones in a row (either at the end, or because there are 
     * still stones missing in the middle).
     * @param n number of connecting stones
     * @param holes the number of allowed holes
     * @param field the field to count on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     * @param what determines if the method checks diagonally, vertically or horizontally
     * @return true, if n connecting stones are found, else false
     */
    private boolean nConnectedWithHolesInAnx(int n, int holes, ModelInterface field, PossibleMove move, Token color, GetMyFieldStatus what) {
       
        if (n < 2 || n > 4) {
            return false;
        }

        if (holes < 0 || holes > 2) {
            return false;
        }

        int countHoles = 0;
        // the row in which a stone would be placed with the given move
        int placedRow = findRowForColumn(field, move);

        if (placedRow < 0) {
            return false;
        }

        int countColor = 0;
        // check in the given row from n to the left to n to the right
        for (int i = -n; i <= n; i++) {
            Token currentColor = what.getFieldStatusAt(i, field, placedRow, move.getMove());

            if (currentColor == color) {
                countColor++;

            } else if (i == 0) {
                countColor++;

            } else if (currentColor == Token.EMPTY) {
                countHoles++;

            } else if (countColor != n) {
                countColor = 0;
                countHoles = 0;
            }

            if (countHoles > holes) {
                countColor = 0;
                countHoles = 0;
            }
            // done
            if (countColor + countHoles == n) {
                break;
            }
        }

        return countColor + countHoles >= n;
    }

    /**
     * checks whether or not there are n stones in a horizontal row.
     * In these case a row means the n stones are vertical connected.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of connected stones in a row
     * @param field the field to check on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return true if n stones are connected, else false
     */
    public boolean nConnecHorrizontal(int n, ModelInterface field, PossibleMove move, Token color) {

        return nConnectedInAnX(n, field, move, color, new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn + loopCounter, placedRow);
            }
        });


    }

    /**
     * checks whether or not there are n stones in a vertical row.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a column
     * @param field field the field to check on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return true if n stones are connected, else false
     */
    public boolean nConnectInVertical(int n, ModelInterface field, PossibleMove move, Token color) {

        return nConnectedInAnX(n, field, move, color, new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn, placedRow + loopCounter);
            }
        });
    }

    /**
     * checks whether or not there are n stones in a diagonal row.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well.
     *
     * @param n number of stones in a diagonal
     * @param field the field to check on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     *
     * @return true if n stones are connected, else false
     */
    public boolean nConnectedDiagonal(int n, ModelInterface field, PossibleMove move, Token color) {

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

        return nConnectedInAnX(n, field, move, color, upperLeftToLowerRight)
                || nConnectedInAnX(n, field, move, color, lowerLeftToUpperRight);
    }

    /** checks whether or not there are n stones in a diagonal row.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well. The number of holes will be counted as well.
     * If this number is greater than 3 or lower than 0 false will be returned.
     *
     * @param n number of stones in a diagonal
     * @param holes the number of allowed holes in a line
     * @param field the field to check on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     * @return true if n stones are connected, else false
     */
    public boolean nConnectedDiagonalWithHoles(int n, int holes, ModelInterface field, PossibleMove move, Token Color) {
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

        return nConnectedWithHolesInAnx(n, holes, field, move, Color, upperLeftToLowerRight)
                || nConnectedWithHolesInAnx(n, holes, field, move, Color, lowerLeftToUpperRight);
    }
    
     /** checks whether or not there are n stones in a vertical row.
     * If n is smaller than 2 or bigger than 4, false will be returned.
     * If the given move is not possible because the column is full, false
     * will be returned as well. The number of holes will be counted as well.
     * If this number is greater than 3 or lower than 0 false will be returned.
     *
     * @param n number of stones in a diagonal
     * @param holes the number of allowed holes in a line
     * @param field the field to check on
     * @param move move to apply to field before checking (move will not be applied permanently)
     * @param color the color of the stones to check for
     * @return true if n stones are connected, else false
     */
    public boolean nConncetedVerticalWithHoles(int n, int holes, ModelInterface field, PossibleMove move, Token color) {
        return nConnectedWithHolesInAnx(n, holes, field, move, color, new GetMyFieldStatus() {

            @Override
            public Token getFieldStatusAt(int loopCounter, ModelInterface field, int placedRow, int placedColumn) {
                return field.getFieldStatus(placedColumn + loopCounter, placedRow);
            }
        });
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
