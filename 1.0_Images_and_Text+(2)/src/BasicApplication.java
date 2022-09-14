//Basic Application Template
// with Images and Text
// sets up a window and puts some images and text on the screen.
// 

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicApplication {

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

    public Image astroPic;
    public Image image;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicApplication myApp = new BasicApplication();   //creates a new instance of the app

    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicApplication() {

        setUpGraphics();  //setup the frame and graphics space.  see method below for more

        astroPic = Toolkit.getDefaultToolkit().getImage("sheep.jpg"); //load the picture
        image = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture

        System.out.println("DONE");  //print to the text window
        render();  //trigger the loading of the images with a render( )
        pause(100);//wait a bit for the images to load
        render();  //repaint things so the images appear

    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Drawing Text
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Generic Application with Text, Shapes and an Image.", 250, 200);


        //Drawing Images
        g.drawImage(astroPic, 0, 0, 200, 200, null);


        //Drawing shapes
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(5));
        g.drawLine(50, 400, 250, 400);
        g.drawRect(400, 250, 150, 40);
        g.fillRect(10, 500, 150, 40);
        g.fillArc(10, 250, 155, 155, 45, 45);

        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

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
        System.out.println("DONE");

    }

}