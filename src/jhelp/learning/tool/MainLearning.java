package jhelp.learning.tool;

import java.util.Locale;

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
      Locale.setDefault(Locale.FRENCH);

      final LearnFrame learnFrame = new LearnFrame();
      learnFrame.setVisible(true);
   }
}