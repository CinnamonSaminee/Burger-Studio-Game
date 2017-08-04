/*
Name: Samin Saberi
Date: January 20th, 2014
Description: A game called "Burger Studio". The player uses the
    left and right arrow keys to move a bottom bun and catch
    toppings according to the number of each topping required,
    which is displayed on the top left of the game screen.
    The player is granted 3 'lives' which will allow them to 
    catch an incorrect topping 3 times before a "Game Over"
    screen is shown.
    
    This class represents the lettuce topping.
*/
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

//Class representing the lettuces
public class Lettuce extends Ingredient {

    private String craft = "Lettuce.png";

    // Same design as the bun
    public Lettuce (int x, int y) {
	   ImageIcon ii = new ImageIcon (this.getClass ().getResource (craft));
	   image = ii.getImage ();
	   width = image.getWidth (null);
	   height = image.getHeight (null);
	   visible = true;
	   this.x = x;
	   this.y = y;
    }
}
