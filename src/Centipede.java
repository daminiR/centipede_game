import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import javax.swing.Timer;

public class Centipede extends Player_der{
    //Centipede variables
    private final int DOT_SIZE = 14;//more like speed
    private final int ALL_DOTS = GamePlay.window_height * GamePlay.window_width / 400;
    public final int DOTS_NUM = 3;
    public final int cent_x[] = new int[DOTS_NUM];
    public final int cent_y[] = new int[DOTS_NUM];
    public  int start_x = 300;
    public  int start_y = 50;
    public Image body;
    public Image head;
    protected int width_body;
    protected int height_body;
    protected int width_head;
    protected int height_head;
    public boolean MOVE_RIGHT = false;
    public boolean leftDirection = false;
    public boolean toggleStartLeftDir = true;
    public int size= 0;
    public  CentipedeNode centi;
    public int centiLoss = 0;
    public static final int RIGHT_WALL = GamePlay.window_width - 40;
    public static final int LEFT_WALL = 0;
    ArrayList<CentipedeNode> bodyCenti = new ArrayList<CentipedeNode>();
    public Centipede(int x, int y, int CentiSize, int sx , int sy)
    {

        super(x, y);
        start_x = sx;
        start_y = sy;
        size = CentiSize;
        //create and start centipede
            for(int i = 0; i <= CentiSize ; i++)
            {
                centi = new CentipedeNode(start_x, start_y);
                bodyCenti.add(centi);
            }

        initShots();
    }
    public void setStartx(int x)
    {
        start_x = x;
    }
    public void setRight(boolean set)
    {
        MOVE_RIGHT = set;
    }
    public  void initShots()
    {
        loadImages();
        saveImageDimensions();
    }

    protected void loadImages() {
        ImageIcon imageIBody = new ImageIcon("src/images/centi_body.png");
        body = imageIBody.getImage();
        ImageIcon imageIHead = new ImageIcon("src/images/centi_head.png");
        head = imageIHead.getImage();
    }

    protected void saveImageDimensions() {
        width_body = body.getWidth(null);
        height_body = body.getHeight(null);
        width_head = body.getWidth(null);
        height_head = body.getHeight(null);
    }
    public Image getBody()
    {
        return body;
    }
    public  Image getHead()
    {
        return head;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width_head, height_head);

    }

    public void move() // edit make it allowable fo rmoving accors mushrroms later when add mushrooms
    {
        if(toggleStartLeftDir)
        {

            bodyCenti.get(0).x -= DOT_SIZE;
        }
        else
        {
            bodyCenti.get(0).x += DOT_SIZE;
        }
//TODO :CHECK WHY TOWARDS THE END TOGGLING HAPPENS RAPDILY IN THE PLAYER AREA
        for (int z = size; z > 0; z--) {
            bodyCenti.get(z).y = bodyCenti.get(z - 1).y;
            bodyCenti.get(z).x = bodyCenti.get(z - 1).x;
        }
        x = bodyCenti.get(0).x;
        y = bodyCenti.get(0).y;

    }
    public int isVisibleCenti() {
        return centiLoss;
    }

    public void setVisibleCenti(int loss) {
        this.centiLoss = loss;
    }

}

