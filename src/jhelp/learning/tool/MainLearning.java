package jhelp.learning.tool;

import jhelp.learning.tool.model.FileExplorer;
import jhelp.learning.tool.ui.LearnFrame;
import jhelp.util.gui.UtilGUI;

/**
 * Launch the learning application
 * 
 * @author JHelp
 */
public class MainLearning
{

   /**
    * Launch the learning application
    * 
    * @param args
    *           Unused
    */
   public static void main(final String[] args)
   {
      UtilGUI.initializeGUI();

      final LearnFrame learnFrame = new LearnFrame();
      learnFrame.setVisible(true);

      FileExplorer.loadTutorialExamples();
   }
}