package jhelp.learning.tool.ui;

import java.awt.Cursor;

/**
 * Capacity to change mouse cursor
 * 
 * @author JHelp
 */
public interface CursorManipulator
{
   /**
    * Change mouse cursor
    * 
    * @param cursor
    *           New cursor to use
    */
   public void setCursor(Cursor cursor);
}