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
package connect4.AI.Controller.MiniMax;

import connect4.Model.GameField;
import Connect4.AI.Controller.IRatingMechanism;
import Connect4.AI.Controller.MWinNot;
import Connect4.AI.Controller.MWin;
import connect4.AI.Model.PossibleMove;
import java.util.ArrayList;
import java.util.List;
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;

/**
 * this class provides a node for the gametree. It contains the
 * changed gamefield and the move to go to this changed gamefield.
 * A List of children will be generated with all possible moves.
 */
public class Node {

    private Token currentOwner;
    private Node parent;
    private PossibleMove move;
    private ModelInterface mf;
    private List<Node> children;
    private boolean isLeaf = false;

    /**
     * makes a new node with empty childs
     * @param move the actually move
     * @param mf the gamefield
     */
    public Node(PossibleMove move, ModelInterface mf) {
        this.mf = mf;
        this.move = move;
        children = new ArrayList<Node>();
    }

    /**
     * sets this node as a leaf
     */
    public void setLeaf() {
        isLeaf = true;
    }

    /**
     * checks if this node is a leaf
     * @return true if this node is a leaf, else false
     */
    public boolean isIsLeaf() {
        return isLeaf;
    }

    /**
     * copys the given gamefield
     * @param mi the gamefield to copy
     * @return the copied gamefield
     */
    private ModelInterface cloneModel(ModelInterface mi) {
        ModelInterface mfCopy = new GameField(6, 7);

        for (int line = mi.getHeight(); line >= 0; line--) {
            for (int column = 0; column < mi.getWidth(); column++) {
                mfCopy.setFieldStatus(column, mi.getFieldStatus(column, line));
            }
        }

        return mfCopy;
    }

    /**
     * returns a list of the node childs
     * @return a list of the node childs
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * generates and returns children for all possible moves (illegal moves
     * will not generate a child).
     * <p>
     * In each child, a copy of the ModelInterface will be changed according
     * to its move.
     * <p>
     * Also, the move will be rated according to the given rating mechanisms.
     * <p>
     * Also, the child nodes will get the opposite owner of this node and this
     * node will be set as its parent.
     *
     * @param ratingMechanisms
     * @param player the color of the ki (do not confuse with currentOwner, which is owner of this node)
     */
    public List<Node> generateChildren(List<IRatingMechanism> ratingMechanisms, Token player) {
        children = new ArrayList<Node>();
        if (!isIsLeaf()) {
            for (int i = 0; i < mf.getWidth(); i++) {
                Token fieldStatus = mf.getFieldStatus(i, 0);
                // only add child if move of it is possible
                if (fieldStatus == Token.EMPTY) {
                    ModelInterface mfCopy = cloneModel(mf);
                    Node newChild = new Node(new PossibleMove(i), mfCopy);
                    newChild.setParent(this);
                    newChild.setCurrentOwner(currentOwner);
                    newChild.switchCurrentOwner();
                    newChild.calcRating(ratingMechanisms, player);
                    newChild.executeMove();
                    children.add(newChild);
                }
            }
        }

        return children;
    }

    /**
     * execute the given move
     */
    public void executeMove() {
        mf.setFieldStatus(move.getMove(), currentOwner);
    }

    /**
     * rates the given move of this node
     * @param ratingMechanisms the mechanism to rate this move
     * @param player the playercolor
     */
    public void calcRating(List<IRatingMechanism> ratingMechanisms, Token player) {
        for (IRatingMechanism ratingMechanism : ratingMechanisms) {
            if (ratingMechanism instanceof MWin && ratingMechanism.isFullfilled(move, mf, currentOwner)) {
                move.addToRating(ratingMechanism.getValue());
                setLeaf();
                return;
            } else if (ratingMechanism instanceof MWinNot && ratingMechanism.isFullfilled(move, mf, currentOwner)) {
                move.addToRating(ratingMechanism.getValue());
                setLeaf();
                return;
            }

            if (ratingMechanism.isFullfilled(move, mf, currentOwner)) {
                move.addToRating(ratingMechanism.getValue());
            }
        }
    }

    /**
     * sets the parent of this node
     * @param parent the paren of this node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the current owner of this node
     */
    public Token getCurrentOwner() {
        return currentOwner;
    }

    /**
     * sets the owner of this node
     * @param currentOwner 
     */
    public void setCurrentOwner(Token currentOwner) {
        this.currentOwner = currentOwner;
    }

    /**
     * switchs the owner. If the owner is <code>Token.BLUE</code>,
     * the new owner will be <code>Token.BLUE</code>. If
     * the owner is <code>Token.BLUE</code> the new owner will be
     * <code>Token.RED</code>
     */
    public void switchCurrentOwner() {
        if (currentOwner == Token.BLUE) {
            currentOwner = Token.RED;
        } else {
            currentOwner = Token.BLUE;
        }
    }

    /**
     * sets the move of this node
     * @param move the move to set
     */
    public void setMove(PossibleMove move) {
        this.move = move;
    }

    /**
     * @return the parent of this node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @return the move of this node
     */
    public PossibleMove getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "N{" + "o=" + currentOwner + ", m=" + move + '}';
    }
}
