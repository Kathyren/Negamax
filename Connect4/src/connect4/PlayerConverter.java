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

import connect4.Model.MyPlayerInterface;
import manager.interfaces.ModelInterface;
import manager.interfaces.Token;

/**
 */
public class PlayerConverter implements manager.interfaces.PlayerInterface {
    
    private MyPlayerInterface player;

    public PlayerConverter(MyPlayerInterface player) {
        this.player = player;
    }

    @Override
    public Token getColor() {
        return player.getColor();
    }

    @Override
    public int makeTurn(ModelInterface mi) {
        int makeTurn = player.makeTurn(new ModelConverter(mi));
        // TODO check this (bounds)
        //System.out.println("maketurn with conv. value: " + (makeTurn + 1));
        return makeTurn + 1;
    }

    @Override
    public void setColor(Token token) {
        player.setColor(token);
    }

    private class ModelConverter implements ModelInterface {

        // TODO removeIllegalMoves in GameField still is a problem (hardcoded to check in line 0 for token)
        // also, same problem with minimax alg 

        private ModelInterface model;

        public ModelConverter(ModelInterface model) {
            this.model = model;
        }
        
        @Override
        public Token getFieldStatus(int column, int row) {
            // TODO check this (bounds)
            int adjustedI = column;
            int adjustedI1 = ((getHeight() - 1) - row);

            //System.out.println("getFieldStatus with conv. valuffasfafes: " + adjustedI + " and " + adjustedI1);
            // TODO debug, delete later.
            if (adjustedI < 0) {
                adjustedI = 0;
            }
            if (adjustedI > 6) {
                adjustedI = 6;
            }
            if (adjustedI1 < 0) {
                adjustedI1 = 0;
            }
            if (adjustedI1 > 5) {
                adjustedI1 = 5;
            }
            
            return model.getFieldStatus(adjustedI, adjustedI1);
        }

        @Override
        public int getHeight() {
            // TODO check this (bounds)
            return model.getHeight();
        }

        @Override
        public int getWidth() {
            // TODO check this (bounds)
            return model.getWidth();
        }

        @Override
        public void setFieldStatus(int i, Token token) {
            // TODO check this (bounds)
            //System.out.println("setFieldStatus with conv. value: " + (i));
            model.setFieldStatus(i - 1, token);
        }
    }
}
