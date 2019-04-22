import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import javax.swing.Timer;

public class CentipedeNode extends Player_der{
    //Centipede variables
    private final int DOT_SIZE = 12;//more like speed
    private final int ALL_DOTS = GamePlay.window_height * GamePlay.window_width / 400;
    public final int DOTS_NUM = 3;
    private final int dots_spacing = 3;
    public Image body;
    public Image head;
    protected int width_body;
    protected int height_body;
    protected int width_head;
    protected int height_head;
    public int centiLoss = 0;
    public CentipedeNode(int x, int y)
    {
        super(x, y);
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
        return new Rectangle(x, y, 10, 10);
    }
    public int isVisibleCenti() {
        return centiLoss;
    }
    public void setVisibleCenti(int loss) {
        this.centiLoss = loss;
    }
}

