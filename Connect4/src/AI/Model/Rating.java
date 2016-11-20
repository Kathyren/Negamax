/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4.AI.Model;

/**
 * This class contains values which may be used to rate moves.
 *
 * @author Fival
 */
public final class Rating {

    /**
     * a very high value which may be used to indicate a win.
     * It is not guaranteed that this rating will always be the highest or lead
     * to a move instantly.
     */
    public static final int WIN = 100000;

    /**
     * a very high value which may be used to indicate a block.
     * It is not guaranteed that this rating will always be the highest or lead
     * to a move instantly.
     */
    public static final int BLOCK = 10000;

      /**
      * this value is the highest value.
      */
     public static final int VERYHIGH = 1000;
      /**
      * this value is lower than <code>VERYHIGH</code>,
      * but higher than <code>MEDIUM</code>.
      */
     public static final int HIGH = 500;

      /**
      * this value is lower than <code>HIGH</code>,
      * but higher than <code>LOW</code>.
      */
     public static final int MEDIUM = 100;
         /**
      * this value is lower than <code>MEDIUM</code>,
      * but higher than <code>VERYLOW</code>.
      */
     public static final int LOW = 40;
         /**
      * this value is lower than <code>LOW/code>,
      * but higher than <code>ZERO</code>.
      */
     public static final int VERYLOW = 10;
     /**
      * the lowest value.
      */
     public static final int ZERO = 0;
}
