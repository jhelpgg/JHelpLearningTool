package jhelp.learning.tool.language;

/**
 * Instruction type
 * 
 * @author JHelp
 */
public enum InstructionType
{
   /** Clear paper with current color */
   CLEAR,
   /** Change pen color */
   COLOR,
   /** Speed fast */
   FAST,
   /** Fill over area with current color */
   FILL,
   /** Forward for given step */
   FORWARD,
   /** Hide spaceship */
   HIDE,
   /** Go to start position */
   HOME,
   /** Normal speed */
   NORMAL,
   /** Pen down for draw on moving */
   PEN_DOWN,
   /** Pen up for not draw on moving */
   PEN_UP,
   /** Rotate spaceship */
   ROTATE,
   /** Show spaceship */
   SHOW,
   /** Slow speed */
   SLOW;

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
}