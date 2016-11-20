/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4.AI.Model;

import connect4.AI.Model.Rating;

/**
 * this class holds a possible move as well as its rating.
 *
 * @author sam
 */
public class PossibleMove {

    private int move;
    private double rating;

    /**
     * constructs a new possible move in the given column.
     *
     * @param move
     */
    public PossibleMove(int move) {
        this.move = move;
        this.rating= 0;
    }

    /**
     * returns a possible move.
     *
     * @return
     */
    public int getMove() {
        return move;
    }

    /**
     * returns the current rating of this move.
     * If a negative value is returned, it means that this move should not
     * be used.
     *
     * @return
     */
    public double getRating() {
        return rating;
    }

    /**
     * adds the given amount to the accumulated rating.
     *
     * @param amount amount to add
     */
    public void addToRating(double amount) {
        this.rating += amount;
    }

    /**
     * sets the rating of this move to zero.
     */
    public void resetRating() {
        this.rating = Rating.ZERO;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Move{" + "move=" + move + ", rating=" + rating + '}';
    }
}
