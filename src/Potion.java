import java.awt.*;

public class Potion {


    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public Image pic;

    public int objectNum;


    public Potion(int pXpos, int pYpos, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 200;
        height = 200;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);
        objectNum = 0;


    }
}// constructor
