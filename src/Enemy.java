
    import java.awt.*;

    public class Enemy {


        public int xpos;                //the x position
        public int ypos;                //the y position
        public int width;
        public int height;
        public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
        public int dx;                    //the speed of the hero in the x direction
        public int dy;                    //the speed of the hero in the y direction
        public Rectangle rec;
        public Image pic;

        public int enemyNum;//the object



        public Enemy(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

            xpos = pXpos;
            ypos = pYpos;
            width = 200;
            height = 200;
            dx = dxParameter;
            dy = dyParameter;
            pic = picParameter;
            isAlive = true;
            rec = new Rectangle(10, 200, width, height);
            enemyNum = 0;


        } // constructor

        public void Emove() {
            xpos = xpos + dx;
            ypos = ypos + dy;


            rec = new Rectangle(xpos, ypos, width, height);


        }
    }
