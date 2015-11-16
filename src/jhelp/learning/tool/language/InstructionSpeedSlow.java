package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.text.UtilText;

/**
 * Go slow speed instruction : spaceship will go slow
 * 
 * @author JHelp
 */
public class InstructionSpeedSlow
      extends Instruction
{
   /** Go slow logo language instruction */
   private static final String INSTRUCTION = UtilText.concatenate(Language.Instructions.SPEED, " 1024");

   /**
    * Create a new instance of InstructionSpeedSlow
    */
   public InstructionSpeedSlow()
   {
      super(InstructionType.SLOW, LearningResources.InstructionSpeedSlow, LearningResources.ExplanationSlow, LearningResources.IMAGE_SPEED_SLOW);
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
      return InstructionSpeedSlow.INSTRUCTION;
   }
}