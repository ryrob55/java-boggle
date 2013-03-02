package view.gui;

import java.awt.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import controller.*;

/**
 * BoggleGUI - Creates the GUI for the Boggle game.
 * 
 * @author Byron Craig and Ryan Robertson
 * @version 12/4/2012
 *
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   TA Du
 *         
 * Modifications:    None
 */
@SuppressWarnings("serial")
public class BoggleGUI extends JFrame
{
    // ------------------------
    // Declarations for the GUI
    // ------------------------
    
    // Panels
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel playPanel;
    private JPanel boardPanel;
    private JPanel gamePanel;
    private JPanel scoringPanel;
    private JPanel roundPanel;
    
    // Borders
    private Border border;
    private Border loweredetched;
    private TitledBorder playBorder;
    private TitledBorder gameBorder;
    private TitledBorder scoringBorder;
    
    // TextAreas
    private JTextArea playWords;
    private JTextArea human;
    private JTextArea computer;
    private JTextArea common;
    private JTextArea invalid;
    private JTextArea rejectText;
    
    // Labels
    private JLabel diff;
    private JLabel pointsLabel;
    private JLabel roundNumLbl;
    private JLabel roundScoreLbl;
    private JLabel totalScoreLbl;
    private JLabel humanScoreLbl;
    private JLabel computerScoreLbl;
    private JLabel humanWordsLbl;
    private JLabel computerWordsLbl;
    private JLabel commonWordsLbl;
    private JLabel invalidWordsLbl;
    private JLabel gameStatus;
    private JLabel roundStatus;
    private JLabel humanRoundScore;
    private JLabel computerRoundScore;
    private JLabel humanTotalScore;
    private JLabel computerTotalScore;
    private JLabel roundNum;
    
    // ProgressBar
    private JProgressBar timer;
    
    // ScrollPanes
    private JScrollPane roundWords;
    private JScrollPane humanWords;
    private JScrollPane computerWords;
    private JScrollPane commonWords;
    private JScrollPane invalidWords;
   
    // Buttons
    private JButton[][] tileBtn;
    private JButton quitBtn;
    private JButton restartBtn;
    private JButton startGame;
    private JButton rejectWords;

    // RadioButtons
    private JRadioButton diff1;
    private JRadioButton diff2;
    private JRadioButton diff3;
    private JRadioButton diff4;
    private JRadioButton diff5;
    private JRadioButton diff6;
    private JRadioButton diff7;
    private JRadioButton diff8;
    private JRadioButton diff9;
    private JRadioButton diff10;
    
    // Spinner for Score
    private JSpinner points;
    private SpinnerModel pointsLimit;
    
    // BoggleListener object
    private BoggleListener listen;
    
    // ButtonGroup
    private ButtonGroup buttonGroup; 
    
    /**
     * constructor
     * 
     * @throws IOException 
     */
    public BoggleGUI() throws IOException
    {
        // Calling the methods
        createComponents();
        setComponents();
        setLayouts();
        addComponents();
        
        // Setting the mainPanel
        setContentPane(mainPanel);
        
        // Setting the listener
        setListeners();
        
        // CenterForm method
        centerForm();
        
        // Setting frame to be visible but not resizable
        setVisible(true);
        setResizable(false);
        
    } // constructor
     
