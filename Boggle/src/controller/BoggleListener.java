package controller;

import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.*;
import view.*;
import model.*;

/**
 * BoggleListener - This handles the listeners for the BoggleGUI class.
 * 
 * @author Michael Norton, Byron Craig, and Ryan Robertson
 * @version 11/16/2009, 12/4/2012
 *
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   TA Du & Wes
 *         
 * Modifications:    None
 */
public class BoggleListener extends WindowAdapter implements TickListener,
ActionListener, ChangeListener
{
    // ------------
    // Declarations
    // ------------
    
    // Class objects
    private Dictionary dict;
    private EggTimer timer;
    private FileIO file;
    private Game game;
    private Player computer;
    private Player human;
    
    private JFrame endGame;
    private JPanel endGamePanel;
    
    // JProgressBar
    private JProgressBar timerBar; 
    
    // JButtons, JLabels, & JRadioButtons
    private JButton[][] board;
    private JButton quit;
    private JButton reject;
    private JButton restart;
    private JButton start;
    private JLabel compRoundScore;
    private JLabel compTotalScore;
    private JLabel endGameStatus;
    private JLabel humRoundScore;
    private JLabel humTotalScore;  
    private JLabel roundNumber;
    private JLabel status;
    private JRadioButton difficulty5;
    
    // JTextArea
    private JTextArea commonText;
    private JTextArea computerText;
    private JTextArea humanText;
    private JTextArea invalidText;
    private JTextArea rejectTextArea;
    private JTextArea roundText;
 
    // JSpinner
    private JSpinner gamePoints;

    // Difficulty Level
    private int difficultyLevel;

    // Points To Win
    private int pointsToWin;

    // Finals that are assigned
    private final int DIFFICULTY_LEVEL = 5;
    private final int INPUT_TIME = 180;
    private final int POINTS_TO_WIN = 100;
    public static final int ALL_WORDS = 0;   // added for PA3
    public static final int INVALID_WORDS = 1;  // added for PA3
    public static final int HUMAN_WORDS = 2;  // added for PA3
    public static final int COMPUTER_WORDS = 3; // added for PA3

    // Word Lists
    private String badWordList = "";
    private String compWordList = "";
    private String humanWordList = "";

    /**
     * Explicit value constructor
     * 
     * @param the start button
     * @param the progress bar
     * @param the text area
     * @throws IOException 
     */
    public BoggleListener( JButton startGame, JButton rejectWords, JProgressBar 
            timer, JTextArea roundWords, JButton[][] tileBtn, JButton restartBtn,
            JButton quitBtn, JLabel roundStatus, JTextArea humanWords, JTextArea 
            computerWords, JTextArea commonWords, JTextArea invalidWords, JLabel 
            roundNum, JLabel humanRoundScore, JLabel computerRoundScore,
            JLabel humanTotalScore, JLabel computerTotalScore, JSpinner points,
            JRadioButton diff5, JTextArea rejectText) throws IOException
            {   
        // initializations
        file = new FileIO( "dictionary.txt" );
        dict = Dictionary.getDictionary();
        game = new Game();
        human = game.getPlayer( Game.HUMAN );
        computer = game.getPlayer( Game.COMPUTER );
        endGame = new JFrame();
        endGameStatus = new JLabel();
        endGamePanel = new JPanel();

        // Assignments to new variables
        start = startGame;
        reject = rejectWords;
        timerBar = timer;
        roundText = roundWords;
        board = tileBtn;
        restart = restartBtn;
        quit = quitBtn;
        status = roundStatus;
        roundText = roundWords;
        humanText = humanWords;
        computerText = computerWords;
        commonText = commonWords;
        invalidText = invalidWords;
        roundNumber = roundNum;
        humRoundScore = humanRoundScore;
        compRoundScore = computerRoundScore;
        humTotalScore = humanTotalScore;
        compTotalScore = computerTotalScore;
        gamePoints = points;
        difficulty5 = diff5;
        rejectTextArea = rejectText;

        // Assignments to finals
        difficultyLevel = DIFFICULTY_LEVEL;
        pointsToWin = POINTS_TO_WIN;

        // if statement reading the dictionary
        if(!readFile())
        {
            System.out.println( "No Dictionary!" );
            System.exit( 1 );
        
        
            
        } // end if
        
        setEndGameComponents();
            } //  method constructor

/*********************************Public Methods*******************************/

    /**
     * Runs when the Start button is pressed
     * 
     * @param the Action Event object
     * @see java.awt.event.ActionListener#
     *      actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        // if/else if statement
        if (e.getSource() instanceof JButton)
        {
            // try/catch statement
            try
            {
                handleJButtons(e);
            }
            catch ( IOException e1 )
            {
                e1.printStackTrace();

            } // end try/catch

        }
        else if ( e.getSource() instanceof JRadioButton)
            handleJRadioButtons(e);
        // end if/else if

    } // method actionPerformed

    /**
     * stateChanged - Handles when the score to win is changed.
     */
    @Override
    public void stateChanged(ChangeEvent e)
    {
        // JSpinner
        JSpinner spinner = (JSpinner)e.getSource();
        String num = spinner.getValue().toString();
        pointsToWin = Integer.parseInt(num);

    } // method stateChanged
    
    /**
     * Runs every second - uses the EggTimer
     * 
     * @param the Egg timer
     * @see TickListener#tick(EggTimer)
     */
    public void tick(EggTimer timer)
    {
        // Sets the Timer
        timerBar.setValue( INPUT_TIME - timer.getSecondsLeft() );
        timerBar.setString( timer.getTimeLeft() );

        // if statement
        if ( timer.getSecondsLeft() == 0 )
        {
            // Setting buttons enabled
            start.setEnabled( true );
            reject.setEnabled( true );
            rejectTextArea.setEnabled( true );
            
            // Setting the status
            status.setText("Round Over");

            // do end of round/game stuff
            endRound();

        } // end if

    } // method tick
    
    /**
     * windowClosing - Handle window closing actions
     * 
     * @param the Window Event object
     * @see java.awt.event.WindowAdapter#
     *      windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing( WindowEvent e )
    {
        // Exits the system
        System.exit( 0 );
        
    } // method windowClosing
    
/******************************Private Methods*********************************/    
    
    /**
     * addWordsToPlayer
     *
     * add the list of words from the player to the Player's word list
     * 
     * ** modified for PA2 -- added difficulty level to param of player.add
     *
     * @param words (String)
     * @throws IOException 
     */
    private void addWordsToPlayer( String words ) throws IOException
    {   
        // Tokenizer  when adding the words
        StringTokenizer token = new StringTokenizer(words, "\n ");

        // if statement
        if ( words != null )
        {
            // while loop
            while (token.hasMoreTokens())
            {
                String word = token.nextToken();
                ((HumanPlayer) human).add( word, game.getDifficulty() );

            } // end while
            
        } // end if

    } // method addWordsToPlayer
     
    /**
     * clearTextFields - Clears all of the text areas.
     */
    private void clearTextFields()
    {
        // TextAreas have been set to null
        roundText.setText( null );
        humanText.setText( null );
        computerText.setText( null );
        commonText.setText( null );
        invalidText.setText( null );

        // Round Scores have been set to 0
        humRoundScore.setText( "0" );
        compRoundScore.setText( "0" );

    } // method clearTextFields
    
    /**
     * determineWinner - if there is a winner it displays game and resets game
     * 
     */
    private void displayWinner(int[] hScores, int[] cScores)
    {
     // end game if there is a winner
        if(hScores[human.GAME] == cScores[computer.GAME])
        {
            if(hScores[human.GAME] >= pointsToWin)
            {
                if(cScores[computer.GAME] >= pointsToWin)
                {
                    endGameStatus.setText( "It's a tie!" );
                    
                    JOptionPane.showMessageDialog( endGame , endGameStatus, 
                            "Game Over!", JOptionPane.WARNING_MESSAGE);
                    restartGame();
                    
                }
            }
        }
        else if ( hScores[ human.GAME ] >= pointsToWin )
        {
            endGameStatus.setText( "You win!" );
            
            JOptionPane.showMessageDialog( endGame , endGameStatus, 
                    "Game Over!", JOptionPane.WARNING_MESSAGE);
            restartGame();
            
        }
        else if(cScores[computer.GAME] >= pointsToWin )
        {
            endGameStatus.setText( "The computer wins!" );
            
            JOptionPane.showMessageDialog( endGame , endGameStatus, 
                    "Game Over!", JOptionPane.WARNING_MESSAGE);
            restartGame();
            
        } // end if/else if
    }
  
    /**
     * displayCommonWords - Displays the Common words between the two players.
     */
    private void displayCommonWords()
    {
        // StringSet getting commonwords
        StringSet words = human.commonWords( computer );
        ((ComputerPlayer) computer).removeCommonWords(words);

        // Putting the common words in the text area
        commonText.setText(words.toString() + "\n");

    } // method displayCommonWords
    
    /**
     * displayComputerScore - Displays the computer's score
     * 
     * @param cScores
     */
    private void displayComputerScore( int[] cScores )
    {
        // Both the round score and total score
        compRoundScore.setText( Integer.toString( cScores[computer.ROUND] ) );
        compTotalScore.setText( Integer.toString( cScores[computer.GAME] ) );

    } // method displayComputerScore
     
    /**
     * displayComputerWords - Displays the computer's words in the text area.
     */
    private void displayComputerWords()
    {
        // for loop for displaying computer words
        for(int i = 0; i < computer.getListSize(); i++)
        {          
            compWordList += computer.getWord(i) + "\n";

        }// end for

        // setting the computer words to the list
        computerText.setText(compWordList);

    } // method displayComputerWords
  
    /**
     * displayHumanScore - Displays the Human's words.
     * 
     * @param hScores
     */
    private void displayHumanScore( int[] hScores )
    {
        // Both the round score and total score
        humRoundScore.setText( Integer.toString( hScores[human.ROUND] ) );
        humTotalScore.setText( Integer.toString( hScores[human.GAME] ) );

    } // method displayHumanScore
    
    /**
     * displayHumanWords - Displays the Human words in the text area.
     * 
     * @throws IOException
     */
    private void displayHumanWords() throws IOException
    {
        // Getting the game board
        Board board = game.getBoard();

        // for loop
        for(int i = 0; i < human.getListSize(); i++)
        {    
            // if statement
            if(board.findWord(human.getWord(i) ) )
            {
                humanWordList += human.getWord(i) + "\n";

            }// end if

        }// end for

        // Setting the human words to the list
        humanText.setText(humanWordList);

    } // method displayHumanWords

    /**
     * displayInvalidWords - Displays the invalid words that the recursion
     * could not find on the board.
     * 
     * @throws IOException
     */
    private void displayInvalidWords() throws IOException
    {
        // Getting the game board
        Board board = game.getBoard();

        // for loop
        for( int i = 0; i < human.getListSize(); i++ )
        {
            // if statement
            if(!board.findWord( human.getWord( i ) ) )
            {
                badWordList += human.getWord( i ) + "\n";
                ((HumanPlayer) human).remove( human.getWord( i ) );

            } // end if

        } // end for

        // Setting the invalid words to the list
        invalidText.setText(badWordList);

    } // method displayInvalidWords

    /**
     * endRound - Ends the round for the game.
     * 
     */
    private void endRound()
    {   
        // Disables showing the roundText
        roundText.setEnabled( false );

        rejectTextArea.requestFocus();
        
        // Beep
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.beep();

        // try/catch statement
        try
        {
            addWordsToPlayer(roundText.getText());

        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();

        } // end try/catch

        // try/catch statement
        try
        {
            displayHumanWords();
            displayInvalidWords();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        } // end try/catch

        // Displays both the Computer and Common words
        displayComputerWords();
        displayCommonWords();

        // calculate score
        computer.computeScore();
        human.computeScore();

        // display scores
        displayHumanScore( human.getScore() );
        displayComputerScore( computer.getScore() );

        // Setting the status to round over
        status.setText("Round Over");
        
        displayWinner(human.getScore(), computer.getScore());

    } // method endRound

    /**
     * handleJButtons - Method that handles the JButtons
     * 
     * @param e
     * @throws IOException 
     */
    private void handleJButtons(ActionEvent e) throws IOException
    {
        // if statement
        if ( e.getSource() == start )
        {
            playRound();

        } // end if 

        // if statement for rejecting
        if ( e.getSource() == reject )
        {
            rejectWords();

        } // end if

        // if statement to restart the game
        if ( e.getSource() == restart )
        {
            game.newGame();

            restartGame();

        }// end if 

        // if statement to quit
        if ( e.getSource() == quit )
        {
            writeFile();
            System.exit( 0 );

        } // end if

    } // method handleJButtons

    /**
     * handleJRadioButtons - Handles the JRadioButtons
     * 
     * @param e
     */
    private void handleJRadioButtons(ActionEvent e)
    {
        // Getting the JRadioButton
        JRadioButton j = (JRadioButton)e.getSource();

        // Getting the text of the String
        String jname = j.getText(); 

        // Converting the int to a String
        difficultyLevel = Integer.parseInt(jname);

    } // method handleJRadioButtons
    
    /**
     * playRound - Begins the round so the player may play.
     * 
     * @throws IOException
     */
    private void playRound() throws IOException
    {   
        // Resets the human and computer
        human.reset();
        computer.reset();

        // Resets the word lists
        humanWordList = "";
        compWordList = "";
        badWordList = "";

        // calls the playRound method from Game
        game.playRound();

        // clear the text fields 
        clearTextFields();

        // disable Start and Reject Words buttons
        start.setEnabled( false );
        reject.setEnabled( false );
        rejectTextArea.setEnabled( false );
        status.setText("Round In Progress");

        // populate Board
        populateBoard();

        // set round number
        setRoundNumber();

        //set computer words
        game.computerSetWords();

        // set difficulty and points to win
        game.setDifficulty(difficultyLevel);
        game.setPointsToWin(pointsToWin);

        // stop timer if its going
        if( timer != null)
        {
            timer.stop();
            timer.removeTickListener( this );
            timer = null;

        } // end if

        // init the timer
        timer = new EggTimer( INPUT_TIME );
        timer.addTickListener( this );
        timerBar.setString( "3:00" );

        // Enables the roundText
        roundText.setEnabled( true );

        // move focus to the text area for input
        roundText.requestFocus();

    } // method playRound

    /**
     * populateBoard - Populates the board to begin.
     */
    private void populateBoard()
    {
        // Getting the game board
        Board gameBoard = game.getBoard();

        // for loop and nested for
        for( int r = 0; r < 4; r++)
        {
            for( int c = 0; c < 4; c++)
            {
                board[r][c].setText(gameBoard.getCell( r, c ) + "");

            } // end nested for
         
        } // end for
        
    } // method populateBoard
    
    /**
     * readFile - Reads file and populate Dictionary
     * 
     * @return true if the file was read successfully
     */
    private boolean readFile() throws IOException
    {
        // Getting the Dictionary
        Dictionary dict = game.getDictionary();

        // boolean to read file
        boolean canRead = file.openReader();

        // if statement
        if (canRead)
        {
            // String declared
            String word = file.readLine();
            
            // while loop
            while ( word != null )
            {
                dict.add( word );
                word = file.readLine();

            } // end while

            // Closes the file reader
            file.closeReader();

        } // end if

        // return statement
        return canRead;

    } // method readFile

    /**
     * rejectWords - removes words from user to remove from the dictionary
     */
    private void rejectWords()
    {
        // declarations & assignments
        StringTokenizer token;
        String entry = rejectTextArea.getText();
        String word = "";

        // if statement
        if ( entry != null ) // to catch user attempt to escape out
        {
            // token initialized
            token = new StringTokenizer( entry );

            // while loop
            while ( token.hasMoreTokens() )
            {
                // String assigned
                word = token.nextToken();
                
                // for loop
                for(int i = 0; i < human.getListSize(); i++)
                { 
                    // if statement
                    if(!word.equals( human.getWord( i ) ))
                    {    
                        ((HumanPlayer) human).remove( human.getWord( i ) );

                    } // end if

                } // end for
                
                // Computes the rejected score of the human
                human.computeRejectedScore();

            } // end while

            // Displays the human score
            displayHumanScore( human.getScore() );

            // Resets the rejectTextArea
            rejectTextArea.setText( " " );
            
        } // end if

    } // method rejectWords

    /**
     * restartGame - restarts the game.
     */
    private void restartGame()
    {
        // Resets the scores
        humTotalScore.setText( "0" );
        humRoundScore.setText( "0" );
        compRoundScore.setText( "0" );
        compTotalScore.setText( "0" );
        roundNumber.setText("0");
        status.setText( " " );

        // Resets the human and computer
        human.reset();
        computer.reset();
        ((HumanPlayer) human).resetTotalScore();
        ((ComputerPlayer) computer).resetTotalScore();

        // Clears all text fields
        clearTextFields();

        // for loop with nested for to clear board
        for( int r = 0; r < 4; r++)
        {
            // nested for
            for( int c = 0; c < 4; c++)
            {
                board[r][c].setText("-");

            } // end for
            
        } // end for
        
        // Sets the default difficulty and points
        difficulty5.setSelected(true);
        gamePoints.setValue( POINTS_TO_WIN );
        
    } // method restartGame

    /**
     * setEndGameComponents - set the JFrame components 
     */
    private void setEndGameComponents()
    {
        endGame.setSize( 400, 300 );
        endGame.setLayout(null);
        endGame.add( endGamePanel );
        
        endGameStatus.setSize(40,15);
        endGameStatus.setLocation( 200, 150 );
        endGameStatus.setText( "Hello " );
        
    }
    
    /**
     * setRoundNumber - Sets the round number for the game
     */
    private void setRoundNumber()
    {
        
        roundNumber.setText( Integer.toString( game.getRoundNumber() ) );

    } // method setRoundNumber
    
    /**
     * writeFile - Write file from Dictionary - if over 300 words randomly 
     *             select 300 without writing duplicates
     *
     * ** added for PA2
     *
     * @return true if the file was written successfully
     * @throws IOException
     */
    private boolean writeFile() throws IOException
    {
        // boolean
        boolean canWrite = file.openWriter();
        
        // ArrayList declared and initialized
        ArrayList< String > dictList = dict.getDictionaryList();

        // if statement
        if ( canWrite )
        {
            // 300 or fewer, write everything
            if ( dictList.size() <= 300 )
            {
                // for loop
                for ( int i = 0; i < dictList.size(); i++ )
                    file.write( dictList.get(i) );
            }
            else // otherwise randomly write 300
            {
                // declarations, & initializations
                Random rand = new Random();
                int[] tracker = new int[ dictList.size() ]; // store slots used
                int counter = 0;
                int index = 0;

                // while loop
                while ( counter < 300 )
                {
                    // random int
                    index = rand.nextInt( dictList.size() );

                    // write this if it hasn't already been written
                    if ( tracker[ index ] != 1 )
                    {
                        file.write( dictList.get(index) );
                        tracker[ index ] = 1;
                        counter++;

                    } // end if

                } // end while

            } // end else

            // Closes the file writer
            file.closeWriter();

        } // end if

        // return statement
        return canWrite;

    } // method writeFile

} // class BoggleListener