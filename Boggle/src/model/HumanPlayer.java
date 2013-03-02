package model;

import java.util.Random;

/**
 * HumanPlayer - This is the child class from Player which only controls the
 * human player. The computer player has its own child class as well.
 * 
 * @author Byron Craig & Ryan Robertson
 * @version PA03 (10/29/12), PA04 (12/4/2012)
 * 
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   TA Wes
 *         
 * Modifications:    resetTotalScore
 */
public class HumanPlayer extends Player
{
    /**
     * constructor
     * 
     * @param d
     */
    public HumanPlayer(Dictionary d)
    {
        // super which extends Player
        super( d );

    } // method constructor
    
    /**
     * add
     *
     * ** modified for PA2 - added dictionary.add(s) & difficulty code
     * ** added 2nd parameter to get difficulty level
     * add word to list
     *
     *
     * @param s (String)
     * @param difficulty (int)
     */
    public void add( String s, int difficulty )
    {
        // random generator
        Random rand = new Random();

        // add to the StringSet
        list.add(s);

        // factor in difficulty level when deciding to add to dictionary
        if ( rand.nextInt( 10 ) < difficulty )
            dictionary.add( s) ;

    } // method add

    /**
     * getListSize
     *
     * return the size of the StringSet
     *
     * @return int
     */
    public int getListSize()
    {
        return list.size();

    } // method getList

    /**
     * getWord
     *
     * return a single word from the StringSet
     *
     * @param index (int)
     * @return String
     */
    public String getWord(int index)
    {
        return list.get( index );

    } // method getWord

    /**
     * remove
     *
     * ** added for PA2
     * remove words from lists/dictionary
     *
     * @param s (String)
     */
    public void remove(String s)
    {
        if (s != null)
        {
            // remove from dictionary only if added in this round
            if ( list.remove( s ) )
                dictionary.remove( s );

        } // end if

    } // method remove

    /**
     * reset
     *
     * ** Added for PA2 **
     */
    public void reset()
    {
        list.clear();
        scores[ ROUND ] = 0;

    } // method reset
    
    /**
     * resetTotalScore - Resets the total score
     * 
     */
    public void resetTotalScore()
    {
        scores[GAME] = 0;
        
    } // method resetTotalScore

} //  Class HumanPlayer