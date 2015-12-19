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

/**
 * Instruction type
 * 
 * @author JHelp
 */
public enum InstructionType
{
   /** Clear paper with current color */
   CLEAR("clear.png"),
   /** Change pen color */
   COLOR("color.png"),
   /** Speed fast */
   FAST("fast.png"),
   /** Fill over area with current color */
   FILL("fill.png"),
   /** Forward for given step */
   FORWARD("forward.png"),
   /** Hide spaceship */
   HIDE("eye.png"),
   /** Go to start position */
   HOME("home.png"),
   /** Normal speed */
   NORMAL("normal.png"),
   /** Pen down for draw on moving */
   PEN_DOWN("pen_down.png"),
   /** Pen up for not draw on moving */
   PEN_UP("pen_up.png"),
   /** Rotate spaceship */
   ROTATE("rotate.png"),
   /** Show spaceship */
   SHOW("eye.png"),
   /** Slow speed */
   SLOW("slow.png");

   /** Icon resource path */
   private final String iconResourcePath;

   /**
    * Create a new instance of InstructionType
    * 
    * @param iconResourcePath
    *           Icon resource path
    */
   InstructionType(final String iconResourcePath)
   {
      this.iconResourcePath = iconResourcePath;
   }

   /**
    * Create an instruction instance for the type
    * 
    * @return Created instance
    */
   public Instruction createInstructionInstance()
   {
      switch(this)
      {
         case CLEAR:
            return new InstructionClear();
         case COLOR:
            return new InstructionColor();
         case FAST:
            return new InstructionSpeedFast();
         case FILL:
            return new InstructionFill();
         case FORWARD:
            return new InstructionForward();
         case HIDE:
            return new InstructionHide();
         case HOME:
            return new InstructionHome();
         case NORMAL:
            return new InstructionSpeedNormal();
         case PEN_DOWN:
            return new InstructionPenDown();
         case PEN_UP:
            return new InstructionPenUp();
         case ROTATE:
            return new InstructionRotate();
         case SHOW:
            return new InstructionShow();
         case SLOW:
            return new InstructionSpeedSlow();
      }

      return null;
   }

   /**
    * Icon resource path
    * 
    * @return Icon resource path
    */
   public String getIconResourcePath()
   {
      return this.iconResourcePath;
   }
}