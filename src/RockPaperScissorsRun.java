//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

//Keyboard and Mouse
//Step 0 - Import
import java.awt.event.*;

//*******************************************************************************
// Class Definition Section

//Step 1 for Keyboard control - implements Keylistener
public class RockPaperScissorsRun implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Enemy Images
    public Image enemyRockA;
    public Image enemyRockB;

    public Image enemyPaperA;
    public Image enemyPaperB;

    public Image enemyScissorsA;
    public Image enemyScissorsB;

    public Image[] enemyPics;

    //Player Images
    public Image playerRockA;
    public Image playerRockB;

    public Image playerPaperA;
    public Image playerPaperB;

    public Image playerScissorsA;
    public Image playerScissorsB;

    public Image blob1;
    public Image blob2;



    public Image Background;

    public Image StartingScreen;


    public Enemy[] enemy;
    public Player user;

    public int scroll;

    public boolean collision = false;

    public int count = 0;

    public int enemyCount = 0; // counts the number of enemies for the text

    public int timer = 0;


    //Sound Effects
    public SoundFile backingSong; //Background music

    public SoundFile shiftRock; //Shifting to rock
    public SoundFile shiftPaper; //Shifting to paper
    public SoundFile shiftScissors; //Shifting to scissors

    public SoundFile boing; //Same vs Same

    public SoundFile papervScissors; //Paper vs. Scissors

    public SoundFile loseSound; // losing sound




    public boolean startScreen = true; // making the starting screen
    public Image LostScreen;
    public boolean youLost = false;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        RockPaperScissorsRun myApp = new RockPaperScissorsRun();   //creates a new instance of the game
        new Thread(myApp).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public RockPaperScissorsRun() {

        setUpGraphics();

        //Step 3 - Keyboard use.  addKeyListener(this)
        canvas.addKeyListener(this);

        //variable and objects
        //load images

        //Enemy Images
        enemyRockA = Toolkit.getDefaultToolkit().getImage("Enemy_Rock_A.png");
        enemyRockB = Toolkit.getDefaultToolkit().getImage("Enemy_Rock_B.png");

        enemyPaperA = Toolkit.getDefaultToolkit().getImage("Enemy_Paper_A.png"); //load the picture
        enemyPaperB = Toolkit.getDefaultToolkit().getImage("Enemy_Paper_B.png"); //load the picture

        enemyScissorsA = Toolkit.getDefaultToolkit().getImage("Enemy_Scissor_A.png");
        enemyScissorsB = Toolkit.getDefaultToolkit().getImage("Enemy_Scissor_B.png");


        //Player Images
        playerRockA = Toolkit.getDefaultToolkit().getImage("Player_Rock_A.png");
        playerRockB = Toolkit.getDefaultToolkit().getImage("Player_Rock_B.png");

        playerPaperA = Toolkit.getDefaultToolkit().getImage("Paper_Player_A.png"); //load the picture
        playerPaperB = Toolkit.getDefaultToolkit().getImage("Paper_Player_B.png"); //load the picture

        playerScissorsA = Toolkit.getDefaultToolkit().getImage("Player_Scissors_A.png");
        playerScissorsB = Toolkit.getDefaultToolkit().getImage("Player_Scissors_B.png");

        blob1 = Toolkit.getDefaultToolkit().getImage("BlobA.png");
        blob2= Toolkit.getDefaultToolkit().getImage("BlobB.png");


        //Background
        Background = Toolkit.getDefaultToolkit().getImage("TryingBackGround.png");





        //create (construct) the objects needed for the game

        user = new Player(100,400,10,0, blob1);

        // choose = new Potion(500,400, blob1);

        enemyPics = new Image[6];
        enemyPics[0] = playerRockA;
        enemyPics[1] = playerPaperA;
        enemyPics[2] = playerScissorsA;
        enemyPics[3] = playerRockB;
        enemyPics[4] = playerPaperB;
        enemyPics[5] = playerScissorsB;



        enemy = new Enemy[30];
        for (int i=0;i<enemy.length;i++) {
            int enemyNum = (int)(Math.random()*3+1);
            enemy[i] = new Enemy(1000 + i*500, 400, -5, 0, enemyPics[enemyNum]);
            enemy[i].enemyNum = enemyNum;


        }



        // Constructing Sounds
        backingSong = new SoundFile("Background.wav");
        backingSong.play();     // starting backing track


        boing = new SoundFile("boing.wav");
        shiftRock = new SoundFile("ShiftRock.wav");
        shiftPaper = new SoundFile("ShiftPaper.wav");
        shiftScissors = new SoundFile("ShiftScissors.wav");
        papervScissors = new SoundFile("PapervScissors.wav");
        loseSound = new SoundFile("LoseSound.wav");







        StartingScreen = Toolkit.getDefaultToolkit().getImage("RockPaperScissorStart.png");
        LostScreen = Toolkit.getDefaultToolkit().getImage("RPSRLoseScreen.png");



    }









