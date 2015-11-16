package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.text.UtilText;

/**
 * Clear instruction : clear paper with current color
 * 
 * @author JHelp
 */
public class InstructionClear
      extends Instruction
{
   /**
    * Create a new instance of InstructionClear
    */
   public InstructionClear()
   {
      super(InstructionType.CLEAR, LearningResources.InstructionClear, LearningResources.ExplanationClear, LearningResources.IMAGE_CLEAR);
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
      return UtilText.concatenate(Language.Instructions.CLEAR, " ", Integer.toHexString(currentColor));
   }
}