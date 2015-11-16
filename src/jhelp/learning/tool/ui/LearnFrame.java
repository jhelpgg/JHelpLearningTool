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