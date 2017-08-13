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
    This class represents the board where the animation,
    collisions and other processes are computed.
*/
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    // Timer for animation
    private Timer timer;

    // The bottom bun
    private Bun bun;

    // Lists of topping objects
    private ArrayList lets;
    private ArrayList meats;
    private ArrayList toms;
    private ArrayList onis;
    private ArrayList pics;
    private ArrayList ches;
    private ArrayList tops;

    // Variables used for game scores
    int lettuceN;
    int pattyN;
    int tomatoN;
    int onionN;
    int pickleN;
    int cheeseN;
    boolean topbunB;

    //Indicates if the player won
    boolean win = false;
    //Used to control stacking
    boolean fstStack = false;
    //Represents number of toppings caught
    int stackN;
    //Represents lives left
    int lives;

    //Controls game over screen
    private boolean ingame;

    // integers representing the board width and height.
    private int B_WIDTH;
    private int B_HEIGHT;

    //Creates random initial positions of the toppings
    //Lettuce positions
    private int[] [] pos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //Meat positions
    private int[] [] meatpos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //Tomato positions
    private int[] [] tompos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //Onion positions
    private int[] [] onipos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //Pickle positions
    private int[] [] picpos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //Cheese positions
    private int[] [] chepos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};
    //TopBun positions
    private int[] [] toppos = {
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () },
	    {getRandomX (), getRandomY () }
	};

    //Method for acquiring random y coordinates
    public static int getRandomY () {
	   int result = (int) (Math.random () * 6000 + 1);
	   result = 0 - result;
	   return result;
    }


    //Method for acquiring random x coordinates
    public static int getRandomX () {
	   int result = (int) (Math.random () * 700 + 1);
	   return result;
    }


    ////Method for acquiring number of each topping needed
    public static int orderUp () {
	   int result = (int) (Math.random () * 4 + 0);
	   return result;
    }


    // Constructor for the board
    public Board () throws Exception {
        // Key listener for the keyboard input.
        this.addKeyListener (new TAdapter ());
        
        //Settings for the JFrame
        this.setFocusable (true);
        this.setBackground (Color.PINK);
        this.setDoubleBuffered (true);
        this.setSize (800, 700);
        
        //Game over screen is hidden
        this.ingame = true;

        //Creates the bun
        this.bun = new Bun ();

        //Method is called to initialize the toppings
        initToppings ();

        //The game requirements are created
        lettuceN = orderUp ();
        pattyN = orderUp ();
        tomatoN = orderUp ();
        onionN = orderUp ();
        pickleN = orderUp ();
        cheeseN = orderUp ();

        //A TopBun has not been caught
        topbunB = false;

        //Starts the animation timer to update animations every 15ms.
        timer = new Timer (15, this);
        timer.start ();

        //Starts audio -- only plays once...
        //InputStream in = new FileInputStream (new File ("07_Gambang.wav"));
        //AudioStream audioStream = new AudioStream (in);
        //AudioPlayer.player.start (audioStream);
    }


    public void addNotify () {
	   super.addNotify ();
	   B_WIDTH = getWidth ();
	   B_HEIGHT = getHeight ();
    }


    // Method to create lists of toppings to be drawn to screen.
    public void initToppings () {
        lets = new ArrayList ();
        meats = new ArrayList ();
        toms = new ArrayList ();
        onis = new ArrayList ();
        pics = new ArrayList ();
        ches = new ArrayList ();
        tops = new ArrayList ();

        for (int i = 0 ; i < pos.length ; i++) {
            lets.add (new Lettuce (pos [i] [0], pos [i] [1]));
            meats.add (new Meat (meatpos [i] [0], meatpos [i] [1]));
            toms.add (new Tomato (tompos [i] [0], tompos [i] [1]));
            onis.add (new Onion (onipos [i] [0], onipos [i] [1]));
            pics.add (new Pickle (picpos [i] [0], picpos [i] [1]));
            ches.add (new Cheese (chepos [i] [0], chepos [i] [1]));
            tops.add (new TopBun (toppos [i] [0], toppos [i] [1]));
        }
    }


    //Draws the game
    public void paint (Graphics g) {
        super.paint (g);

        if (ingame) { //If the player still has lives
            Graphics2D g2d = (Graphics2D) g;

            //The bun is drawn
            if (bun.isVisible ()) {
                g2d.drawImage (bun.getImage (), bun.getX (), bun.getY (), this);
            }

            //For loop to draw toppings.
            for (int i = 0 ; i < lets.size () ; i++) {
                Lettuce a = (Lettuce) lets.get (i);
                if (a.isVisible ())
                    g2d.drawImage (a.getImage (), a.getXA (), a.getYA (), this);

                Meat m = (Meat) meats.get (i);
                if (a.isVisible ())
                    g2d.drawImage (m.getImage (), m.getXA (), m.getYA (), this);

                Tomato t = (Tomato) toms.get (i);
                if (t.isVisible ())
                    g2d.drawImage (t.getImage (), t.getXA (), t.getYA (), this);

                Onion o = (Onion) onis.get (i);
                if (o.isVisible ())
                    g2d.drawImage (o.getImage (), o.getXA (), o.getYA (), this);

                Pickle p = (Pickle) pics.get (i);
                if (p.isVisible ())
                    g2d.drawImage (p.getImage (), p.getXA (), p.getYA (), this);

                Cheese c = (Cheese) ches.get (i);
                if (c.isVisible ())
                    g2d.drawImage (c.getImage (), c.getXA (), c.getYA (), this);

                TopBun b = (TopBun) tops.get (i);
                if (b.isVisible ())
                    g2d.drawImage (b.getImage (), b.getXA (), b.getYA (), this);
            }

            // Displays game stats
            Font small = new Font ("Courier", Font.PLAIN, 20);
            FontMetrics metr = this.getFontMetrics (small);

            g2d.setColor (Color.BLACK);
            g2d.setFont (small);
            g2d.drawString ("Lettuce: " + lettuceN, 5, 20);
            g2d.drawString ("Patty:   " + pattyN, 5, 40);
            g2d.drawString ("Tomato:  " + tomatoN, 5, 60);
            g2d.drawString ("Onion:   " + onionN, 5, 80);
            g2d.drawString ("Pickle:  " + pickleN, 5, 100);
            g2d.drawString ("Cheese:  " + cheeseN, 5, 120);
            //g2d.drawString ("Stacked: " + stackN, 500, 20);
            g2d.drawString ("Mistakes left: " + lives, 200, 20);
            
            //If the player wins
            if (win) {
                String msg = "Burger Done!";
                Font small2 = new Font ("Cooper Black", Font.PLAIN, 80);
                FontMetrics metr2 = this.getFontMetrics (small2);
                g.setColor (Color.BLACK);
                g.setFont (small2);
                g.drawString (msg, (B_WIDTH - metr2.stringWidth (msg)) / 2,
                              B_HEIGHT / 2);
            }
            
            //If not all toppings are caught
            if (topbunB && !win) {
                String msg = "Burger Incomplete";
                Font small3 = new Font ("Cooper Black", Font.PLAIN, 80);
                FontMetrics metrHit = this.getFontMetrics (small3);
                g.setColor (Color.BLACK);
                g.setFont (small3);
                g.drawString (msg, (B_WIDTH - metrHit.stringWidth (msg)) / 2,
                              B_HEIGHT / 2);
            }
        }
        else {
            //If the player runs out of lives
            String msg = "Game Over";
            Font small = new Font ("Cooper Black", Font.PLAIN, 80);
            FontMetrics metr = this.getFontMetrics (small);
            g.setColor (Color.BLACK);
            g.setFont (small);
            g.drawString (msg, (B_WIDTH - metr.stringWidth (msg)) / 2,
                          B_HEIGHT / 2);
        }
        Toolkit.getDefaultToolkit ().sync ();
        g.dispose ();
    }
    

    public void actionPerformed (ActionEvent e) {
        // Updates the animations
        // Updates bun location
        bun.move ();

        //Updates topping locations
        //Based on if it's stacked and the bun's x coordinate
        for (int i = 0 ; i < lets.size () ; i++) {
            Lettuce a = (Lettuce) lets.get (i);
            if (!a.isStack ())
                a.moveAlien ();
            else
                a.move (bun.getX ());

            Meat m = (Meat) meats.get (i);
            if (!m.isStack ())
                m.moveAlien ();
            else
                m.move (bun.getX ());

            Tomato t = (Tomato) toms.get (i);
            if (!t.isStack ())
                t.moveAlien ();
            else
                t.move (bun.getX ());

            Onion o = (Onion) onis.get (i);
            if (!o.isStack ())
                o.moveAlien ();
            else
                o.move (bun.getX ());

            Pickle p = (Pickle) pics.get (i);
            if (!p.isStack ())
                p.moveAlien ();
            else
                p.move (bun.getX ());

            Cheese c = (Cheese) ches.get (i);
            if (!c.isStack ())
                c.moveAlien ();
            else
                c.move (bun.getX ());

            TopBun b = (TopBun) tops.get (i);
            if (!b.isStack ())
                b.moveAlien ();
            else
                b.move (bun.getX ());
        }

        //Checks if toppings are stacked and repaints board
        checkCollisions ();
        repaint ();
    }


    public void checkCollisions () {
        //Creates 'Hitbox' based of bun x-coordinate and the number of stacked toppings
        Rectangle rHit = new Rectangle (bun.getX (), 600 - stackN * 30, 100, 30);

        //Number of toppings stacked is set to 0 and lives to 3
        if (fstStack == false) {
            stackN = 0;
            lives = 3;
        }

        //Rectangle is created for every topping.
        //There is probably more efficient ways to do this... let it be...
        Lettuce a1 = (Lettuce) lets.get (0);
        Rectangle rL1 = a1.getBounds ();
        Lettuce a2 = (Lettuce) lets.get (1);
        Rectangle rL2 = a2.getBounds ();
        Lettuce a3 = (Lettuce) lets.get (2);
        Rectangle rL3 = a3.getBounds ();
        Lettuce a4 = (Lettuce) lets.get (3);
        Rectangle rL4 = a4.getBounds ();
        Lettuce a5 = (Lettuce) lets.get (4);
        Rectangle rL5 = a5.getBounds ();

        Meat m1 = (Meat) meats.get (0);
        Rectangle rM1 = m1.getBounds ();
        Meat m2 = (Meat) meats.get (1);
        Rectangle rM2 = m2.getBounds ();
        Meat m3 = (Meat) meats.get (2);
        Rectangle rM3 = m3.getBounds ();
        Meat m4 = (Meat) meats.get (3);
        Rectangle rM4 = m4.getBounds ();
        Meat m5 = (Meat) meats.get (4);
        Rectangle rM5 = m5.getBounds ();

        Tomato t1 = (Tomato) toms.get (0);
        Rectangle rT1 = t1.getBounds ();
        Tomato t2 = (Tomato) toms.get (1);
        Rectangle rT2 = t2.getBounds ();
        Tomato t3 = (Tomato) toms.get (2);
        Rectangle rT3 = t3.getBounds ();
        Tomato t4 = (Tomato) toms.get (3);
        Rectangle rT4 = t4.getBounds ();
        Tomato t5 = (Tomato) toms.get (4);
        Rectangle rT5 = t5.getBounds ();

        Onion o1 = (Onion) onis.get (0);
        Rectangle rO1 = o1.getBounds ();
        Onion o2 = (Onion) onis.get (1);
        Rectangle rO2 = o2.getBounds ();
        Onion o3 = (Onion) onis.get (2);
        Rectangle rO3 = o3.getBounds ();
        Onion o4 = (Onion) onis.get (3);
        Rectangle rO4 = o4.getBounds ();
        Onion o5 = (Onion) onis.get (4);
        Rectangle rO5 = o5.getBounds ();

        Pickle p1 = (Pickle) pics.get (0);
        Rectangle rP1 = p1.getBounds ();
        Pickle p2 = (Pickle) pics.get (1);
        Rectangle rP2 = p2.getBounds ();
        Pickle p3 = (Pickle) pics.get (2);
        Rectangle rP3 = p3.getBounds ();
        Pickle p4 = (Pickle) pics.get (3);
        Rectangle rP4 = p4.getBounds ();
        Pickle p5 = (Pickle) pics.get (4);
        Rectangle rP5 = p5.getBounds ();

        Cheese c1 = (Cheese) ches.get (0);
        Rectangle rC1 = c1.getBounds ();
        Cheese c2 = (Cheese) ches.get (1);
        Rectangle rC2 = c2.getBounds ();
        Cheese c3 = (Cheese) ches.get (2);
        Rectangle rC3 = c3.getBounds ();
        Cheese c4 = (Cheese) ches.get (3);
        Rectangle rC4 = c4.getBounds ();
        Cheese c5 = (Cheese) ches.get (4);
        Rectangle rC5 = c5.getBounds ();

        TopBun b1 = (TopBun) tops.get (0);
        Rectangle rB1 = b1.getBounds ();
        TopBun b2 = (TopBun) tops.get (1);
        Rectangle rB2 = b2.getBounds ();
        TopBun b3 = (TopBun) tops.get (2);
        Rectangle rB3 = b3.getBounds ();
        TopBun b4 = (TopBun) tops.get (3);
        Rectangle rB4 = b4.getBounds ();
        TopBun b5 = (TopBun) tops.get (4);
        Rectangle rB5 = b5.getBounds ();

        //If the Hitbox intersects with a topping
        if (rHit.intersects (rL1) ||
            rHit.intersects (rL2) ||
            rHit.intersects (rL3) ||
            rHit.intersects (rL4) ||
            rHit.intersects (rL5) ||

            rHit.intersects (rM1) ||
            rHit.intersects (rM2) ||
            rHit.intersects (rM3) ||
            rHit.intersects (rM4) ||
            rHit.intersects (rM5) ||

            rHit.intersects (rT1) ||
            rHit.intersects (rT2) ||
            rHit.intersects (rT3) ||
            rHit.intersects (rT4) ||
            rHit.intersects (rT5) ||

            rHit.intersects (rO1) ||
            rHit.intersects (rO2) ||
            rHit.intersects (rO3) ||
            rHit.intersects (rO4) ||
            rHit.intersects (rO5) ||

            rHit.intersects (rP1) ||
            rHit.intersects (rP2) ||
            rHit.intersects (rP3) ||
            rHit.intersects (rP4) ||
            rHit.intersects (rP5) ||

            rHit.intersects (rC1) ||
            rHit.intersects (rC2) ||
            rHit.intersects (rC3) ||
            rHit.intersects (rC4) ||
            rHit.intersects (rC5) ||

            rHit.intersects (rB1) ||
            rHit.intersects (rB2) ||
            rHit.intersects (rB3) ||
            rHit.intersects (rB4) ||
            rHit.intersects (rB5) ) {
            //The first topping has been stacked
            fstStack = true;
            
            //If the intersection took place here and the topping is not stacked
            if (rHit.intersects (rL1) && !a1.isStack ()) { 
                rHit = a1.getBounds (); //The hitbox moves to the location of this topping
                stackN++;   //The number of toppings stacked is increased
                a1.stopFall ();
                //The topping stops falling
                //(more of a precaution now -- may not be necessary)
                a1.stack (bun, stackN); //The topping is added to the stack

                //The game score is updated depending on if the topping was required
                if (lettuceN == 0)
                    lives--;
                if (lettuceN > 0)
                    lettuceN--;
            } //Repeated for every other topping
            else if (rHit.intersects (rL2) && !a2.isStack ()) {
                rHit = a2.getBounds ();
                stackN++;
                a2.stopFall ();
                a2.stack (bun, stackN);
                
                if (lettuceN == 0)
                    lives--;
                if (lettuceN > 0)
                    lettuceN--;
            }
            else if (rHit.intersects (rL3) && !a3.isStack ()) {
                rHit = a3.getBounds ();
                stackN++;
                a3.stopFall ();
                a3.stack (bun, stackN);

                if (lettuceN == 0)
                    lives--;
                if (lettuceN > 0)
                    lettuceN--;
            }
            else if (rHit.intersects (rL4) && !a4.isStack ()) {
                rHit = a4.getBounds ();
                stackN++;
                a4.stopFall ();
                a4.stack (bun, stackN);

                if (lettuceN == 0)
                    lives--;
                if (lettuceN > 0)
                    lettuceN--;
            }
            else if (rHit.intersects (rL5) && !a5.isStack ()) {
                rHit = a5.getBounds ();
                stackN++;
                a5.stopFall ();
                a5.stack (bun, stackN);

                if (lettuceN == 0)
                    lives--;
                if (lettuceN > 0)
                    lettuceN--;
            }

    	    else if (rHit.intersects (rM1) && !m1.isStack ()) {
                rHit = m1.getBounds ();
                stackN++;
                m1.stopFall ();
                m1.stack (bun, stackN);

                if (pattyN == 0)
                    lives--;
                if (pattyN > 0)
                    pattyN--;
            }
    	    else if (rHit.intersects (rM2) && !m2.isStack ()) {
                rHit = m2.getBounds ();
                stackN++;
                m2.stopFall ();
                m2.stack (bun, stackN);

                if (pattyN == 0)
                    lives--;
                if (pattyN > 0)
                    pattyN--;
    	    }
    	    else if (rHit.intersects (rM3) && !m3.isStack ()) {
                rHit = m3.getBounds ();
                stackN++;
                m3.stopFall ();
                m3.stack (bun, stackN);

                if (pattyN == 0)
                    lives--;
                if (pattyN > 0)
                    pattyN--;
            }
    	    else if (rHit.intersects (rM4) && !m4.isStack ()) {
                rHit = m4.getBounds ();
                stackN++;
                m4.stopFall ();
                m4.stack (bun, stackN);

                if (pattyN == 0)
                    lives--;
                if (pattyN > 0)
                    pattyN--;
            }
    	    else if (rHit.intersects (rM5) && !m5.isStack ()) {
                rHit = m5.getBounds ();
                stackN++;
                m5.stopFall ();
                m5.stack (bun, stackN);

                if (pattyN == 0)
                    lives--;
                if (pattyN > 0)
                    pattyN--;
            }

    	    else if (rHit.intersects (rT1) && !t1.isStack ()) {
                rHit = t1.getBounds ();
                stackN++;
                t1.stopFall ();
                t1.stack (bun, stackN);

                if (tomatoN == 0)
                    lives--;
                if (tomatoN > 0)
                    tomatoN--;
            }
    	    else if (rHit.intersects (rT2) && !t2.isStack ()) {
                rHit = t2.getBounds ();
                stackN++;
                t2.stopFall ();
                t2.stack (bun, stackN);

                if (tomatoN == 0)
                    lives--;
                if (tomatoN > 0)
                    tomatoN--;
            }
    	    else if (rHit.intersects (rT3) && !t3.isStack ()) {
                rHit = t3.getBounds ();
                stackN++;
                t3.stopFall ();
                t3.stack (bun, stackN);

                if (tomatoN == 0)
                    lives--;
                if (tomatoN > 0)
                    tomatoN--;
            }
    	    else if (rHit.intersects (rT4) && !t4.isStack ()) {
                rHit = t4.getBounds ();
                stackN++;
                t4.stopFall ();
                t4.stack (bun, stackN);

                if (tomatoN == 0)
                    lives--;
                if (tomatoN > 0)
                    tomatoN--;
            }
    	    else if (rHit.intersects (rT5) && !t5.isStack ()) {
                rHit = t5.getBounds ();
                stackN++;
                t5.stopFall ();
                t5.stack (bun, stackN);

                if (tomatoN == 0)
                    lives--;
                if (tomatoN > 0)
                    tomatoN--;
            }

    	    else if (rHit.intersects (rO1) && !o1.isStack ()) {
                rHit = o1.getBounds ();
                stackN++;
                o1.stopFall ();
                o1.stack (bun, stackN);

                if (onionN == 0)
                    lives--;
                if (onionN > 0)
                    onionN--;
            }
    	    else if (rHit.intersects (rO2) && !o2.isStack ()) {
                rHit = o2.getBounds ();
                stackN++;
                o2.stopFall ();
                o2.stack (bun, stackN);

                if (onionN == 0)
                    lives--;
                if (onionN > 0)
                    onionN--;
            }
    	    else if (rHit.intersects (rO3) && !o3.isStack ()) {
                rHit = o3.getBounds ();
                stackN++;
                o3.stopFall ();
                o3.stack (bun, stackN);

                if (onionN == 0)
                    lives--;
                if (onionN > 0)
                    onionN--;
            }
    	    else if (rHit.intersects (rO4) && !o4.isStack ()) {
                rHit = o4.getBounds ();
                stackN++;
                o4.stopFall ();
                o4.stack (bun, stackN);

                if (onionN == 0)
                    lives--;
                if (onionN > 0)
                    onionN--;
            }
    	    else if (rHit.intersects (rO5) && !o5.isStack ()) {
                rHit = o5.getBounds ();
                stackN++;
                o5.stopFall ();
                o5.stack (bun, stackN);

                if (onionN == 0)
                    lives--;
                if (onionN > 0)
                    onionN--;
            }

    	    else if (rHit.intersects (rP1) && !p1.isStack ()) {
                rHit = p1.getBounds ();
                stackN++;
                p1.stopFall ();
                p1.stack (bun, stackN);

                if (pickleN == 0)
                    lives--;
                if (pickleN > 0)
                    pickleN--;
            }
    	    else if (rHit.intersects (rP2) && !p2.isStack ()) {
                rHit = p2.getBounds ();
                stackN++;
                p2.stopFall ();
                p2.stack (bun, stackN);

                if (pickleN == 0)
                    lives--;
                if (pickleN > 0)
                    pickleN--;
            }
    	    else if (rHit.intersects (rP3) && !p3.isStack ()) {
                rHit = p3.getBounds ();
                stackN++;
                p3.stopFall ();
                p3.stack (bun, stackN);

                if (pickleN == 0)
                    lives--;
                if (pickleN > 0)
                    pickleN--;
            }
    	    else if (rHit.intersects (rP4) && !p4.isStack ()) {
                rHit = p4.getBounds ();
                stackN++;
                p4.stopFall ();
                p4.stack (bun, stackN);

                if (pickleN == 0)
                    lives--;
                if (pickleN > 0)
                    pickleN--;
            }
    	    else if (rHit.intersects (rP5) && !p5.isStack ()) {
                rHit = p5.getBounds ();
                stackN++;
                p5.stopFall ();
                p5.stack (bun, stackN);

                if (pickleN == 0)
                    lives--;
                if (pickleN > 0)
                    pickleN--;
            }

    	    else if (rHit.intersects (rC1) && !c1.isStack ()) {
                rHit = c1.getBounds ();
                stackN++;
                c1.stopFall ();
                c1.stack (bun, stackN);

                if (cheeseN == 0)
                    lives--;
                if (cheeseN > 0)
                    cheeseN--;
            }
    	    else if (rHit.intersects (rC2) && !c2.isStack ()) {
                rHit = c2.getBounds ();
                stackN++;
                c2.stopFall ();
                c2.stack (bun, stackN);

                if (cheeseN == 0)
                    lives--;
                if (cheeseN > 0)
                    cheeseN--;
            }
    	    else if (rHit.intersects (rC3) && !c3.isStack ()) {
                rHit = c3.getBounds ();
                stackN++;
                c3.stopFall ();
                c3.stack (bun, stackN);

                if (cheeseN == 0)
                    lives--;
                if (cheeseN > 0)
                    cheeseN--;
            }
    	    else if (rHit.intersects (rC4) && !c4.isStack ()) {
                rHit = c4.getBounds ();
                stackN++;
                c4.stopFall ();
                c4.stack (bun, stackN);

                if (cheeseN == 0)
                    lives--;
                if (cheeseN > 0)
                    cheeseN--;
            }
    	    else if (rHit.intersects (rC5) && !c5.isStack ()) {
                rHit = c5.getBounds ();
                stackN++;
                c5.stopFall ();
                c5.stack (bun, stackN);

                if (cheeseN == 0)
                    lives--;
                if (cheeseN > 0)
                    cheeseN--;
            }

    	    else if (rHit.intersects (rB1) && !b1.isStack ()) {
                rHit = (null);
                stackN++;
                b1.stopFall ();
                b1.stack (bun, stackN);
                topbunB = true;

                if (lettuceN == 0 && pattyN == 0 && tomatoN == 0 &&
                    onionN == 0 && pickleN == 0 && cheeseN == 0) {
                    win = true;
                }
            }
    	    else if (rHit.intersects (rB2) && !b2.isStack ()) {
                rHit = (null);
                stackN++;
                b2.stopFall ();
                b2.stack (bun, stackN);
                topbunB = true;

                if (lettuceN == 0 && pattyN == 0 && tomatoN == 0 &&
                    onionN == 0 && pickleN == 0 && cheeseN == 0) {
                    win = true;
                }
            }
    	    else if (rHit.intersects (rB3) && !b3.isStack ()) {
                rHit = (null);
                stackN++;
                b3.stopFall ();
                b3.stack (bun, stackN);
                topbunB = true;

                if (lettuceN == 0 && pattyN == 0 && tomatoN == 0 &&
                    onionN == 0 && pickleN == 0 && cheeseN == 0) {
                    win = true;
                }
    	    }
    	    else if (rHit.intersects (rB4) && !b4.isStack ()) {
                rHit = (null);
                stackN++;
                b4.stopFall ();
                b4.stack (bun, stackN);
                topbunB = true;

                if (lettuceN == 0 && pattyN == 0 && tomatoN == 0 &&
                    onionN == 0 && pickleN == 0 && cheeseN == 0) {
                    win = true;
                }
            }
    	    else if (rHit.intersects (rB5) && !b5.isStack ()) {
                rHit = (null);
                stackN++;
                b5.stopFall ();
                b5.stack (bun, stackN);
                topbunB = true;

                if (lettuceN == 0 && pattyN == 0 && tomatoN == 0 &&
                    onionN == 0 && pickleN == 0 && cheeseN == 0) {
                    win = true;
                }
    	    }
	   }

        //If a TopBun is caught
    	if (topbunB) { //the toppings that are not stacked stop falling
            if (!a1.isStack ()) {
                //a1.setVisible (false);
                a1.stopFall ();
            }
            if (!a2.isStack ()) {
                //a2.setVisible (false);
                a2.stopFall ();
            }
            if (!a3.isStack ()) {
                //a3.setVisible (false);
                a3.stopFall ();
            }
            if (!a4.isStack ()) {
                //a4.setVisible (false);
                a4.stopFall ();
            }
            if (!a5.isStack ()) {
                //a5.setVisible (false);
                a5.stopFall ();
            }

            if (!m1.isStack ()) {
                //m1.setVisible (false);
                m1.stopFall ();
            }
            if (!m2.isStack ()) {
                //m2.setVisible (false);
                m2.stopFall ();
            }
            if (!m3.isStack ()) {
                //m3.setVisible (false);
                m3.stopFall ();
            }
            if (!m4.isStack ()) {
                //m4.setVisible (false);
                m4.stopFall ();
            }
            if (!m5.isStack ()) {
                //m5.setVisible (false);
                m5.stopFall ();
            }

            if (!t1.isStack ()) {
                //t1.setVisible (false);
                t1.stopFall ();
            }
            if (!t2.isStack ()) {
                //t2.setVisible (false);
                t2.stopFall ();
            }
            if (!t3.isStack ()) {
                //t3.setVisible (false);
                t3.stopFall ();
            }
            if (!t4.isStack ()) {
                //t4.setVisible (false);
                t4.stopFall ();
            }
            if (!t5.isStack ()) {
                //t5.setVisible (false);
                t5.stopFall ();
            }

            if (!o1.isStack ()) {
                //o1.setVisible (false);
                o1.stopFall ();
            }
            if (!o2.isStack ()) {
                //o2.setVisible (false);
                o2.stopFall ();
            }
            if (!o3.isStack ()) {
                //o3.setVisible (false);
                o3.stopFall ();
            }
            if (!o4.isStack ()) {
                //o4.setVisible (false);
                o4.stopFall ();
            }
            if (!o5.isStack ()) {
                //o5.setVisible (false);
                o5.stopFall ();
            }

            if (!p1.isStack ()) {
                //p1.setVisible (false);
                p1.stopFall ();
            }
            if (!p2.isStack ()) {
                //p2.setVisible (false);
                p2.stopFall ();
            }
            if (!p3.isStack ()) {
                //p3.setVisible (false);
                p3.stopFall ();
            }
            if (!p4.isStack ()) {
                //p4.setVisible (false);
                p4.stopFall ();
            }
            if (!p5.isStack ()) {
                //p5.setVisible (false);
                p5.stopFall ();
            }

            if (!c1.isStack ()) {
                //c1.setVisible (false);
                c1.stopFall ();
            }
            if (!c2.isStack ()) {
                //c2.setVisible (false);
                c2.stopFall ();
            }
            if (!c3.isStack ()) {
                //c3.setVisible (false);
                c3.stopFall ();
            }
            if (!c4.isStack ()) {
                //c4.setVisible (false);
                c4.stopFall ();
            }
            if (!c5.isStack ()) {
                //c5.setVisible (false);
                c5.stopFall ();
            }

            if (!b1.isStack ()) {
                //b1.setVisible (false);
                b1.stopFall ();
            }
            if (!b2.isStack ()) {
                //b2.setVisible (false);
                b2.stopFall ();
            }
            if (!b3.isStack ()) {
                //b3.setVisible (false);
                b3.stopFall ();
            }
            if (!b4.isStack ()) {
                //b4.setVisible (false);
                b4.stopFall ();
            }
            if (!b5.isStack ()) {
                //b5.setVisible (false);
                b5.stopFall ();
            }
        }

        if (lives == 0) //If there are no lives left, the game is over
            ingame = false;
    }


    // Key listener
    private class TAdapter extends KeyAdapter {

        public void keyReleased (KeyEvent e) {
            bun.keyReleased (e);
        }

        public void keyPressed (KeyEvent e) {
            bun.keyPressed (e);
        }
    }
}