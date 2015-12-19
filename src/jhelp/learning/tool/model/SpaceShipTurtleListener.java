/**
 * <h1>License :</h1> <br>
 * The following code is deliver as is. I take care that code compile and work, but I am not responsible about any damage it may
 * cause.<br>
 * You can use, modify, the code as your need for any usage. But you can't do any action that avoid me or other person use,
 * modify this code. The code is free for usage and modification, you can't change that fact.<br>
 * <br>
 * 
 * @author JHelp
 */
package jhelp.learning.tool.model;

/**
 * Listener of spaceship events
 * 
 * @author JHelp
 */
public interface SpaceShipTurtleListener
{
   /**
    * Called when pen down animation finished
    */
   public void penDown();

   /**
    * Called in middle of fill animation, to say its the good time to fill the paper
    */
   public void penFill();

   /**
    * Called when fill animation finished
    */
   public void penFillEnd();

   /**
    * Called when pen up animation finished
    */
   public void penUp();
}