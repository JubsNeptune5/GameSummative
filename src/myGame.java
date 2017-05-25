
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * To make the start of my game
 *
 * @author laveh2107
 */
public class myGame extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    //Title of the window
    String title = "My Game";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //Create counter for block placement
    int x = 0;
    //Create Colour for tron and clu
    Color clu = new Color(232, 94, 39);
    Color tron = new Color(40, 146, 239);
    //Create variable for thickness of everything inc. characters
    int thick = 10;
    //Set variables for tron
    int tronx = WIDTH - thick;
    int trony = HEIGHT - thick;
    //Set variable for clu
    int clux = 0;
    int cluy = 0;
    //Variables for tron's movement
    boolean tronR = false;
    boolean tronU = false;
    boolean tronL = false;
    boolean tronD = false;
    //Variables for clu's movement
    boolean cluR = false;
    boolean cluU = false;
    boolean cluL = false;
    boolean cluD = false;

    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public myGame() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();

        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, 1000, 1000);

        // GAME DRAWING GOES HERE
        //Create backround for the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1250, 1250);

        //Create the character clu
        g.setColor(clu);
        g.fillRect(clux, cluy, thick, thick);

        //Create the character tron
        g.setColor(tron);
        g.fillRect(tronx, trony, thick, thick);
        //int tron[] = new int[1000];

        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE
            //Create an array for the tron
            // int tron[] = new int[1000];
            //TRON movement
            //Have the tron move left 5 spaces when left is pressed
            if (tronL && tronx > WIDTH - WIDTH) {
                tronx = tronx - 5;

            }
            //Have the tron move right 5 spaces when right is pressed
            if (tronR && tronx < WIDTH - thick) {
                tronx = tronx + 5;

            }
            //Have the tron move up 5 spaces when up is pressed
            if (tronU && trony > HEIGHT - HEIGHT) {
                trony = trony - 5;

            }
            //Have the tron move down 5 spaces when down is pressed
            if (tronD && trony < HEIGHT - thick) {
                trony = trony + 5;

            }
            //CLU movement
            //Have the clu move left 5 spaces when left is pressed
            if (cluL && clux > WIDTH - WIDTH) {
                clux = clux - 5;

            }
            //Have the clu move right 5 spaces when right is pressed
            if (cluR && clux < WIDTH - thick) {
                clux = clux + 5;

            }
            //Have the clu move up 5 spaces when up is pressed
            if (cluU && cluy > HEIGHT - HEIGHT) {
                cluy = cluy - 5;

            }
            //Have the clu move down 5 spaces when down is pressed
            if (cluD && cluy < HEIGHT - thick) {
                cluy = cluy + 5;

            }

            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down

        @Override
        public void mousePressed(MouseEvent e) {
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {
        // if a key has been pressed down

        @Override
        public void keyPressed(KeyEvent e) {
            //Intitalize the arrow keys
            int code = e.getKeyCode();
            //Use the arrow keys for tron's movement
            if (code == KeyEvent.VK_RIGHT) {
                tronR = true;
                tronU = false;
                tronL = false;
                tronD = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                tronL = true;
                tronR = false;
                tronU = false;
                tronD = false;

            }
            if (code == KeyEvent.VK_UP) {
                tronU = true;
                tronR = false;
                tronL = false;
                tronD = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                tronD = true;
                tronR = true;
                tronU = false;
                tronL = false;
            }

            //Use the S,E,D,F keys to move clu
            if (code == KeyEvent.VK_F) {
                cluR = true;
                cluU = false;
                cluL = false;
                cluD = false;
            }
            if (code == KeyEvent.VK_S) {
                cluR = false;
                cluU = false;
                cluL = true;
                cluD = false;
            }
            if (code == KeyEvent.VK_E) {
                cluR = false;
                cluU = true;
                cluL = false;
                cluD = false;
            }
            if (code == KeyEvent.VK_D) {
                cluR = false;
                cluU = false;
                cluL = false;
                cluD = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            //Intitalize the arrow keys 
            int code = e.getKeyCode();
            //Use the arrow keys for tron's movement
            if (code == KeyEvent.VK_RIGHT) {
                tronR = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                tronL = false;
            }
            if (code == KeyEvent.VK_UP) {
                tronU = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                tronD = false;
            }

            //Use the S,E,D,F keys to move clu
            if (code == KeyEvent.VK_F) {
                cluR = false;
            }
            if (code == KeyEvent.VK_S) {
                cluL = false;
            }
            if (code == KeyEvent.VK_E) {
                cluU = false;
            }
            if (code == KeyEvent.VK_D) {
                cluD = false;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        myGame game = new myGame();

        // starts the game loop
        game.run();
    }
}
