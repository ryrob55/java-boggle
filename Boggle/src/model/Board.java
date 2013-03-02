package model;

import java.io.IOException;
import java.util.*;

/**
 * Board
 * 
 * This class stores the game board (4x4 array). It is responsible for
 * shuffling and mixing letters and for providing client access to
 * individual letters.
 * 
 * @author Michael Norton, Byron Craig, and Ryan Robertson
 * @version PA01 (09/09/2012), PA03 (10/25/2012)
 * 
 * Acknowledgments: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 * 
 *                   TA Wes and Xing
 * 
 * Modifications: findWord, isWord methods
 */
public class Board
{
    // ----------------------------------------------------------------------
    // Declarations
    // ----------------------------------------------------------------------
    private char[][] board;
    private Random random; // we only want a single Random object!!!

    public final int X_VALUE = 4;
    public final int Y_VALUE = 4;

    public final String LETTERS = "JKQYZ" + "BCFGMPV" + "BCFGMPV" + "DUWX"
            + "DUWX" + "DUWX" + "HLR" + "HLR" + "HLR" + "HLR" + "HLR"
            + "AINSO" + "AINSO" + "AINSO" + "AINSO" + "AINSO" + "AINSO"
            + "ET" + "ET" + "ET" + "ET" + "ET" + "ET" + "ET" + "ET" + "ET"
            + "ET";


    /**
     * Default constructor
     */
    public Board()
    {
        board = new char[X_VALUE][Y_VALUE];
        random = new Random();

        mix();

    } // constructor


    /*************************** public methods ***************************/

    /**
     * findWord - finds the first letter of the word and checks to see if it
     * is on the board and then sends the cut down word to isWord to see if
     * the word is valid
     * 
     * @param word
     * @return
     * @throws IOException
     */
    public boolean findWord(String word) throws IOException
    {
        // declarations & assignments
        boolean position = false;

        if(word != null)
        {
            boolean[][] isUsed = new boolean[X_VALUE][Y_VALUE];
            int row = 0;
            int col = 0;

            // begin for
            for (int i = 0; i < X_VALUE; i++)
                for (int ii = 0; ii < Y_VALUE; ii++)
                    isUsed[ i ][ ii ] = false;

            while( row < 4 && !position)
            {
                while( col < 4 && !position)
                {
                    position = isWord( word, row, col, isUsed);
                    col++;
                }

                row++;
                col = 0;
            }

        }
        // return statement
        return position;

    } // method findWord


    /**
     * getCell - Returns the requested cell to the client. If the cell does
     * not exist, returns null character (which is the same as null)
     * 
     * @return char
     */
    public char getCell(int x, int y)
    {
        char cell = (char) 0;

        if ( isInBounds( x , y ) )
            cell = board[ x ][ y ];

        return cell;

    } // method getCell


    /**
     * isWord - takes the position of the first letter and looks around it
     * on the board to search for the next letter and so on to find the word
     * 
     * @param word
     * @param row
     * @param col
     * @return
     */
    public boolean isWord(String word, int row, int col, boolean[][] isUsed)
    {
        // boolean
        boolean wordCheck = false;

        // begin if statement
        if ( word != null )
        {
            word = word.toUpperCase();

            if(word.length() > 0 && word.charAt( 0 ) == getCell(row,col) )
            {
                if(!isUsed[row][col])
                {
                    isUsed[row][col] = true;

                    if( word.length() == 1)
                        wordCheck = true;
                    else
                    {
                        int checkRow = 0;
                        int checkCol = 0;
                        int startRow = row - 1;
                        int startCol = col - 1;

                        while ( checkRow < 3 && !wordCheck )
                        {
                            while ( checkCol < 3 && !wordCheck )
                            {
                                wordCheck = isWord( word.substring(1),
                                        startRow + checkRow,
                                        startCol + checkCol,
                                        isUsed );

                                checkCol++;

                            } // end while

                                checkRow++;
                                checkCol = 0;

                        } // end while

                    } // end else

                }// end if

            } // end if
        
        } // end if
        
            return wordCheck;

        } // method isWord


        /**
         * mix - Generates a new mixed board
         */
        public void mix()
        {
            for (int x = 0; x < board.length; x++)
            {
                for (int y = 0; y < board[ 0 ].length; y++)
                    board[ x ][ y ] = getRandomChar();
            }

        } // method mix


        /************************** private methods ***************************/

        /**
         * getRandomChar - Returns a random char from the String
         * 
         * @return a randomchar
         */
        private char getRandomChar()
        {
            return LETTERS.charAt( random.nextInt( LETTERS.length() ) );

        } // getRandomChar


        /**
         * isInBounds - Returns true if the x,y coordinates are in bounds for
         * the array
         * 
         * @param x
         *            value
         * @param y
         *            value
         * @return true if x and y are in bounds
         */
        private boolean isInBounds(int x, int y)
        {
            return (x > -1 && x < X_VALUE && y > -1 && y < Y_VALUE);

        } // method isInBounds

    } // class Board