    /**
     * addComponents - adds the components to the window
     */
    public void addComponents()
    {
        // -------------------------
        // Adding Panels and Buttons
        // -------------------------
        
        // Adding panels for the GUI board
        leftPanel.add( playPanel );
        mainPanel.add( leftPanel );
        playPanel.add( startGame );
        playPanel.add( boardPanel );
        mainPanel.add( rightPanel );
        rightPanel.add( gamePanel );
        rightPanel.add( scoringPanel );
        playPanel.add( timer );
        playPanel.add( roundWords );
        gamePanel.add( restartBtn );
        gamePanel.add( quitBtn );
        scoringPanel.add( roundPanel );
        
        // Adding objects to the roundPanel
        roundPanel.add( roundNumLbl );
        roundPanel.add( roundScoreLbl );
        roundPanel.add( totalScoreLbl );
        roundPanel.add( humanScoreLbl );
        roundPanel.add( computerScoreLbl );
        roundPanel.add( humanRoundScore );
        roundPanel.add( humanTotalScore );
        roundPanel.add( computerRoundScore );
        roundPanel.add( computerTotalScore );
        roundPanel.add( roundNum );
        
        // for loop and nested for loop to 
        // add the board to the tiles.
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                boardPanel.add( tileBtn [r][c]);
        
        // Adding buttons to the button group
        buttonGroup.add( diff1 );
        buttonGroup.add( diff2 );
        buttonGroup.add( diff3 );
        buttonGroup.add( diff4 );
        buttonGroup.add( diff5 );
        buttonGroup.add( diff6 );
        buttonGroup.add( diff7 );
        buttonGroup.add( diff8 );
        buttonGroup.add( diff9 );
        buttonGroup.add( diff10 );
        
        // GamePanel adding the RadioButtons
        gamePanel.add( diff1 );
        gamePanel.add( diff2 );
        gamePanel.add( diff3 );
        gamePanel.add( diff4 );
        gamePanel.add( diff5 );
        gamePanel.add( diff6 );
        gamePanel.add( diff7 );
        gamePanel.add( diff8 );
        gamePanel.add( diff9 );
        gamePanel.add( diff10 );
        gamePanel.add( diff );
        gamePanel.add( pointsLabel );
        gamePanel.add( points );
        
        // scoringPanel for scoring
        scoringPanel.add( humanWordsLbl );
        scoringPanel.add( computerWordsLbl );
        scoringPanel.add( commonWordsLbl );
        scoringPanel.add( invalidWordsLbl );
        scoringPanel.add( humanWords );
        scoringPanel.add( computerWords );
        scoringPanel.add( commonWords );
        scoringPanel.add( invalidWords );
        scoringPanel.add( rejectWords );
        scoringPanel.add( gameStatus );
        scoringPanel.add( roundStatus );
        scoringPanel.add( rejectText );
        
    } // method addComponents
     
    /**
     * centerForm - centers form on screen
     */
    public void centerForm()
    {
        // Dimensions
        Dimension dimScreenSize = 
            Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimFrameSize = getSize();

        // if statements
        if ( dimFrameSize.height > dimScreenSize.height )
            dimFrameSize.height = dimScreenSize.height;
        if ( dimFrameSize.width > dimScreenSize.width )
            dimFrameSize.width = dimScreenSize.width;

        setLocation( ( dimScreenSize.width - dimFrameSize.width ) / 2,
            ( dimScreenSize.height - dimFrameSize.height ) / 2 );

    } // method centerForm
    
    /**
     * createComponents - creates the components
     * 
     * @throws IOException 
     */
    public void createComponents() throws IOException
    {
        // --------------------------------------------
        // Initializing all of the new declared objects
        // --------------------------------------------
        
        // Panels initialized
        mainPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        playPanel = new JPanel();
        gamePanel = new JPanel();
        boardPanel = new JPanel();
        scoringPanel = new JPanel();
        roundPanel = new JPanel();
        
        // Buttons initialized
        startGame = new JButton("Start A New Round");
        restartBtn = new JButton("Restart");
        quitBtn = new JButton("Quit");
        rejectWords = new JButton("Reject Word");
        
        // TextArea initialized
        playWords = new JTextArea();
        human = new JTextArea();
        computer = new JTextArea();
        common = new JTextArea();
        invalid = new JTextArea();
        rejectText = new JTextArea();
        
        // Timer initialized
        timer = new JProgressBar(0,180);
        
        // ScrollPane initialized
        roundWords = new JScrollPane(playWords);
        humanWords = new JScrollPane(human);
        computerWords = new JScrollPane(computer);
        commonWords = new JScrollPane(common);
        invalidWords = new JScrollPane(invalid);
              
        // Labels initialized
        pointsLabel = new JLabel("Needed to Win:");
        diff = new JLabel("Difficulty:");
        roundNumLbl = new JLabel("Round:");
        roundScoreLbl = new JLabel("Round Score");
        totalScoreLbl = new JLabel("Total Score");
        humanScoreLbl = new JLabel("You:");
        computerScoreLbl = new JLabel("Computer:");
        humanWordsLbl = new JLabel("You");
        computerWordsLbl = new JLabel("Computer");
        commonWordsLbl = new JLabel("Common");
        invalidWordsLbl = new JLabel("Invalid");
        gameStatus = new JLabel("Game Status:");
        roundStatus = new JLabel("");
        humanRoundScore = new JLabel("0");
        humanTotalScore = new JLabel("0");
        computerRoundScore = new JLabel("0");
        computerTotalScore = new JLabel("0");
        roundNum = new JLabel("0");
        
        // Button array initialized
        tileBtn = new JButton[4][4];
        
        // Setting each tile with a default text
        for (int r = 0; r < 4; r++)
            for (int c= 0; c < 4; c++)
            {
                tileBtn[r][c] = new JButton("-");
            }
        
        // RadioButtons initialized
        diff1 = new JRadioButton("1");
        diff2 = new JRadioButton("2");
        diff3 = new JRadioButton("3");
        diff4 = new JRadioButton("4");
        diff5 = new JRadioButton("5");
        diff6 = new JRadioButton("6");
        diff7 = new JRadioButton("7");
        diff8 = new JRadioButton("8");
        diff9 = new JRadioButton("9");
        diff10 = new JRadioButton("10");
        
        // buttonGroup initialized
        buttonGroup = new ButtonGroup();
        
        // Spinner and Model initialized
        pointsLimit = new SpinnerNumberModel(100, 1, 10000, 1);
        points = new JSpinner(pointsLimit);
        
        // Creating the Borders
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        border = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        playBorder = BorderFactory.createTitledBorder( loweredetched, "Play" );
        gameBorder = BorderFactory.createTitledBorder( loweredetched, "Game" );
        scoringBorder = BorderFactory.createTitledBorder( loweredetched, 
                "Scoring" );
        
        // BoggleListener Object initialized
        listen = new BoggleListener(startGame,rejectWords, timer, playWords,
                tileBtn, restartBtn, quitBtn, roundStatus, human, 
                computer, common, invalid, roundNum, humanRoundScore, 
                computerRoundScore, humanTotalScore, computerTotalScore, points,
                diff5, rejectText);
    
    } // method createComponents
    
