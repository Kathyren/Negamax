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

import connect4.AI.Model.PossibleMove;
import connect4.AI.Model.Rating;
import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

/**
 * This class checks,if the move will lead to two connecting stones.
 * If it does so, it will add a <code>VERYLOW</code> rating for two connecting 
 * stones vertical as well as a <code>LOW</code> rating for two stones 
 * horizontally as well as a <code>LOW</code> rating for two stones diagonally.
 * A move do only rate good, if it is enough space in horizontal,vertical
 * or diagonal to win.
 * <p>
 * The <code>isFullfilled</code> method calculates the value, so it must be called
 * before getValue().
 */
public class MTwoStonesWithSpaceChecking extends AbstractRatingMechanism implements IRatingMechanism {

    private int value = 0;

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        boolean result = false;

        if (super.nConnecHorrizontal(2, model, move, color)) {
            Token row[] = getCompleteRow(model, super.findRowForColumn(model, move));

            if (testIfSpace(row, move, color)) {
                result = true;
                value += Rating.LOW;
            }
        }

        if (super.nConnectedDiagonal(2, model, move, color)) {
            Token RLDiagonal[] = getRightLeftDiagonal(model, move.getMove(),
                    super.findRowForColumn(model, move));
            Token LRDiagonal[] = getLeftRightDiagonal(model, move.getMove(),
                    super.findRowForColumn(model, move));

            if (testIfSpace(RLDiagonal, move, color)
                    || testIfSpace(LRDiagonal, move, color)) {
                result = true;
                value += Rating.LOW;
            }
        }

        if (super.nConnectInVertical(2, model, move, color)) {
            Token column[] = getCompleteColumn(model, move.getMove());

            if (testIfSpace(column, move, color)) {
                result = true;
                value += Rating.VERYLOW;
            }
        }
        return result;
    }

    /**
     * test if enough space is in the given array to win in future.
     * @param array the array to test on
     * @param move the player move
     * @param color the player color
     * @return true if enouh space, else false
     */
    private boolean testIfSpace(Token[] array, PossibleMove move, Token color) {

        boolean isOneSpaceToRight = isSpace(move.getMove(), 1, array, color);
        boolean isOneSpaceToLeft = isSpace(move.getMove() - 1, 1, array, color);
        boolean isTwoSpaceToRight = isSpace(move.getMove() + 1, 2, array, color);
        boolean isTwoSpaceToLeft = isSpace(move.getMove() - 2, 2, array, color);
        boolean isThreeSpaceToRight = isSpace(move.getMove() + 1, 3, array, color);
        boolean isThreeSpaceToLeft = isSpace(move.getMove() - 3, 3, array, color);

        return (isOneSpaceToRight && isTwoSpaceToLeft)
                || (isOneSpaceToLeft && isTwoSpaceToRight)
                || isThreeSpaceToRight || isThreeSpaceToLeft;
    }

    /**
     * checks if in the given array from the startingpoint to the number of wide
     * are only the given token or empty token.
     * @param startingPoint the point to start checking from
     * @param wide the wide to check to
     * @param array the array to check
     * @param color the player color
     * @return true if only player token or empty token is in the interval
     */
    private boolean isSpace(int startingPoint, int wide, Token[] array, Token color) {
        int space = 0;
        for (int i = startingPoint; i < startingPoint + wide; i++) {
            try {
                if (array[i] != null && (array[i] == Token.EMPTY || array[i] == color)) {
                    space++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return wide == space;
    }

    /**
     * returns a complete row of the given model as an array.
     * @param model the model to get the row from
     * @param currentRow the wished row
     * @return an array of <code> Token</code> from the row
     */
    private Token[] getCompleteRow(ModelInterface model, int currentRow) {
        Token row[] = new Token[6];
        for (int i = 0; i < 6; i++) {
            row[i] = model.getFieldStatus(i, currentRow);
        }
        return row;
    }

    /**
     * returns a complete column of the given model as an array.
     * @param model the model to get the row from
     * @param currentColumn the wished column
     * @return an array of <code> Token</code> from the column
     */
    private Token[] getCompleteColumn(ModelInterface model, int currentColumn) {
        Token column[] = new Token[6];
        for (int i = 0; i < 6; i++) {
            column[i] = model.getFieldStatus(currentColumn, i);
        }
        return column;
    }

    /**
     * returns an array of the diagonl, which goes from
     * upper left to lower right
     * @param model the model to geht the diagonal from
     * @param currentColumn the wished colum
     * @param currentRow the wished row
     * @return an array of <code>Token</code>
     */
    private Token[] getLeftRightDiagonal(ModelInterface model,
            int currentColumn, int currentRow) {
        Token diagonal[] = new Token[7];
        for (int i = 0; i <= 3; i++) {
            int scanningColumn = currentColumn - i;
            int scanningRow = currentRow - i;
            if (scanningColumn < 0 || scanningRow < 0) {
                diagonal[i] = null;
            } else {
                diagonal[i] = model.getFieldStatus(scanningColumn, scanningRow);

            }
        }

        for (int i = 0; i <= 3; i++) {
            int scanningColumn = currentColumn + i;
            int scanningRow = currentRow + i;
            if (scanningColumn > model.getWidth() - 1
                    || scanningRow > model.getHeight() - 1) {
                diagonal[i + 3] = null;
            } else {
                diagonal[i + 3] = model.getFieldStatus(scanningColumn, scanningRow);

            }
        }
        return diagonal;
    }

    /**
     * returns an array of the diagonl, which goes from
     * upper right to lower left
     * @param model the model to geht the diagonal from
     * @param currentColumn the wished colum
     * @param currentRow the wished row
     * @return an array of <code>Token</code>
     */
    private Token[] getRightLeftDiagonal(ModelInterface model,
            int currentColumn, int currentRow) {
        Token diagonal[] = new Token[7];
        for (int i = 0; i <= 3; i++) {
            int scanningColumn = currentColumn + i;
            int scanningRow = currentRow - i;
            if (scanningColumn < 0 || scanningRow < 0) {
                diagonal[i] = null;
            } else {
                diagonal[i] = model.getFieldStatus(scanningColumn, scanningRow);

            }
        }
        for (int i = 0; i <= 3; i++) {
            int scanningColumn = currentColumn - i;
            int scanningRow = currentRow + i;
            if (scanningColumn > model.getWidth() - 1
                    || scanningRow > model.getHeight() - 1) {
                diagonal[i + 3] = null;
            } else {
                diagonal[i + 3] = model.getFieldStatus(scanningColumn, scanningRow);

            }
        }
        return diagonal;
    }

    @Override
    public int getValue() {
        int returnValue = value;
        value = 0; // reset value
        return returnValue;
    }
}
