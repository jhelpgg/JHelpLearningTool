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