    /**
     * setComponents - sets size, borders, locations, and anything else for the 
     * look of the components in the window
     */
    public void setComponents()
    {
        // --------------------------------
        // Setting up all of the components 
        // --------------------------------
        
        // Frame size and title
        setSize(700,520);
        setTitle("Boggle");
        
        // Left Panel
        leftPanel.setSize( 230 , 500 );
        leftPanel.setLocation( 0 , 0 );
        
        // Right Panel
        rightPanel.setSize( 470, 500);
        rightPanel.setLocation( 231, 0 );
        
        // Play Panel
        playPanel.setSize( 215 , 467);
        playPanel.setBorder( playBorder );
        playPanel.setLocation( 13 , 15 );
        
        // Starting the game 
        startGame.setSize( 170 , 20 );
        startGame.setLocation( 22 , 25 );
        
        // Board Panel
        boardPanel.setSize( 170 , 170 );
        boardPanel.setLocation( 22 , 60 );
        boardPanel.setBorder( border );
        
        // Setting the Background color of each tile for the board
        for (int r = 0; r < 4; r++)
            for (int c= 0; c < 4; c++)
            {
                tileBtn[r][c].setBackground( Color.WHITE );
            }
        // end for loops
        
        // Setting up the timer
        timer.setSize( 170, 30 );
        timer.setLocation( 22 , 240 );
        timer.setStringPainted( true );
        timer.setString( "0:00" );
        
        // Setting Round Words
        roundWords.setSize( 170, 170 );
        roundWords.setLocation( 22, 285 );
        roundWords.setBorder( border );
        
        // Setting up the Game Panel
        gamePanel.setSize( 455, 110 );
        gamePanel.setLocation( 0 , 15 );
        gamePanel.setBorder( gameBorder );
        
        // Setting up the Scoring Panel
        scoringPanel.setSize( 455 , 352 );
        scoringPanel.setLocation( 0 , 130 );
        scoringPanel.setBorder( scoringBorder );
        
        // Restart Button size and location are set
        restartBtn.setSize( 80 , 20 );
        restartBtn.setLocation( 280, 70 );
        
        // Quit Button set
        quitBtn.setSize( 65 , 20 );
        quitBtn.setLocation( 370, 70 );
        
        // Round Panel set
        roundPanel.setSize( 425, 80 );
        roundPanel.setLocation( 15, 25 );
        roundPanel.setBorder( border );
        
        // Setting the size and location of the Radio Buttons
        diff.setSize( 80,25 );
        diff.setLocation( 14,27 );
        diff1.setSize( 40,17 );
        diff1.setLocation( 80,30 );
        diff2.setSize( 40,17 );
        diff2.setLocation( 115,30 );
        diff3.setSize( 40,17 );
        diff3.setLocation( 150,30 );
        diff4.setSize( 40,17 );
        diff4.setLocation( 185,30 );
        diff5.setSize( 40,17 );
        diff5.setLocation( 220,30 );
        diff5.setSelected( true );
        diff6.setSize( 40,17 );
        diff6.setLocation( 255,30 );
        diff7.setSize( 40,17 );
        diff7.setLocation( 290,30 );
        diff8.setSize( 40,17 );
        diff8.setLocation( 325,30 );
        diff9.setSize( 40,17 );
        diff9.setLocation( 360,30 );
        diff10.setSize( 48,17 );
        diff10.setLocation( 395,30 );
        
        // Points Label set
        pointsLabel.setSize( 95, 20 );
        pointsLabel.setLocation( 14, 70 );
        
        // Points are set
        points.setSize( 95,22 );
        points.setLocation( 115,70 );
        
        // Round Number Label is set
        roundNumLbl.setSize( 44, 15 );
        roundNumLbl.setLocation( 20, 27 );
        
        // Round Number is set
        roundNum.setSize( 20, 15 );
        roundNum.setLocation( 70, 27 );
        
        // Round Score Label is set
        roundScoreLbl.setSize( 85, 15 );
        roundScoreLbl.setLocation( 240, 10 );
        
        // Human Round Score is set
        humanRoundScore.setSize( 30, 15 );
        humanRoundScore.setLocation( 279, 27 );
        
        // Computer Round Score is set
        computerRoundScore.setSize( 30, 15 );
        computerRoundScore.setLocation( 279, 43 );
        
        // Total Score Label is set
        totalScoreLbl.setSize( 85, 15 );
        totalScoreLbl.setLocation( 335, 10 );
        
        // Human Total Score is set
        humanTotalScore.setSize( 30, 15);
        humanTotalScore.setLocation( 368, 27 );
        
        // Computer Total Score is set
        computerTotalScore.setSize( 30, 15 );
        computerTotalScore.setLocation( 368, 43 );
        
        // Label for Human Score is set
        humanScoreLbl.setSize( 30, 15 );
        humanScoreLbl.setLocation( 190, 27 );
        
        // Label for Computer Score is set
        computerScoreLbl.setSize( 70, 15 );
        computerScoreLbl.setLocation( 152, 43 );
        
        // The label words for the human are set
        humanWordsLbl.setSize( 30, 15 );
        humanWordsLbl.setLocation(50, 113);
        
        // Label for Computer Words are set
        computerWordsLbl.setSize( 70, 15 );
        computerWordsLbl.setLocation(140, 113);
        
        // Common Words Label is set
        commonWordsLbl.setSize( 65, 15 );
        commonWordsLbl.setLocation( 245, 113 );
        
        // Invalid Words Label is set
        invalidWordsLbl.setSize( 60, 15 );
        invalidWordsLbl.setLocation( 365, 113 );
        
        // Human Words now set
        humanWords.setSize( 100, 160 );
        humanWords.setLocation( 15, 128 );
        
        // Computer Words now set
        computerWords.setSize( 100, 160 );
        computerWords.setLocation( 120, 128 );
        
        // Common Words now set
        commonWords.setSize( 100, 160 );
        commonWords.setLocation( 225, 128 );
        
        // Invalid Words are now set
        invalidWords.setSize( 100, 190);
        invalidWords.setLocation( 340, 128 );
        
        // Reject Words now set
        rejectWords.setSize( 125, 20 );
        rejectWords.setLocation( 40, 293 );
        
        // Reject Text is set
        rejectText.setSize( 125, 20 );
        rejectText.setLocation( 180, 293 );
        rejectText.setBorder( border );
        
        // Game Status is now set
        gameStatus.setSize( 90, 15 );
        gameStatus.setLocation( 135, 325 );
        
        // Round Status is now set
        roundStatus.setSize( 150 , 15 );
        roundStatus.setLocation( 225, 325 );
        
    } // method setComponents
    
