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
import manager.interfaces.PlayerInterface;

/**
 * Jeder Spieler soll dieses Interface erfuellen.
 */
public interface MyPlayerInterface extends PlayerInterface
{
	/**
	 * Hiermit soll ein Spieler benachrichtigt werden, 
	 * dass dieser am Zug ist.
	 * 
	 * @param model - Der aktuelle Spielfeldzustand
	 * @return - Die gewaehlte/berechnete Spalte (0 - 6)
	 */
	public int makeTurn(ModelInterface model);

	/**
	 * Weist dem Spieler eine Farbe zu.
	 * 
	 * @param spielerfarbe Token.RED oder Token.BLUE
	 */
	public void setColor(Token spielerfarbe);

	/**
	 * Gibt die Farbe des Spieler zurueck.
	 * 
	 * @return spielerfarbe des Spielers
	 *	 Token.RED oder Token.BLUE
	 */
	public Token getColor();
        
        public String getName();
        
        public void setName(String name);
}
















