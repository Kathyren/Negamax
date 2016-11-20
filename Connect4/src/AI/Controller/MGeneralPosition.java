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
 * This class checks how close the move is to the middle of the field.
 * The closer to the middle it is, the higher its rating will be.
 * The rating will be small compared to most of the other rating mechanisms.
 * The isFullfilled method assigns the value of this mechanism,
 * if it is not called, getValue will return 0 or a value previously set.
 */
public class MGeneralPosition implements IRatingMechanism {

    /**
     * amount to multiply distance to edges of field by to get rating.
     */
    private int value = 0;

    @Override
    public int getValue() {
        int returnValue = value;
        value = 0; // reset value
        return returnValue;
    }


    @Override
    public boolean isFullfilled(final PossibleMove move, final ModelInterface model, Token color) {
        calculateValue(move, model);
        return true; //this rule is always fullfilled
    }

    /**
     * calculates the value by determining the distance to the edge of the field
     * and multiplying a pre-defined value.
     * WARNING: this method only works with 7 rows!!
     * @param move the move to calc on
     * @param model the model to calc on
     */
    private void calculateValue(final PossibleMove move, final ModelInterface model){
        int moveToCollumn = move.getMove();
        int width = model.getWidth() - 1;
        if(moveToCollumn == 1 || moveToCollumn == (width -1)) {
            value = Rating.VERYLOW;
        } else if(moveToCollumn == 2 || moveToCollumn == (width - 2)) {
            value = Rating.LOW;
        } else if(moveToCollumn == 3) {
            value = Rating.MEDIUM;
        } else {
            value = Rating.ZERO;
        }
    }
}
