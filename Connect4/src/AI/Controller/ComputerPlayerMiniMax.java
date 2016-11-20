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

import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

import connect4.AI.Model.PossibleMove;
import java.util.ArrayList;
import java.util.List;
import connect4.Model.MyPlayerInterface;
import connect4.AI.Controller.MiniMax.MiniMaxAlgorithm;

/**
 * this class provides
 */
public abstract class ComputerPlayerMiniMax implements MyPlayerInterface {

    private Token color;
    private String name;
    private List<PossibleMove> possibleMoves;
    private List<IRatingMechanism> ratingMechanisms;

    /**
     * construct new computer player.
     *
     * @param width width of game field
     */
    public ComputerPlayerMiniMax(int width) {
        this.possibleMoves = new ArrayList<PossibleMove>();
        this.ratingMechanisms = new ArrayList<IRatingMechanism>();
        initMoves(width);
    }  
    
    /**
     * delete all ratingmechanism of this ai
     */
    public void deleteAllRatingMechanism(){
        ratingMechanisms.clear();
    }

    @Override
    public Token getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setColor(Token spielerfarbe) {
        if (spielerfarbe == Token.EMPTY) {
            throw new Error("EMPTY is not allowed");
        } else {
            this.color = spielerfarbe;
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * resets the KI. This method is to call whenever the game is reset
     * and the old KI is used instead of creating a new one.
     *
     * @param width width of game field
     */
    public void resetKI(int width) {
        this.possibleMoves = new ArrayList<PossibleMove>();
        initMoves(width);
    }

    /**
     * adds the given rating mechanisms to the list of mechanisms of this
     * player.
     *
     * @param ratingMechanism the rating mechanism to add
     */
    public void addRatingMechanism(IRatingMechanism ratingMechanism) {
        ratingMechanisms.add(ratingMechanism);
    }

    private void initMoves(int width) {
        for (int i = 0; i <= width - 1; i++) {
            possibleMoves.add(new PossibleMove(i));
        }
    }

    @Override
    public int makeTurn(ModelInterface model) {
        resetAllRatings();
        MiniMaxAlgorithm mm = new MiniMaxAlgorithm();
        return mm.getBestMove(model, ratingMechanisms, color);
    }

    /**
     * resets all values of possibleMoves to 0.
     */
    private void resetAllRatings() {
        for (PossibleMove possibleMove : possibleMoves) {
            possibleMove.resetRating();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComputerPlayerMiniMax other = (ComputerPlayerMiniMax) obj;
        if (this.color != other.color) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.color != null ? this.color.hashCode() : 0);
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
