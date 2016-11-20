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
import manager.interfaces.Token;
import manager.interfaces.ModelInterface;
/**
 */
public class MTwoStonesWithSpaceCheckingNot extends AbstractRatingMechanism implements IRatingMechanism {

    private int value = 0;

    @Override
    public int getValue() {
        int returnValue = value;
        value = 0; // reset value
        return returnValue;
    }

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        MTwoStonesWithSpaceChecking ts = new MTwoStonesWithSpaceChecking();
        Token enemyColor = Token.EMPTY;
        if (color == Token.BLUE) {
            enemyColor = Token.RED;
        } else {
            enemyColor = Token.BLUE;
        }
        boolean fullfilled = ts.isFullfilled(move, model, enemyColor);
        value = ts.getValue();
        return fullfilled;
    }
}
