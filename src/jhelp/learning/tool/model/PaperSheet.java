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

import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.atomic.AtomicBoolean;

import jhelp.engine.JHelpSceneRenderer;
import jhelp.engine.Material;
import jhelp.engine.Node;
import jhelp.engine.Texture;
import jhelp.engine.anim.AnimationPositionNode;
import jhelp.engine.geom.Plane;
import jhelp.engine.util.PositionNode;
import jhelp.logo.language.LogoGraphics;
import jhelp.logo.language.Turtle;
import jhelp.util.gui.JHelpImage;

/**
 * Paper sheet where draw is paint and spaceship turtle fly over
 * 
 * @author JHelp
 */
public class PaperSheet
      extends Node
      implements LogoGraphics, SpaceShipTurtleListener, MouseWheelListener
{
   /** Back view animation */
   private final AnimationPositionNode backView;
   /** Indicates if we are in draw mode */
   private boolean                     draw;
   /** Front/face view animation */
   private final AnimationPositionNode frontView;
   /** Left view animation */
   private final AnimationPositionNode leftView;
   /** Original view animation */
   private final AnimationPositionNode originalPosition;
   /** Plane where lies the paper */
   private final Plane                 paper;
   /** Texture for draw on paper */
   private final Texture               paperTexture;
   /** Right view animation */
   private final AnimationPositionNode rightView;
   /** Scene render parent */
   private final JHelpSceneRenderer    sceneRenderer;
   /** Space ship turtle linked */
   private final SpaceShipTurtle       spaceShipTurtle;
   /** Logo turtle */
   private final Turtle                turtle;
   /** Indicates if currently waiting an end of animation */
   private final AtomicBoolean         waiting;

   /**
    * Create a new instance of PaperSheet
    * 
    * @param sceneRenderer
    *           Scene render parent
    */
   public PaperSheet(final JHelpSceneRenderer sceneRenderer)
   {
      if(sceneRenderer == null)
      {
         throw new NullPointerException("sceneRenderer musn't be null");
      }

      this.sceneRenderer = sceneRenderer;
      this.sceneRenderer.addMouseWheelListener(this);
      this.waiting = new AtomicBoolean(false);
      this.turtle = new Turtle(1024, 1024);
      this.spaceShipTurtle = new SpaceShipTurtle();
      this.spaceShipTurtle.registerSpaceShipTurtleListener(this);
      this.paperTexture = new Texture("Paper", 1024, 1024, 0xFFFFFFFF);
      this.paper = new Plane();
      this.draw = false;

      this.paper.setAngleX(90);
      this.paper.scale(10.24f);
      final Material material = new Material("Paper");
      material.settingAsFor2D();
      material.setTextureDiffuse(this.paperTexture);
      this.paper.setMaterial(material);
      this.addChild(this.paper);

      this.spaceShipTurtle.setPosition(0, 0.15f, 0);
      this.spaceShipTurtle.setScale(0.1f);
      this.addChild(this.spaceShipTurtle);

      this.setPosition(0, 0, -12.3f);
      this.setAngleX(90);

      this.originalPosition = new AnimationPositionNode(this);
      this.originalPosition.addFrame(12, new PositionNode(0, 0, -12.3f, 90, 0, 0));

      this.frontView = new AnimationPositionNode(this);
      this.frontView.addFrame(12, new PositionNode(0, 0, -12.3f, 45, 0, 0));

      this.leftView = new AnimationPositionNode(this);
      this.leftView.addFrame(12, new PositionNode(0, 0, -12.3f, 45, 90, 0));

      this.rightView = new AnimationPositionNode(this);
      this.rightView.addFrame(12, new PositionNode(0, 0, -12.3f, 45, -90, 0));

      this.backView = new AnimationPositionNode(this);
      this.backView.addFrame(12, new PositionNode(0, 0, -12.3f, 45, 180, 0));

      this.refresh();
   }

   /**
    * Go to back view animation
    */
   public void backView()
   {
      this.sceneRenderer.playAnimation(this.backView);
   }

   /**
    * Clear the paper with one color <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param color
    *           Color used for clear
    * @see jhelp.logo.language.LogoGraphics#clear(int)
    */
   @Override
   public void clear(final int color)
   {
      this.paperTexture.clear(new Color(color));
   }

   /**
    * Draw line on paper <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param startX
    *           Start point X
    * @param startY
    *           Start point Y
    * @param endX
    *           End point X
    * @param endY
    *           End point Y
    * @param color
    *           Line color
    * @see jhelp.logo.language.LogoGraphics#drawLine(double, double, double, double, int)
    */
   @Override
   public void drawLine(final double startX, final double startY, final double endX, final double endY, final int color)
   {
      this.paperTexture.drawLine((int) startX, (int) startY, (int) endX, (int) endY, new Color(color), false);
   }

   /**
    * Fill an area with color <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param x
    *           Start position X
    * @param y
    *           Start position Y
    * @param color
    *           Color for fill
    * @param near
    *           Color distance to consider is same color
    * @see jhelp.logo.language.LogoGraphics#fill(double, double, int, int)
    */
   @Override
   public void fill(final double x, final double y, final int color, final int near)
   {
      this.spaceShipTurtle.setPenColor(color);
      this.sceneRenderer.playAnimation(this.spaceShipTurtle.getPenFillAnimation());

      if(this.turtle.getSpeed() > 0)
      {
         synchronized(this.waiting)
         {
            this.waiting.set(true);

            try
            {
               this.waiting.wait();
            }
            catch(final Exception exception)
            {
            }

            this.waiting.set(false);
         }
      }

      final JHelpImage image = this.paperTexture.toJHelpImage();
      image.startDrawMode();
      image.fillColor((int) x, (int) y, color, near, false);
      image.endDrawMode();
      this.paperTexture.setImage(image);

      if(this.turtle.getSpeed() > 0)
      {
         synchronized(this.waiting)
         {
            this.waiting.set(true);

            try
            {
               this.waiting.wait();
            }
            catch(final Exception exception)
            {
            }

            this.waiting.set(false);
         }
      }
   }

   /**
    * Go to front/face view animation
    */
   public void frontView()
   {
      this.sceneRenderer.playAnimation(this.frontView);
   }

   /**
    * Logo turtle
    * 
    * @return Logo turtle
    */
   public Turtle getTurtle()
   {
      return this.turtle;
   }

   /**
    * Go to left view animation
    */
   public void leftView()
   {
      this.sceneRenderer.playAnimation(this.leftView);
   }

   /**
    * Called when mouse wheel moved <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param e
    *           Event description
    * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
    */
   @Override
   public void mouseWheelMoved(final MouseWheelEvent e)
   {
      this.translate(0, 0, e.getWheelRotation());
   }

   /**
    * Go to origin view animation
    */
   public void originalPosition()
   {
      this.sceneRenderer.playAnimation(this.originalPosition);
   }

   /**
    * Called when pen down animation finished <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.learning.tool.model.SpaceShipTurtleListener#penDown()
    */
   @Override
   public void penDown()
   {
   }

   /**
    * Called when pen fill animation is in middle (The paint is put down).<br>
    * Its the good moment for do the fill on paper <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.learning.tool.model.SpaceShipTurtleListener#penFill()
    */
   @Override
   public void penFill()
   {
      synchronized(this.waiting)
      {
         if(this.waiting.get() == true)
         {
            this.waiting.notify();
         }
      }
   }

   /**
    * Called when fill animation complete <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.learning.tool.model.SpaceShipTurtleListener#penFillEnd()
    */
   @Override
   public void penFillEnd()
   {
      this.draw = false;
      this.refresh();

      synchronized(this.waiting)
      {
         if(this.waiting.get() == true)
         {
            this.waiting.notify();
         }
      }
   }

   /**
    * Called when pen up animation finished <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.learning.tool.model.SpaceShipTurtleListener#penUp()
    */
   @Override
   public void penUp()
   {
   }

   /**
    * Refresh the paper and spaceship status <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.logo.language.LogoGraphics#refresh()
    */
   @Override
   public void refresh()
   {
      if(this.turtle.isDraw() != this.draw)
      {
         if(this.turtle.isDraw() == true)
         {
            this.sceneRenderer.playAnimation(this.spaceShipTurtle.getPenDownAnimation());
         }
         else
         {
            this.sceneRenderer.playAnimation(this.spaceShipTurtle.getPenUpAnimation());
         }
      }

      this.draw = this.turtle.isDraw();

      if(this.turtle.isVisible() == true)
      {
         this.spaceShipTurtle.makeVisible();
      }
      else
      {
         this.spaceShipTurtle.makeFurtive();
      }

      this.spaceShipTurtle.setPenColor(this.turtle.getColor());
      this.spaceShipTurtle.setPosition((float) ((this.turtle.getX() - 512) / 102.4), 0.15f, (float) ((this.turtle.getY() - 512) / 102.4));
      this.spaceShipTurtle.setAngleY(270 - (float) this.turtle.getAngle());
   }

   /**
    * Go to right view animation
    */
   public void rightView()
   {
      this.sceneRenderer.playAnimation(this.rightView);
   }
}