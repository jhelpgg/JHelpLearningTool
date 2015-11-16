package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;

/**
 * Pen UP instruction : No more drawing on move
 * 
 * @author JHelp
 */
public class InstructionPenUp
      extends Instruction
{
   /**
    * Create a new instance of InstructionPenUp
    */
   public InstructionPenUp()
   {
      super(InstructionType.PEN_UP, LearningResources.InstructionPenUp, LearningResources.ExplanationPenUp, LearningResources.IMAGE_PEN_UP);
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
      return Language.Instructions.PEN_UP;
   }
}