//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up

    public void moveThings() {
        //calls the move( ) code in the objects

        user.move();
        for (int i =0;i<enemy.length;i++) {
            enemy[i].Emove();
        }
        scroll = scroll - 1;

        for (int i=0;i<enemy.length;i++) {

            if (enemyCount > 5) {
                enemy[i].dx = -8;
            }

            if (enemyCount > 10) {
                enemy[i].dx = -10;
            }

            if (enemyCount > 15) {
                enemy[i].dx = -12;
            }

            if (enemyCount > 20) {
                enemy[i].dx = -16;
            }
        }



    }

    public void checkIntersections() {

        // enemy deaths
        for (int i =0; i<enemy.length;i++) {

            if (user.rec.intersects(enemy[i].rec) && (collision == false) && (enemy[i].isAlive == true)) {
                if ((user.objectNum == 2) && (enemy[i].enemyNum == 1)) {
                    enemy[i].isAlive = false;
                    enemyCount = enemyCount +1;
                    collision = true;
                }
                else if ((user.objectNum == 1) && (enemy[i].enemyNum == 3)) {
                    enemy[i].isAlive = false;
                    collision = true;
                    enemyCount = enemyCount +1;


                }
                else if ((user.objectNum == 3) && (enemy[i].enemyNum == 2)) {
                    enemy[i].isAlive = false;
                    collision = true;
                    enemyCount = enemyCount +1;
                    papervScissors.play();



                }
                // user deaths
                else if ((user.objectNum == 1) && (enemy[i].enemyNum == 2)) {
                    user.isAlive = false;
                    user.ypos += 5;
                    collision = true;


                }

                else if ((user.objectNum == 2) && (enemy[i].enemyNum == 3)) {
                    user.isAlive = false;
                    user.ypos += 5;
                    collision = true;

                }

                else if ((user.objectNum == 3) && (enemy[i].enemyNum == 1)) {
                    user.isAlive = false;
                    user.ypos += 5;
                    collision = true;


                } else{
                    user.isBouncing = true;
                    user.dx = -5;
                    boing.play();
                } //none of the other pairings (the same object- like paper vs. paper)

            }






            if (! user.rec.intersects(enemy[i].rec)){
                collision = false;
            }


        }


    }

    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(Background, scroll, 0, 8000, 700, null);

        if (user.isAlive == false) {
            timer++;
            if (timer > 80) {
                youLost = true;
            }
        }


        // if (youLost == true){g.drawString("Press 1 to change to Rock")}



        //enemy image changes
        for (int i =0; i<enemy.length; i++) {
            if (enemy[i].isAlive == true) {

                if (enemy[i].enemyNum == 1) {
                    g.drawImage(enemyRockA, enemy[i].xpos, enemy[i].ypos, enemy[i].width, enemy[i].height, null);
                }
                if (enemy[i].enemyNum == 2) {
                    g.drawImage(enemyPaperA, enemy[i].xpos, enemy[i].ypos, enemy[i].width, enemy[i].height, null);
                }
                if (enemy[i].enemyNum == 3) {
                    g.drawImage(enemyScissorsA, enemy[i].xpos, enemy[i].ypos, enemy[i].width, enemy[i].height, null);
                }
            } else {
            }
        }

        //user image changes
        if (user.isAlive == true){

            if (user.objectNum == 0){
                g.drawImage(blob1, (int)user.xpos, user.ypos, user.width, user.height, null);

            }
            if (user.objectNum == 1){
                g.drawImage(playerRockA, (int)user.xpos, user.ypos, user.width, user.height, null);
                shiftRock.play();
            }
            if (user.objectNum == 2){
                g.drawImage(playerPaperA, (int)user.xpos, user.ypos, user.width, user.height, null);
                shiftPaper.play();
            }
            if (user.objectNum == 3){
                g.drawImage(playerScissorsA, (int)user.xpos, user.ypos, user.width, user.height, null);
                shiftScissors.play();
            }
        }


        //user death
        if (user.isAlive == false){
            g.drawImage(blob2, (int)user.xpos, user.ypos , user.width, user.height, null);
        }

        if (startScreen == true) {
            g.drawImage(StartingScreen, 0, 0, null);
        }

        if (youLost == true){
            g.drawImage(LostScreen, 0,0,null);
            loseSound.play();
        }



        g.setFont(new Font("TimesRoman", Font.PLAIN,30));
        g.drawString("Enemies Killed:   " + enemyCount, 750, 50);

        if (user.isAlive == false){
            backingSong.stop();
        }





        g.dispose();
        bufferStrategy.show();
    }

    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            checkIntersections();
            moveThings();  //move all the game objects

            render();  // paint the graphics
            pause(20); // sleep for 10 ms
            count ++;

            if (count > 100){

            }
        }
    }

    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("RunJumpSlide");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //*******************************************************************************
    //  The Required 3 Keyboard Methods
    //  You need to have all 3 even if you aren't going to use them all
    //*******************************************************************************

    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed



        //direction
        if ((keyCode == 68) && (user.isBouncing == false)) {
            user.right = true;
        }//Right
        if ((keyCode == 83 )&& (user.isBouncing == false)) {
            user.down = true;
        }//Down
        if ((keyCode == 65) && (user.isBouncing == false)) {
            user.left = true;
        }//Left
        if ((keyCode == 87) && (user.isBouncing == false)){
            user.up = true;
        }//Up

        //object changes
        if (keyCode == 48){
            user.objectNum = 0;
        }//blob
        if (keyCode == 49){
            user.objectNum = 1;
        }//Rock
        if (keyCode == 50){
            user.objectNum = 2;
        }//paper
        if (keyCode == 51){
            user.objectNum = 3;
        }//Scissor



    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 68) {
            user.right = false;
        }
        if (keyCode == 83) {
            user.down = false;
        }
        if (keyCode == 65) {
            user.left = false;
        }
        if (keyCode == 87) {
            user.up = false;
        }

        if (keyCode == 32){
            startScreen = false;
        }

        //object changes
        if (keyCode == 48){
            user.objectNum = 0;
        }
        if (keyCode == 49){
            user.objectNum = 1;
        }
        if (keyCode == 50){
            user.objectNum = 2;
        }
        if (keyCode == 51){
            user.objectNum = 3;
        }



    }//keyReleased()

    public void keyTyped(KeyEvent event) {
        // handles a press of a character key  (any key that can be printed but not keys like SHIFT)
        // we won't be using this method but the definition needs to be in your program
    }//keyTyped()


}//class
