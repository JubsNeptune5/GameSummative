
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
    //Variables for the main menu
    //VAriables fior the rectangles for the text boxes
    Rectangle cluTextBox = new Rectangle(0, HEIGHT / 2, WIDTH / 2 - 1, HEIGHT / 2);
    Rectangle tronTextBox = new Rectangle(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
    //Variables for the main game
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
    int talkTx = -1000;
    int talkTy = -1000;
    int talkCx = -1000;
    int talkCy = -1000;
    //Create variable for the start menu and end menu
    int titlex = 50;
    int speechX = 0;
    int menux = 0;
    //Create a variable for scores
    int scoreTron = 0;
    int scoreClu = 0;
    //Create sizing variables for the score
    int scorey = -1000;
    int scoreClux = 0;
    int scoreTronx = WIDTH - thick * 2;
    //initalize the scanner
    Scanner input = new Scanner(System.in);
    //Create words for winners
    String PreviousWinner = "No previuos winner,";
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

        //Create the wrinting for the score
        g.setFont(myScore);
        //Set coplour specifically for clu
        g.setColor(clu);
        g.drawString("" + scoreClu, scoreClux, scorey);
        g.setColor(tron);
        g.drawString("" + scoreTron, scoreTronx, scorey);

        //END game
        //Create backround for the main menu
        g.setColor(Color.BLACK);
        g.fillRect(menux, 0, WIDTH, HEIGHT);
        //Create speach to talk to the players
        //draw winning speech for tron
        //Set the font


        g.setFont(myWin);
        //set the colour
        g.setColor(tron);
        g.drawString("Congratulations", talkTx, talkTy);
        g.drawString("Tron, you won!!!", talkTx, talkTy + 50);
        //draw winning speech of clu
        g.setColor(clu);
        //Set the colour
        g.drawString("Congratulations", talkCx, talkCy);
        g.drawString("Clu, you won!!!", talkCx, talkCy + 50);
        //Let the players knwo to restart the game if they want to play 
        g.setFont(myTalk);
        g.drawString("press enter to play again", talkCx, talkCy + 100);
        g.setColor(tron);
        g.drawString("press enter to play again", talkTx, talkTy + 100);
        g.drawString("The previous winner is: " + PreviousWinner, talkTx, talkTy + 150);
        g.drawString("Please enter your name in the java program then press enter", talkTx, talkTy + 200);
        g.setColor(clu);
        g.drawString("The previous winner is: " + PreviousWinner, talkCx, talkCy + 150);
        g.drawString("Please enter your name in the java program then press enter", talkCx, talkCy + 200);
        //START MENU
        //Create coloured text boxes
        //Clu's text box
        g.setFont(myText);
        g.setColor(clu);
        g.drawRect(cluTextBox.x, cluTextBox.y, cluTextBox.width, cluTextBox.height);
        //Tron's text box
        g.setColor(tron);
        g.drawRect(tronTextBox.x, tronTextBox.y, tronTextBox.width, tronTextBox.height);

        //Create coloured text boxes
        //Clus text box
        g.setColor(clu);
        g.drawRect(cluTextBox.x, cluTextBox.y, cluTextBox.width, cluTextBox.height);
        //Tron's text box
        g.setColor(tron);
        g.drawRect(tronTextBox.x, tronTextBox.y, tronTextBox.width, tronTextBox.height);

        //Draw the title
        g.setFont(myTitle);
        g.setColor(tron);
        g.drawString("TRON", titlex, 100);

        //Draw in instructions
        g.setFont(myText);
        g.setColor(Color.WHITE);
        g.drawString("The object of the game is to avoid touching the walls and the trails left behind", speechX, 150);
        g.drawString("by both your opponent and your own trail", speechX, 200);
        g.drawString("PRESS ENTER KEY TO CONTINUE", speechX, 250);


        //Draw instructions for clu specifically
        //Draw blocks for letters AWSD
        g.drawRect(cluTextBox.x + 40, 350, thick * 4, thick * 4);
        g.drawRect(cluTextBox.x + 80, 350, thick * 4, thick * 4);
        g.drawRect(cluTextBox.x + 70, 310, thick * 4, thick * 4);
        g.drawRect(cluTextBox.x + 120, 350, thick * 4, thick * 4);
        //Draw in the letters to corrispond in the boxes
        g.setColor(clu);
        g.drawString("A", cluTextBox.x + 60, 370);
        g.drawString("S", cluTextBox.x + 100, 370);
        g.drawString("W", cluTextBox.x + 90, 330);
        g.drawString("D", cluTextBox.x + 140, 370);

        //Draw instructions for tron specifically
        //Draw blocks for key pad
        //Set colour
        g.setColor(Color.WHITE);
        g.drawRect(tronTextBox.x + 40, 350, thick * 4, thick * 4);
        g.drawRect(tronTextBox.x + 80, 350, thick * 4, thick * 4);
        g.drawRect(tronTextBox.x + 80, 310, thick * 4, thick * 4);
        g.drawRect(tronTextBox.x + 120, 350, thick * 4, thick * 4);
        //Draw in the letters to corrispond in the boxes
        g.setColor(tron);
        g.drawString("<", tronTextBox.x + 60, 370);
        g.drawString("!", tronTextBox.x + 100, 370);
        g.drawString("^", tronTextBox.x + 90, 330);
        g.drawString(">", tronTextBox.x + 140, 370);
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
                //Check if tron touched his own trail
                tronTouchItself();
                //Check if clu touched his own trail
                cluTouchItself();
                //TRON movement 
                //Have the tron move left 5 spaces when left is pressed
                if (tronL) {
                    //Method for adding the trail to tron
                    tronTrailAdding();
                    Tron.x = Tron.x - 1;

                    //Check if tron collided with clue after he moves
                    cluWin();
                }

                //Have the tron move right thick spaces when right is pressed
                if (tronR) {
                    //Method for adding the trail to tron
                    tronTrailAdding();
                    Tron.x = Tron.x + 1;

                    //Check if tron collided with clue after he moves
                    cluWin();

                }
                //Have the tron move up thick spaces when up is pressed
                if (tronU) {
                    //Method for adding the trail to tron
                    tronTrailAdding();
                    Tron.y = Tron.y - 1;

                    //Check if tron collided with clue after he moves
                    cluWin();
                }
                //Have the tron move down thick spaces when down is pressed
                if (tronD) {
                    //Method for adding the trail to tron
                    tronTrailAdding();
                    Tron.y = Tron.y + 1;

                    //Check if tron collided with clue after he moves
                    cluWin();
                }
                //CLU movement
                //Have the clu move left thick spaces when left is pressed
                if (cluL) {
                    //Method for adding the trail to clu
                    cluTrailAdding();
                    Clu.x = Clu.x - 1;

                    //Check if clu collided with tron
                    tronWin();
                }
                //Have the clu move right thick spaces when right is pressed
                if (cluR) {
                    //Method for adding the trail to clu
                    cluTrailAdding();
                    Clu.x = Clu.x + 1;

                    //Check if clu collided with tron
                    tronWin();
                }
                //Have the clu move up thick spaces when up is pressed
                if (cluU) {
                    //Method for adding the trail to clu
                    cluTrailAdding();
                    Clu.y = Clu.y - 1;

                    //Check if clu collided with tron
                    tronWin();
                }
                //Have the clu move down thick spaces when down is pressed
                if (cluD) {
                    //Method for adding the trail to clu
                    cluTrailAdding();
                    Clu.y = Clu.y + 1;

                    //Check if clu collided with tron
                    tronWin();
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
                scoreTron = 0;
                scoreClu = 0;
                start = true;
                resetGame();
            }
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
     * Method that checks if a character hits a wall
     */
    public void collisionWall() {
        //If Tron hits a wall, tron loses and Clu is told that they won
        for (int i = 0; i <= ci + ti; i++) {
            //check if the trial hit any part fo the walls
            if (tx[i] == WIDTH - thick || tx[i] == thick
                    || ty[i] == wallNorth.y + thick || ty[i] == wallSouth.y + thick) {
                //Add top the score
                scoreClu++;
                resetCharacters();
                //call up the end menu
                if (scoreClu == 3) {
                    cluEndMenu();
                }
            }
        }
        //If Clu hits the wall, clu loses and tron is told he wins
        for (int i = 0; i <= ci + ti; i++) {
            //check if the trial hit any part fo the walls
            if (cx[i] == WIDTH - thick || cx[i] == thick
                    || cy[i] == wallNorth.y + thick || cy[i] == wallSouth.y + thick) {
                //Add top the score
                scoreTron++;
                resetCharacters();
                //call up the end menu
                if (scoreTron == 3) {
                    tronEndMenu();
                }
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
                    //Add top the score
                    scoreTron++;
                    resetCharacters();
                    //call up the end menu
                    if (scoreTron == 3) {
                        tronEndMenu();
                    }

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
                    //Add top the score
                    scoreClu++;
                    resetCharacters();
                    //call up the end menu
                    if (scoreClu == 3) {
                        cluEndMenu();
                    }
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
                        //Add top the score
                        scoreClu++;
                        resetCharacters();
                        //call up the end menu
                        if (scoreClu == 3) {
                            cluEndMenu();
                        }
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
                        //Add top the score
                        scoreTron++;
                        resetCharacters();
                        //call up the end menu
                        if (scoreTron == 3) {
                            tronEndMenu();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to erase Tron's trail after they lose
     */
    public void tronTrailBeGone() {
        // determines when we started so we can keep a framerate
        //Set variable to go through the x and y poijnt arrays of clu backwards
        for (int i = ti; i >= 0; i--) {
            //Move the array position out of the area 
            tx[i] = 0;
            ty[i] = 0;
        }
        //Move the original start square to 0
        Tron.x = 0;
        Tron.y = 0;

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
        }
        //Move the original start square to 0
        Clu.x = 0;
        Clu.y = 0;

    }

    /**
     * Method to introduce the end menu of the game if tron wins
     */
    public void tronEndMenu() {
        //Method to erase the trail of clu once it dies
        cluTrailBeGone();
        //Increase the variables of the x/y points of the winning speech
        talkTx = 0;
        talkTy = HEIGHT / 2;
        //Increase the backround variable
        menux = 0;
        //dissable the movement of the characters
        start = false;

        //Have person enter in thier name
        String winner = input.nextLine();
        PreviousWinner = winner;
    }

    /**
     * Method to introduce the end menu of the game if clue wins
     */
    public void cluEndMenu() {
        //Method to erase the trail of clu once it dies
        tronTrailBeGone();
        //Increase the variables of the x/y points of the winning speech
        talkCx = 0;
        talkCy = HEIGHT / 2;
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
        cluTextBox.x = - 1000;
        tronTextBox.x = -1000;
        //change the y variable on both scores
        scorey = 100;
        //Take down the end menu if nessisary
        //Create variableas for speech
        talkTx = -1000;
        talkTy = -1000;
        talkCx = -1000;
        talkCy = -1000;
    }

    /**
     * Method to reset the character back to the start position
     */
    public void resetCharacters() {


        //Reset the tron postions to the start position
        Tron.x = WIDTH - thick * 4;
        Tron.y = HEIGHT / 2;
        //Reset tron to it's start position 
        Clu.x = thick * 3;
        Clu.y = HEIGHT / 2;
        //go throught the positions of the tron array
        for (int i = 0; i < ti; i++) {
            tx[i] = Tron.x;
            ty[i] = Tron.y;
        }
        //go throught the position of the clu array
        for (int i = 0; i < ci; i++) {
            cx[i] = Clu.x;
            cy[i] = Clu.y;
        }
        //Reset array counters
        ti = 0;
        ci = 0;
        //reset the booleans of the moving tof the characters
        tronU = false;
        tronD = false;
        tronL = false;
        tronR = false;
        cluU = false;
        cluD = false;
        cluL = false;
        cluR = false;
    }
}
    
//Abadee badee badeee that's all folks