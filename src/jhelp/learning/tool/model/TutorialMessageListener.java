package jhelp.learning.tool.model;

/**
 * Listener of tutorial message events
 * 
 * @author JHelp
 */
public interface TutorialMessageListener
{
   /**
    * Called when user click on tutorial message
    * 
    * @param tutorialID
    *           Tutorial ID clicked
    */
   public void tutorialClicked(int tutorialID);
}