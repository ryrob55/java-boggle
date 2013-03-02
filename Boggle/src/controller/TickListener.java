package controller;

import util.*;

/**
 * TickListener - This handles the listener for the EggTimer.
 * 
 * @author Michael Norton, Byron Craig, and Ryan Robertson
 * @version 12/4/2012
 *
 * Acknowledgements: I acknowledge that I have neither given nor received 
 *                   assistance for this assignment except as noted below:
 *  
 *                   Dr. Norton
 *         
 * Modifications:    None
 */
public interface TickListener
{
    /******************public methods*******************/

    /**
     *  Listen for EggTimer ticks occurring every second.
     *
     *   @param timer The EggTimer producing the tick.
     */
    void tick( EggTimer timer );
    
} // Class TickListener