    /**
     * setLayouts - sets the layouts for the panels
     */
    public void setLayouts()
    {
        // ---------------------------------
        // Setting the Layouts of the Panels
        // ---------------------------------
        mainPanel.setLayout( null );
        leftPanel.setLayout( null );
        rightPanel.setLayout( null );
        gamePanel.setLayout( null );
        playPanel.setLayout( null );
        boardPanel.setLayout( new GridLayout(4,4) );
        scoringPanel.setLayout( null );
        roundPanel.setLayout( null );
        
    } // method setLayouts
    
    /**
     * setListeners - Sets the Listeners of each object.
     */
    public void setListeners()
    {
        // ---------------------------------
        // Adding ActionListeners to Buttons
        // ---------------------------------
        
        // JButtons
        startGame.addActionListener(listen);
        rejectWords.addActionListener(listen);
        restartBtn.addActionListener(listen);
        quitBtn.addActionListener(listen);
        
        // JRadioButtons
        diff1.addActionListener(listen);
        diff2.addActionListener(listen);
        diff3.addActionListener(listen);
        diff4.addActionListener(listen);
        diff5.addActionListener(listen);
        diff6.addActionListener(listen);
        diff7.addActionListener(listen);
        diff8.addActionListener(listen);
        diff9.addActionListener(listen);
        diff10.addActionListener(listen);
        
        // Points
        points.addChangeListener(listen);
        
        // Adding a WindowListener for the Frame
        addWindowListener(listen);
        
    } // method setListeners    
    
} // class BoggleGUI