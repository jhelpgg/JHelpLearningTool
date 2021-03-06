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
import jhelp.util.io.json.ObjectJSON;
import jhelp.util.io.json.ValueJSON;
import jhelp.util.text.UtilText;

/**
 * Go forward instruction : spaceship goes on its current direction for a given number of step
 * 
 * @author JHelp
 */
public class InstructionForward
      extends Instruction
{
   /** JSON step key */
   private static final String STEP = "step";
   /** Indicates if number of step defined */
   private boolean             defined;
   /** Number of step */
   private int                 step;

   /**
    * Create a new instance of InstructionForward
    */
   public InstructionForward()
   {
      super(InstructionType.FORWARD, LearningResources.InstructionForward, LearningResources.ExplanationForward, LearningResources.IMAGE_FORWARD);
      this.defined = false;
      this.step = 0;
   }

   /**
    * Copy instruction parameters.<br>
    * Given instruction is in good type, so can be safely cast <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param instruction
    *           Instruction to copy
    * @see jhelp.learning.tool.language.Instruction#copy(jhelp.learning.tool.language.Instruction)
    */
   @Override
   protected void copy(final Instruction instruction)
   {
      this.defined = ((InstructionForward) instruction).defined;
      this.step = ((InstructionForward) instruction).step;
   }

   /**
    * Parameter representation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Parameter representation
    * @see jhelp.learning.tool.language.Instruction#getParameter()
    */
   @Override
   protected String getParameter()
   {
      if(!this.defined)
      {
         return "{?}";
      }

      return String.valueOf(this.step);
   }

   /**
    * Parse JSON to get parameters <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param json
    *           JSON to parse
    * @see jhelp.learning.tool.language.Instruction#parse(jhelp.util.io.json.ObjectJSON)
    */
   @Override
   protected void parse(final ObjectJSON json)
   {
      final ValueJSON value = json.get(InstructionForward.STEP);

      this.defined = value != null;

      if(this.defined)
      {
         this.step = (int) value.getNumber();
      }
   }

   /**
    * Serialize parameters in JSON <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param json
    *           JSON where write
    * @see jhelp.learning.tool.language.Instruction#serialize(jhelp.util.io.json.ObjectJSON)
    */
   @Override
   protected void serialize(final ObjectJSON json)
   {
      if(this.defined)
      {
         json.put(InstructionForward.STEP, ValueJSON.newValue(this.step));
      }
   }

   /**
    * Number of step
    * 
    * @return Number of step
    */
   public int getStep()
   {
      return this.step;
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
      return UtilText.concatenate(Language.Instructions.FORWARD, " ", this.step);
   }

   /**
    * Change number of step
    * 
    * @param step
    *           New number of steps
    */
   public void setStep(final int step)
   {
      this.step = step;
      this.defined = true;
      this.update();
   }

   /**
    * Indicates if number of step defined
    * 
    * @return {@code true} if number of step defined
    */
   public boolean stepDefined()
   {
      return this.defined;
   }
}