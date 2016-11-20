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
import java.util.Random;

/**
 * This mechanism randomly returns either ZERO, VERYLOW or LOW
 */
public class MRandomizer implements IRatingMechanism {

    @Override
    public int getValue() {
        Random generator = new Random();
        // random int from 0 to 2
        int random = generator.nextInt(3);
        if (random == 0) {
            return Rating.ZERO;
        } else if (random == 1) {
            return Rating.VERYLOW;
        } else {
            return Rating.LOW;
        }

    }

    @Override
    public boolean isFullfilled(PossibleMove move, ModelInterface model, Token color) {
        return true;
    }



}
