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
package connect4.Controller;

import connect4.AI.AggressivePlayer;
import connect4.AI.PassivePlayer;
import connect4.AI.RandomPlayer;
import connect4.AI.StrategicPlayer;
import connect4.Model.AiCharacter;
import manager.interfaces.Token;

import java.awt.Color;
import connect4.Model.GameField;
import java.awt.event.MouseEvent;
import connect4.Model.Buffer;
import connect4.Model.GameState;
import connect4.Model.HumanPlayer;
import connect4.Model.MyPlayerInterface;
import connect4.View.GameGui;
import connect4.View.GamePanel;

/**
 * This class allows communication between the view and the model.
 * It contains the main game loop as well.
 */
public class GameController implements Runnable {

    private GameField field;
    private GameRuleController gameRuleController;
    private GameGui gui;
    private Buffer<MouseEvent> buff; // stores one user mouse event
    private MyPlayerInterface playerOne;
    private MyPlayerInterface playerTwo;
    private MyPlayerInterface currentPlayer;
    private boolean isP1Computer;
    private boolean isP2Computer;
    private boolean pause;
    private final Object syncObject;
    private final static int ROWS = 6;
    public final static int COLUMNS = 7;
    private GameState state;
    private int lastUpdatedColumn;
    private int lastUpdatedRow;
    private AiCharacter com1Charakter;
    private AiCharacter com2Charakter;

    /**
     * instance a game controller for the game "4Gewinnt"
     */
    public GameController() {
        lastUpdatedColumn = 0;
        lastUpdatedRow = 0;
        syncObject = new Object();
        buff = new Buffer<MouseEvent>();
        this.gui = new GameGui(this, buff);
        gui.setVisible(true);
        this.isP1Computer = false;
        this.isP2Computer = false;
        this.pause = false;
        this.state = GameState.PLAYING;
        com1Charakter = AiCharacter.Aggessive;
        com2Charakter = AiCharacter.Strategic;
    }

    /**
     * make a new instances of <code>GameField</code>,
     * <code>GameRuleController</code>, clears the buffer
     * and setup the players dependent of the boolean flags
     * <code>isP1Computer</code> and <code>isP2Computer</code>
     **/
    private void makeNewGame() {
        lastUpdatedColumn = 0;
        lastUpdatedRow = 0;
        this.field = new GameField(ROWS, COLUMNS);
        this.gameRuleController = new GameRuleController(field);
        buff.clear();
        //make player one as computer
        if (isP1Computer) {
            playerOne = createComputerPlayer(com1Charakter);
            playerOne.setName("The intelligence Peter");
            playerOne.setColor(Token.BLUE);

            // make player one as human
        } else {
            playerOne = new HumanPlayer(this, "Player one");
            playerOne.setColor(Token.BLUE);
        }

        // make player two as computer
        if (isP2Computer) {
            playerTwo = createComputerPlayer(com2Charakter);
            playerTwo.setName("The intelligence Karl");
            playerTwo.setColor(Token.RED);

        } //make player two as human
        else {
            playerTwo = new HumanPlayer(this, "Player two");
            playerTwo.setColor(Token.RED);
        }
        currentPlayer = playerOne;
    }

    /**
     * play the game "4 Gewinnt". This method will recieve
     * human and computer moves. In this case the move is a column.
     * This Colum will be send to the model <code> GameField </code>
     * and there it is processed. This method checks then the last updatet 
     * position and says the gui to update this position.
     * <p>
     * If the boolean flag <code>pause</code> is true, the
     * thread will wait for starting a new, reseted game.
     * if.
     * <p>
     * If the game is won or draw the thread will wait until
     * a new game is starting.
     */
    private void play() {

        while (true) {
            // make the turn
            int move = currentPlayer.makeTurn(field);
            // if legal move:
            if (gameRuleController.isLegalMove(move)) {
                //move
                field.setFieldStatus(move, currentPlayer.getColor());
                // get updates
                lastUpdatedColumn = field.getLastUpdatedColumn();
                lastUpdatedRow = field.getLastUpdatedRow();
                //draw
                drawToGui(lastUpdatedRow, lastUpdatedColumn);
                //checks game state:
                state = gameRuleController.getGameState(lastUpdatedRow,
                        lastUpdatedColumn, currentPlayer.getColor());
                // process game state
                if (state != GameState.PLAYING) {
                    try {
                        if (state == GameState.DRAW) {
                            gui.sendMessage("There are no winners");

                        } else {
                            gui.sendMessage("The winner is: " + currentPlayer.getName());
                        }

                        synchronized (syncObject) {
                            gui.repaint();
                            //wait for restart game.
                            syncObject.wait();
                        }

                    } catch (InterruptedException ex) {
                    }

                } else {
                    changePlayer();
                    gui.sendMessage(currentPlayer.getName() + " has its turn");
                }

                gui.repaint();

                if (pause) {
                    try {
                        synchronized (syncObject) {
                            // wait for reset game
                            syncObject.notify();
                            syncObject.wait();
                        }
                    } catch (InterruptedException ex) {
                    }
                }
            }

        }
    }

