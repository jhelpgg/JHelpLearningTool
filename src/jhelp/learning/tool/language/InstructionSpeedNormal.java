package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.text.UtilText;

/**
 * Go normal speed instruction : spaceship go default speed
 * 
 * @author JHelp
 */
public class InstructionSpeedNormal
      extends Instruction
{
   /** Go normal speed logo language instruction */
   private static final String INSTRUCTION = UtilText.concatenate(Language.Instructions.SPEED, " 345");

   /**
    * Create a new instance of InstructionSpeedNormal
    */
   public InstructionSpeedNormal()
   {
      super(InstructionType.NORMAL, LearningResources.InstructionSpeedNormal, LearningResources.ExplanationNormal, LearningResources.IMAGE_SPEED_NORMAL);
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
      return InstructionSpeedNormal.INSTRUCTION;
   }
}