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