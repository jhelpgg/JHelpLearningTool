package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;

/**
 * Show spaceship turtle instruction
 * 
 * @author JHelp
 */
public class InstructionShow
      extends Instruction
{
   /**
    * Create a new instance of InstructionShow
    */
   public InstructionShow()
   {
      super(InstructionType.SHOW, LearningResources.InstructionShow, LearningResources.ExplanationShow, LearningResources.IMAGE_SHOW);
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
      return Language.Instructions.VISIBLE;
   }
}