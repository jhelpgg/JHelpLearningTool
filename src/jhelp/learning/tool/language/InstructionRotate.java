package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.io.json.ObjectJSON;
import jhelp.util.io.json.ValueJSON;
import jhelp.util.math.UtilMath;
import jhelp.util.text.UtilText;

/**
 * Rotate instruction : spaceship rotate on itself for given angle
 * 
 * @author JHelp
 */
public class InstructionRotate
      extends Instruction
{
   /** Angle JSON key */
   private static final String ANGLE = "angle";
   /** Angle to rotate */
   private int                 angle;
   /** Indicates if angle is defined */
   private boolean             defined;

   /**
    * Create a new instance of InstructionRotate
    */
   public InstructionRotate()
   {
      super(InstructionType.ROTATE, LearningResources.InstructionRotate, LearningResources.ExplanationRotate, LearningResources.IMAGE_ROTATE);
      this.defined = false;
      this.angle = 0;
   }

   /**
    * Copy instruction parameter.<br>
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
      this.defined = ((InstructionRotate) instruction).defined;
      this.angle = ((InstructionRotate) instruction).angle;
   }

   /**
    * Instruction parameter string <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Instruction parameter string
    * @see jhelp.learning.tool.language.Instruction#getParameter()
    */
   @Override
   protected String getParameter()
   {
      if(this.defined == false)
      {
         return "{?}";
      }

      return String.valueOf(this.angle);
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
      final ValueJSON value = json.get(InstructionRotate.ANGLE);

      this.defined = value != null;

      if(this.defined == true)
      {
         this.angle = (int) value.getNumber();
      }
   }

   /**
    * Serialize parameters in JSON <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param json
    *           JSON where serialize
    * @see jhelp.learning.tool.language.Instruction#serialize(jhelp.util.io.json.ObjectJSON)
    */
   @Override
   protected void serialize(final ObjectJSON json)
   {
      if(this.defined == true)
      {
         json.put(InstructionRotate.ANGLE, ValueJSON.newValue(this.angle));
      }
   }

   /**
    * Indicates if angle is defined
    * 
    * @return {@code true} if angle is defined
    */
   public boolean angleDefined()
   {
      return this.defined;
   }

   /**
    * Rotation angle
    * 
    * @return Rotation angle
    */
   public int getAngle()
   {
      return this.angle;
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
      return UtilText.concatenate(Language.Instructions.ROTATE, " ", this.angle);
   }

   /**
    * Change rotation angle
    * 
    * @param angle
    *           New angle
    */
   public void setAngle(final int angle)
   {
      this.angle = UtilMath.modulo(angle, 360);
      this.defined = true;
      this.update();
   }
}