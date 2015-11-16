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