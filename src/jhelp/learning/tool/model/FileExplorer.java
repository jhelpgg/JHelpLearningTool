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

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jhelp.engine.Texture;
import jhelp.engine.event.Object2DListener;
import jhelp.engine.twoD.Object2D;
import jhelp.learning.tool.resources.LearningResources;
import jhelp.util.debug.Debug;
import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpTextAlign;
import jhelp.util.gui.alphabet.AlphabetGreen8x16;
import jhelp.util.io.UtilIO;
import jhelp.util.resources.ResourceDirectory;
import jhelp.util.resources.ResourceElement;
import jhelp.util.resources.ResourceFile;
import jhelp.util.resources.ResourcesSystem;
import jhelp.util.text.UtilText;
import jhelp.util.thread.ThreadManager;
import jhelp.util.thread.ThreadedVerySimpleTask;

/**
 * File explorer
 * 
 * @author JHelp
 */
public class FileExplorer
      extends Object2D
      implements Object2DListener
{
   /**
    * Area where lies a file
    * 
    * @author JHelp
    */
   class FileArea
   {
      /** File inside area */
      File      file;
      /** Exposed name */
      String    text;
      /** Maximum X value for area bounds */
      final int xMax;
      /** Minimum X value for area bounds */
      final int xMin;
      /** Maximum Y value for area bounds */
      final int yMax;
      /** Minimum Y value for area bounds */
      final int yMin;

      /**
       * Create a new instance of FileArea
       * 
       * @param xMin
       *           Minimum X value for area bounds
       * @param xMax
       *           Maximum X value for area bounds
       * @param yMin
       *           Minimum Y value for area bounds
       * @param yMax
       *           Maximum Y value for area bounds
       * @param file
       *           File inside area
       */
      FileArea(final int xMin, final int xMax, final int yMin, final int yMax, final File file)
      {
         this.xMin = xMin;
         this.xMax = xMax;
         this.yMin = yMin;
         this.yMax = yMax;
         this.file = file;
         final String name = file.getName();
         this.text = name.substring(0, name.length() - FileExplorer.EXTENTION.length());
      }

      /**
       * Indicates if mouse position inside area
       * 
       * @param x
       *           Mouse X
       * @param y
       *           Mouse Y
       * @return {@code true} if mouse position inside area
       */
      boolean inside(int x, int y)
      {
         // We divide position, because texture is multiply by 2
         x >>= 1;
         y >>= 1;
         return (x >= this.xMin) && (x <= this.xMax) && (y >= this.yMin) && (y <= this.yMax);
      }
   }

   /**
    * Filter for learning files
    * 
    * @author JHelp
    */
   static class LearnFileFilter
         implements FileFilter
   {
      /**
       * Create a new instance of LearnFileFilter
       */
      LearnFileFilter()
      {
      }

      /**
       * Indicates if given file is a learning file <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param file
       *           Tested file
       * @return {@code true} if given file is a learning file
       * @see java.io.FileFilter#accept(java.io.File)
       */
      @Override
      public boolean accept(final File file)
      {
         if((file.isDirectory() == true) || (UtilIO.isVirtualLink(file) == true))
         {
            return false;
         }

         return file.getName().endsWith(FileExplorer.EXTENTION);
      }
   }

   /**
    * Task for copy samples to user directory (For make them available for him)
    * 
    * @author JHelp
    */
   static class TaskObtainTutorialExamples
         extends ThreadedVerySimpleTask
   {
      /**
       * Create a new instance of TaskObtainTutorialExamples
       */
      TaskObtainTutorialExamples()
      {
      }

      /**
       * Copy samples to user directory (For make them available for him) <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @see jhelp.util.thread.ThreadedVerySimpleTask#doVerySimpleAction()
       */
      @Override
      protected void doVerySimpleAction()
      {
         try
         {
            final ResourcesSystem resourcesSystem = LearningResources.RESOURCES.obtainResourcesSystem();
            final ResourceDirectory resourceDirectory = new ResourceDirectory("tutorial");
            ResourceFile resourceFile;
            File file;
            InputStream inputStream;

            for(final ResourceElement resourceElement : resourcesSystem.obtainList(resourceDirectory))
            {
               resourceFile = (ResourceFile) resourceElement;
               file = new File(FileExplorer.DIRECTORY, resourceFile.getName());

               if(file.exists() == false)
               {
                  inputStream = resourcesSystem.obtainInputStream(resourceFile);
                  UtilIO.write(inputStream, file);
                  inputStream.close();
               }
            }
         }
         catch(final Exception exception)
         {
            Debug.printException(exception, "Failed to copy tutorial examples");
         }
      }
   }

   /** Learn file extension */
   private static final String          EXTENTION   = ".learn";
   /** Learning file filter */
   private static final LearnFileFilter FILE_FILTER = new LearnFileFilter();
   /** Directory where search learning files */
   static final File                    DIRECTORY   = UtilIO.obtainExternalFile("Learn/programs");

   /**
    * Launch the copy samples to user directory (For make them available for him)
    */
   public static void loadTutorialExamples()
   {
      ThreadManager.THREAD_MANAGER.doThread(new TaskObtainTutorialExamples(), null);
   }

   /** Files areas */
   private final List<FileArea> fileAreas;
   /** Listener of file explorer events */
   private FileExplorerListener fileExplorerListener;

   /** Texture where draw the file explorer */
   private final Texture        texture;

   /**
    * Create a new instance of FileExplorer
    * 
    * @param x
    *           X position
    * @param y
    *           Y position
    * @param width
    *           Width
    * @param height
    *           Height
    */
   public FileExplorer(final int x, final int y, final int width, final int height)
   {
      super(x, y, width, height);
      this.fileAreas = new ArrayList<FileArea>();
      this.texture = new Texture("FileExplorer", width >> 1, height >> 1);
      this.setTexture(this.texture);
      this.setVisible(false);
      this.addObject2DListener(this);
   }

   /**
    * Update file explorer texture
    */
   private void update()
   {
      UtilIO.createDirectory(FileExplorer.DIRECTORY);
      this.fileAreas.clear();
      String text, name;
      int x = 0;
      int y = 0;
      int coulmnWidth = 0;
      final int width = this.texture.getWidth();
      final int height = this.texture.getHeight();
      final JHelpImage image = new JHelpImage(width, height, 0xCC0000FF);
      image.startDrawMode();

      for(final File file : FileExplorer.DIRECTORY.listFiles(FileExplorer.FILE_FILTER))
      {
         name = file.getName();
         text = name.substring(0, name.length() - FileExplorer.EXTENTION.length());
         this.fileAreas.add(new FileArea(x, x + (text.length() * 8), y, y + 16, file));
         AlphabetGreen8x16.ALPHABET_GREEN8X16.drawOn(text, JHelpTextAlign.LEFT, image, x, y);
         coulmnWidth = Math.max(coulmnWidth, text.length() * 8);
         y += 18;

         if((y + 18) > height)
         {
            x = coulmnWidth + 8;
            coulmnWidth = 0;
            y = 0;
         }
      }

      image.endDrawMode();
      this.texture.setImage(image);
   }

   /**
    * Called when mouse clicked <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
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
      for(final FileArea fileArea : this.fileAreas)
      {
         if(fileArea.inside(x, y) == true)
         {
            this.fileExplorerListener.openFile(fileArea.file);
            this.setVisible(false);
            return;
         }
      }

      this.fileExplorerListener.cancel();
      this.setVisible(false);
   }

   /**
    * Called when mouse drag <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
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
   }

   /**
    * Called when mouse enter <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseEnter(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseEnter(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Called when mouse exit <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseExit(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseExit(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Called when mouse move <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param object2d
    *           Object reference
    * @param x
    *           Mouse X on object
    * @param y
    *           Mouse Y on object
    * @see jhelp.engine.event.Object2DListener#mouseMove(jhelp.engine.twoD.Object2D, int, int)
    */
   @Override
   public void mouseMove(final Object2D object2d, final int x, final int y)
   {
   }

   /**
    * Show for open a file
    * 
    * @param fileExplorerListener
    *           File explorer listener to alert on events
    */
   public void openFile(final FileExplorerListener fileExplorerListener)
   {
      if(fileExplorerListener == null)
      {
         throw new NullPointerException("fileExplorerListener musn't be null");
      }

      this.fileExplorerListener = fileExplorerListener;
      this.update();
      this.setVisible(true);
   }

   /**
    * Show for save a file
    * 
    * @param fileExplorerListener
    *           File explorer listener to alert on events
    */
   public void saveFile(final FileExplorerListener fileExplorerListener)
   {
      this.update();
      this.setVisible(true);
      String name = JOptionPane.showInputDialog("");

      if(name == null)
      {
         fileExplorerListener.cancel();
         this.setVisible(false);
         return;
      }

      name = UtilText.removeWhiteCharacters(name.trim().replace(' ', '_'));

      if(name.length() == 0)
      {
         fileExplorerListener.cancel();
         this.setVisible(false);
         return;
      }

      final File file = new File(FileExplorer.DIRECTORY, name + FileExplorer.EXTENTION);

      if(file.exists() == true)
      {
         if(fileExplorerListener.canOverWrite(file, name) == false)
         {
            fileExplorerListener.cancel();
            this.setVisible(false);
            return;
         }
      }

      fileExplorerListener.saveFile(file);
      this.setVisible(false);
   }

}