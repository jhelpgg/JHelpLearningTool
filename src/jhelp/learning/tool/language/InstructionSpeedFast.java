package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.text.UtilText;

/**
 * Go fast instruction : space ship will draw as fast as possible
 * 
 * @author JHelp
 */
public class InstructionSpeedFast
      extends Instruction
{
   /** Go fast logo language instruction */
   private static final String INSTRUCTION = UtilText.concatenate(Language.Instructions.SPEED, " 0");

   /**
    * Create a new instance of InstructionSpeedFast
    */
   public InstructionSpeedFast()
   {
      super(InstructionType.FAST, LearningResources.InstructionSpeedFast, LearningResources.ExplanationFast, LearningResources.IMAGE_SPEED_FAST);
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
      return InstructionSpeedFast.INSTRUCTION;
   }
}