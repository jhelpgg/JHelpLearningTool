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

import java.util.concurrent.atomic.AtomicInteger;

import jhelp.engine.Texture;
import jhelp.engine.twoD.Object2D;
import jhelp.learning.tool.resources.LearningResources;
import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpTextAlign;
import jhelp.util.gui.alphabet.AlphabetBlue16x16;
import jhelp.util.io.json.ObjectJSON;
import jhelp.util.io.json.ValueJSON;
import jhelp.util.text.UtilText;

/**
 * Instruction to play.<br>
 * Instructions corresponds to logo language instructions
 * 
 * @author JHelp
 */
public abstract class Instruction
{
   /** Background color */
   private static final int           BACKGROUND = 0xDD448866;
   /** Next instruction ID */
   private static final AtomicInteger NEXT_ID    = new AtomicInteger(0);
   /** JSON type key */
   private static final String        TYPE       = "TYPE";

   /**
    * Parse instruction from JSON
    * 
    * @param json
    *           JSON to parse
    * @return Parsed instruction
    */
   public static Instruction parseInstruction(final ObjectJSON json)
   {
      final InstructionType instructionType = InstructionType.valueOf(json.get(Instruction.TYPE).getString());
      final Instruction instruction = instructionType.createInstructionInstance();
      instruction.parse(json);
      instruction.update();
      return instruction;
   }

   /** Instruction complete image */
   private final JHelpImage      composedImage;
   /** 2D object for draw instruction */
   private final Object2D        composedObject;
   /** Instruction texture */
   private final Texture         composedTexture;
   /** Instruction icon */
   private final JHelpImage      icon;
   /** Instruction type */
   private final InstructionType instructionType;
   /** Detail information key text */
   private final String          textInformation;
   /** Instruction name key text */
   private final String          textName;

   /**
    * Create a new instance of Instruction
    * 
    * @param instructionType
    *           Instruction type
    * @param textName
    *           Instruction name key text
    * @param textInformation
    *           Detail information key text
    * @param icon
    *           Instruction icon
    */
   Instruction(final InstructionType instructionType, final String textName, final String textInformation, final JHelpImage icon)
   {
      this.instructionType = instructionType;
      this.textName = textName;
      this.textInformation = textInformation;
      this.icon = icon;
      this.composedImage = new JHelpImage(256, 64, Instruction.BACKGROUND);
      this.composedTexture = new Texture(textName + Instruction.NEXT_ID.getAndIncrement(), 256, 64);
      this.composedTexture.setImage(this.composedImage);
      this.composedObject = new Object2D(0, 0, 256, 64);
      this.composedObject.setTexture(this.composedTexture);
      this.composedObject.setAdditionalInformation(this);
      this.update();
   }

   /**
    * Copy an instruction parameter.<br>
    * Given instruction is in good type so can be safely cast.<br>
    * Does nothing by default, override it for instruction with parameters
    * 
    * @param instruction
    *           Instruction to copy
    */
   protected void copy(final Instruction instruction)
   {
   }

   /**
    * Draw additional stuff on final image.<br>
    * Does nothing by default, ovveride it for instruction need draw additional stuff
    * 
    * @param image
    *           Image where draw
    * @param x
    *           Draw X start on image
    * @param width
    *           Free space width
    * @param height
    *           Image height
    */
   protected void drawSomethingMore(final JHelpImage image, final int x, final int width, final int height)
   {
   }

   /**
    * Obtain parameters representation.<br>
    * Ovveride it for instructions with parameters
    * 
    * @return {@code null} because no parameter by default
    */
   protected String getParameter()
   {
      return null;
   }

   /**
    * Parse JSON to get parameters value.<br>
    * Does nothing by default, override it for instruction with parameters
    * 
    * @param json
    *           JSON to parse
    */
   protected void parse(final ObjectJSON json)
   {
   }

   /**
    * Serialize parameters in JSON.<br>
    * Does nothing by default, override it for instruction with parameters
    * 
    * @param json
    *           JSON where write
    */
   protected void serialize(final ObjectJSON json)
   {
   }

   /**
    * Update instruction texture
    */
   protected final void update()
   {
      final int y = (64 - 16) >> 1;
      final String name = this.getName();
      final int x = 64 + (name.length() << 4);
      final int width = 256 - x;

      this.composedImage.startDrawMode();
      this.composedImage.clear(Instruction.BACKGROUND);
      this.composedImage.drawImage(0, 0, this.icon);
      AlphabetBlue16x16.ALPHABET_BLUE16X16.drawOn(name, JHelpTextAlign.LEFT, this.composedImage, 64, y);
      this.drawSomethingMore(this.composedImage, x, width, 64);
      this.composedImage.endDrawMode();
      this.composedTexture.setImage(this.composedImage);
   }

   /**
    * Create an instruction copy
    * 
    * @return Created copy
    */
   public final Instruction copy()
   {
      final Instruction instruction = this.instructionType.createInstructionInstance();
      instruction.copy(this);
      instruction.update();
      return instruction;
   }

   /**
    * Complete image
    * 
    * @return Complete image
    */
   public final JHelpImage getComposedImage()
   {
      return this.composedImage;
   }

   /**
    * Instruction icon
    * 
    * @return Instruction icon
    */
   public final JHelpImage getIcon()
   {
      return this.icon;
   }

   /**
    * Instruction detail information
    * 
    * @return Instruction detail information
    */
   public final String getInformation()
   {
      return UtilText.replaceHole(LearningResources.RESOURCE_TEXT.getText(this.textInformation),
            LearningResources.RESOURCE_TEXT.getText(LearningResources.TurtleName));
   }

   /**
    * Instruction type
    * 
    * @return Instruction type
    */
   public final InstructionType getInstructionType()
   {
      return this.instructionType;
   }

   /**
    * Instruction name
    * 
    * @return Instruction name
    */
   public final String getName()
   {
      final String name = LearningResources.RESOURCE_TEXT.getText(this.textName);
      String parameter = this.getParameter();

      if(parameter == null)
      {
         parameter = "";
      }

      return UtilText.replaceHole(name, parameter);
   }

   /**
    * Compute corresponding logo language instruction
    * 
    * @param currentColor
    *           Current color
    * @return Corresponding logo language instruction
    */
   public abstract String languageInstructions(int currentColor);

   /**
    * Object 2D to use
    * 
    * @return Object 2D to use
    */
   public final Object2D obtainObject2D()
   {
      return this.composedObject;
   }

   /**
    * Serialize instruction to JSON
    * 
    * @return Serialized JSON
    */
   public final ObjectJSON serialize()
   {
      final ObjectJSON json = new ObjectJSON();
      json.put(Instruction.TYPE, ValueJSON.newValue(this.instructionType.name()));
      this.serialize(json);
      return json;
   }
}