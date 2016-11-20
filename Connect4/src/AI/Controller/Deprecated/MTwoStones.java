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
 * This class checks,if the move will lead to two connecting stones.
 * If it does so, it will add a <code>VERYLOW</code> rating for two connecting 
 * stones vertical as well as a <code>LOW</code> rating for two stones 
 * horizontally as well as a <code>LOW</code> rating for two stones diagonally.
 * <p>
 * The <code>isFullfilled</code> method calculates the value, so it must be called
 * before getValue().
 */
// TODO this class can be deleted, right?
public class MTwoStones extends AbstractRatingMechanism implements IRatingMechanism {

    private int value = 0;
        
    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        boolean result = false;


        if(super.nConnecHorrizontal(2, model, move, color)) {
            result = true;
            value += Rating.LOW;
        }
        if(super.nConnectedDiagonal(2, model, move, color)){
            result = true;
            value += Rating.LOW;
        }
        if(super.nConnectInVertical(2, model, move, color)){
            result = true;
            value += Rating.VERYLOW;
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
