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
package jhelp.learning.tool.model;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jhelp.engine.JHelpSceneRenderer;
import jhelp.engine.Texture;
import jhelp.engine.event.Button2DListener;
import jhelp.engine.event.Object2DListener;
import jhelp.engine.twoD.Button2D;
import jhelp.engine.twoD.GUI2D;
import jhelp.engine.twoD.Object2D;
import jhelp.learning.tool.language.Instruction;
import jhelp.learning.tool.language.InstructionClear;
import jhelp.learning.tool.language.InstructionColor;
import jhelp.learning.tool.language.InstructionFill;
import jhelp.learning.tool.language.InstructionForward;
import jhelp.learning.tool.language.InstructionHide;
import jhelp.learning.tool.language.InstructionHome;
import jhelp.learning.tool.language.InstructionPenDown;
import jhelp.learning.tool.language.InstructionPenUp;
import jhelp.learning.tool.language.InstructionRotate;
import jhelp.learning.tool.language.InstructionShow;
import jhelp.learning.tool.language.InstructionSpeedFast;
import jhelp.learning.tool.language.InstructionSpeedNormal;
import jhelp.learning.tool.language.InstructionSpeedSlow;
import jhelp.learning.tool.language.InstructionType;
import jhelp.learning.tool.resources.LearningResources;
import jhelp.learning.tool.ui.CursorManipulator;
import jhelp.logo.language.Language;
import jhelp.logo.language.LanguageSpy;
import jhelp.util.debug.Debug;
import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpTextAlign;
import jhelp.util.gui.alphabet.AlphabetGraphitti;
import jhelp.util.gui.alphabet.AlphabetOrange16x16;
import jhelp.util.io.UtilIO;
import jhelp.util.io.json.ArrayJSON;
import jhelp.util.io.json.ObjectJSON;
import jhelp.util.io.json.ValueJSON;
import jhelp.util.math.UtilMath;
import jhelp.util.preference.Preferences;

/**
 * Play instructions, manage them
 * 
 * @author JHelp
 */
