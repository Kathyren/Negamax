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
package connect4.AI;

import Connect4.AI.Controller.ComputerPlayerMiniMax;
import connect4.Model.MyPlayerInterface;
import Connect4.AI.Controller.MRandomizer;

/**
 * this class provides an randomly ai for the game "connect 4"
 */
public class RandomPlayer extends ComputerPlayerMiniMax implements MyPlayerInterface {

    public RandomPlayer(int width) {
        super(width);
        super.addRatingMechanism(new MRandomizer());
    }
}
