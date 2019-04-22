import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
public class Mushrooms {

    protected int height_head;
    private boolean place;
    ArrayList<Integer> mush_x2 = new ArrayList<Integer>();
    ArrayList<Integer> mush_y2 = new ArrayList<Integer>();
    public int x;
    public int y;
    public int widthFull;
    public int heightFull;
    public int widthHalf;
    public int heightHalf;
    public int widthQuarter;
    public int heightQuarter;
    public Image imageFull;
    public Image imageHalf;
    public Image imageQuarter;
    protected int visible;

    private int midMushroomsGrid = 30;
    private int lowMushroomsGrid = 20;
    private final int noMushroomsGrid = 10;

    /* create grid FOR MUSHROOM PLACEMENT. The + 1 for gridx and Y allow the oth place of grid to be added and shifted*/
    private int gridX = GamePlay.window_width / 20 + 1;
    private int gridY = (GamePlay.window_height / 20) ;
    private int[][] MushroomPlacmentGrid = new int[gridY][gridX];
    public Mushrooms(int x, int y) {
        this.x = x;
        this.y = y;
        //super(x, y);
        initShots();
    }
    public void setX(int mushroom_x_pos)
    {
        x = mushroom_x_pos;
    }
    public void setY(int mushroom_y_pos) {
        y = mushroom_y_pos;
    }
    public void initShots() {
//        rand_pos();
        loadMushroomImages();
        getImageDimensions();

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    protected void loadMushroomImages() {

        ImageIcon i1 = new ImageIcon("src/images/mushroom.png");
        imageFull = i1.getImage();
        ImageIcon i2 = new ImageIcon("src/images/mushroom_less1.png");
        imageHalf = i2.getImage();
        ImageIcon i3 = new ImageIcon("src/images/mushroom_less2.png");
        imageQuarter = i3.getImage();
    }

    protected void getImageDimensions() {

        widthFull = imageFull.getWidth(null);
        heightFull = imageFull.getHeight(null);
        widthHalf= imageHalf.getWidth(null);
        heightHalf = imageHalf.getHeight(null);
        widthQuarter= imageQuarter.getWidth(null);
        heightQuarter = imageQuarter.getHeight(null);
    }
    public Rectangle getBounds() {

        return new Rectangle(x, y, widthFull, heightFull );

    }
    public int isVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }


}