    /**
     * gets the panel from the gui and says the panel to draw.
     *
     * @param row the changed row
     * @param column the changed column
     */
    private void drawToGui(int row, int column) {
        GamePanel drawing = gui.getPanelAt(row, column);

        if (currentPlayer.getColor() == Token.RED) {
            drawing.setX();
            drawing.setColor(Color.RED);

        } else if (currentPlayer.getColor() == Token.BLUE) {
            drawing.setCircle();
            drawing.setColor(Color.BLUE);
        }
    }

    /**
     * changes the player.
     */
    private void changePlayer() {
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

    /**
     * Reads the <code> MouseEvent </code> from the buffer and
     * verifies it. When the verification failed the buffer will be read
     * until the verification does not failed.
     * WARINING: when the thread is stopping -1 will be returned.
     *
     * @return the verified human input from the gui.
     */
    public int getHumanInput() {
        while (true) {
            MouseEvent event = buff.read();

            if (pause) {
                return -1;
            }

            if (event.getButton() == 1
                    && currentPlayer.equals(playerOne)) {
                GamePanel temp = (GamePanel) event.getComponent();
                return temp.getColumn();

            } else if (event.getButton() == 3
                    && currentPlayer.equals(playerTwo)) {
                GamePanel temp = (GamePanel) event.getComponent();
                return temp.getColumn();
            }
        }
    }

    /**
     * returns the number of columns
     * @return the number of columns
     */
    public int getWidth() {
        return COLUMNS;
    }

    /**
     * returns the number of rows
     * @return the number of rows
     */
    public int getHeight() {
        return ROWS;
    }

    /**
     * sets player one to computer or not.
     * @param isP1Computer true if computer, else false
     */
    public void setIsP1Computer(boolean isP1Computer) {
        this.isP1Computer = isP1Computer;
    }

    /**
     * sets player two to computer or not.
     * @param isP2Computer true if computer, else false
     */
    public void setIsP2Computer(boolean isP2Computer) {
        this.isP2Computer = isP2Computer;
    }

    /**
     * runnable method to process from a thread.
     */
    @Override
    public void run() {
        makeNewGame();
        play();
    }

    /**
     * start a thread to provide the game "4Gewinnt"
     */
    public void start() {
        new Thread(this).start();
    }

    /**
     * reset the game content. When the game is not finish the thread inside
     * this method waits until the game thread is paused,
     * and sends a notify. Makes a new game and repaint the gui panels.
     */
    public void reset() {
        synchronized (syncObject) {
            try {
                pause = true;
                buff.wakeUp();
                // waits for game thread paused.
                if (state == GameState.PLAYING) {
                    syncObject.wait();

                }

                makeNewGame();
                gui.deletePanelContent();
                pause = false;
                syncObject.notify();
            } catch (InterruptedException ex) {
            }
        }
    }
    
    /**
     * returns an Ai instance with the given character
     * @param chara the character of the ai
     * @return an Ai instance with the given character
     */
    private MyPlayerInterface createComputerPlayer(AiCharacter chara) {
       
        if (chara == AiCharacter.Aggessive) {
            return new AggressivePlayer(COLUMNS);
        }

        if (chara == AiCharacter.Passive) {
            return new PassivePlayer(COLUMNS);
        }

        if (chara == AiCharacter.Randomly) {
            return new RandomPlayer(COLUMNS);
        }

        if (chara == AiCharacter.Strategic) {
            return new StrategicPlayer(COLUMNS);
            
        } else {
            return new StrategicPlayer(COLUMNS);
        }

    }
    
    /**
     * sets the ai character for the player one ai
     * @param com1Charakter the character of the ai
     */
    public void setCom1Charakter(AiCharacter character) {
        this.com1Charakter = character;
    }
    
    /**
     * sets the ai character for the player two ai
     * @param com1Charakter the character of the ai
     */
    public void setCom2Charakter(AiCharacter character) {
        this.com2Charakter = character;
    }
   
}
