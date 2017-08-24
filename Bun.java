/*
    This class represents the bottom bun.
*/
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bun {

    //The name of the image representing the bun
    private String bun = "BurgerBun.png";
    private Image image;

    //Movement variables
    int dx;
    int dy;
    public int x;
    public int y;
    public int speed = 2;
    private Timer timer;

    //Represents the width and height of the bun
    private int width;
    public int height;

    //Controls visibility of the bun
    private boolean visible;

    //Constructs the bun
    public Bun () {
	   //Creates the image representing the bun
	   ImageIcon ii = new ImageIcon (this.getClass ().getResource (bun));
	   image = ii.getImage ();
    
	   //Calculcates the size of the bun image.
	   width = image.getWidth (null);
	   height = image.getHeight (null);
    
	   visible = true;
    
	   //Sets the initial position of the bun
	   x = 350;
	   y = 600;
    }

    // Moves the bun
    public void move () {
	   x += dx;
    
	   //If statement keeps it within the board width
	   if (x < 1) {
	       x = 1;
	   }
	   else if (x > 700) {
	       x = 700;
	   }
    }

    // Returns the x-coordinate of the bun
    public int getX () {
	   return x;
    }

    // Returns the y-coordinate of the bun
    public int getY () {
	   return y;
    }

    //Method for possible power-down that was never finshed... sorry...
    public void decreaseSpeed () {
	   speed = 1;
	   timer.start ();
    }

    //...keeping it in case...
    public void increaseSpeed () {
	   speed = 5;
	   timer.start ();
    }

    // Returns the image of the bun
    public Image getImage () {
	   return image;
    }

    //Sets the visibility of the bun
    public void setVisible (boolean visible) {
	   this.visible = visible;
    }

    //Returns whether or not the bun is visible
    public boolean isVisible () {
	   return visible;
    }

    //Returns a Rectangle object representing the shape of the bun
    public Rectangle getBounds () {
	   return new Rectangle (x, y, width, height);
    }

    //Movement in reaction to pressed keys
    public void keyPressed (KeyEvent e) {
	   int key = e.getKeyCode ();
    
	   if (key == KeyEvent.VK_LEFT) {
	       dx = speed * -1;
	   }
	   if (key == KeyEvent.VK_RIGHT) {
	       dx = speed;
	   }
    }

    //Movement in reaction to released keys
    public void keyReleased (KeyEvent e) {
	   int key = e.getKeyCode ();
    
	   if (key == KeyEvent.VK_LEFT) {
	       dx = 0;
	   }
	   if (key == KeyEvent.VK_RIGHT) {
	       dx = 0;
	   }
    }
}
