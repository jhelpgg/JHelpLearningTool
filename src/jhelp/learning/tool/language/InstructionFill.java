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
import jhelp.util.text.UtilText;

/**
 * Fil area instruction : fill the area where spaceship fly over with current pen color
 * 
 * @author JHelp
 */
public class InstructionFill
      extends Instruction
{
   /**
    * Create a new instance of InstructionFill
    */
   public InstructionFill()
   {
      super(InstructionType.FILL, LearningResources.InstructionFill, LearningResources.ExplanationFill, LearningResources.IMAGE_FILL);
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
      return UtilText.concatenate(Language.Instructions.FILL, " ", Integer.toHexString(currentColor), ",1");
   }
}