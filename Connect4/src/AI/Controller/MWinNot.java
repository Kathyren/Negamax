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
 *
 */
public class MWinNot extends AbstractRatingMechanism implements IRatingMechanism {

    @Override
    public int getValue() {
        return -Rating.WIN;
    }


    @Override
    public boolean isFullfilled(final PossibleMove move, final ModelInterface model, Token color) {
        Token enemyColor = Token.EMPTY;
        if (color == Token.BLUE) {
            enemyColor = Token.RED;
        } else {
            enemyColor = Token.BLUE;
        }

        return super.nConnecHorrizontal(4, model, move, enemyColor) ||
                super.nConnectInVertical(4, model, move, enemyColor)||
                super.nConnectedDiagonal(4, model, move, enemyColor);
    }

}
