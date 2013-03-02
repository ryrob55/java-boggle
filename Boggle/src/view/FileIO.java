package view;

import java.io.*;

/**
 * FileIO
 *
 * File I/O for Boggle Game
 *
 * @author Michael Norton
 * @version pa02 (10/11/2012)
 */
public class FileIO
{

    //---------------------------------------------------------------------
    // Declarations
    //---------------------------------------------------------------------
    private BufferedReader reader;  // the reader object
    private BufferedWriter writer;  // the writer object
    private File inFile;
    private File outFile;


    /**
     * constructor
     */
    public FileIO(String inName)
    {
        String outName;

        if ( inName != null )
        {
            // create temp file for output
            if ( inName.indexOf( "." ) > -1 )
                outName = inName.substring( 0, inName.lastIndexOf( "." ) ) + ".tmp";
            else
                outName = inName + ".tmp";

            // instantiate File objects
            inFile = new File( inName );
            outFile = new File( outName );

            // instantiate File reader and writer
        } // end if
    }

    /**************************** Public Methods *************************/

    /**
     * closeReader
     *
     * close the files and rename the output file to the input file name
     */
    public void closeReader() throws IOException
    {
        reader.close();

    } // method closeReader


    /**
     * closeWriter
     *
     * close the files and rename the output file to the input file name
     */
    public void closeWriter() throws IOException
    {
        reader.close(); // redundant close, just in case
        writer.close(); // shouldn't be open, but we'll close it anyway

        // rename tmp file to dat
        inFile.delete();
        outFile.renameTo( inFile );

    } // method closeWriter


    /**
     * openReader
     *
     * open the file reader
     */
    public boolean openReader() throws IOException
    {
        boolean canRead = false;

        try
        {
            if ( inFile.exists() )
            {
                reader = new BufferedReader( new FileReader( inFile ) );
                canRead = true;

            } // end if

        } // end try

        catch (IOException e) {}

        return canRead;

    } // method openReader


    /**
     * openWriter
     *
     * open the file writer
     *
     * @return boolean
     * @throws IOException
     */
    public boolean openWriter() throws IOException
    {
        boolean canWrite = false;

        try
        {
            writer = new BufferedWriter( new FileWriter( outFile ) );
            canWrite = true;

        } // end try

        catch ( IOException e ) {}

        return canWrite;

    } // method closeWriter


    /**
     * readLine
     *
     * read a line
     *
     * @return String
     */
    public String readLine() throws IOException
    {
        return reader.readLine();

    } // method getInputFromUser


    /**
     * write
     *
     * write a line
     *
     * @param String
     */
    public void write(String s) throws IOException
    {
        writer.write( s + "\n" );

    } // method write

} // class FileIO
