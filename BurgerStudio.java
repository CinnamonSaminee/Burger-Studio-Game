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
    
    This is the main class of the program.
*/
import javax.swing.*;


public class BurgerStudio extends JFrame
{

    public BurgerStudio () throws Exception
    {
	this.getContentPane ().add (new Board ());

	this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	this.setSize (800, 700);
	this.setLocationRelativeTo (null);
	this.setTitle ("*** =^.^= *** Burger Studio *** =^.^= ***");
	this.setResizable (false);
	this.setVisible (true);

    }


    public static void main (String[] args) throws Exception
    {
	new BurgerStudio ();
    }
}
