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

import manager.interfaces.Token;
import manager.interfaces.ModelInterface;

import java.util.ArrayList;
import java.util.List;
import connect4.Model.MyPlayerInterface;
import Connect4.AI.Controller.IRatingMechanism;
import connect4.AI.Model.PossibleMove;

/**
 * This class contains the main logic behind a computer player/artificial intelligence.
 * When it is its turn, it rates all possible moves and returns the highest
 * rated move.
 * <p>
 * Its behaviour is determined by the RatingMechanism it uses.
 * Rating mechanisms must be added for this computer player to
 * produce any meaningful moves.
 * @see IRatingMechanism
 */
public class ComputerPlayer implements MyPlayerInterface {

    private Token color;
    private String name;
    private List<PossibleMove> possibleMoves;
    private List<IRatingMechanism> ratingMechanisms;

    /**
     * construct new computer player.
     *
     * @param width width of game field
     */
    public ComputerPlayer(int width) {
        this.possibleMoves = new ArrayList<PossibleMove>();
        this.ratingMechanisms = new ArrayList<IRatingMechanism>();
        // initRatingMechanisms();
        initMoves(width);
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
     * @param ratingMechanism
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
        removeIllegalMoves(model);
        rateMoves(model);
        return getHighestRated().getMove();
    }

    /**
     * rates the moves according to ratingMechanisms.
     *
     * @param model
     */
    private void rateMoves(ModelInterface model) {
        for (PossibleMove possibleMove : possibleMoves) {
            for (IRatingMechanism ratingMechanism : ratingMechanisms) {
                if (ratingMechanism.isFullfilled(possibleMove, model, color)) {
                    possibleMove.addToRating(ratingMechanism.getValue());
                }
            }
        }

        // debug (prints out values of all moves
        for (PossibleMove possibleMove : possibleMoves) {
            System.out.println(possibleMove.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * removes moves that would place into a full column.
     *
     * @param model
     */
    private void removeIllegalMoves(ModelInterface model) {
        List<PossibleMove> deletedMoves = new ArrayList<PossibleMove>();
        for (PossibleMove possibleMove : possibleMoves) {

            Token fieldStatus = model.getFieldStatus(possibleMove.getMove(), 0);
            if (fieldStatus != Token.EMPTY) {
                deletedMoves.add(possibleMove);
            }
        }
        possibleMoves.removeAll(deletedMoves);
    }

    /**
     * resets all values of possibleMoves to 0.
     */
    private void resetAllRatings() {
        for (PossibleMove possibleMove : possibleMoves) {
            possibleMove.resetRating();
        }
    }

    /**
     * returns the move with the highest rating.
     * If there are more than one with the same highest rating,
     * the first will be returned.
     *
     * @return
     */
    private PossibleMove getHighestRated() {
        PossibleMove highest = possibleMoves.get(0);
        for (PossibleMove possibleMove : possibleMoves) {
            if (possibleMove.getRating() > highest.getRating()) {
                highest = possibleMove;
            }
        }
        return highest;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComputerPlayer other = (ComputerPlayer) obj;
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
