import javax.swing.*;
import java.awt.Image;
import java.util.Random;
public class Spider extends Player_der{
    private final int speed = 1;
    private Random randomNum = new Random();
    public   int x = LEFT_WALL + randomNum.nextInt(RIGHT_WALL);
    public   int y = UP_WALL + randomNum.nextInt(DOWN_WALL);
    private int speedX = 2;
    private int speedY = 2;
    private int spiderLoss = 0;
    private static final int RIGHT_WALL = GamePlay.window_width;
    private static final int LEFT_WALL = 1;
    private static final int DOWN_WALL = GamePlay.window_height;
    private static final int UP_WALL = 1;


//    private int x = RIGHT_WALL - LEFT_WALL;
//    private int y = DOWN_WALL - UP_WALL;

    public Spider(int x, int y)
    {

        super(x, y);
        initShots();
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public  void initShots()
    {
        loadImage("src/images/spider.png");
        getImageDimensions();
    }
    public void moveRandomDirection()
    {
        double direction = Math.random() * 2.0 * Math.PI;
        double speed = 7;
        speedX = (int) (speed * Math.cos(direction));
        speedY = (int) (speed * Math.sin(direction));
    }

    public void move() // important for spider
    {


        x += speedX;
        y += speedY;
        if (x >= RIGHT_WALL)
        {
            x = RIGHT_WALL;
            moveRandomDirection();
        }
        if (y > DOWN_WALL)
        {
            y = DOWN_WALL;
            moveRandomDirection();
        }
        if (x <= LEFT_WALL)
        {
            x = LEFT_WALL;
            moveRandomDirection();
        }
        if (y < UP_WALL)
        {
            y = UP_WALL;
            moveRandomDirection();
        }
        if (x == 50  )
    {
        x = 50;
        moveRandomDirection();
    }
        if (x == 250  )
        {
            x = 250;
            moveRandomDirection();
        }
        if (x == 400  )
        {
            x = 400;
            moveRandomDirection();
        }
        if (x == 550  )
        {
            x = 550;
            moveRandomDirection();
        }
        if (x == 750  )
        {
            x = 750;
            moveRandomDirection();
        }
        //some y poition change axis

        if (y == 300)
        {
            y = 300;
            moveRandomDirection();
        }
        if (y == 500  )
        {
            y = 500;
            moveRandomDirection();
        }
        setX(x);
        setY(y);
    }

    public int isVisibleSpider() {
        return spiderLoss;
    }

    public void setVisibleSpider(int loss) {
        this.spiderLoss = loss;
    }

}
