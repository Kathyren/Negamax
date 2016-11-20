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
import connect4.AI.Model.PossibleMove;
import java.util.List;
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;

/**
 * this class provides the "minimax" algorithm.
 */
public class MiniMaxAlgorithm {

    private Tree tree;
    /**
     * calculate the best move with the given gamefield and the ratingmechanism
     * @param field the input field where to calculate from
     * @param ratingMechanisms the mechanism to rate the move
     * @param owner the current player
     * @return the best move
     */
    public int getBestMove(ModelInterface field, List<IRatingMechanism> ratingMechanisms, Token owner) {
        tree = new Tree(field, ratingMechanisms, owner);
        // minimize or maximize each level
        for (int i = Tree.MAXDEPTH - 1; i > 0; i--) {
            //System.out.println("max/min level " + i);
            List<Node> currLevel = tree.getLevel(i);
            if (!currLevel.isEmpty()) {
                
                if (currLevel.get(0).getCurrentOwner() == owner) {
                    
                    for (Node node : currLevel) {
                        maximize(node);
                    }
                    
                } else {
                    
                    for (Node node : currLevel) {
                        minimize(node);
                    }
                }
            }
        }

        // debug
        for (int i = 0; i < tree.getLevel(1).size(); i++) {
            System.out.println(tree.getLevel(1).get(i).getMove().toString());
        }

        PossibleMove minMove = tree.getLevel(1).get(0).getMove();
        for (int i = 0; i < tree.getLevel(1).size(); i++) {
            if (tree.getLevel(1).get(i).getMove().getRating() < minMove.getRating()) {
                minMove = tree.getLevel(1).get(i).getMove();
            }
        }
        return minMove.getMove();
    }
    
    /**
     * the maximize part of the algorithmus. The node
     * will get the biggest rating of his childs.
     * WARNING: of no child exists, the rating will be 
     * <code>Tree.MININT</code>
     * @param node 
     */
    private void maximize(Node node) {
        // maximize:
        double max = Tree.MININT;
        List<Node> children = node.getChildren();
        
//        if(children.isEmpty()){
//            return;
//        }
        
        //String values = "";
        for (Node child : children) {
            if (child.getMove().getRating() > max) {
                max = child.getMove().getRating();
            }
           // values += "" + child.getMove().getRating() + ", ";
        }
        // set value
        // System.out.println("setting value for next parent (maximize): " + max + " (all values: " + values + ")");

        node.getMove().setRating(max);
    }
    
    /** the minimize part of the algorithmus. The node
     * will get the lowest rating of his childs.
     * WARNING: if no child exists, the rating will be 
     * <code>Tree.MAXINT</code>
     * */
    private void minimize(Node node) {
        // minimize:
        double min = Tree.MAXINT;
        List<Node> children = node.getChildren();
        
//        if(children.isEmpty()){
//            return;
//        }
        
       // String values = "";
        for (Node child : children) {
            if (child.getMove().getRating() < min) {
                min = child.getMove().getRating();
            }
          //  values += "" + child.getMove().getRating() + ", ";
        }
        // set value
        //  System.out.println("setting value for next parent (minimize): " + min + " (all values: " + values + ")");

        node.getMove().setRating(min);
    }
}