public class InstructionExecuter
      implements LanguageSpy, Object2DListener, ColorChooserListener, Button2DListener, FileExplorerListener, KeyListener, TutorialMessageListener
{
   /** Continue tutorial button ID */
   private static final int         BUTTON_ID_CONTINUE_TUTORIAL = 4;
   /** Load file button ID */
   private static final int         BUTTON_ID_LOAD              = 0;
   /** New file button ID */
   private static final int         BUTTON_ID_NEW               = 1;
   /** Restart tutorial button ID */
   private static final int         BUTTON_ID_RESTART_TUTORIAL  = 3;
   /** Save file button ID */
   private static final int         BUTTON_ID_SAVE              = 2;
   /** GO button text and ID */
   private static final String      GO                          = "GO";
   /** Last open file preference key */
   private static final String      LAST_OPEN_FILE              = "LastOpenFile";
   /** Instructions receiver texture name and object ID */
   private static final String      RECEIVER                    = "RECEIVER";
   /** Number of tutorials (+1) */
   private static final int         TOTAL_TUTORIAL_STEPS        = 11;
   /** Preference for save current tutorial step */
   private static final String      TUTORIAL_STEP               = "TutorialStep";
   /** Button for continue tutorial */
   private final Button2D           buttonContinueTutorial;
   /** Button for restart tutorial */
   private final Button2D           buttonRestartTutorial;
   /** Color chooser */
   private final ColorChooser       colorChooser;
   /** Cursor changer */
   private CursorManipulator        cursorManipulator;
   /** Current editing instruction index */
   private int                      editInstruction;
   /** Indicates if program is executing */
   private boolean                  executing;
   /** File explorer */
   private final FileExplorer       fileExplorer;
   /**
    * Object over instruction for indicates with instruction is playing. It is also used to show witch instruction is selected
    * for edition
    */
   private final Object2D           followInstruction;
   /** 2D GUI manager */
   private final GUI2D              gui2d;
   /** Image for show instructions to play */
   private final JHelpImage         imageInstructions;
   /** Current program instructions */
   private final List<Instruction>  instructions;
   /** Language logo interpreter */
   private final Language           language;
   /** Last mouse Y position in instruction list */
   private int                      mouseListY;
   /** Number of visible instruction in list */
   private final int                numberVisible;
   /** Paper sheet where draw is paint and spaceship turtle fly over */
   private final PaperSheet         paperSheet;
   /** User preferences */
   private final Preferences        preferences;
   /** Scene renderer for draw the scene */
   private final JHelpSceneRenderer sceneRenderer;
   /** Actual instruction list scroll */
   private int                      scroll;
   /** Texture for show instruction list */
   private final Texture            textureInstructions;
   /** Object for show tutorial */
   private final TutorialMessage    tutorialMessage;
   /** Current tutorial step */
   private int                      tutorialStep;

   /**
    * Create a new instance of InstructionExecuter
    * 
    * @param sceneRenderer
    *           Scene renderer for draw the scene
    * @param x
    *           Desired instruction list position X
    * @param width
    *           Desired instruction list width
    * @param height
    *           Total available height
    */
   public InstructionExecuter(final JHelpSceneRenderer sceneRenderer, final int x, final int width, final int height)
   {
      this.sceneRenderer = sceneRenderer;
      this.gui2d = this.sceneRenderer.getGui2d();
      this.paperSheet = new PaperSheet(this.sceneRenderer);
      this.language = new Language(this.paperSheet.getTurtle(), this.paperSheet);
      this.language.setLanguageSpy(this);
      this.instructions = new ArrayList<Instruction>();
      this.scroll = 0;
      this.numberVisible = (height - 64) >> 6;
      this.editInstruction = -1;
      this.sceneRenderer.addKeyListener(this);
      this.sceneRenderer.getScene().add(this.paperSheet);
      this.executing = false;
      this.preferences = new Preferences(UtilIO.obtainExternalFile("Learn/learn.pref"));
      int y = 4;

      for(final InstructionType instructionType : InstructionType.values())
      {
         this.addInstructionChoice(x, y, instructionType);
         y += 66;
      }

      final Object2D goButton = new Object2D((x + width) - 256, 0, 256, 64);
      Texture texture = new Texture(InstructionExecuter.GO, 256, 64);
      final JHelpImage image = new JHelpImage(256, 64);
      image.startDrawMode();
      image.fillRoundRectangle(64, 2, 128, 60, 32, 32, 0xFF668844, false);
      AlphabetGraphitti.NORMAL.drawOn(InstructionExecuter.GO, JHelpTextAlign.CENTER, image, 128, 32, true);
      image.endDrawMode();
      texture.setImage(image);
      goButton.setTexture(texture);
      goButton.setAdditionalInformation(InstructionExecuter.GO);
      goButton.addObject2DListener(this);
      this.gui2d.addOver3D(goButton);

      final Object2D instructionsReceiver = new Object2D((x + width) - 256, 64, 256, height - 64);
      this.textureInstructions = new Texture("Instructions", 256, height - 64, 0x88FFFFFF);
      this.imageInstructions = new JHelpImage(256, height - 64, 0x88FFFFFF);
      this.textureInstructions.setImage(this.imageInstructions);
      instructionsReceiver.setTexture(this.textureInstructions);
      this.gui2d.addOver3D(instructionsReceiver);
      instructionsReceiver.setAdditionalInformation(InstructionExecuter.RECEIVER);
      instructionsReceiver.addObject2DListener(this);

      int yy = 16;
      Button2D button2d = new Button2D(InstructionExecuter.BUTTON_ID_LOAD, 16, yy, 64, 64,//
            LearningResources.RESOURCES.obtainJHelpImage("load_normal.png"),//
            LearningResources.RESOURCES.obtainJHelpImage("load_over.png"));
      button2d.registerButton2DListener(this);
      this.gui2d.addOver3D(button2d);

      button2d = new Button2D(InstructionExecuter.BUTTON_ID_SAVE, 90, yy, 64, 64,//
            LearningResources.RESOURCES.obtainJHelpImage("save_normal.png"),//
            LearningResources.RESOURCES.obtainJHelpImage("save_over.png"),//
            LearningResources.RESOURCES.obtainJHelpImage("save_clicked.png"));
      button2d.registerButton2DListener(this);
      this.gui2d.addOver3D(button2d);

      button2d = new Button2D(InstructionExecuter.BUTTON_ID_NEW, 164, yy, 64, 64,//
            LearningResources.RESOURCES.obtainJHelpImage("new.png"));
      button2d.registerButton2DListener(this);
      this.gui2d.addOver3D(button2d);
      yy += 70;

      this.addPosition(LearningResources.PositionOrigin, yy);
      this.addPosition(LearningResources.PositionFace, yy + 80);
      this.addPosition(LearningResources.PositionLeft, yy + 160);
      this.addPosition(LearningResources.PositionBack, yy + 240);
      this.addPosition(LearningResources.PositionRight, yy + 320);

      this.buttonRestartTutorial = new Button2D(InstructionExecuter.BUTTON_ID_RESTART_TUTORIAL, 8, yy + 420, 64, 64,//
            LearningResources.RESOURCES.obtainJHelpImage("restart.png"));
      this.buttonRestartTutorial.registerButton2DListener(this);
      this.gui2d.addOver3D(this.buttonRestartTutorial);

      this.buttonContinueTutorial = new Button2D(InstructionExecuter.BUTTON_ID_CONTINUE_TUTORIAL, 80, yy + 420, 100, 50,//
            LearningResources.RESOURCES.obtainJHelpImage("continue_normal.png"),//
            LearningResources.RESOURCES.obtainJHelpImage("continue_clicked.png"));
      this.buttonContinueTutorial.registerButton2DListener(this);
      this.gui2d.addOver3D(this.buttonContinueTutorial);

      this.colorChooser = new ColorChooser(((x + width) - ColorChooser.WIDTH) >> 1, (height - ColorChooser.HEIGHT) >> 1, this);
      this.gui2d.addOver3D(this.colorChooser);
      this.colorChooser.setVisible(false);

      this.followInstruction = new Object2D((x + width) - 256, 64, 256, 64);
      texture = new Texture("Follow", 8, 8, 0x88FF0000);
      this.followInstruction.setTexture(texture);
      this.followInstruction.setVisible(false);
      this.gui2d.addOver3D(this.followInstruction);

      final int wi = x + width;
      final int w = wi >> 1;
      final int h = height >> 1;
      this.fileExplorer = new FileExplorer((wi - w) >> 1, (height - h) >> 1, w, h);
      this.gui2d.addOver3D(this.fileExplorer);
      this.tutorialMessage = new TutorialMessage(sceneRenderer, x + width, height, this);

      this.scroll(0);
      final File file = this.preferences.getFileValue(InstructionExecuter.LAST_OPEN_FILE);

      if(file != null)
      {
         this.openFile(file);
      }

      this.tutorialStep = this.preferences.getValue(InstructionExecuter.TUTORIAL_STEP, 1);
      this.updateTutorialButtons();
      this.showTutorial();
   }

   /**
    * Add instruction copy to the end of instructions list
    * 
    * @param instruction
    *           Instruction to copy
    */
   private void addInstruction(final Instruction instruction)
   {
      this.addInstruction(instruction, this.instructions.size());
   }

   /**
    * Insert instruction copy to a given index in instructions list
    * 
    * @param instruction
    *           Instruction to copy
    * @param index
    *           Index where insert copy
    */
   private void addInstruction(final Instruction instruction, final int index)
   {
      if(this.executing == true)
      {
         return;
      }

      this.finishEdit();
      final InstructionType instructionType = instruction.getInstructionType();
      Instruction instructionToAdd = null;

      switch(instructionType)
      {
         case CLEAR:
            instructionToAdd = new InstructionClear();
         break;
         case COLOR:
            this.colorChooser.setVisible(true);
            this.gui2d.setExclusiveDetection(this.colorChooser);
         break;
         case FAST:
            instructionToAdd = new InstructionSpeedFast();
         break;
         case FILL:
            instructionToAdd = new InstructionFill();
         break;
         case FORWARD:
         {
            final String value = JOptionPane.showInputDialog(instruction.getInformation());

            if(value != null)
            {
               try
               {
                  final int step = Integer.parseInt(value);
                  instructionToAdd = new InstructionForward();
                  ((InstructionForward) instructionToAdd).setStep(step);
               }
               catch(final Exception exception)
               {
               }
            }
         }
         break;
         case HIDE:
            instructionToAdd = new InstructionHide();
         break;
         case HOME:
            instructionToAdd = new InstructionHome();
         break;
         case NORMAL:
            instructionToAdd = new InstructionSpeedNormal();
         break;
         case PEN_DOWN:
            instructionToAdd = new InstructionPenDown();
         break;
         case PEN_UP:
            instructionToAdd = new InstructionPenUp();
         break;
         case ROTATE:
         {
            final String value = JOptionPane.showInputDialog(instruction.getInformation());

            if(value != null)
            {
               try
               {
                  final int angle = Integer.parseInt(value);
                  instructionToAdd = new InstructionRotate();
                  ((InstructionRotate) instructionToAdd).setAngle(angle);
               }
               catch(final Exception exception)
               {
               }
            }
         }
         break;
         case SHOW:
            instructionToAdd = new InstructionShow();
         break;
         case SLOW:
            instructionToAdd = new InstructionSpeedSlow();
         break;
         default:
         break;
      }

      if(instructionToAdd != null)
      {
         this.instructions.add(index, instructionToAdd);
         this.makeVisible(index);
      }
   }

   /**
    * Add an instruction choice (To add to program)
    * 
    * @param x
    *           X position
    * @param y
    *           Y position
    * @param instructionType
    *           Instruction type to add
    */
   private void addInstructionChoice(final int x, final int y, final InstructionType instructionType)
   {
      final Instruction instruction = instructionType.createInstructionInstance();
      final Object2D object2d = instruction.obtainObject2D();
      object2d.setCanBeDetected(true);
      object2d.addObject2DListener(this);
      object2d.setX(x);
      object2d.setY(y);
      this.gui2d.addOver3D(object2d);
   }

   /**
    * Add a change position button
    * 
    * @param textKey
    *           Button reference text key and button ID
    * @param y
    *           Y position
    */
   private void addPosition(final String textKey, final int y)
   {
      final Object2D object2d = new Object2D(8, y, 256, 64);
      object2d.setAdditionalInformation(textKey);
      object2d.setCanBeDetected(true);
      object2d.addObject2DListener(this);
      final Texture texture = new Texture(textKey, 256, 64);
      object2d.setTexture(texture);
      final JHelpImage image = new JHelpImage(256, 64, 0xFFFFFFFF);
      image.startDrawMode();
      AlphabetOrange16x16.ALPHABET_ORANGE16X16.drawOn(LearningResources.RESOURCE_TEXT.getText(textKey), JHelpTextAlign.CENTER, image, 128, 32, true);
      image.endDrawMode();
      texture.setImage(image);
      this.gui2d.addOver3D(object2d);
   }

   /**
    * Called when object 2D not an instruction clicked
    * 
    * @param ordrer
    *           Object 2D ID
    */
   private void applyOrder(final String ordrer)
   {
      if(LearningResources.PositionOrigin.equals(ordrer) == true)
      {
         this.paperSheet.originalPosition();
         return;
      }

      if(LearningResources.PositionBack.equals(ordrer) == true)
      {
         this.paperSheet.backView();
         return;
      }

      if(LearningResources.PositionFace.equals(ordrer) == true)
      {
         this.paperSheet.frontView();
         return;
      }

      if(LearningResources.PositionLeft.equals(ordrer) == true)
      {
         this.paperSheet.leftView();
         return;
      }

      if(LearningResources.PositionRight.equals(ordrer) == true)
      {
         this.paperSheet.rightView();
         return;
      }

      if((InstructionExecuter.GO.equals(ordrer) == true) && (this.executing == false))
      {
         this.executing = true;
         this.finishEdit();
         this.language.raz();
         int color = 0xFF000000;
         final int length = this.instructions.size();
         final String[] lines = new String[length];
         Instruction instruction;

         for(int i = 0; i < length; i++)
         {
            instruction = this.instructions.get(i);
            lines[i] = instruction.languageInstructions(color);

            if(instruction.getInstructionType() == InstructionType.COLOR)
            {
               color = ((InstructionColor) instruction).getColor();
            }
         }

         this.language.appendBlocks(lines);
         return;
      }
   }

   /**
    * Finish current instruction edition
    */
   private void finishEdit()
   {
      if(this.editInstruction >= 0)
      {
         this.followInstruction.setVisible(false);
         this.editInstruction = -1;
      }
   }

   /**
    * Scroll instruction list so that the given index is visible
    * 
    * @param index
    *           Index to show
    */
   private void makeVisible(int index)
   {
      index = UtilMath.limit(index, 0, Math.max(0, this.instructions.size() - 1));

      if((index >= this.scroll) && (index < (this.scroll + this.numberVisible)))
      {
         this.updateList();
         return;
      }

      if(index < this.scroll)
      {
         this.scroll(index - this.scroll);
         return;
      }

      this.scroll((index - this.scroll - this.numberVisible) + 1);
   }

   /**
    * Scroll instruction list of an amount of cell.<br>
    * If given amount positive, the list scroll up, if negative list scroll down
    * 
    * @param amount
    *           Number of cell to scroll
    */
   private void scroll(final int amount)
   {
      this.scroll = UtilMath.limit(this.scroll + amount, 0, Math.max(0, (this.instructions.size() - this.numberVisible) + 1));
      this.updateList();
   }

   /**
    * Show current tutorial message
    */
   private void showTutorial()
   {
      if(this.tutorialStep >= InstructionExecuter.TOTAL_TUTORIAL_STEPS)
      {
         return;
      }

      this.tutorialMessage.showMessage("TutorialStep" + this.tutorialStep, this.tutorialStep);
   }

   /**
    * Update the instruction list image
    */
   private void updateList()
   {
      this.imageInstructions.startDrawMode();
      this.imageInstructions.clear(0x88FFFFFF);
      final int max = Math.min(this.instructions.size(), this.scroll + this.numberVisible + 1);

      for(int index = this.scroll; index < max; index++)
      {
         this.imageInstructions.drawImage(0, (index - this.scroll) << 6, this.instructions.get(index).getComposedImage());
      }

      this.imageInstructions.endDrawMode();
      this.textureInstructions.setImage(this.imageInstructions);
   }

   /**
    * Update tutorial buttons visibilities
    */
   private void updateTutorialButtons()
   {
      this.buttonRestartTutorial.setVisible(this.tutorialStep > 1);
      this.buttonContinueTutorial.setVisible(this.tutorialStep < InstructionExecuter.TOTAL_TUTORIAL_STEPS);
   }

   /**
    * Called when button 2D clicked <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param button2d
    *           Button clicked
    * @param buttonID
    *           Button ID
    * @see jhelp.engine.event.Button2DListener#button2DClicked(jhelp.engine.twoD.Button2D, int)
    */
   @Override
   public void button2DClicked(final Button2D button2d, final int buttonID)
   {
      if(this.executing == true)
      {
         return;
      }

      switch(buttonID)
      {
         case BUTTON_ID_LOAD:
            this.fileExplorer.openFile(this);
         break;
         case BUTTON_ID_SAVE:
            this.fileExplorer.saveFile(this);
         break;
         case BUTTON_ID_NEW:
            this.instructions.clear();
            this.updateList();
            this.finishEdit();
            this.language.raz();
            this.preferences.setValue(InstructionExecuter.LAST_OPEN_FILE, new File(""));
         break;
         case BUTTON_ID_RESTART_TUTORIAL:
            this.tutorialStep = 1;
            this.updateTutorialButtons();
            this.showTutorial();
         break;
         case BUTTON_ID_CONTINUE_TUTORIAL:
            this.showTutorial();
         break;
      }
   }

   /**
    * Called when file choose is canceled <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.learning.tool.model.FileExplorerListener#cancel()
    */
   @Override
   public void cancel()
   {
   }

   /**
    * Indicates if its allow to over write a file before saving <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param file
    *           File to over write
    * @param text
    *           User file name
    * @return {@code true} if its allowed
    * @see jhelp.learning.tool.model.FileExplorerListener#canOverWrite(java.io.File, java.lang.String)
    */
   @Override
   public boolean canOverWrite(final File file, final String text)
   {
      return true;
   }

   /**
    * Called when color choose <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param color
    *           Chosen color
    * @see jhelp.learning.tool.model.ColorChooserListener#colorChoose(int)
    */
   @Override
   public void colorChoose(final int color)
   {
      this.colorChooser.setVisible(false);
      this.gui2d.setExclusiveDetection(null);
      final InstructionColor instructionColor;

      if(this.editInstruction < 0)
      {
         instructionColor = new InstructionColor();
      }
      else
      {
         instructionColor = (InstructionColor) this.instructions.get(this.editInstruction);
      }

      instructionColor.setColor(color);

      if(this.editInstruction < 0)
      {
         this.instructions.add(instructionColor);
         this.makeVisible(this.instructions.size() - 1);
      }
      else
      {
         this.updateList();
      }
   }

   /**
    * Called when language execute a line <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param line
    *           Read line
    * @param lineNumber
    *           Line number
    * @param executing
    *           Indicates if its real execution or a register for later
    * @see jhelp.logo.language.LanguageSpy#excuteLine(java.lang.String, int, boolean)
    */
   @Override
   public void excuteLine(final String line, final int lineNumber, final boolean executing)
   {
      this.makeVisible(lineNumber);
      this.followInstruction.setY(((lineNumber - this.scroll) * 64) + 64);
      this.followInstruction.setVisible(true);
   }

   /**
    * Called when program is finished <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.logo.language.LanguageSpy#finished()
    */
   @Override
   public void finished()
   {
      this.executing = false;
      this.followInstruction.setVisible(false);
   }

   /**
    * Called when a key pressed <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param keyEvent
    *           Key event description
    * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
    */
   @Override
   public void keyPressed(final KeyEvent keyEvent)
   {
      if((this.colorChooser.isVisible() == true) || (this.executing == true))
      {
         return;
      }

      switch(keyEvent.getKeyCode())
      {
         case KeyEvent.VK_UP:
            if(this.editInstruction > 0)
            {
               final Instruction instruction = this.instructions.remove(this.editInstruction);
               this.editInstruction--;
               this.instructions.add(this.editInstruction, instruction);
               this.makeVisible(this.editInstruction);
               this.followInstruction.setY(((this.editInstruction - this.scroll) * 64) + 64);
               this.followInstruction.setVisible(true);
            }
         break;
         case KeyEvent.VK_DOWN:
            if((this.editInstruction >= 0) && (this.editInstruction < (this.instructions.size() - 1)))
            {
               final Instruction instruction = this.instructions.remove(this.editInstruction);
               this.editInstruction++;
               this.instructions.add(this.editInstruction, instruction);
               this.makeVisible(this.editInstruction);
               this.followInstruction.setY(((this.editInstruction - this.scroll) * 64) + 64);
               this.followInstruction.setVisible(true);
            }
         break;
         case KeyEvent.VK_BACK_SPACE:
         case KeyEvent.VK_DELETE:
            if(this.editInstruction >= 0)
            {
               this.instructions.remove(this.editInstruction);
               this.makeVisible(this.editInstruction);
               this.followInstruction.setVisible(false);
               this.editInstruction = -1;
            }
         break;
         case KeyEvent.VK_ENTER:
            this.finishEdit();
         break;
         case KeyEvent.VK_SPACE:
            if(this.editInstruction >= 0)
            {
               final Instruction instruction = this.instructions.get(this.editInstruction);

               switch(instruction.getInstructionType())
               {
                  case COLOR:
                     this.colorChooser.setVisible(true);
                     this.gui2d.setExclusiveDetection(this.colorChooser);
                  break;
                  case FORWARD:
                  {
                     final String value = JOptionPane.showInputDialog(instruction.getInformation());

                     if(value != null)
                     {
                        try
                        {
                           final int step = Integer.parseInt(value);
                           ((InstructionForward) instruction).setStep(step);
                           this.updateList();
                        }
                        catch(final Exception exception)
                        {
                        }
                     }
                  }
                  break;
                  case ROTATE:
                  {
                     final String value = JOptionPane.showInputDialog(instruction.getInformation());

                     if(value != null)
                     {
                        try
                        {
                           final int angle = Integer.parseInt(value);
                           ((InstructionRotate) instruction).setAngle(angle);
                           this.updateList();
                        }
                        catch(final Exception exception)
                        {
                        }
                     }
                  }
                  break;
                  default:
                  break;
               }
            }
         break;
         case KeyEvent.VK_C:
            if(this.editInstruction >= 0)
            {
               this.instructions.add(this.editInstruction + 1, this.instructions.get(this.editInstruction).copy());
               this.makeVisible(this.editInstruction + 1);
            }
         break;
      }
   }

   /**
    * Called when a key released <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param keyEvent
    *           Key event description
    * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
    */
   @Override
   public void keyReleased(final KeyEvent keyEvent)
   {
   }

   /**
    * Called when a key typed <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param keyEvent
    *           Key event description
    * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
    */
   @Override
   public void keyTyped(final KeyEvent keyEvent)
   {
   }

   /**
    * Called when mouse click on 2D object <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Clicked object
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @param leftButton
    *           Indicates if left button is down
    * @param rightButton
    *           Indicates if right button is down
    * @see jhelp.engine.event.Object2DListener#mouseClick(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseClick(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
      final Object information = object2d.getAdditionalInformation();

      if(InstructionExecuter.RECEIVER.equals(information) == true)
      {
         if(rightButton == true)
         {
            final int index = this.scroll + (y / 64);

            if(index < this.instructions.size())
            {
               this.editInstruction = index;
               this.followInstruction.setY(((this.editInstruction - this.scroll) * 64) + 64);
               this.followInstruction.setVisible(true);
            }
         }

         this.mouseListY = y;
         return;
      }

      if(information instanceof String)
      {
         this.applyOrder((String) information);
      }
      else if(information instanceof Instruction)
      {
         this.addInstruction((Instruction) information);
      }
   }

   /**
    * Called when mouse drag on 2D object <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Reference object
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @param leftButton
    *           Indicates if left button is down
    * @param rightButton
    *           Indicates if right button is down
    * @see jhelp.engine.event.Object2DListener#mouseDrag(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
    */
   @Override
   public void mouseDrag(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
   {
      if(InstructionExecuter.RECEIVER.equals(object2d.getAdditionalInformation()) == true)
      {
         if(leftButton == true)
         {
            final int dy = (this.mouseListY - y) / 64;

            if(dy != 0)
            {
               this.scroll(dy);
               this.mouseListY = y;
            }
         }
         else
         {
            this.mouseListY = y;
         }

         return;
      }
   }

   /**
    * Called when mouse enter on 2D object <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Reference object
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseEnter(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseEnter(final Object2D object2d, final int x, final int y)
   {
      if(InstructionExecuter.RECEIVER.equals(object2d.getAdditionalInformation()) == true)
      {
         this.mouseListY = y;
         return;
      }
   }

   /**
    * Called when mouse exit from 2D object <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Reference object
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseExit(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseExit(final Object2D object2d, final int x, final int y)
   {
      if(InstructionExecuter.RECEIVER.equals(object2d.getAdditionalInformation()) == true)
      {
         this.mouseListY = y;
         return;
      }
   }

   /**
    * Called when mouse move over 2D object <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Reference object
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseMove(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseMove(final Object2D object2d, final int x, final int y)
   {
      if(InstructionExecuter.RECEIVER.equals(object2d.getAdditionalInformation()) == true)
      {
         this.mouseListY = y;
         return;
      }
   }

   /**
    * Open instructions file <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param file
    *           Instructions file to open
    * @see jhelp.learning.tool.model.FileExplorerListener#openFile(java.io.File)
    */
   @Override
   public void openFile(final File file)
   {
      BufferedReader bufferedReader = null;

      try
      {
         if((file == null) || (file.exists() == false) || (file.isDirectory() == true))
         {
            return;
         }

         bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
         final StringBuilder stringBuilder = new StringBuilder();
         String line = bufferedReader.readLine();

         while(line != null)
         {
            stringBuilder.append(line);
            stringBuilder.append('\n');
            line = bufferedReader.readLine();
         }

         final ObjectJSON json = ObjectJSON.parse(stringBuilder.toString());
         final ArrayJSON arrayJSON = json.get("Instructions").getArray();
         final int length = arrayJSON.numberOfValue();
         this.instructions.clear();

         for(int i = 0; i < length; i++)
         {
            this.instructions.add(Instruction.parseInstruction(arrayJSON.getValue(i).getObject()));
         }

         this.makeVisible(0);
         this.preferences.setValue(InstructionExecuter.LAST_OPEN_FILE, file);
      }
      catch(final Exception exception)
      {
         Debug.printException(exception, "Failed to open ", file.getAbsolutePath());
      }
      finally
      {
         if(bufferedReader != null)
         {
            try
            {
               bufferedReader.close();
            }
            catch(final Exception exception)
            {
            }
         }
      }
   }

   /**
    * Save current program <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param file
    *           File where save program
    * @see jhelp.learning.tool.model.FileExplorerListener#saveFile(java.io.File)
    */
   @Override
   public void saveFile(final File file)
   {
      BufferedWriter bufferedWriter = null;

      try
      {
         if(UtilIO.createFile(file) == false)
         {
            throw new IOException("Failed to create " + file.getAbsolutePath());
         }

         final ArrayJSON arrayJSON = new ArrayJSON();

         for(final Instruction instruction : this.instructions)
         {
            arrayJSON.addValue(ValueJSON.newValue(instruction.serialize()));
         }

         final ObjectJSON json = new ObjectJSON();
         json.put("Instructions", ValueJSON.newValue(arrayJSON));

         bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
         bufferedWriter.write(json.toString());
         this.preferences.setValue(InstructionExecuter.LAST_OPEN_FILE, file);
      }
      catch(final Exception exception)
      {
         Debug.printException(exception, "Failed to save ", file.getAbsolutePath());
      }
      finally
      {
         if(bufferedWriter != null)
         {
            try
            {
               bufferedWriter.flush();
            }
            catch(final Exception exception)
            {
            }

            try
            {
               bufferedWriter.close();
            }
            catch(final Exception exception)
            {
            }
         }
      }
   }

   /**
    * Change mouse cursor
    * 
    * @param cursor
    *           New cursor
    */
   public void setCursor(final Cursor cursor)
   {
      if(this.cursorManipulator != null)
      {
         this.cursorManipulator.setCursor(cursor);
      }
   }

   /**
    * Defines/changes the cursor manager.<br>
    * Use {@code null} to remove actual
    * 
    * @param cursorManipulator
    *           Cursor manager to use OR {@code null} to remove actual
    */
   public void setCursorManipulator(final CursorManipulator cursorManipulator)
   {
      this.cursorManipulator = cursorManipulator;
   }

   /**
    * Called when tutorial message clicked <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param tutorialID
    *           Tutorial ID
    * @see jhelp.learning.tool.model.TutorialMessageListener#tutorialClicked(int)
    */
   @Override
   public void tutorialClicked(final int tutorialID)
   {
      this.tutorialStep = tutorialID + 1;
      this.updateTutorialButtons();

      switch(this.tutorialStep)
      {
         case 2:
            this.showTutorial();
         break;
      }

      this.preferences.setValue(InstructionExecuter.TUTORIAL_STEP, this.tutorialStep);
   }
}