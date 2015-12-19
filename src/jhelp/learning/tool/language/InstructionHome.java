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
 * Go home instruction : spaceship return to its initial position and turn to original angle.<br>
 * Note : pen status not changed, so if down a draw is done
 * 
 * @author JHelp
 */
public class InstructionHome
      extends Instruction
{
   /**
    * Create a new instance of InstructionHome
    */
   public InstructionHome()
   {
      super(InstructionType.HOME, LearningResources.InstructionHome, LearningResources.ExplanationHome, LearningResources.IMAGE_HOME);
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
      return Language.Instructions.HOME;
   }
}