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
import Connect4.AI.Controller.MBlockTwoStonesWithSpaceChecking;
import Connect4.AI.Controller.MCatch22;
import Connect4.AI.Controller.MGeneralPosition;
import Connect4.AI.Controller.MMakeRowsWithHoleChecking;
import Connect4.AI.Controller.MMakeRowsWithHoleCheckingBlocking;
import Connect4.AI.Controller.MRandomizer;
import Connect4.AI.Controller.MTwoStonesWithSpaceChecking;
import Connect4.AI.Controller.MWin;
import connect4.AI.Controller.Deprecated.MBlockWin;

/**
 * this class provides an strategic ai for the game "connect 4"
 */
public class StrategicPlayer extends ComputerPlayerMiniMax implements MyPlayerInterface {

    public StrategicPlayer(int width) {
        super(width);
        super.addRatingMechanism(new MBlockWin());
        super.addRatingMechanism(new MWin());
        super.addRatingMechanism(new MGeneralPosition());
        super.addRatingMechanism(new MBlockTwoStonesWithSpaceChecking());
        super.addRatingMechanism(new MRandomizer());
        super.addRatingMechanism(new MTwoStonesWithSpaceChecking());
        super.addRatingMechanism(new MCatch22());
        super.addRatingMechanism(new MMakeRowsWithHoleChecking());
        super.addRatingMechanism(new MMakeRowsWithHoleCheckingBlocking());
    }
}
