package model;

/**
 * Player - This class is the parent player class to the two individual players
 * which are the human and the computer.
 * 
 * @author Michael Norton, Byron Craig, and Ryan Robertson
 * @version 11/5/12, 12/4/2012
 * 
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   TA Wes
 *         
 * Modifications:    computeRejectedScore
 */
abstract public class Player
{
    // declarations
    protected int[] scores;
    protected StringSet list;           // added for PA2
    protected Dictionary dictionary;
    
    // finals
    public final int ROUND = 0;
    public final int GAME = 1;
    
    /**
     * Constructor
     * 
     * ** modified for PA2 - added Dictionary d as parameter
     * 
     */
    public Player( Dictionary d )
    {
        list = new StringSet(); // generate new empty StringSet
        scores = new int[ 2 ] ;
        dictionary = d;

        scores[ ROUND ] = 0;      // init scores to 0
        scores[ GAME ] = 0;

    } // constructor
    
    // Abstract Methods
    abstract public int getListSize();

    abstract public String getWord(int index);
    
    abstract public void reset();
    
    /**
     * commonWords - uses the StringSets intersection to method to find words
     * found by both players
     * 
     * @param name
     * @return
     */
    public StringSet commonWords(Player name)
    {
        StringSet words = list.intersection( name.getList() );
        list = list.difference( words );
        return words;
        
    } // method commonWords
    
    /**
     * computeRejectedScore - Computes the score once the Rejected Words have
     * been taken out of the list.
     */
    public void computeRejectedScore()
    {
        // declarations & assignments
        int points = 0;
        
        // for loop
        for ( int i = 0; i < list.size(); i++ )
        {
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

        scores[ROUND] -= points;
        scores[GAME] -= points;

    } // method computeScore
    
    /**
     * computeScore
     *
     * computes the current round and game scores
     * ** Added for PA2
     */
    public void computeScore()
    {
        int points = 0;
        
        for ( int i = 0; i < list.size(); i++ )
        {
            System.out.println(list.get( i ));
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
        System.out.println( points );
        scores[ROUND] += points;
        scores[GAME] += points;

    } // method computeScore
    
    /**
     * differentWords - uses the StringSet defined difference method to get the
     * difference between two sets of words
     * 
     * @param name
     * @return
     */
    public StringSet differentWords(Player name)
    {
        return list.difference( name.getList() );
        
    } // method differentWords
    
    /**
     * getList - returns the list
     * 
     * @return
     */
    public StringSet getList()
    {
        return list;
        
    } // getList
    
    /**
     * getScore
     *
     * return the score array
     *
     * ** Added for PA2 **
     *
     * @return int[]
     */
    public int[] getScore()
    {
        return scores;

    } // method getWord
        
} // class Player