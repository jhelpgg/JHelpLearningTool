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