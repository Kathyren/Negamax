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
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;

/**
 */
public class MMakeRowsWithHoleCheckingBlocking extends AbstractRatingMechanism implements IRatingMechanism {

    private int value;

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        boolean isFullfilled = false;
        
        Token enemyColor = Token.EMPTY;
        if (color == Token.BLUE) {
            enemyColor = Token.RED;
        } else {
            enemyColor = Token.BLUE;
        }
        
        if (super.nConncetedVerticalWithHoles(3, 1, model, move, enemyColor)) {
            value += Rating.MEDIUM;
            isFullfilled = true;
            
        } else if (super.nConncetedVerticalWithHoles(2, 2, model, move, enemyColor)) {
            value += Rating.LOW;
            isFullfilled = true;
        }
        
        if (super.nConnectedDiagonalWithHoles(3, 1, model, move, enemyColor)) {
            value += Rating.VERYHIGH;
            isFullfilled = true;
            
        } else if (super.nConnectedDiagonalWithHoles(2, 2, model, move, enemyColor)) {
            value += Rating.MEDIUM;
            isFullfilled = true;
        }
        
        return isFullfilled;
    }

    @Override
    public int getValue() {
        int retValue = value;
        value = 0;
        return retValue;
    }
}
