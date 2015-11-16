package jhelp.learning.tool.model;

import java.awt.Color;

import jhelp.engine.Texture;
import jhelp.engine.event.Object2DListener;
import jhelp.engine.twoD.Object2D;

/**
 * Color chooser
 * 
 * @author JHelp
 */
public class ColorChooser
      extends Object2D
      implements Object2DListener
{
   /** Color chooser background */
   private static final Color         BACKGROUND = new Color(0xCAFEFACE, true);
   /** List of colors to choose */
   private static final int[]         COLORS;
   /** Number of columns */
   private static final int           NUMBER_COLUMS;
   /** Number of lines */
   private static final int           NUMBER_LINES;
   /** Color element size (width and height) */
   private static final int           SIZE;
   /** Color chooser height */
   public static final int            HEIGHT;
   /** Color chooser width */
   public static final int            WIDTH;

   static
   {
      SIZE = 64;
      COLORS = new int[]
      {
         0xFF000000, 0xFF000044, 0xFF000088, 0xFF0000CC, 0xFF0000FF, 0xFF004400, 0xFF004444, 0xFF004488, 0xFF0044CC, 0xFF0044FF, 0xFF008800, 0xFF008844,
         0xFF008888, 0xFF0088CC, 0xFF0088FF, 0xFF00FF00, 0xFF00FF44, 0xFF00FF88, 0xFF00FFCC, 0xFF00FFFF, 0xFF440000, 0xFF440044, 0xFF440088, 0xFF4400CC,
         0xFF4400FF, 0xFF444400, 0xFF444444, 0xFF444488, 0xFF4444CC, 0xFF4444FF, 0xFF448800, 0xFF448844, 0xFF448888, 0xFF4488CC, 0xFF4488FF, 0xFF44FF00,
         0xFF44FF44, 0xFF44FF88, 0xFF44FFCC, 0xFF44FFFF, 0xFF880000, 0xFF880044, 0xFF880088, 0xFF8800CC, 0xFF8800FF, 0xFF884400, 0xFF884444, 0xFF884488,
         0xFF8844CC, 0xFF8844FF, 0xFF888800, 0xFF888844, 0xFF888888, 0xFF8888CC, 0xFF8888FF, 0xFF88FF00, 0xFF88FF44, 0xFF88FF88, 0xFF88FFCC, 0xFF88FFFF,
         0xFFCC0000, 0xFFCC0044, 0xFFCC0088, 0xFFCC00CC, 0xFFCC00FF, 0xFFCC4400, 0xFFCC4444, 0xFFCC4488, 0xFFCC44CC, 0xFFCC44FF, 0xFFCC8800, 0xFFCC8844,
         0xFFCC8888, 0xFFCC88CC, 0xFFCC88FF, 0xFFCCFF00, 0xFFCCFF44, 0xFFCCFF88, 0xFFCCFFCC, 0xFFCCFFFF, 0xFFFF0000, 0xFFFF0044, 0xFFFF0088, 0xFFFF00CC,
         0xFFFF00FF, 0xFFFF4400, 0xFFFF4444, 0xFFFF4488, 0xFFFF44CC, 0xFFFF44FF, 0xFFFF8800, 0xFFFF8844, 0xFFFF8888, 0xFFFF88CC, 0xFFFF88FF, 0xFFFFFF00,
         0xFFFFFF44, 0xFFFFFF88, 0xFFFFFFCC, 0xFFFFFFFF
      };
      final int length = ColorChooser.COLORS.length;
      NUMBER_COLUMS = (int) Math.ceil(Math.sqrt(length));
      NUMBER_LINES = (int) Math.ceil((double) length / (double) ColorChooser.NUMBER_COLUMS);
      WIDTH = ColorChooser.NUMBER_COLUMS * ColorChooser.SIZE;
      HEIGHT = ColorChooser.NUMBER_LINES * ColorChooser.SIZE;
   }

   /** Listener of color chooser events */
   private final ColorChooserListener colorChooserListener;
   /** Current over color */
   private int                        over       = -1;

   /**
    * Create a new instance of ColorChooser
    * 
    * @param x
    *           X position
    * @param y
    *           Y position
    * @param colorChooserListener
    *           Listener of color chooser events
    */
   public ColorChooser(final int x, final int y, final ColorChooserListener colorChooserListener)
   {
      super(x, y, ColorChooser.WIDTH, ColorChooser.HEIGHT);

      if(colorChooserListener == null)
      {
         throw new NullPointerException("colorChooserListener musn't be null");
      }

      this.colorChooserListener = colorChooserListener;
      final Texture texture = new Texture("ColorChooser", ColorChooser.WIDTH, ColorChooser.HEIGHT, ColorChooser.BACKGROUND);
      int xx = 0;
      int yy = 0;

      for(final int color : ColorChooser.COLORS)
      {
         texture.fillRect((xx * ColorChooser.SIZE) + 2, (yy * ColorChooser.SIZE) + 2, ColorChooser.SIZE - 4, ColorChooser.SIZE - 4, new Color(color), false);
         xx++;

         if(xx >= ColorChooser.NUMBER_COLUMS)
         {
            xx = 0;
            yy++;
         }
      }

      this.setTexture(texture);
      this.addObject2DListener(this);
   }

   /**
    * Called on mouse click <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @param leftButton
    *           Indicates if left button down
    * @param rightButton
    *           Indicates if right button down
    * @see jhelp.engine.event.Object2DListener#mouseClick(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseClick(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
      final int xx = x / ColorChooser.SIZE;
      final int yy = y / ColorChooser.SIZE;
      final int index = xx + (yy * ColorChooser.NUMBER_COLUMS);

      if(index < ColorChooser.COLORS.length)
      {
         this.colorChooserListener.colorChoose(ColorChooser.COLORS[index]);
      }
   }

   /**
    * Called on mouse drag <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @param leftButton
    *           Indicates if left button down
    * @param rightButton
    *           Indicates if right button down
    * @see jhelp.engine.event.Object2DListener#mouseDrag(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseDrag(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
   }

   /**
    * Called on mouse enter <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @see jhelp.engine.event.Object2DListener#mouseEnter(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseEnter(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Called on mouse exit <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @see jhelp.engine.event.Object2DListener#mouseExit(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseExit(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Called on mouse move <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @see jhelp.engine.event.Object2DListener#mouseMove(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseMove(final Object2D object2d, final int x, final int y)
   {
      int xx = x / ColorChooser.SIZE;
      int yy = y / ColorChooser.SIZE;
      int index = xx + (yy * ColorChooser.NUMBER_COLUMS);

      if(index >= ColorChooser.COLORS.length)
      {
         index = -1;
      }

      if(index == this.over)
      {
         return;
      }

      if(index >= 0)
      {
         this.texture.drawRect(xx * ColorChooser.SIZE, yy * ColorChooser.SIZE, ColorChooser.SIZE, ColorChooser.SIZE, Color.BLACK, false);
         this.texture.drawRect((xx * ColorChooser.SIZE) + 1, (yy * ColorChooser.SIZE) + 1, ColorChooser.SIZE - 2, ColorChooser.SIZE - 2, Color.BLACK, false);
      }

      if(this.over >= 0)
      {
         xx = this.over % ColorChooser.NUMBER_COLUMS;
         yy = this.over / ColorChooser.NUMBER_COLUMS;
         this.texture.drawRect(xx * ColorChooser.SIZE, yy * ColorChooser.SIZE, ColorChooser.SIZE, ColorChooser.SIZE, ColorChooser.BACKGROUND, false);
         this.texture.drawRect((xx * ColorChooser.SIZE) + 1, (yy * ColorChooser.SIZE) + 1, ColorChooser.SIZE - 2, ColorChooser.SIZE - 2,
               ColorChooser.BACKGROUND, false);
      }

      this.over = index;
   }
}