package model;

import java.io.IOException;
import java.util.*;

/**
 * ComputerPlayer - This class extends the Player method and is used to structure the
 * computer player.
 * 
 * @author Byron Craig & Ryan Robertson
 * @version pa03 (11/5/12), pa04 (12/04/12)
 * 
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   TA Wes
 *         
 * Modifications:    resetTotalScore()
 *
 */
public class ComputerPlayer extends Player
{
    /**
     * constructor
     * 
     * @param d
     */
    public ComputerPlayer(Dictionary d)
    {
        // super
        super( d );

    } // method constructor

    
    /**
     * computeScore - computes the score for the computer.
     */
    public void computeScore()
    {
        // declarations, assignments
        int points = 0;
        
        // begin for
        for ( int i = 0; i < list.size(); i++ )
        {
            // switch case
            switch ( list.get( i ).length() )
            {
                case 0: case 1: case 2: points += 0; break;
                case 3: case 4:         points += 1; break;
                case 5:                 points += 2; break;
                case 6:                 points += 3; break;
                case 7:                 points += 5; break;
                default:                points += 11;

            } // end switch

        } // end for
        
        // scoring
        scores[ROUND] += points;
        scores[GAME] += points;
    
    } // method computeScore

    
    /**
     * getListSize
     *
     * return the size of the StringSet
     *
     * @return int
     */
    public int getListSize()
    {
        // return statement
        return list.size();

    } // method getList
    
    
    /**
     * getWord - gets the word from the list.
     * 
     * @param index
     */
    public String getWord(int index)
    {  
        // return statement
        return list.get(index);

    } // method getWord

    
    /**
     * removeCommonWords - removes the common words from scoring and display
     * 
     * @param commonWords
     */
    public void removeCommonWords(StringSet commonWords)
    {
        list = list.difference( commonWords );
        
    } // method removeCommonWords
    
    
    /**
     * reset - resets the round score to 0 at the start of every round.
     */
    public void reset()
    {
        // setting the round score to 0
        list.clear();
        scores[ROUND] = 0;

    } // method reset
    
    /**
     * resetTotalScore - Resets the total score
     * 
     */
    public void resetTotalScore()
    {
        scores[GAME] = 0;
    
    } // method resetTotalScore
    
    
    /**
     * setWords - gets a word from the dictionary thats on the board. This word 
     * is used by the computer.
     * 
     * @param dictionary, board
     * @throws IOException
     */
    public void setWords(Dictionary dictionary, Board board) throws IOException
    {
        // ArrayList declared and instantiated
        ArrayList<String> dict = dictionary.getDictionaryList();

        // begin for
        for(int i = 0; i < dict.size(); i++)
        {
            // if statement
            if(board.findWord( dict.get( i )))
            {
                list.add(dict.get(i));

            } // end if

        } // end for

    } // method findWords

} // class ComputerPlayer