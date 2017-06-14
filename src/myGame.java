
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * xwing: Two players go head to head to try to trap the other with the trail
 * the other creates If the one character hits the other character or the walls,
 * that character looses
 *
 * @author laveh2107
 */
public class myGame extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    //Title of the window
    String title = "DROID";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //Variables for the main menu
    //VAriables fior the rectangles for the text boxes
    Rectangle TieTextBox = new Rectangle(0, HEIGHT / 2, WIDTH / 2 - 1, HEIGHT / 2);
    Rectangle xWingTextBox = new Rectangle(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
    //Variables for the main game
    //Create Colour for xwing and tie fighter
    Color tie = new Color(232, 94, 39);
    Color xwing = new Color(40, 146, 239);
    //Create variable for thickness of everything inc. characters
    int thick = 10;
    //Set variables for xwing
    Rectangle XWing = new Rectangle(WIDTH - thick * 4, HEIGHT / 2, thick, thick);
    //Cretae arrays for xwing coordinents
    int wingx[] = new int[WIDTH * HEIGHT];
    int wingy[] = new int[WIDTH * HEIGHT];
    //Create counter for array
    int xi = 0;
    //Set variable for tie fighter
    Rectangle Tie = new Rectangle(thick * 3, HEIGHT / 2, thick, thick);
    //Create arrays for tie fighter coordinents
    int tiex[] = new int[WIDTH * HEIGHT];
    int tiey[] = new int[WIDTH * HEIGHT];
    //Create counter for array
    int ti = 0;
    //Variables for xwing's movement
    boolean xWingR = false;
    boolean xWingU = false;
    boolean xWingL = false;
    boolean xWingD = false;
    //Variables for tie fighter's movement
    boolean TieR = false;
    boolean TieU = false;
    boolean TieL = false;
    boolean TieD = false;
    //Boolean to activate once the space bar is pressed
    boolean start = false;
    //Create colour for wall outline
    Color Lukelightsaber = new Color(180, 200, 232);
    //Variables for the walls
    Rectangle wallNorth = new Rectangle(0, 0, thick * 2, WIDTH);
    Rectangle wallEast = new Rectangle(WIDTH - thick * 2, 0, HEIGHT, thick * 2);
    Rectangle wallSouth = new Rectangle(0, HEIGHT - thick * 2, thick * 2, WIDTH);
    Rectangle wallWest = new Rectangle(0, 0, HEIGHT, thick * 2);
    //Create variable for font for title
    Font myTitle = new Font("Impact", Font.BOLD, 100);
    //Create variable for font for text
    Font myText = new Font("Impact", Font.BOLD, 10);
    //Create variable for font for win speeches
    Font myWin = new Font("Impact", Font.BOLD, 50);
    //create avriable for font for score
    Font myScore = new Font("Impact", Font.BOLD, 30);
    //create avriable for font for score
    Font myTalk = new Font("Impact", Font.BOLD, 10);
    //Create variableas for speech
    int talkXwingx = -1000;
    int talkXwingy = -1000;
    int talkXwingiex = -1000;
    int talkXwingiey = -1000;
    //Create variable for the start menu and end menu
    int titlex = 50;
    int speechX = 0;
    int menux = 0;
    //Create a variable for scores
    int scoreXWing = 0;
    int scoreTie = 0;
    //Create sizing variables for the score
    int scorey = -1000;
    int scoreTiex = 0;
    int scoreXWingx = WIDTH - thick * 2;
    //initalize the scanner
    Scanner input = new Scanner(System.in);
    //Create words for winners
    String PreviousWinner = "No previuos winner,";
    //Create variable for speed
    int speed = 1;
    //Create booleans to tell the X-wing to turn
    boolean xWingStart = false;
    //Only one boolean becasue the xwing has four different shapes so it can follow the throster booleans  
    //Create boolean to tell the tie wing to turn
    boolean tieLeftRight = false;
    boolean tieUpDown = false;
    //Create variables for the droid bonus
    int droidXPoint = -100;
    int droidYPoint = -100;
    //Create rectangle for the astromech droid
    Rectangle droid = new Rectangle (droidXPoint,droidYPoint,thick,thick);
    //Counter to only allow the droid to respawn once
    int counter =1;
    
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

        ///MAIN GAME
        //Create backround for the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Create the character tie fighter's throsters
        g.setColor(tie);
        g.fillRect(Tie.x, Tie.y, Tie.height, Tie.width);
        //Create shape for the trail of tie fighter
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            g.fillRect(tiex[i], tiey[i], Tie.height, Tie.width);
        }

        //Create the character xwing's throsters
        g.setColor(xwing);
        g.fillRect(XWing.x, XWing.y, XWing.height, XWing.width);
        //Create shape for xwing's trail
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            g.fillRect(wingx[i], wingy[i], XWing.height, XWing.width);
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

        //Create the wrinting for the score
        g.setFont(myScore);
        //Set coplour specifically for tie fighter
        g.setColor(tie);
        g.drawString("" + scoreTie, scoreTiex, scorey);
        g.setColor(xwing);
        g.drawString("" + scoreXWing, scoreXWingx, scorey);

        //END game
        //Create backround for the main menu
        g.setColor(Color.BLACK);
        g.fillRect(menux, 0, WIDTH, HEIGHT);
        //Create speach to talk to the players
        //draw winning speech for xwing
        //Set the font


        g.setFont(myWin);
        //set the colour
        g.setColor(xwing);
        g.drawString("Congratulations", talkXwingx, talkXwingy);
        g.drawString("X-wing, you won!!!", talkXwingx, talkXwingy + 50);
        //draw winning speech of tie fighter
        g.setColor(tie);
        //Set the colour
        g.drawString("Congratulations", talkXwingiex, talkXwingiey);
        g.drawString("Tie fighter, you won!!!", talkXwingiex, talkXwingiey + 50);
        //Let the players knwo to restart the game if they want to play 
        g.setFont(myTalk);
        g.drawString("press enter to play again", talkXwingiex, talkXwingiey + 100);
        g.setColor(xwing);
        g.drawString("press enter to play again", talkXwingx, talkXwingy + 100);
        g.drawString("The previous winner is: " + PreviousWinner, talkXwingx, talkXwingy + 150);
        g.drawString("Please enter your name in the java program then press enter", talkXwingx, talkXwingy + 200);
        g.setColor(tie);
        g.drawString("The previous winner is: " + PreviousWinner, talkXwingiex, talkXwingiey + 150);
        g.drawString("Please enter your name in the java program then press enter", talkXwingiex, talkXwingiey + 200);


        //Draw the droid power up
        //Draw the legs
        g.setColor(Color.WHITE);
        g.fillRect(droid.x, droid.y+9, 3, 1);
        g.fillRect(droid.x+1, droid.y+5, 2, 5);
        g.fillRect(droid.x+2, droid.y+4, 6, 1);
        g.fillRect(droid.x+7, droid.y+5, 2, 5);
        g.fillRect(droid.x+7, droid.y+9, 3, 1);
        //Draw the base
        g.fillRect(droid.x+3, droid.y+1, 4, 8);
        g.fillRect(droid.x+4,droid.y,2,1);
        g.setColor(Color.BLUE);
        g.fillRect(droid.x+4,droid.y+1,2,2);
        g.fillRect(droid.x+4, droid.y+4, 2, 1);
        g.fillRect(droid.x+4, droid.y+6, 1, 2);
        //Draw the characters to go onto the throsters
        //draw a x-wing face up
        if (xWingU == true) {
            g.setColor(Color.LIGHT_GRAY);
            //Wings
            g.fillRect(XWing.x, XWing.y + 7, thick, 3);
            //Main nose
            g.fillRect(XWing.x + 4, XWing.y, 2, thick);
            //make blasters
            g.drawLine(XWing.x, XWing.y + 5, XWing.x, XWing.y + 7);
            g.drawLine(XWing.x + 9, XWing.y + 5, XWing.x + 9, XWing.y + 7);
        }
        if (xWingD == true) {
            //draw a x-wing face down
            g.setColor(Color.LIGHT_GRAY);
            //Wings
            g.fillRect(XWing.x, XWing.y, thick, 3);
            //Main nose
            g.fillRect(XWing.x + 4, XWing.y, 2, thick);
            //make blasters
            g.drawLine(XWing.x, XWing.y + 5, XWing.x, XWing.y);
            g.drawLine(XWing.x + 9, XWing.y + 5, XWing.x + 9, XWing.y);
        }
        if (xWingR == true) {
            //draw a x-wing face right
            g.setColor(Color.LIGHT_GRAY);
            //Wings
            g.fillRect(XWing.x, XWing.y, 3, thick);
            //Main nose
            g.fillRect(XWing.x, XWing.y + 4, thick, 2);
            //make blasters
            g.drawLine(XWing.x, XWing.y, XWing.x + 5, XWing.y);
            g.drawLine(XWing.x, XWing.y + thick, XWing.x + 5, XWing.y + thick);
        }
        if (xWingL == true || xWingStart == true) {
            //draw a x-wing face left
            g.setColor(Color.LIGHT_GRAY);
            //Wings
            g.fillRect(XWing.x + 7, XWing.y, 3, thick);
            //Main nose
            g.fillRect(XWing.x, XWing.y + 4, thick, 2);
            //make blasters
            g.drawLine(XWing.x + thick, XWing.y, XWing.x + 5, XWing.y);
            g.drawLine(XWing.x + thick, XWing.y + thick, XWing.x + 5, XWing.y + thick);
        }
        if (tieLeftRight == true) {
            //draw a tie fighter face right or left
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(Tie.x, Tie.y, Tie.x + thick, Tie.y);
            g.drawLine(Tie.x, Tie.y + thick, Tie.x + thick, Tie.y + thick);
            g.drawLine(Tie.x + 5, Tie.y, Tie.x + 5, Tie.y + thick);
            g.fillOval(Tie.x + 3, Tie.y + 2, 5, 5);
        }
        if (tieUpDown == true) {
            //draw a tie fighter face up or down
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(Tie.x, Tie.y, Tie.x, Tie.y + thick);
            g.drawLine(Tie.x + thick, Tie.y, Tie.x + thick, Tie.y + thick);
            g.drawLine(Tie.x, Tie.y + 5, Tie.x + thick, Tie.y + 5);
            g.fillOval(Tie.x + 3, Tie.y + 2, 5, 5);
        }
        //START MENU
        //Create coloured text boxes
        //tie's text box
        g.setFont(myText);
        g.setColor(tie);
        g.drawRect(TieTextBox.x, TieTextBox.y, TieTextBox.width, TieTextBox.height);
        //xwing's text box
        g.setColor(xwing);
        g.drawRect(xWingTextBox.x, xWingTextBox.y, xWingTextBox.width, xWingTextBox.height);

        //Create coloured text boxes
        //ties text box
        g.setColor(tie);
        g.drawRect(TieTextBox.x, TieTextBox.y, TieTextBox.width, TieTextBox.height);
        //xwing's text box
        g.setColor(xwing);
        g.drawRect(xWingTextBox.x, xWingTextBox.y, xWingTextBox.width, xWingTextBox.height);

        //Draw the title
        g.setFont(myTitle);
        g.setColor(xwing);
        g.drawString("DROID", titlex, 100);

        //Draw in instructions
        g.setFont(myText);
        g.setColor(Color.WHITE);
        g.drawString("The object of the game is to retreive the droid by avoid touching the walls and the trails left behind", speechX, 150);
        g.drawString("by both your opponent and your own trail", speechX, 200);
        g.drawString("PRESS ENTER KEY TO CONTINUE", speechX, 250);


        //Draw instructions for the tie fighter specifically
        //Draw blocks for letters AWSD
        g.drawRect(TieTextBox.x + 40, 350, thick * 4, thick * 4);
        g.drawRect(TieTextBox.x + 80, 350, thick * 4, thick * 4);
        g.drawRect(TieTextBox.x + 70, 310, thick * 4, thick * 4);
        g.drawRect(TieTextBox.x + 120, 350, thick * 4, thick * 4);
        //Draw in the letters to corrispond in the boxes
        g.setColor(tie);
        g.drawString("A", TieTextBox.x + 60, 370);
        g.drawString("S", TieTextBox.x + 100, 370);
        g.drawString("W", TieTextBox.x + 90, 330);
        g.drawString("D", TieTextBox.x + 140, 370);

        //Draw instructions for xwing specifically
        //Draw blocks for key pad
        //Set colour
        g.setColor(Color.WHITE);
        g.drawRect(xWingTextBox.x + 40, 350, thick * 4, thick * 4);
        g.drawRect(xWingTextBox.x + 80, 350, thick * 4, thick * 4);
        g.drawRect(xWingTextBox.x + 80, 310, thick * 4, thick * 4);
        g.drawRect(xWingTextBox.x + 120, 350, thick * 4, thick * 4);
        //Draw in the letters to corrispond in the boxes
        g.setColor(xwing);
        g.drawString("<", xWingTextBox.x + 60, 370);
        g.drawString("!", xWingTextBox.x + 100, 370);
        g.drawString("^", xWingTextBox.x + 90, 330);
        g.drawString(">", xWingTextBox.x + 140, 370);
        // GAME DRAWING ENDS HERE
    }
    // Beets Bears Battlestar galactica
    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!

    public void preSetup() {
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
            //Once space bar is pressed the game begins
            if (start == true) {
                //Check to see if there is any collisions
                //Check if any character collided with the wall
                collisionWall();
                //Check if xwing touched his own trail
                xWingSuicide();
                //Check if the xwing got the droid
                xWingCaughtDroid();
                //Check if tie fighter touched his own trail
                tieSuicide();
                //Only set up the droid once
           if(counter == 1){
                //Do the math random method for random x and y positions from wall to wall
             int randYPoint = (int) (Math.random() * ((wallSouth.y-thick) - (wallNorth.y+thick*2)) + 1) + (wallNorth.y+thick*2);
             //Set x and y positions for the robot
             droid.x = WIDTH/2;
             droid.y = randYPoint; 
                //TRON movement 
             counter--;
            }
                
                //Have the xwing move left 5 spaces when left is pressed
                if (xWingL) {
                    //Method for adding the trail to xwing
                    xWingThrosterAdding();
                    XWing.x = XWing.x - speed;

                    //Check if xwing collided with tie fightere after he moves
                    empireWin();
                }

                //Have the xwing move right thick spaces when right is pressed
                if (xWingR) {
                    //Method for adding the trail to xwing
                    xWingThrosterAdding();
                    XWing.x = XWing.x + speed;

                    //Check if xwing collided with tie fightere after he moves
                    empireWin();

                }
                //Have the xwing move up thick spaces when up is pressed
                if (xWingU) {
                    //Method for adding the trail to xwing
                    xWingThrosterAdding();
                    XWing.y = XWing.y - speed;

                    //Check if xwing collided with tie fightere after he moves
                    empireWin();
                }
                //Have the xwing move down thick spaces when down is pressed
                if (xWingD) {
                    //Method for adding the trail to xwing
                    xWingThrosterAdding();
                    XWing.y = XWing.y + speed;

                    //Check if xwing collided with tie fightere after he moves
                    empireWin();
                }
                //CLU movement
                //Have the tie fighter move left thick spaces when left is pressed
                if (TieL) {
                    //Method for adding the trail to tie fighter
                    tieThrosterAdding();
                    Tie.x = Tie.x - speed;

                    //Check if tie fighter collided with xwing
                    rebelWin();
                }
                //Have the tie fighter move right thick spaces when right is pressed
                if (TieR) {
                    //Method for adding the trail to tie fighter
                    tieThrosterAdding();
                    Tie.x = Tie.x + speed;

                    //Check if tie fighter collided with xwing
                    rebelWin();
                }
                //Have the tie fighter move up thick spaces when up is pressed
                if (TieU) {
                    //Make the tie face up
                    tieUpDown = true;
                    //Make the other boolean false to maintain shape
                    tieLeftRight = false;
                    //Method for adding the trail to tie fighter
                    tieThrosterAdding();
                    Tie.y = Tie.y - speed;

                    //Check if tie fighter collided with xwing
                    rebelWin();
                }
                //Have the tie fighter move down thick spaces when down is pressed
                if (TieD) {
                    //Make the tie face down
                    tieUpDown = true;
                    //Make the other boolean false to maintain shape
                    tieLeftRight = false;
                    //Method for adding the trail to tie fighter
                    tieThrosterAdding();
                    Tie.y = Tie.y + speed;

                    //Check if tie fighter collided with xwing
                    rebelWin();
                }

            } else {
                resetCharacters();
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

    private BufferedImage loadImage(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            //When the enter key is pressed, and released, the game restarts
            if (code == KeyEvent.VK_ENTER) {
                //Reset the score for new game
                scoreXWing = 0;
                scoreTie = 0;
                start = true;
                resetGame();
            }
            //Use the arrow keys for xwing's movement
            if (code == KeyEvent.VK_RIGHT) {
                xWingR = true;
                //Only have xwing move in a single direction by setting the booleans other than right false
                xWingU = false;
                xWingL = false;
                xWingD = false;
                xWingStart = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                xWingL = true;
                //Only have xwing move in a single direction by setting the booleans other than left false
                xWingR = false;
                xWingU = false;
                xWingD = false;
                xWingStart = false;

            }
            if (code == KeyEvent.VK_UP) {
                xWingU = true;
                //Only have xwing move in a single direction by setting the booleans other than up false
                xWingR = false;
                xWingL = false;
                xWingD = false;
                xWingStart = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                xWingD = true;
                //Only have xwing move in a single direction by setting the booleans other than down false
                xWingR = false;
                xWingU = false;
                xWingL = false;
                xWingStart = false;
            }

            //Use the A,W,S,D keys to move tie fighter
            if (code == KeyEvent.VK_D) {
                TieR = true;
                //Only have xwing move in a single direction by setting the booleans other than D false
                TieU = false;
                TieL = false;
                TieD = false;
                //Make the tie face right
                tieLeftRight = true;
                //Make the other boolean false to maintain shape
                tieUpDown = false;
            }
            if (code == KeyEvent.VK_A) {
                TieL = true;
                //Only have xwing move in a single direction by setting the booleans other than A false
                TieR = false;
                TieU = false;
                TieD = false;
                //Make the tie face left
                tieLeftRight = true;
                //Make the other boolean false to maintain shape
                tieUpDown = false;
            }
            if (code == KeyEvent.VK_W) {
                TieU = true;
                //Only have xwing move in a single direction by setting the booleans other than W false
                TieR = false;
                TieL = false;
                TieD = false;
                //Make the tie face up
                tieUpDown = true;
                //Make the other boolean false to maintain shape
                tieLeftRight = false;
            }
            if (code == KeyEvent.VK_S) {
                TieD = true;
                //Only have xwing move in a single direction by setting the booleans other than S false
                TieR = false;
                TieU = false;
                TieL = false;//Make the tie face down
                tieUpDown = true;
                //Make the other boolean false to maintain shape
                tieLeftRight = false;

            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {

            //Intitalize the arrow keys 
            int code = e.getKeyCode();
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
 * Method to see if the droid was caught
 */
    public void xWingCaughtDroid(){
        if(XWing.intersects(droid)){
            resetCharacters();
            xwingEndMenu();
        }
    }
    
    /**
 * Method to see if the droid was caught
 */
    public void tieCaughtDroid(){
        if(Tie.intersects(droid)){
            resetCharacters();
            tieEndMenu();
        }
    }
    /**
     * Method that checks if a character hits a wall
     */
    public void collisionWall() {
        //If xwing hits a wall, xwing loses and tie is told that they won
        for (int i = 0; i <= ti + xi; i++) {
            //check if the trial hit any part fo the walls
            if (wingx[i] == WIDTH - thick || wingx[i] == thick
                    || wingy[i] == wallNorth.y + thick || wingy[i] == wallSouth.y + thick) {
                //Add top the score
                scoreTie++;
                resetCharacters();
                //call up the end menu
                if (scoreTie == 3) {
                    tieEndMenu();
                }
            }
        }
        //If tie hits the wall, tie fighter loses and xwing is told he wins
        for (int i = 0; i <= ti + xi; i++) {
            //check if the trial hit any part fo the walls
            if (tiex[i] == WIDTH - thick || tiex[i] == thick
                    || tiey[i] == wallNorth.y + thick || tiey[i] == wallSouth.y + thick) {
                //Add top the score
                scoreXWing++;
                resetCharacters();
                //call up the end menu
                if (scoreXWing == 3) {
                    xwingEndMenu();
                }
            }
        }
    }

    /**
     * Method to see if tie hit xwing if so, xwing won
     */
    public void rebelWin() {
        //Have two for loops to run throught and compare points form both xwing and tie fighter
        for (int i = 0; i < xi; i++) {
            for (int j = 0; j < ti; j++) {
                //Check if xwing hit tie fighter at it's x or y point or on the opposite side of where the x or y point is (thick)
                if ((tiex[j] == wingx[i] || tiex[j] == wingx[i] - thick * 2) && (tiey[j] == wingy[i] || tiey[j] == wingy[i] - thick)) {
                    //Add top the score
                    scoreXWing++;
                    resetCharacters();
                    //call up the end menu
                    if (scoreXWing == 3) {
                        xwingEndMenu();
                    }

                }
            }
        }
    }

    /**
     * Method to see if xwing hits tie if so, tiee won
     */
    public void empireWin() {
        //Have two for loops to run throught and compare points form both xwing and tie fighter
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < xi; j++) {
                //Check if tie fighter hit xwing at it's x or y point or on the opposite side of where the x or y point is (thick)
                if ((tiex[i] == wingx[j] || tiex[i] == wingx[j] - thick * 2) && (tiey[i] == wingy[j] || tiey[i] == wingy[j] - thick)) {
                    //Add top the score
                    scoreTie++;
                    resetCharacters();
                    //call up the end menu
                    if (scoreTie == 3) {
                        tieEndMenu();
                    }
                }
            }
        }
    }

    /**
     * Method to set the array attached to tie fighter
     */
    public void tieThrosterAdding() {
        //Set the psoition in the x points of tie fighter array to equal the corrent x position
        tiex[ti] = Tie.x;
        //Set the psoition in the y points of tie fighter array to equal the corrent y position
        tiey[ti] = Tie.y;
        //Increase the counter for the array positions
        ti++;
    }

    /**
     * Method to set array attached to xwing
     */
    public void xWingThrosterAdding() {
        //Set the psoition in the x points of xwing array to equal the corrent x position    
        wingx[xi] = XWing.x;
        //Set the psoition in the y points of xwing array to equal the corrent y position
        wingy[xi] = XWing.y;
        //Increase the counter for the array positions
        xi++;
    }

    /**
     * Method to check if xwing crossed over its own path
     */
    public void xWingSuicide() {
        //Have two for loops to run throught and compare points form both xwing and tie fighter
        for (int i = 0; i < xi; i++) {
            for (int j = 0; j < xi; j++) {
                //Do NOT allow the same square to be compared at the same time
                if (j != i) {
                    //Check if xwing of one square is  the same of another, which means he is over lapping
                    if ((wingx[i] == wingx[j] && wingy[i] == wingy[j])) {
                        //Add top the score
                        scoreTie++;
                        resetCharacters();
                        //call up the end menu
                        if (scoreTie == 3) {
                            tieEndMenu();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to check if tie fighter crossed over its own path
     */
    public void tieSuicide() {
        //Have two for loops to run throught and compare points form both xwing and tie fighter
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < ti; j++) {
                //Do NOT allow the same square to be compared at the same time
                if (j != i) {
                    //Check if tie fighter of one square is the same of another, which means he is over lapping
                    if ((tiex[i] == tiex[j] && tiey[i] == tiey[j])) {
                        //Add top the score
                        scoreXWing++;
                        resetCharacters();
                        //call up the end menu
                        if (scoreXWing == 3) {
                            xwingEndMenu();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to erase xwing's trail after they lose
     */
    public void xWingTrailBeGone() {
        // determines when we started so we can keep a framerate
        //Set variable to go through the x and y poijnt arrays of tie backwards
        for (int i = xi; i >= 0; i--) {
            //Move the array position out of the area 
            wingx[i] = 0;
            wingy[i] = 0;
        }
        //Move the original start square to 0
        XWing.x = 0;
        XWing.y = 0;

    }

    /**
     * Method to erase tie's trail after they lose
     */
    public void tieTrailBeGone() {
        //Set variable to go through the x and y poijnt arrays of tie fighter backwards
        for (int i = ti; i >= 0; i--) {
            //Move the array position out of the area
            tiex[i] = 0;
            tiey[i] = 0;
        }
        //Move the original start square to 0
        Tie.x = 0;
        Tie.y = 0;

    }

    /**
     * Method to introduce the end menu of the game if xwing wins
     */
    public void xwingEndMenu() {
        //Method to erase the trail of tie fighter once it dies
        tieTrailBeGone();
        //Increase the variables of the x/y points of the winning speech
        talkXwingx = 0;
        talkXwingy = HEIGHT / 2;
        //Increase the backround variable
        menux = 0;
        //dissable the movement of the characters
        start = false;

        //Have person enter in thier name
        String winner = input.nextLine();
        PreviousWinner = winner;
    }

    /**
     * Method to introduce the end menu of the game if tie fightere wins
     */
    public void tieEndMenu() {
        //Method to erase the trail of tie fighter once it dies
        xWingTrailBeGone();
        //Increase the variables of the x/y points of the winning speech
        talkXwingiex = 0;
        talkXwingiey = HEIGHT / 2;
        //Increase the backround variable
        menux = 0;
        //dissable the movement of the characters
        start = false;

        //Have person enter in thier name
        String winner = input.nextLine();
        PreviousWinner = winner;
    }

    /**
     * Method to create the start environment of the game after the main menu
     */
    public void resetGame() {
        //Move the start menu out of the way
        //Change the speech quardinents
        titlex = -1000;
        speechX = -1000;
        //Change the backround coorinents
        menux = -1000;
        //change the fcoorinantas of the boxes
        TieTextBox.x = - 1000;
        xWingTextBox.x = -1000;
        //change the y variable on both scores
        scorey = 100;
        //Take down the end menu if nessisary
        //Create variableas for speech
        talkXwingx = -1000;
        talkXwingy = -1000;
        talkXwingiex = -1000;
        talkXwingiey = -1000;
        
    }

    /**
     * Method to reset the character back to the start position
     */
    public void resetCharacters() {
//Reset the booleans to start the characters
        boolean tieLeftRight = true;
        boolean xWingStart = true;

        //Reset the xwing postions to the start position
        XWing.x = WIDTH - thick * 4;
        XWing.y = HEIGHT / 2;
        //Reset xwing to it's start position 
        Tie.x = thick * 3;
        Tie.y = HEIGHT / 2;
        //go throught the positions of the xwing array
        for (int i = 0; i < xi; i++) {
            wingx[i] = XWing.x;
            wingy[i] = XWing.y;
        }
        //go throught the position of the tie fighter array
        for (int i = 0; i < ti; i++) {
            tiex[i] = Tie.x;
            tiey[i] = Tie.y;
        }
        //Reset array counters
        xi = 0;
        ti = 0;
        //reset the booleans of the moving tof the characters
        xWingU = false;
        xWingD = false;
        xWingL = false;
        xWingR = false;
        TieU = false;
        TieD = false;
        TieL = false;
        TieR = false;
        //Resetr the droid position
        droid.x =-100;
        droid.y =-100;
        
        //Reset the counter for the droid
        counter=1;
    }
}
    
//Abadee badee badeee that's all folks