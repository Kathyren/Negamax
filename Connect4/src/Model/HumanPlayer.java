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
package connect4.Model;

import manager.interfaces.Token;
import manager.interfaces.ModelInterface;
import connect4.Controller.GameController;

/**
 * This class provides a human player for the game "ConnectFour".
 */
public class HumanPlayer implements MyPlayerInterface {

    private Token tok;
    private GameController ctrl;
    private String name;

    /**
     * constructs a new human player.
     *
     * @param ctrl
     * @param name
     */
    public HumanPlayer(GameController ctrl, String name){
        this.ctrl = ctrl;
        this.name = name;
    }
    
    
    /**
     * returns the human input
     * @param model the model to play on
     * @return the choosen column
     */
    @Override
    public int makeTurn(ModelInterface model) {
            return ctrl.getHumanInput();
    }
    
    /**
     * sets the color of the player.
     * WARNING: <code> Token.EMPTY </code> is not allowed
     * and throws an exception.
     * @param color the color of this player
     */
    @Override
    public void setColor(Token color) {
        if(color == Token.EMPTY){
            throw new Error("EMPTY is not allowed");
        } else {
            this.tok = color;
        }
    }
    
    /**
     * returns the color of the player
     * @return the color
     */
    @Override
    public Token getColor() {
        return tok;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HumanPlayer other = (HumanPlayer) obj;
        if (this.tok != other.tok) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.tok != null ? this.tok.hashCode() : 0);
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
     

}
