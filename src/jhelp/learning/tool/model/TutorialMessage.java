package jhelp.learning.tool.model;

import jhelp.engine.JHelpSceneRenderer;
import jhelp.engine.Texture;
import jhelp.engine.event.Object2DListener;
import jhelp.engine.twoD.Object2D;
import jhelp.learning.tool.language.InstructionType;
import jhelp.learning.tool.resources.LearningResources;
import jhelp.util.gui.JHelpFont;
import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpRichText;
import jhelp.util.text.UtilText;

/**
 * Tutorial message
 * 
 * @author JHelp
 */
public class TutorialMessage
      extends Object2D
      implements Object2DListener
{
   /** Font use for show tutorial text */
   private static final JHelpFont        FONT = new JHelpFont("Arial", 32, true);
   /** Rich text for create message */
   private final JHelpRichText           richText;
   /** Scene renderer where message is show */
   private final JHelpSceneRenderer      sceneRenderer;
   /** Total screen height */
   private final int                     screenHeight;
   /** Total screen width */
   private final int                     screenWidth;
   /** Texture where message draw */
   private final Texture                 texture;
   /** Current tutorial ID */
   private int                           tutorialID;
   /** Listener of tutorial events */
   private final TutorialMessageListener tutorialMessageListener;

   /**
    * Create a new instance of TutorialMessage
    * 
    * @param sceneRenderer
    *           Scene renderer where message is show
    * @param screenWidth
    *           Total screen width
    * @param screenHeight
    *           Total screen height
    * @param tutorialMessageListener
    *           Listener of tutorial events
    */
   public TutorialMessage(final JHelpSceneRenderer sceneRenderer, final int screenWidth, final int screenHeight,
         final TutorialMessageListener tutorialMessageListener)
   {
      super(0, 0, 8, 8);

      if(sceneRenderer == null)
      {
         throw new NullPointerException("sceneRenderer musn't be null");
      }

      if(tutorialMessageListener == null)
      {
         throw new NullPointerException("tutorialMessageListener musn't be null");
      }

      this.sceneRenderer = sceneRenderer;
      this.tutorialMessageListener = tutorialMessageListener;
      this.screenWidth = screenWidth;
      this.screenHeight = screenHeight;
      this.texture = new Texture("TutorialMessage", 8, 8);
      this.setTexture(this.texture);
      this.setVisible(false);
      this.sceneRenderer.getGui2d().addOver3D(this);
      this.richText = new JHelpRichText(LearningResources.RESOURCES);

      for(final InstructionType instructionType : InstructionType.values())
      {
         this.richText.associate(UtilText.concatenate('#', instructionType.name(), '#'), instructionType.getIconResourcePath());
      }

      this.addObject2DListener(this);
   }

   /**
    * Called when mouse clicked on tutorial message <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object 2D clicked
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @param leftButton
    *           Indicates if mouse left button is down
    * @param rightButton
    *           Indicates if mouse right button is down
    * @see jhelp.engine.event.Object2DListener#mouseClick(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseClick(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
      this.setVisible(false);
      this.sceneRenderer.getGui2d().allCanBeDetected();
      this.tutorialMessageListener.tutorialClicked(this.tutorialID);
   }

   /**
    * Called when mouse dragged on tutorial message <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object 2D clicked
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @param leftButton
    *           Indicates if mouse left button is down
    * @param rightButton
    *           Indicates if mouse right button is down
    * @see jhelp.engine.event.Object2DListener#mouseDrag(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseDrag(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
   }

   /**
    * Called when mouse entered on tutorial message <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object 2D clicked
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
    * Called when mouse exited on tutorial message <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object 2D clicked
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
    * Called when mouse moved on tutorial message <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object 2D clicked
    * @param x
    *           Mouse X
    * @param y
    *           Mouse Y
    * @see jhelp.engine.event.Object2DListener#mouseMove(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseMove(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Show tutorial message
    * 
    * @param keyText
    *           Key of text to show
    * @param tutorialID
    *           Tutorial ID
    */
   public void showMessage(final String keyText, final int tutorialID)
   {
      this.tutorialID = tutorialID;
      final String text = LearningResources.RESOURCE_TEXT.getText(keyText);
      final JHelpImage image = this.richText.createImage(text, TutorialMessage.FONT, 0xFF000000);
      final JHelpImage background = new JHelpImage(image.getWidth() + 64, image.getHeight() + 64);
      background.startDrawMode();
      background.fillRoundRectangle(0, 0, background.getWidth(), background.getHeight(), 32, 32, 0xCAFEFACE);
      background.drawImage(32, 32, image);
      background.endDrawMode();
      this.texture.setImage(background);
      this.setWidth(image.getWidth());
      this.setHeight(image.getHeight());
      this.setX((this.screenWidth - image.getWidth()) >> 1);
      this.setY((this.screenHeight - image.getHeight()) >> 1);
      this.setVisible(true);
      this.sceneRenderer.getGui2d().setExclusiveDetection(this);
   }
}