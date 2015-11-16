package jhelp.learning.tool.language;

import jhelp.learning.tool.resources.LearningResources;
import jhelp.logo.language.Language;
import jhelp.util.gui.JHelpImage;
import jhelp.util.io.json.ObjectJSON;
import jhelp.util.io.json.ValueJSON;
import jhelp.util.text.UtilText;

/**
 * Change pen color instruction
 * 
 * @author JHelp
 */
public class InstructionColor
      extends Instruction
{
   /** Color key in JSON serialization */
   private static final String COLOR = "color";
   /** Pen color */
   private int                 color;

   /**
    * Create a new instance of InstructionColor
    */
   public InstructionColor()
   {
      super(InstructionType.COLOR, LearningResources.InstructionColor, LearningResources.ExplanationColor, LearningResources.IMAGE_COLOR);
      this.color = 0;
   }

   /**
    * Copy instruction parameters.<br>
    * Given instruction have good type, so can be safely cast <br>
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
      this.color = ((InstructionColor) instruction).color;
   }

   /**
    * Draw something more on instruction representation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param image
    *           Image where draw
    * @param x
    *           X where start draw on image
    * @param width
    *           Free space width
    * @param height
    *           Free space height
    * @see jhelp.learning.tool.language.Instruction#drawSomethingMore(jhelp.util.gui.JHelpImage, int, int, int)
    */
   @Override
   protected void drawSomethingMore(final JHelpImage image, final int x, final int width, final int height)
   {
      if(this.color != 0)
      {
         int size = Math.min(width, height);
         size -= size >> 4;
         image.fillRectangle(x + ((width - size) >> 1), (height - size) >> 1, size, size, this.color);
      }
   }

   /**
    * Compute parameter representation <br>
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
      if(this.color == 0)
      {
         return "{?}";
      }

      return "";
   }

   /**
    * Parse JSON to get specific parameters <br>
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
      this.color = (int) json.get(InstructionColor.COLOR).getNumber();
   }

   /**
    * Serialize in JSON specific parameters <br>
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
      json.put(InstructionColor.COLOR, ValueJSON.newValue(this.color));
   }

   /**
    * Indicates if color is defined
    * 
    * @return {@code true} if color is defined
    */
   public boolean colorDefined()
   {
      return this.color != 0;
   }

   /**
    * Color to use
    * 
    * @return Color to use
    */
   public int getColor()
   {
      return this.color;
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
      return UtilText.concatenate(Language.Instructions.COLOR, " ", Integer.toHexString(this.color));
   }

   /**
    * Change the color to use
    * 
    * @param color
    *           New color
    */
   public void setColor(final int color)
   {
      this.color = color | 0xFF000000;
      this.update();
   }
}