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
 * This mechanism checks if with this move a situation can be created in which
 * the ki will win on its next move because it creates two winning possibilities
 * which the enemy cannot block both (in german this is known as Zwickmuehle).
 * It is only checked, if two of three possibilities can be reached:
 * nConnecHorrizontal, nConnectedDiagonal, nConnectInVertical.
 * two stones open ended in a row for example, are not included.
 */
public class MCatch22 extends AbstractRatingMechanism implements IRatingMechanism {

    @Override
    public int getValue() {
        return Rating.VERYHIGH;
    }

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {

        int winningSituations = 0;
        if (super.nConnecHorrizontal(3, model, move, color)) {
            winningSituations++;
        }
        if (super.nConnectedDiagonal(3, model, move, color)) {
            winningSituations++;
        }
        if (super.nConnectInVertical(3, model, move, color)) {
            winningSituations++;
        }

        return winningSituations >= 2;
    }
}
