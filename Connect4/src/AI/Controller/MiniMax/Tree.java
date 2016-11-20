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

import Connect4.AI.Controller.IRatingMechanism;
import java.util.ArrayList;
import java.util.List;
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;

/**
 * this class provides an gametree for "connect4"
 */
public class Tree {

    /**
     * maximal depth to which the minimax algorithm tests moves/ the tree is build.
     */
    public static final int MAXDEPTH = 4; // moves to look ahead/depth of tree. depth 0 = root
    public static final int MAXINT = Integer.MAX_VALUE / 2;
    public static final int MININT = Integer.MIN_VALUE / 2;

    private Node rootNode; // does not contain a move itself but will save the best move later
    private List<List<Node>> levels; // levels of the tree, representing turns
    private List<IRatingMechanism> ratingMechanisms;
    private Token player; // the color of the ki
    
    /**
     * generate a tree with the modell as the fundament.
     * For each possible move a child will be make until the depth has reached.
     * All childs will be rating by the given ratingmechanism. Every level of
     * the tree has the owner  obverse to the level before.
     * @param mf the gamefield
     * @param ratingMechanisms the ratingmechanism
     * @param owner the player
     */
    public Tree(ModelInterface mf, List<IRatingMechanism> ratingMechanisms, Token owner) {
        this.ratingMechanisms = ratingMechanisms;
        this.player = owner;
        rootNode = new Node(null, mf);
        rootNode.setCurrentOwner(player);
        // first row of actual moves should be for ki
        rootNode.switchCurrentOwner();
        levels = new ArrayList<List<Node>>();
        for (int i = 0; i <= MAXDEPTH; i++) {
            levels.add(new ArrayList<Node>());
        }
        getLevel(0).add(rootNode);
        buildTree(MAXDEPTH);
    }
    
    /**
     * returns a list of the node at this level
     * @param level the level of the tree
     * @return a list of the node at this level
     */
    public List<Node> getLevel(int level) {
        return levels.get(level);
    }
    
    /**
     * generates for all nodes childrens and rates the moves until
     * the max depth is reached
     * @param depth the depth of the tree
     */
    private void buildTree(int depth) {
        for (int i = 0; i < depth; i++) {
            List<Node> currentLevel = getLevel(i);
            List<Node> nextLevel = getLevel(i + 1);
            for (Node node : currentLevel) {
                nextLevel.addAll(node.generateChildren(ratingMechanisms, player));
            }
        }
    }


    @Override
    public String toString() {
        String ret = "";
        for (List<Node> list : levels) {
            for (Node node : list) {
                ret += node.toString() + "      ";
            }
            ret += '\n';
        }
        return ret;
    }
}
