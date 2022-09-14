import java.awt.*;

public class Player {


    public double xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public double dx;                    //the speed of the hero in the x direction
    public double ddx = .1;
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;

    public int objectNum;
    public boolean isBouncing = false;


    //movement booleans
    public boolean right;
    public boolean down;
    public boolean left;
    public boolean up;


    public Player(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 200;
        height = 200;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle((int)xpos, ypos, width, height);
        objectNum = 0;


    } // constructor

    //move( ) method for a keyboard controlled character
    public void move() {

        if (isBouncing == true) {
            xpos = xpos + dx;
            dx += ddx;
            if (dx > -3) {
                isBouncing = false;
                dx = 10;
            }
        }else {

            if (right) {
                xpos = (int) (xpos + dx);
                if (xpos > 1000 - width) {
                    xpos = 1000 - width;
                }
            }
            if (down) {
                ypos = ypos + dy;
                if (ypos > 700 - height) {
                    ypos = 700 - height;
                }
            }
            if (left) {
                xpos = (int) (xpos - dx);
                if (xpos < 0) {
                    xpos = 0;
                }
            }
            if (up) {
                ypos = ypos - dy;
                if (ypos < 0) {
                    ypos = 0;
                }
            }
        }

        //always put this after you've done all the changing of the xpos and ypos values
        rec = new Rectangle((int)xpos, ypos, width, height);

    }


}

