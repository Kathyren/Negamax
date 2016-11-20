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
package connect4;

import connect4.Model.GameField;
import Connect4.AI.Controller.IRatingMechanism;
import java.util.ArrayList;
import java.util.List;
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;
import java.io.Serializable;
import Connect4.AI.Controller.MWinNot;
import Connect4.AI.Controller.MWin;
import connect4.AI.Controller.MiniMax.MiniMaxAlgorithm;
import connect4.AI.Controller.MiniMax.Tree;

/**
 */
public class Test {

    public static void main(String[] args) {

//        System.out.println("getBestMove");
//
//        ModelInterface field = new MyModel();
//        List<IRatingMechanism> ratingMechanisms = new ArrayList<IRatingMechanism>();
//        //ratingMechanisms.add(new MRandomizer());
//        ratingMechanisms.add(new MWin());
//        //ratingMechanisms.add(new MGeneralPosition());
//      //  ratingMechanisms.add(new MTwoStonesWithSpaceChecking());
//      //  ratingMechanisms.add(new MCatch22());
//        Token owner = Token.BLUE;
//        MiniMaxAlgorithm instance = new MiniMaxAlgorithm();
//        int expResult = 4;
//        int result = instance.getBestMove(field, ratingMechanisms, owner);
//        System.out.println("" + result);

        
//        Token owner = Token.RED;
//        List<IRatingMechanism> ratingMechanisms = new ArrayList<IRatingMechanism>();
//        ratingMechanisms.add(new MWin());
//        ratingMechanisms.add(new MRandomizer());
//        ModelInterface field = new MyModel();
//        Tree tree = new Tree(field, ratingMechanisms, owner);
//        System.out.println(tree.toString());





        ModelInterface model = new GameField(6, 7);
        Token blue = Token.BLUE;
        Token red = Token.RED;

//        model.setFieldStatus(0, red);
//        model.setFieldStatus(0, red);
//        model.setFieldStatus(0, red);

        model.setFieldStatus(0, red);
        model.setFieldStatus(1, blue);
        model.setFieldStatus(2, red);

        model.setFieldStatus(0, red);
        model.setFieldStatus(1, red);
        model.setFieldStatus(2, red);

//        model.setFieldStatus(2, blue);
//        model.setFieldStatus(2, blue);
//        model.setFieldStatus(2, blue);
        
        List<IRatingMechanism> ratingMechanisms = new ArrayList<IRatingMechanism>();
        ratingMechanisms.add(new MWin());
        ratingMechanisms.add(new MWinNot());
//        ratingMechanisms.add(new MGeneralPosition());
//        ratingMechanisms.add(new MTwoStonesWithSpaceChecking());
//        ratingMechanisms.add(new MCatch22());

        Tree tree = new Tree(model, ratingMechanisms, blue);
        System.out.println(tree.toString());

        MiniMaxAlgorithm instance = new MiniMaxAlgorithm();
        int result = instance.getBestMove(model, ratingMechanisms, blue);
        System.out.println("" + result);





    }


    private static class MyModel implements ModelInterface, Serializable {

            @Override
            public Token getFieldStatus(int spalte, int zeile) {
                if (spalte == 0 && zeile == 5) {
                    return Token.BLUE;
                } else if (spalte == 1 && zeile == 5) {
                    return Token.BLUE;
                } else if (spalte == 2 && zeile == 5) {
                    return Token.BLUE;
                }

                else if (spalte == 4 && zeile == 5) {
                    return Token.RED;
                } else if (spalte == 4 && zeile == 4) {
                    return Token.RED;
                } else if (spalte == 4 && zeile == 3) {
                    return Token.RED;
                }

                return Token.EMPTY;
            }

            @Override
            public void setFieldStatus(int spalte, Token tok) {
            }

            @Override
            public int getWidth() {
                return 7;
            }

            @Override
            public int getHeight() {
                return 6;
            }
        }


}
