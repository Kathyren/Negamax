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

package connect4.Model;

import java.io.Serializable;
import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

/**
 * this class provides a field of tokens to play "4 Gewinnt".
 */
public class GameField implements ModelInterface, Serializable {

    private int columns;
    private int rows;
    // save the last changins for the gui updating!
    private int lastUpdatedColumn;
    private int lastUpdatedRow;
    private Token[][] field;

    /**
     * constructs a new field and initiate it with <code> Token.Empty </code>
     * @param rows how many lines of tokens
     * @param columns how many columns of tokens
     */
    public GameField(int rows, int columns) {
        field = new Token[rows][columns];
        this.columns = columns;
        this.rows = rows;
        initField();
    }

    /**
     * returns the field at the specific position
     * If the specific position goes outside the array
     * a <code> Token.Empty </code> will be return.
     * The rows are numbered from top to bottom from 0 to hight - 1.
     * The columns are numbered from left to right from 0 to width - 1.
     *
     * @param column the column
     * @param row the row
     * @return the token
     */
    @Override
    public Token getFieldStatus(int column, int row) {
        if (column < 0 || column >= columns || row < 0 || row >= rows) {
            return Token.EMPTY;
        }
        return field[row][column];
    }

    /**
     * puts a token into the column of this field. It will be
     * placed in the lowest row where no other not empty token is
     * placed.
     * The <code>lastUpdatedColumn</code> and
     * <code>lastUpdatedColumn</code> will be updated.
     * When the column is full, nothing be happen.
     * If the given columns does not exist in the game, nothing will happen.
     * @param column the column where to put the token
     * @param tok  the token to place
     */
    @Override
    public void setFieldStatus(int column, Token tok) {
        if (column < 0 || column >= columns) {
            return;
        }
        boolean done = false;
        int emptyTokenRow = -1;
        int currentRow = 0;

        //find free place.
        while (!done) {
            if (field[currentRow][column] != Token.EMPTY) {
                done = true;
                emptyTokenRow = currentRow - 1;
            } else if (currentRow == rows - 1) {
                done = true;
                emptyTokenRow = rows - 1;
            }
            currentRow++;

        }
        //if free place is found, place it.
        if (emptyTokenRow != -1) {
            field[emptyTokenRow][column] = tok;
            lastUpdatedColumn = column;
            lastUpdatedRow = emptyTokenRow;
        }
    }

    /**
     * returns the last updatet column
     * @return the last updatet column
     */
    public int getLastUpdatedColumn() {
        return lastUpdatedColumn;
    }

    /**
     * returns the last updatet row
     * @return the last updatet row
     */
    public int getLastUpdatedRow() {
        return lastUpdatedRow;
    }

    /**
     * returns the number of columns of the field
     * @return number of columns
     */
    @Override
    public int getWidth() {
        return columns;
    }

    /**
     * returns the number of rows of the field
     * @return number of rows
     */
    @Override
    public int getHeight() {
        return rows;
    }

    /**
     * initiate the two dimensional array with
     * <code> Token.EMPTY </code>.
     */
    private void initField() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                field[x][y] = Token.EMPTY;
            }
        }
    }
}
