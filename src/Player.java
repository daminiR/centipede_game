import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player  extends Player_der implements MouseMotionListener, MouseListener {
    private int dx;
    private int dy;
    public int x = 40;
    public int y = 60;
    private Image image;
    protected boolean visible;
    private List<PlayerShots> shots;


    public Player(int x, int y)  {

        super(x, y);
        initPlayer();
    }
    public void initPlayer()
    {
       // addMouseMotionListener(this);
        shots = new ArrayList<>();
        loadImage("src/images/player.png");
        getImageDimensions();
    }

    public void fire() {
        shots.add(new PlayerShots(x - width/2, y + height / 2));
    }
    public List<PlayerShots> getShots() {
        return shots;
    }

//    public Image getImage() {
//
//        return image;
//
//    }
    @Override
    public  void mouseMoved(MouseEvent e) {
        x = (int) e.getPoint().getX();
        y = (int) e.getPoint().getY();
        setX(x);
        setY(y);
    }
    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        fire();
        ShotSound shot = new ShotSound();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
