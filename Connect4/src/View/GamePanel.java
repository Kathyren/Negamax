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
package connect4.View;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * This class provides an extended panel to draw a "X" or
 * a circle inside and to save an position.
 *
 * @version 1.1
 */
public class GamePanel extends JPanel {

    /* boolean values to check if the panel has a "X" or a
     * cirle inside, or nothing. */
    private boolean isCircle;
    private boolean isX;
    private int column;
    private int row;
    private Color color;

    /**
     * makes a new panel and save his position
     * @param row
     * @param column 
     */
    public GamePanel(int row, int column) {
        super();
        this.isCircle = false;
        this.isX = false;
        this.setBorder(BorderFactory.createEtchedBorder());
        this.color = Color.BLACK;
        this.column = column;
        this.row = row;
    }

    /**
     * sets the panel to show a circle inside it when <code>draw</code> is called.
     * <code>isX</code> will be set to false.
     */
    public void setCircle() {
        this.isCircle = true;
        this.isX = false;
    }

    /**
     * sets the panel to show an X inside it when <code>draw</code> is called.
     * <code>isCircle</code> will be set to false.
     */
    public void setX() {
        this.isX = true;
        this.isCircle = false;
    }
    
    /**
     * clear the content of the panel and
     * set the boolean flags for <code>isX</code>
     * and <code>isCircle</code> to false.
     */
    public void clear(){
        this.isCircle = false;
        this.isX = false;
        Graphics g = getGraphics();
        int distToEdge = 3;
        int width = super.getWidth() - distToEdge;
        int height = super.getHeight() - distToEdge;
        g.clearRect(0, 0, width, height);
    }

    /**
     * If the <code>setCircle</code> method was call before,
     * this method draws a circle inside this panel.
     * If the <code>setX</code> method was called before,
     * this method draws an "X" inside this panel.
     * <p>
     * Both forms are stretched to the edge of the panel.
     * Either way, the old printed form is deleted.
     */
    public void draw() {
        Graphics g = getGraphics();

        int distToEdge = 3;
        int width = super.getWidth() - distToEdge;
        int height = super.getHeight() - distToEdge;

        if (isX) {
            g.clearRect(0, 0, width, height);
            g.setColor(color);
            g.fillOval(distToEdge, distToEdge, width - 4, height - 4);
        } else if (isCircle) {
            g.clearRect(0, 0, width, height);
            g.setColor(color);
            g.fillOval(distToEdge, distToEdge, width - 4, height - 4);
        } else {
             g.clearRect(0, 0, width, height);
        }
        this.setBorder(BorderFactory.createEtchedBorder());
    }

    /**
     * returns number of columns.
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     * returns number of rows.
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * sets the color of this panel.
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }  
}
