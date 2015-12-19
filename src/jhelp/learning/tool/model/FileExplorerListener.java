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

/**
 * File explorer listener
 * 
 * @author JHelp
 */
public interface FileExplorerListener
{
   /**
    * Called when file select for open or save canceled
    */
   public void cancel();

   /**
    * Called when saving to know if its allow to over write an existing file
    * 
    * @param file
    *           File that will be over write if allow
    * @param text
    *           Text reference for user
    * @return {@code true} if allow to over write OR {@code false} to cancel saving
    */
   public boolean canOverWrite(File file, String text);

   /**
    * Called when a file have to open
    * 
    * @param file
    *           File to open
    */
   public void openFile(File file);

   /**
    * Called when have to save to a file
    * 
    * @param file
    *           File where save
    */
   public void saveFile(File file);
}