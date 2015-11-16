package jhelp.learning.tool.resources;

import jhelp.util.gui.JHelpImage;
import jhelp.util.resources.ResourceText;
import jhelp.util.resources.Resources;

/**
 * Resources access used by learning application
 * 
 * @author JHelp
 */
public class LearningResources
{
   /**
    * {@link #InstructionClear} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationClear       = "ExplanationClear";
   /**
    * {@link #InstructionColor} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationColor       = "ExplanationColor";

   /**
    * {@link #InstructionSpeedFast} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationFast        = "ExplanationFast";
   /**
    * {@link #InstructionFill} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationFill        = "ExplanationFill";
   /**
    * {@link #InstructionForward} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationForward     = "ExplanationForward";
   /**
    * {@link #InstructionHide} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationHide        = "ExplanationHide";
   /**
    * {@link #InstructionHome} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationHome        = "ExplanationHome";
   /**
    * {@link #InstructionSpeedNormal} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationNormal      = "ExplanationNormal";
   /**
    * {@link #InstructionPenDown} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationPenDown     = "ExplanationPenDown";
   /**
    * {@link #InstructionPenUp} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationPenUp       = "ExplanationPenUp";
   /**
    * {@link #InstructionRotate} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationRotate      = "ExplanationRotate";
   /**
    * {@link #InstructionShow} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationShow        = "ExplanationShow";
   /**
    * {@link #InstructionSpeedSlow} explanation 0 : Turtle name in {@link #TurtleName}
    */
   public static final String       ExplanationSlow        = "ExplanationSlow";
   /** Clear instruction image */
   public static final JHelpImage   IMAGE_CLEAR;
   /** Color instruction image */
   public static final JHelpImage   IMAGE_COLOR;
   /** Fill instruction image */
   public static final JHelpImage   IMAGE_FILL;
   /** Forward instruction image */
   public static final JHelpImage   IMAGE_FORWARD;
   /** Hide spaceship instruction image */
   public static final JHelpImage   IMAGE_HIDE;
   /** Home instruction image */
   public static final JHelpImage   IMAGE_HOME;
   /** Pen down instruction image */
   public static final JHelpImage   IMAGE_PEN_DOWN;
   /** Pen up instruction image */
   public static final JHelpImage   IMAGE_PEN_UP;
   /** Rotate instruction image */
   public static final JHelpImage   IMAGE_ROTATE;
   /** Show spaceship instruction image */
   public static final JHelpImage   IMAGE_SHOW;
   /** Speed fast instruction image */
   public static final JHelpImage   IMAGE_SPEED_FAST;
   /** Speed normal instruction image */
   public static final JHelpImage   IMAGE_SPEED_NORMAL;
   /** Speed slow instruction image */
   public static final JHelpImage   IMAGE_SPEED_SLOW;
   /** Clear with current color */
   public static final String       InstructionClear       = "InstructionClear";
   /**
    * Change current color 0 : The color
    */
   public static final String       InstructionColor       = "InstructionColor";
   /** Fill current area */
   public static final String       InstructionFill        = "InstructionFill";
   /**
    * Forward instruction 0 : number of step
    */
   public static final String       InstructionForward     = "InstructionForward";
   /** Hide turtle */
   public static final String       InstructionHide        = "InstructionHide";
   /** Goto to initial position */
   public static final String       InstructionHome        = "InstructionHome";
   /** Put the pen down */
   public static final String       InstructionPenDown     = "InstructionPenDown";
   /** Put the pen up */
   public static final String       InstructionPenUp       = "InstructionPenUp";
   /**
    * Rotate instruction 0 : angle
    */
   public static final String       InstructionRotate      = "InstructionRotate";
   /** Show turtle */
   public static final String       InstructionShow        = "InstructionShow";
   /** Speed fast */
   public static final String       InstructionSpeedFast   = "InstructionSpeedFast";
   /** Speed normal */
   public static final String       InstructionSpeedNormal = "InstructionSpeedNormal";
   /** Speed slow */
   public static final String       InstructionSpeedSlow   = "InstructionSpeedSlow";
   /** Position back text key */
   public static final String       PositionBack           = "PositionBack";
   /** Position face text key */
   public static final String       PositionFace           = "PositionFace";
   /** Position left text key */
   public static final String       PositionLeft           = "PositionLeft";
   /** Position origin text key */
   public static final String       PositionOrigin         = "PositionOrigin";
   /** Position right text key */
   public static final String       PositionRight          = "PositionRight";
   /** Resources text provider */
   public static final ResourceText RESOURCE_TEXT;
   /** Resources access */
   public static final Resources    RESOURCES;
   /** Turtle name */
   public static final String       TurtleName             = "TurtleName";

   static
   {
      RESOURCES = new Resources(LearningResources.class);
      RESOURCE_TEXT = LearningResources.RESOURCES.obtainResourceText("texts");

      final int size = 64;
      IMAGE_CLEAR = LearningResources.RESOURCES.obtainResizedJHelpImage("clear.png", size, size);
      IMAGE_COLOR = LearningResources.RESOURCES.obtainResizedJHelpImage("color.png", size, size);
      IMAGE_FILL = LearningResources.RESOURCES.obtainResizedJHelpImage("fill.png", size, size);
      IMAGE_FORWARD = LearningResources.RESOURCES.obtainResizedJHelpImage("forward.png", size, size);

      IMAGE_HIDE = LearningResources.RESOURCES.obtainResizedJHelpImage("eye.png", size, size);
      LearningResources.IMAGE_HIDE.startDrawMode();
      LearningResources.IMAGE_HIDE.drawThickLine(0, 0, size, size, 5, 0xFFFF0000);
      LearningResources.IMAGE_HIDE.drawThickLine(0, size, size, 0, 5, 0xFFFF0000);
      LearningResources.IMAGE_HIDE.endDrawMode();

      IMAGE_HOME = LearningResources.RESOURCES.obtainResizedJHelpImage("home.png", size, size);

      final JHelpImage image = LearningResources.RESOURCES.obtainResizedJHelpImage("forward.png", size, size);
      IMAGE_PEN_DOWN = image.rotate270();
      IMAGE_PEN_UP = image.rotate90();

      IMAGE_ROTATE = LearningResources.RESOURCES.obtainResizedJHelpImage("rotate.png", size, size);
      LearningResources.IMAGE_ROTATE.startDrawMode();
      LearningResources.IMAGE_ROTATE.flipVertical();
      LearningResources.IMAGE_ROTATE.endDrawMode();

      IMAGE_SHOW = LearningResources.RESOURCES.obtainResizedJHelpImage("eye.png", size, size);
      IMAGE_SPEED_FAST = LearningResources.RESOURCES.obtainResizedJHelpImage("fast.png", size, size);
      IMAGE_SPEED_NORMAL = LearningResources.RESOURCES.obtainResizedJHelpImage("normal.png", size, size);
      IMAGE_SPEED_SLOW = LearningResources.RESOURCES.obtainResizedJHelpImage("slow.png", size, size);
   }
}