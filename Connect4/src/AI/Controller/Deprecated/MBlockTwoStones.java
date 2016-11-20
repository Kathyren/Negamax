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

import Connect4.AI.Controller.AbstractRatingMechanism;
import Connect4.AI.Controller.IRatingMechanism;
import connect4.AI.Model.PossibleMove;
import connect4.AI.Model.Rating;
import manager.interfaces.Token;
import manager.interfaces.ModelInterface;


/**
 * This mechanism blocks the situation described in MTwoStones.
 * Blocking is rated slightly more important than creating these
 * circumstances.
 */
public class MBlockTwoStones extends AbstractRatingMechanism implements IRatingMechanism {

    private int value = 0;

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        Token enemyColor = Token.EMPTY;
        if (color == Token.BLUE) {
            enemyColor = Token.RED;
        } else {
            enemyColor = Token.BLUE;
        }

        boolean result = false;
   

        if(super.nConnecHorrizontal(2, model, move, enemyColor)) {
            result = true;
            value += Rating.MEDIUM;
        }
        if(super.nConnectedDiagonal(2, model, move, enemyColor)){
            result = true;
            value += Rating.MEDIUM;
        }
        if(super.nConnectInVertical(2, model, move, enemyColor)){
            result = true;
            value += Rating.LOW;
        }
        return result;
    }

    @Override
    public int getValue() {
        int returnValue = value;
        value = 0; // reset value
        return returnValue;
    }
}
