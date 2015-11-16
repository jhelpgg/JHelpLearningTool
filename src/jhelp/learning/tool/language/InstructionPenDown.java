package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;

/**
 * Pen down instruction : space ship will draw on moving
 * 
 * @author JHelp
 */
public class InstructionPenDown
      extends Instruction
{
   /**
    * Create a new instance of InstructionPenDown
    */
   public InstructionPenDown()
   {
      super(InstructionType.PEN_DOWN, LearningResources.InstructionPenDown, LearningResources.ExplanationPenDown, LearningResources.IMAGE_PEN_DOWN);
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
      return Language.Instructions.PEN_DOWN;
   }
}