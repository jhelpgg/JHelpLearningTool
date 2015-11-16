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