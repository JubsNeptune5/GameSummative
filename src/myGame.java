
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Tron: Two players go head to head to try to trap the other with the trail the
 * other creates If the one character hits the other character or the walls,
 * that character looses
 *
 * @author laveh2107
 */
public class myGame extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    //Title of the window
    String title = "TRON";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //Create Colour for tron and clu
    Color clu = new Color(232, 94, 39);
    Color tron = new Color(40, 146, 239);
    //Create variable for thickness of everything inc. characters
    int thick = 10;
    //Set variables for tron
    Rectangle Tron = new Rectangle(WIDTH - thick * 4, HEIGHT / 2, thick, thick);
    //Cretae arrays for tron coordinents
    int tx[] = new int[WIDTH * HEIGHT];
    int ty[] = new int[WIDTH * HEIGHT];
    //Create counter for array
    int ti = 0;
    //Set variable for clu
    Rectangle Clu = new Rectangle(thick * 3, HEIGHT / 2, thick, thick);
    //Create arrays for clu coordinents
    int cx[] = new int[WIDTH * HEIGHT];
    int cy[] = new int[WIDTH * HEIGHT];
    //Create counter for array
    int ci = 0;
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
    //Create colour for wall outline
    Color Lukelightsaber = new Color(180, 200, 232);
    //Variables for the walls
    Rectangle wallNorth = new Rectangle(0, 0, thick * 2, WIDTH);
    Rectangle wallEast = new Rectangle(WIDTH - thick * 2, 0, HEIGHT, thick * 2);
    Rectangle wallSouth = new Rectangle(0, HEIGHT - thick * 2, thick * 2, WIDTH);
    Rectangle wallWest = new Rectangle(0, 0, HEIGHT, thick * 2);
    //Create variable for font
    Font myFont = new Font("Arial", Font.BOLD, 300);
    //Create variableas for speech
    int talkTx = -10;
    int talkTy = -10;
    int talkCx = -10;
    int talkCy = -10;
    //Create a variable for seeing if the collisions are true
    int True = 0;
    //Create a variable for the speed to make sure that the people always move
    int constantMoveT = 0;

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
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //Create backround for the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Create the character clu
        g.setColor(clu);
        g.fillRect(Clu.x, Clu.y, Clu.height, Clu.width);
        //Create shape for the trail of clu
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            g.fillRect(cx[i], cy[i], Clu.height, Clu.width);
        }
        //Create the character tron
        g.setColor(tron);
        g.fillRect(Tron.x, Tron.y, Tron.height, Tron.width);
        //Create shape for tron's trail
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            g.fillRect(tx[i], ty[i], Tron.height, Tron.width);
        }
        //Fill the walls
        g.setColor(Color.BLACK);
        g.fillRect(wallNorth.x, wallNorth.y, wallNorth.height, wallNorth.width);
        g.fillRect(wallSouth.x, wallSouth.y, wallSouth.height, wallSouth.width);
        g.fillRect(wallEast.x, wallEast.y, wallEast.height, wallEast.width);
        g.fillRect(wallWest.x, wallWest.y, wallWest.height, wallWest.width);
        //Draw the outline
        g.setColor(Lukelightsaber);
        g.drawRect(wallNorth.x, wallNorth.y, wallNorth.height, wallNorth.width);
        g.drawRect(wallSouth.x, wallSouth.y, wallSouth.height, wallSouth.width);
        g.drawRect(wallEast.x, wallEast.y, wallEast.height, wallEast.width);
        g.drawRect(wallWest.x, wallWest.y, wallWest.height, wallWest.width);

        //Create speach to takl to the players
        //draw winning speech for tron
        g.drawString("Congratulations Tron, you won!!!", talkTx, talkTy);
        //draw winning speech of clu
        g.setColor(clu);
        g.drawString("Congradulations Clu, you won!!!", talkCx, talkCy);
        // GAME DRAWING ENDS HERE
    }
    // Beets Bears Battlestar galactica
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
            //Check to see if there is any collisions
            //Check if any character collided with the wall
            collisionWall();
            //Check if tron touched his own trail
            tronTouchItself();
            //Check if clu touched his own trail
            cluTouchItself();
            //If loop so that if any of these methods are true then the ifn loop will end the game
            if (True == 1) {
                done = true;
            }

            //TRON movement 
            //Have the tron move left 5 spaces when left is pressed
            if (tronL) {
                //Method for adding the trail to tron
                tronTrailAdding();
                Tron.x = Tron.x - 1;
                if (True == 1) {
                    done = true;
                }
            }

            //Have the tron move right thick spaces when right is pressed
            if (tronR) {
                //Method for adding the trail to tron
                tronTrailAdding();
                Tron.x = Tron.x + 1;
                if (True == 1) {
                    done = true;
                }
            }
            //Have the tron move up thick spaces when up is pressed
            if (tronU) {
                //Method for adding the trail to tron
                tronTrailAdding();
                Tron.y = Tron.y - 1;
                if (True == 1) {
                    done = true;
                }
            }
            //Have the tron move down thick spaces when down is pressed
            if (tronD) {
                //Method for adding the trail to tron
                tronTrailAdding();
                Tron.y = Tron.y + 1;
                if (True == 1) {
                    done = true;
                }
            }
            //Check if tron collided with clue after he moves
            cluWin();
            //If tron did collide, the method will set the true variable as 1, ending the game
            if (True == 1) {
                done = true;
            }
            //CLU movement
            //Have the clu move left thick spaces when left is pressed
            if (cluL) {
                //Method for adding the trail to clu
                cluTrailAdding();
                Clu.x = Clu.x - 1;
            }
            //Have the clu move right thick spaces when right is pressed
            if (cluR) {
                //Method for adding the trail to clu
                cluTrailAdding();
                Clu.x = Clu.x + 1;
            }
            //Have the clu move up thick spaces when up is pressed
            if (cluU) {
                //Method for adding the trail to clu
                cluTrailAdding();
                Clu.y = Clu.y - 1;
            }
            //Have the clu move down thick spaces when down is pressed
            if (cluD) {
                //Method for adding the trail to clu
                cluTrailAdding();
                Clu.y = Clu.y + 1;
            }
            //Check if clu collided with tron
            tronWin();
            if (True == 1) {
                //If clu did collide, the method will set the true variable as 1, ending the game
                done = true;
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
                //Only have tron move in a single direction by setting the booleans other than right false
                tronU = false;
                tronL = false;
                tronD = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                tronL = true;
                //Only have tron move in a single direction by setting the booleans other than left false
                tronR = false;
                tronU = false;
                tronD = false;

            }
            if (code == KeyEvent.VK_UP) {
                tronU = true;
                //Only have tron move in a single direction by setting the booleans other than up false
                tronR = false;
                tronL = false;
                tronD = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                tronD = true;
                //Only have tron move in a single direction by setting the booleans other than down false
                tronR = false;
                tronU = false;
                tronL = false;
            }

            //Use the A,W,S,D keys to move clu
            if (code == KeyEvent.VK_D) {
                cluR = true;
                //Only have tron move in a single direction by setting the booleans other than D false
                cluU = false;
                cluL = false;
                cluD = false;
            }
            if (code == KeyEvent.VK_A) {
                cluL = true;
                //Only have tron move in a single direction by setting the booleans other than A false
                cluR = false;
                cluU = false;
                cluD = false;
            }
            if (code == KeyEvent.VK_W) {
                cluU = true;
                //Only have tron move in a single direction by setting the booleans other than W false
                cluR = false;
                cluL = false;
                cluD = false;
            }
            if (code == KeyEvent.VK_S) {
                cluD = true;
                //Only have tron move in a single direction by setting the booleans other than S false
                cluR = false;
                cluU = false;
                cluL = false;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            //THERE IS NO CODE IN THE KEYRELEASED AS THIS ALLOWS THE 
            //CHARACTERS TO CONSTANTLY BE MOVING

            //Intitalize the arrow keys 
            int code = e.getKeyCode();
            //Use the arrow keys for tron's movement
            if (code == KeyEvent.VK_RIGHT) {
            }
            if (code == KeyEvent.VK_LEFT) {
            }
            if (code == KeyEvent.VK_UP) {
            }
            if (code == KeyEvent.VK_DOWN) {
            }

            //Use the S,E,D,F keys to move clu
            if (code == KeyEvent.VK_D) {
            }
            if (code == KeyEvent.VK_A) {
            }
            if (code == KeyEvent.VK_W) {
            }
            if (code == KeyEvent.VK_S) {
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

    /**
     * Method that checks if a character hits a wall
     */
    public void collisionWall() {
        //If Tron hits a wall, tron loses and Clu is told that they won
        for (int i = 0; i <= ci + ti; i++) {
            //check if the trial hit any part fo the walls
            if (tx[i] == WIDTH - thick || tx[i] == thick
                    || ty[i] == wallNorth.y + thick || ty[i] == wallSouth.y + thick) {
                //Erase the Trail of tron
                tronTrailBeGone();
                //Reveal the message that Clu won 
                talkCx = WIDTH / 2 - 100;
                talkCy = HEIGHT / 2;
                //Add one to the counter that will end the game
                True = 1;
            }
        }
        //If Clu hits the wall, clu loses and tron is told he wins
        for (int i = 0; i <= ci + ti; i++) {
            //check if the trial hit any part fo the walls
            if (cx[i] == WIDTH - thick || cx[i] == thick
                    || cy[i] == wallNorth.y + thick || cy[i] == wallSouth.y + thick) {
                //Erase the trail of Clu
                cluTrailBeGone();
                //Reveal the message that Tron won 
                talkTx = WIDTH / 2 - 100;
                talkTy = HEIGHT / 2;
                //Add one to the counter that will end the game
                True = 1;
            }
        }
    }

    /**
     * Method to see if Clu hit tron if so, tron won
     */
    public void tronWin() {
        //Have two for loops to run throught and compare points form both tron and clu
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < ci; j++) {
                //Check if tron hit clu at it's x or y point or on the opposite side of where the x or y point is (thick)
                if ((cx[j] == tx[i] || cx[j] == tx[i] - thick * 2) && (cy[j] == ty[i] || cy[j] == ty[i] - thick)) {
                    //Method to erase the trail of clu once it dies
                    cluTrailBeGone();
                    //Increase the variables of the x/y points of the winning speech
                    talkTx = WIDTH / 2;
                    talkTy = HEIGHT / 2;
                    //Set the variable to be true for the if loop later
                    True = 1;
                }
            }
        }
    }

    /**
     * Method to see if Tron hits Clu if so, Clue won
     */
    public void cluWin() {
        //Have two for loops to run throught and compare points form both tron and clu
        for (int i = 0; i < ci; i++) {
            for (int j = 0; j < ti; j++) {
                //Check if clu hit tron at it's x or y point or on the opposite side of where the x or y point is (thick)
                if ((cx[i] == tx[j] || cx[i] == tx[j] - thick * 2) && (cy[i] == ty[j] || cy[i] == ty[j] - thick)) {
                    //Method to erase the trail of tron once it dies
                    tronTrailBeGone();
                    //Increase the variables of the x/y points of the winning speech
                    talkCx = WIDTH / 2 - 100;
                    talkCy = HEIGHT / 2;
                    //Set variable to end the game in an if loop later
                    True = 1;
                }
            }
        }
    }

    /**
     * Method to set the array attached to clu
     */
    public void cluTrailAdding() {
        //Set the psoition in the x points of clu array to equal the corrent x position
        cx[ci] = Clu.x;
        //Set the psoition in the y points of clu array to equal the corrent y position
        cy[ci] = Clu.y;
        //Increase the counter for the array positions
        ci++;
    }

    /**
     * Method to set array attached to tron
     */
    public void tronTrailAdding() {
        //Set the psoition in the x points of tron array to equal the corrent x position    
        tx[ti] = Tron.x;
        //Set the psoition in the y points of tron array to equal the corrent y position
        ty[ti] = Tron.y;
        //Increase the counter for the array positions
        ti++;
    }

    /**
     * Method to check if tron crossed over its own path
     */
    public void tronTouchItself() {
        //Have two for loops to run throught and compare points form both tron and clu
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < ti; j++) {
                //Do NOT allow the same square to be compared at the same time
                if (j != i) {
                    //Check if tron of one square is  the same of another, which means he is over lapping
                    if (tx[i] == tx[j] && ty[i] == ty[j]) {
                        //Increase the variables of the x/y points of the winning speech
                        talkCx = WIDTH / 2 - 100;
                        talkCy = HEIGHT / 2;
                        //Set variable to 1 to end the program in an if loop later 
                        True = 1;
                    }
                }
            }
        }
    }

    /**
     * Method to check if clu crossed over its own path
     */
    public void cluTouchItself() {
        //Have two for loops to run throught and compare points form both tron and clu
        for (int i = 0; i < ci; i++) {
            for (int j = 0; j < ci; j++) {
                //Do NOT allow the same square to be compared at the same time
                if (j != i) {
                    //Check if clu of one square is the same of another, which means he is over lapping
                    if (cx[i] == cx[j] && cy[i] == cy[j]) {
                        //Increase the variables of the x/y points of the winning speech
                        talkTx = WIDTH / 2 - 100;
                        talkTy = HEIGHT / 2;
                        //Set variable to 1 to end the program in an if loop later 
                        True = 1;
                    }
                }
            }
        }
    }

    /**
     * Method to erase Tron's trail after they lose
     */
    public void tronTrailBeGone() {
        //Set variable to go through the x and y poijnt arrays of clu backwards
        for (int i = ti; i >= 0; i--) {
            //Move the array position out of the area 
            tx[i] = 0;
            ty[i] = 0;
            //Move the original start square to 0
            Tron.x = 0;
            Tron.y = 0;
        }
    }

    /**
     * Method to erase Clu's trail after they lose
     */
    public void cluTrailBeGone() {
        //Set variable to go through the x and y poijnt arrays of clu backwards
        for (int i = ci; i >= 0; i--) {
            //Move the array position out of the area
            cx[i] = 0;
            cy[i] = 0;
            //Move the original start square to 0
            Clu.x = 0;
            Clu.y = 0;
        }
    }
}
//Abadee badee badeee that's all folks