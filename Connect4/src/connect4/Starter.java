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

import connect4.Controller.GameController;
import manager.controler.Tuniercontroler;

/**
 */
public class Starter {

	public static void main(String[] args) {

		Tuniercontroler tunier = new Tuniercontroler();

//		tunier.addTeilnehmer(new RandomPlayer(), "Existing Random");
//                tunier.addTeilnehmer(new RandomPlayer(), "Existing Random2");

                // TODO maybe: this is not working, because columns and lines are counted differently.
                // eitherit has to be rewriten completely or there needs to be some kind of converter (-> converter writen).
                // -> blackbox testing? fun^^
                tunier.addTeilnehmer(new PlayerConverter(new connect4.AI.AggressivePlayer(GameController.COLUMNS)), "Aggressive");
                tunier.addTeilnehmer(new PlayerConverter(new connect4.AI.PassivePlayer(GameController.COLUMNS)), "Passive");
                // tunier.addTeilnehmer(new PlayerConverter(new RandomPlayer(GameController.COLUMNS)), "Random");
                // tunier.addTeilnehmer(new PlayerConverter(new StrategicPlayer(GameController.COLUMNS)), "Strategic");
                tunier.run();
	}

}
