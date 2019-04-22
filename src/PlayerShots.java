public class PlayerShots extends Player_der {
    private final int BOARD_HEIGHT = GamePlay.window_height;
    private final int shot_speed = 14;
    public PlayerShots(int x, int y)
    {
        super(x, y);
        initShots();
    }
    public void initShots()
    {
        loadImage("src/images/fire3.png");
        getImageDimensions();
    }
    public void move()
    {
        y -= shot_speed;
        if( y > BOARD_HEIGHT)
        {
            visible = false;
        }
    }
}
