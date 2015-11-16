package jhelp.learning.tool.model;

import java.util.ArrayList;
import java.util.List;

import jhelp.engine.Animation;
import jhelp.engine.Color4f;
import jhelp.engine.Material;
import jhelp.engine.Node;
import jhelp.engine.Object3D;
import jhelp.engine.Point2D;
import jhelp.engine.Vertex;
import jhelp.engine.anim.AnimationLaunchThread;
import jhelp.engine.anim.AnimationPositionNode;
import jhelp.engine.anim.MultiAnimation;
import jhelp.engine.geom.Revolution;
import jhelp.engine.geom.Sphere;
import jhelp.engine.util.PositionNode;
import jhelp.util.thread.ThreadedSimpleTask;

/**
 * Spaceship that represents the logo turtle
 * 
 * @author JHelp
 */
public class SpaceShipTurtle
      extends Node
{
   /**
    * Pen events type
    * 
    * @author JHelp
    */
   static enum PenEvent
   {
      /** Pen down animation finished */
      DOWN,
      /** Fill animation on middle, good time for fill the paper */
      FILL,
      /** Fill animation finished */
      FILL_END,
      /** Pen up animation finished */
      UP
   }

   /**
    * Task for fire pen animation events
    * 
    * @author JHelp
    */
   static class TaskFirePenEvents
         extends ThreadedSimpleTask<PenEvent>
   {
      /** Listeners to alert on event */
      private final List<SpaceShipTurtleListener> listeners;

      /**
       * Create a new instance of TaskFirePenEvents
       */
      TaskFirePenEvents()
      {
         this.listeners = new ArrayList<SpaceShipTurtleListener>();
      }

      /**
       * Do tha task : alert listeners of animation pen evnt <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param penEvent
       *           Animation pen event to fire
       * @see jhelp.util.thread.ThreadedSimpleTask#doSimpleAction(java.lang.Object)
       */
      @Override
      protected void doSimpleAction(final PenEvent penEvent)
      {
         synchronized(this.listeners)
         {
            for(final SpaceShipTurtleListener listener : this.listeners)
            {
               switch(penEvent)
               {
                  case DOWN:
                     listener.penDown();
                  break;
                  case FILL:
                     listener.penFill();
                  break;
                  case FILL_END:
                     listener.penFillEnd();
                  break;
                  case UP:
                     listener.penUp();
                  break;
               }
            }
         }
      }

      /**
       * Register listener of pen animation events
       * 
       * @param listener
       *           Listener to register
       */
      public void register(final SpaceShipTurtleListener listener)
      {
         if(listener == null)
         {
            return;
         }

         synchronized(this.listeners)
         {
            if(this.listeners.contains(listener) == false)
            {
               this.listeners.add(listener);
            }
         }
      }

      /**
       * Unregister listener of pen animation events
       * 
       * @param listener
       *           Listener to unregister
       */
      public void unregister(final SpaceShipTurtleListener listener)
      {
         synchronized(this.listeners)
         {
            this.listeners.remove(listener);
         }
      }
   }

   /** Cokpit ID */
   private static final String     COCKPIT = "Cockpit";
   /** Pen ID */
   private static final String     PEN     = "Pen";
   /** Wings ID */
   private static final String     WINGS   = "Wings";

   /** Pen down animation */
   private final Animation         penDownAnimation;
   /** Fill animation */
   private final Animation         penFillAnimation;
   /** Pen up animation */
   private final Animation         penUpAnimation;
   /** Animation events manager */
   private final TaskFirePenEvents taskFirePenEvents;

   /**
    * Create a new instance of SpaceShipTurtle
    */
   public SpaceShipTurtle()
   {
      this.taskFirePenEvents = new TaskFirePenEvents();
      this.wings();
      this.cockpit();
      this.pen();

      this.penDownAnimation = this.createPenDownAnimation();
      this.penUpAnimation = this.createPenUpAnimation();
      this.penFillAnimation = this.createPenFillAnimation();
   }

   /**
    * Draw the cockpit
    */
   private void cockpit()
   {
      final Sphere cockpit = new Sphere();
      cockpit.nodeName = SpaceShipTurtle.COCKPIT;
      cockpit.setPosition(0, 0, -2);
      cockpit.setScale(0.5f, 1f, 1.5f);
      final Material material = new Material(SpaceShipTurtle.COCKPIT);
      material.setColorDiffuse(Color4f.LIGHT_BLUE);
      cockpit.setMaterial(material);

      this.addChild(cockpit);
   }

   /**
    * Create pen down animation
    * 
    * @return Pen down animation
    */
   private Animation createPenDownAnimation()
   {
      final MultiAnimation multiAnimation = new MultiAnimation();
      final AnimationPositionNode animationPositionNode = new AnimationPositionNode(this.getFirstNode(SpaceShipTurtle.PEN));
      animationPositionNode.addFrame(10, new PositionNode(0, -1, 0));
      multiAnimation.addAnimation(animationPositionNode);
      multiAnimation.addAnimation(new AnimationLaunchThread<PenEvent, Void, Void>(this.taskFirePenEvents, PenEvent.DOWN));
      return multiAnimation;
   }

   /**
    * Create fill animation
    * 
    * @return Fill animation
    */
   private Animation createPenFillAnimation()
   {
      final MultiAnimation multiAnimation = new MultiAnimation();
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(this.getFirstNode(SpaceShipTurtle.PEN));
      animationPositionNode.addFrame(10, new PositionNode(0, 0, 0));
      animationPositionNode.addFrame(25, new PositionNode(0, 0, 0, 0, 0, 180));
      multiAnimation.addAnimation(animationPositionNode);
      multiAnimation.addAnimation(new AnimationLaunchThread<PenEvent, Void, Void>(this.taskFirePenEvents, PenEvent.FILL));
      animationPositionNode = new AnimationPositionNode(this.getFirstNode(SpaceShipTurtle.PEN));
      animationPositionNode.addFrame(25, new PositionNode(0, 0, 0));
      multiAnimation.addAnimation(animationPositionNode);
      multiAnimation.addAnimation(new AnimationLaunchThread<PenEvent, Void, Void>(this.taskFirePenEvents, PenEvent.FILL_END));
      return multiAnimation;
   }

   /**
    * Create pen up animation
    * 
    * @return Pen up animation
    */
   private Animation createPenUpAnimation()
   {
      final MultiAnimation multiAnimation = new MultiAnimation();
      final AnimationPositionNode animationPositionNode = new AnimationPositionNode(this.getFirstNode(SpaceShipTurtle.PEN));
      animationPositionNode.addFrame(10, new PositionNode(0, 0, 0));
      multiAnimation.addAnimation(animationPositionNode);
      multiAnimation.addAnimation(new AnimationLaunchThread<PenEvent, Void, Void>(this.taskFirePenEvents, PenEvent.UP));
      return multiAnimation;
   }

   /**
    * Hide a spaceship part
    * 
    * @param name
    *           Space ship part ID
    */
   private void makeFurtive(final String name)
   {
      final Object3D object3d = (Object3D) this.getFirstNode(name);
      object3d.setShowWire(true);
      final Material material = Material.obtainMaterialOrCreate(name);
      material.setTransparency(0);
      object3d.setVisible(false);
   }

   /**
    * Show a spaceship part
    * 
    * @param name
    *           Space ship part ID
    */
   private void makeVisible(final String name)
   {
      final Object3D object3d = (Object3D) this.getFirstNode(name);
      object3d.setShowWire(false);
      final Material material = Material.obtainMaterialOrCreate(name);
      material.setTransparency(1);
      object3d.setVisible(true);
   }

   /**
    * Create the pen
    */
   private void pen()
   {
      final Revolution pen = new Revolution();
      pen.nodeName = SpaceShipTurtle.PEN;
      pen.appendLine(new Point2D(0.5f, 2), new Point2D(0.5f, 0));
      pen.appendLine(new Point2D(0.5f, 0), new Point2D(0, -0.5f));
      pen.refreshRevolution();

      final Material material = new Material(SpaceShipTurtle.PEN);
      material.setColorDiffuse(Color4f.BLACK);
      material.setTwoSided(true);
      pen.setMaterial(material);

      this.addChild(pen);
   }

   /**
    * Create the wings
    */
   private void wings()
   {
      final Object3D wings = new Object3D();
      wings.nodeName = SpaceShipTurtle.WINGS;
      wings.addFast(new Vertex(0, 1, -1, 0.5f, 0.75f, 0, 1, 0));
      wings.addFast(new Vertex(2, 0, 0, 1, 0.5f, 0.5f, 0.5f, 0));
      wings.addFast(new Vertex(0, 0, -4, 0.5f, 0, 0, 0, -1));
      wings.nextFace();
      wings.addFast(new Vertex(0, 1, -1, 0.5f, 0.75f, 0, 1, 0));
      wings.addFast(new Vertex(-2, 0, 0, 0, 0.5f, -0.5f, 0.5f, 0));
      wings.addFast(new Vertex(0, 0, -4, 0.5f, 0, 0, 0, -1));

      wings.nextFace();

      wings.addFast(new Vertex(0, -1, -1, 0.5f, 0.25f, 0, -1, 0));
      wings.addFast(new Vertex(2, 0, 0, 1, 0.5f, 0.5f, -0.5f, 0));
      wings.addFast(new Vertex(0, 0, -4, 0.5f, 0, 0, 0, -1));
      wings.nextFace();
      wings.addFast(new Vertex(0, -1, -1, 0.5f, 0.25f, 0, -1, 0));
      wings.addFast(new Vertex(-2, 0, 0, 0, 0.5f, -0.5f, -0.5f, 0));
      wings.addFast(new Vertex(0, 0, -4, 0.5f, 0, 0, 0, -1));

      wings.reconstructTheList();

      final Material material = new Material(SpaceShipTurtle.WINGS);
      material.setColorDiffuse(Color4f.LIGHT_GREEN);
      material.setTwoSided(true);
      wings.setMaterial(material);

      this.addChild(wings);
   }

   /**
    * Pen down animation
    * 
    * @return Pen down animation
    */
   public Animation getPenDownAnimation()
   {
      return this.penDownAnimation;
   }

   /**
    * Fill animation
    * 
    * @return Fill animation
    */
   public Animation getPenFillAnimation()
   {
      return this.penFillAnimation;
   }

   /**
    * Pen up animation
    * 
    * @return Pen up animation
    */
   public Animation getPenUpAnimation()
   {
      return this.penUpAnimation;
   }

   /**
    * Hide the spaceship
    */
   public void makeFurtive()
   {
      this.makeFurtive(SpaceShipTurtle.WINGS);
      this.makeFurtive(SpaceShipTurtle.COCKPIT);
      this.makeFurtive(SpaceShipTurtle.PEN);
   }

   /**
    * Show the spaceship
    */
   public void makeVisible()
   {
      this.makeVisible(SpaceShipTurtle.WINGS);
      this.makeVisible(SpaceShipTurtle.COCKPIT);
      this.makeVisible(SpaceShipTurtle.PEN);
   }

   /**
    * Register listener of pen animation events
    * 
    * @param spaceShipTurtleListener
    *           Listener to register
    */
   public void registerSpaceShipTurtleListener(final SpaceShipTurtleListener spaceShipTurtleListener)
   {
      this.taskFirePenEvents.register(spaceShipTurtleListener);
   }

   /**
    * Change pen color
    * 
    * @param color
    *           New color
    */
   public void setPenColor(final int color)
   {
      Material.obtainMaterialOrCreate(SpaceShipTurtle.PEN).getColorDiffuse().set(color);
   }

   /**
    * Unregister listener of pen animation events
    * 
    * @param spaceShipTurtleListener
    *           Listener to unregister
    */
   public void unregisterSpaceShipTurtleListener(final SpaceShipTurtleListener spaceShipTurtleListener)
   {
      this.taskFirePenEvents.unregister(spaceShipTurtleListener);
   }
}