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
 * An interface describing a rating mechanism.
 * A move may be tested by a rating mechanism, as to whether or not it
 * satisfies the rule defined by the concrete mechanism.
 * In addition, it holds a value which represents the value of the rule
 * the mechanism contains.
 * <p>
 * The rating mechanism itself should NOT change the value of the move,
 * nor should it change the ModelInterface.
 */
public interface IRatingMechanism {

    /**
     * returns whether or not the given move satisfies this mechanism.
     *
     * @param move the move to be tested
     * @param model model
     * @param color the color of the ki
     *
     * @return
     */
    boolean isFullfilled(final PossibleMove move, final ModelInterface model, Token color);

    /**
     * returns the value of this mechanism.
     *
     * @return value of this mechanism
     * @see Rating
     */
    int getValue();

}
