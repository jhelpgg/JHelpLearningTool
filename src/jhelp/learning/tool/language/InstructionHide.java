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
package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;

/**
 * Hide space ship instruction
 * 
 * @author JHelp
 */
public class InstructionHide
      extends Instruction
{
   /**
    * Create a new instance of InstructionHide
    */
   public InstructionHide()
   {
      super(InstructionType.HIDE, LearningResources.InstructionHide, LearningResources.ExplanationHide, LearningResources.IMAGE_HIDE);
   }

   /**
    * Compute corresponding logo language instruction <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param currentColor
    *           Current color
    * @return Corresponding logo language instruction
    * @see jhelp.learning.tool.language.Instruction#languageInstructions(int)
    */
   @Override
   public String languageInstructions(final int currentColor)
   {
      return Language.Instructions.HIDE;
   }
}