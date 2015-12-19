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
package jhelp.learning.tool.ui;

import jhelp.engine.gui.FrameView3D;
import jhelp.learning.tool.model.InstructionExecuter;

/**
 * Frame that carry the learning scene
 * 
 * @author JHelp
 */
public class LearnFrame
      extends FrameView3D
      implements CursorManipulator
{
   /**
    * Create a new instance of LearnFrame
    */
   public LearnFrame()
   {
      super();

      this.getScene().setBackground(0, 0, 0);

      this.setManipulatedNode(null);
      this.setVisible(true);

      final int width = (this.getWidth() - 1100) >> 1;
      final InstructionExecuter instructionExecuter = new InstructionExecuter(this.getSceneRenderer(), this.getWidth() - width, width, this.getHeight());
      instructionExecuter.setCursorManipulator(this);
   }
}