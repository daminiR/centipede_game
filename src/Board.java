import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.List;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    public static Timer timer;
    public  Player player;
    private Centipede centipede;
    private Mushrooms trial_mushroom;
    private Spider spider;
    public  int x;
    public int y;
    private final int DELAY =20;
    private boolean delayed = false;
    private  boolean CheckedFlag = false;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private int gridX = GamePlay.window_width / 20 + 1;
    private int gridY = (GamePlay.window_height / 20) ;
    ArrayList<Mushrooms> mushrooms = new ArrayList<Mushrooms>();
    private int[][] MushroomPlacmentGrid = new int[gridY][gridX];
    private int midMushroomsGrid = 30;
    private int lowMushroomsGrid = 20;
    private final int noMushroomsGrid = 10;
    private boolean place;
    private int num_Centi = 0;
    public  boolean STOP_CENTIPEDE = false;
    public  boolean SCORE_CENTI = false;
    public int removeIndex;
    private boolean REMOVE = false;
    ArrayList<Centipede> centList = new ArrayList<Centipede>();
    public Board() {
        initBoard();
    }
    public Player getPlayer()
    {
        return player;
    }
    public  boolean SCORE = false;
    private boolean collison = false;
    public boolean START_NEW_CENTIPEDE = false;
    public void initBoard() {

        setBackground(Color.black);
        setDoubleBuffered(true);
        player = new Player(ICRAFT_X,ICRAFT_Y );
        addMouseMotionListener(player);
        addMouseListener(player);
        centipede = new Centipede(0 , 0, 10, 0, 10);
        trial_mushroom = new Mushrooms(470, 60);
        rand_pos();
        spider = new Spider(0, 0);
        timer = new Timer(DELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(GamePlay.LIVES > 0) {
            drawGame(g, g2d);
        }
        if(GamePlay.LIVES == 0) {

            drawGameOver(g);
        }
    }
    private void drawGame(Graphics g, Graphics2D g2d) {
        int x = player.getX() - (player.width / 2);
        int y = player.getY() - (player.height  / 2);
        g2d.drawImage(player.getImage(), x, y, this);
        List<PlayerShots> shots = player.getShots();
        for (PlayerShots shot : shots)
        {
            g2d.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
        if(centipede != null) {
            drawCentipede(centipede, 0, false, g);
        }
        if (collison == true) {
            for(int k =0 ; k < centList.size(); k++) {
                for (int z = 0; z < centList.get(k).bodyCenti.size(); z++) {
                    g.drawImage(centList.get(k).getHead(), centList.get(k).bodyCenti.get(z).x, centList.get(k).bodyCenti.get(z).y, this);
                }

            }
        }
        drawScore(g2d);
        if(delayed) {
            drawRestart(g2d);
        }
        for (Mushrooms mushroom : mushrooms) {
            if (mushroom.isVisible() == 0) {
                g.drawImage(mushroom.imageFull, mushroom.getX(), mushroom.getY(), this);
            }
            else if(mushroom.isVisible() == 1)
            {
                g.drawImage(mushroom.imageHalf, mushroom.getX(), mushroom.getY(), this);
            }
            else if(mushroom.isVisible() == 2)
            {
                g.drawImage(mushroom.imageQuarter, mushroom.getX(), mushroom.getY(), this);
            }
            else if(mushroom.isVisible() > 3)
            {
                //display nothing! as timer gets update draw from all other for this condition disappears
            }

        }
        g.drawImage(trial_mushroom.imageFull, trial_mushroom.getX(), trial_mushroom.getY(), this);
        if(spider.isVisibleSpider() < 3) {
            g2d.drawImage(spider.getImage(), spider.getX(), spider.getY(), this);
        }
        else if(spider.isVisibleSpider() == 3) {
        }
        drawLives(g2d);
    }

    private void drawGameOver(Graphics g) {

        String over = "Game Over!";
        String score  = "Score " + GamePlay.score;
        Font font = new Font("Helvetica", Font.BOLD, 19);
        FontMetrics fm = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(over, (GamePlay.window_width - fm.stringWidth(over)) / 2, GamePlay.window_height / 2);
        g.drawString(score, (GamePlay.window_width - fm.stringWidth(over)) / 2, (GamePlay.window_height / 2 ) + 20 );
    }
    public void drawCentipede(Centipede tempCenti, int num_Centi, boolean collison, Graphics g)
    {
        for (int z = 0; z < centipede.bodyCenti.size(); z++) {
            if (collison == false) {
                g.drawImage(centipede.getHead(), centipede.bodyCenti.get(z).x, centipede.bodyCenti.get(z).y, this);
            }
        }
    }
    private void drawScore(Graphics2D g) {
        String s;

        g.setFont(new Font("Helvetica", Font.BOLD, 16));
        g.setColor(new Color(192, 0, 0));
        s = "Score: " + GamePlay.score;
        g.drawString(s, 10, 15);

    }
    private void drawLives(Graphics2D g) {
        String s;
        g.setFont(new Font("Helvetica", Font.BOLD, 16));
        g.setColor(new Color(192, 0, 0));
        s = "Lives: " + GamePlay.LIVES;
        g.drawString(s, GamePlay.window_width - 80, 15);

    }
    private void drawRestart(Graphics2D g) {
        g.setFont(new Font("Helvetica", Font.BOLD, 16));
        g.setColor(new Color(192, 0, 0));
        g.drawString("Restarting", 100, 15);

    }
    private void mushroomGenerate(int startMushrooms,  int stopMushrooms, double disperseIntensity )
    {
        for(int i = startMushrooms; i< gridY - stopMushrooms ; i++) {
            for (int k = 1; k < gridX - 1  ; k++) {
                place = Math.random() < disperseIntensity;
                if(MushroomPlacmentGrid[i - 1][k - 1] != 1 && MushroomPlacmentGrid[i - 1][k + 1] != 1) {
                    if (place  ) {
                        MushroomPlacmentGrid[i][k] = 1;
                    } else {
                        MushroomPlacmentGrid[i][k] = 0;
                    }
                }
                else {
                    MushroomPlacmentGrid[i][k] = 0;
                }
            }
        }
    }
    private void rand_pos() // better ran algoirthm change later
    {

        mushroomGenerate(3, midMushroomsGrid, 0.6);
        mushroomGenerate(gridY - midMushroomsGrid, lowMushroomsGrid, 0.3);
        mushroomGenerate(gridY - lowMushroomsGrid, noMushroomsGrid, 0.05);
        int mush = 1;
        for (int i = 1; i < gridY; i++) {
            for (int k = 1; k < gridX; k++) {
                if (MushroomPlacmentGrid[i][k] == 1) {
                    mushrooms.add(new Mushrooms(k*20 - 20, i*20));
                }
            }
        }

    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, this.getWidth(), this.getHeight());
    }
    public void checkShotsMushroomCollision()
    {

        //check for collision between
        List<PlayerShots> ms = player.getShots();
        for( PlayerShots shot : ms) {
            Rectangle rectShot = shot.getBounds();
            for (Mushrooms mushroom : mushrooms) {
                Rectangle rectMush = mushroom.getBounds();
                if (rectShot.intersects(rectMush)) {
                    shot.setVisible(false);
                    if( mushroom.isVisible() == 0)
                    {
                        mushroom.setVisible(1);
                        CheckedFlag = false;
                    }
                    else if(mushroom.isVisible() == 1)
                    {
                        mushroom.setVisible(2);
                        CheckedFlag = false;
                    }
                    else if(mushroom.isVisible() == 2)
                    {
                        mushroom.setVisible(3);
                        CheckedFlag = false;
                    }

                }
            }
        }
    }
    public void checkSpiderPlayerCollision()
    {
        Rectangle rectPlayer = player.getBounds();
        Rectangle rectSpider = spider.getBounds();
        if (rectPlayer.intersects(rectSpider))
        {
            if(GamePlay.LIVES > 0) {
                GamePlay.LIVES--;
                restartLevel();
                if(spider.isVisibleSpider() == 3)
                {
                    spider.setVisibleSpider(0);
                }
            }

            delayed = true;
        }
        //TODO add scoring to first centiped.
    }
   public void checkCentipedePlayerCollision()
    {
        Rectangle rectPlayer = player.getBounds();
        for (int i = 0; i < centList.size(); i++) {
            Rectangle rectCenti =  centList.get(i).bodyCenti.get(0).getBounds();
            if(rectPlayer.intersects(rectCenti))
            {
                if(GamePlay.LIVES > 0) {
                    GamePlay.LIVES--;
                    restartLevel();
                    centList.clear();
                    centList.add(new Centipede(0 , 0, 10, 0, 10));
                }
            }
        }
        if(centipede != null) {
            Rectangle rectCentipede = centipede.getBounds();
            if(rectPlayer.intersects(rectCentipede))
            {
                if(GamePlay.LIVES > 0) {
                    GamePlay.LIVES--;
                    STOP_CENTIPEDE = true;
                    centipede = null;
                    restartLevel();
                    START_NEW_CENTIPEDE = true;
                    centList.add(new Centipede(0 , 0, 10, 0, 10));
                }
            }
        }

    }
    public void checkShotsSpiderCollision()
    {

        //check for collision between
        List<PlayerShots> ms = player.getShots();
        Rectangle rectSpider = spider.getBounds();
        for( PlayerShots shot : ms) {

            Rectangle rectShot = shot.getBounds();
            if (rectShot.intersects(rectSpider)) {
                shot.setVisible(false);
                if(spider.isVisibleSpider() == 0)
                {
                    spider.setVisibleSpider(1);
                    GamePlay.score += 100;

                }
                else if (spider.isVisibleSpider() == 1)
                {
                    spider.setVisibleSpider(3);
                    GamePlay.score += 600;
                }

            }

        }
    }
//    public void checkShotsCentipedeCollision()
//    {
//        //check for collision between
//        List<PlayerShots> ms = player.getShots();
//        for( PlayerShots shot : ms) {
//            Rectangle rectShot = shot.getBounds();
//            for (int i = 0; i < centipede.bodyCenti.size(); i++) {
//                Rectangle rectNode = centipede.bodyCenti.get(i).getBounds();
//                if (rectShot.intersects(rectNode)) {
//                    collison = true;
//                    shot.setVisible(false);
//                    if(i != centipede.size && i != 1 && centipede.size != 0 && i != 0 && centipede.size != 1) {
//                        if (centList.size() == 0) {
//
//                            centList.add(new Centipede(centipede.bodyCenti.get(i + 1).x,
//                                    centipede.bodyCenti.get(i + 1).y, centipede.bodyCenti.size() - i - 1,
//                                    centipede.bodyCenti.get(i).x, centipede.bodyCenti.get(i).y));
//                            centList.get(num_Centi).toggleStartLeftDir = false;
//                            centList.add(new Centipede(centipede.bodyCenti.get(i + 1).x,
//                                    centipede.bodyCenti.get(i + 1).y, i - 1,
//                                    centipede.bodyCenti.get(i).x, centipede.bodyCenti.get(i).y));
//                        }
//                    }
//                        if (centipede.isVisibleCenti() == 0) {
//                            centipede.setVisibleCenti(1);
//                        } else if (centipede.isVisibleCenti() == 1) {
//                            centipede.setVisibleCenti(2);
//                        }
//                    //TODO: change this to frelfect 2 times whatever. othersiw this will go ober cetni2 as cent1
//                    num_Centi++;
//                    STOP_CENTIPEDE = true;
//                }
//            }
//        }
//
//    }


public void checkShotsCentipedeCollision()
    {
        //check for collision between
        List<PlayerShots> ms = player.getShots();
        for( PlayerShots shot : ms) {
            Rectangle rectShot = shot.getBounds();
            for (int i = 0; i < centipede.bodyCenti.size(); i++) {
                Rectangle rectNode = centipede.bodyCenti.get(i).getBounds();
                if (rectShot.intersects(rectNode)) {
                    collison = true;
                    if(SCORE == true) {
                        GamePlay.score += 2;
                        SCORE = false;
                    }
                    SCORE = true;
                    shot.setVisible(false);
                    if(i != centipede.size && i != 1 && centipede.size != 0 && i != 0 && centipede.size != 1) {
                        if (centList.size() == 0) {

                            centList.add(new Centipede(centipede.bodyCenti.get(i + 1).x,
                                    centipede.bodyCenti.get(i + 1).y, centipede.bodyCenti.size() - i - 1,
                                    centipede.bodyCenti.get(i).x, centipede.bodyCenti.get(i).y));
                            centList.get(0).toggleStartLeftDir = false;
                            centList.add(new Centipede(centipede.bodyCenti.get(i + 1).x,
                                    centipede.bodyCenti.get(i + 1).y, i - 1,
                                    centipede.bodyCenti.get(i).x, centipede.bodyCenti.get(i).y));
                        }
                    }
                    if (centipede.isVisibleCenti() == 0) {
                        centipede.setVisibleCenti(1);
                    } else if (centipede.isVisibleCenti() == 1) {
                        centipede.setVisibleCenti(2);
                    }
                    if(i != 0) {
                        num_Centi++;
                        STOP_CENTIPEDE = true;
                    }
                    else
                    {
                        System.out.println("this is a problem");
                        centipede.bodyCenti.remove(centipede.bodyCenti.size() - 1 );
                    }
                    //TODO: change this to frelfect 2 times whatever. othersiw this will go ober cetni2 as cent1

                }
            }
        }

    }
    public void checkChildrenShot(Centipede centipedeChild)
    {
            //check for collision between
            List<PlayerShots> ms = player.getShots();
            for (PlayerShots shot : ms) {
                Rectangle rectShot = shot.getBounds();

                for (int i = 0; i < centipedeChild.bodyCenti.size(); i++) {
                    Rectangle rectNode = centipedeChild.bodyCenti.get(i).getBounds();
                    if (rectShot.intersects(rectNode)) {
                        collison = true;
                        if(SCORE == true) {
                            GamePlay.score += 2;
                            SCORE = false;
                        }
                        SCORE = true;
                        shot.setVisible(false);

                        if (i != centipedeChild.size && i != 1 && centipedeChild.size != 0 && i != 0 && centipedeChild.size != 1) {
                            if(centipedeChild.toggleStartLeftDir ==  false) {
                                centList.add(new Centipede(centipedeChild.bodyCenti.get(i + 1).x,
                                        centipedeChild.bodyCenti.get(i + 1).y, centipedeChild.bodyCenti.size() - i - 1,
                                        centipedeChild.bodyCenti.get(i).x, centipedeChild.bodyCenti.get(i).y));

                                centList.add(new Centipede(centipedeChild.bodyCenti.get(i + 1).x,
                                        centipedeChild.bodyCenti.get(i + 1).y, i - 1,
                                        centipedeChild.bodyCenti.get(i).x, centipedeChild.bodyCenti.get(i).y));
                                centList.get(centList.size() - 1).toggleStartLeftDir = true;
                            }
                            else
                            {
                                centList.add(new Centipede(centipedeChild.bodyCenti.get(i + 1).x,
                                        centipedeChild.bodyCenti.get(i + 1).y, centipedeChild.bodyCenti.size() - i - 1,
                                        centipedeChild.bodyCenti.get(i).x, centipedeChild.bodyCenti.get(i).y));
                                centList.get(centList.size() - 1).toggleStartLeftDir = false;
                                centList.add(new Centipede(centipedeChild.bodyCenti.get(i + 1).x,
                                        centipedeChild.bodyCenti.get(i + 1).y, i - 1,
                                        centipedeChild.bodyCenti.get(i).x, centipedeChild.bodyCenti.get(i).y));
                            }
                        }

                        REMOVE = true;
                        if (centipedeChild.isVisibleCenti() == 0) {
                            centipedeChild.setVisibleCenti(1);
                        } else if (centipedeChild.isVisibleCenti() == 1) {
                            centipedeChild.setVisibleCenti(2);
                        }
                        if(centList.size() == 1)
                        {
                            SCORE_CENTI = true;
                        }

                        if(centipedeChild.bodyCenti.size() == 1)
                        {
                            GamePlay.score += 5;
                        }
                    }
                }
            }
    }
    public void restartLevel()
    {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Mushrooms mushroom : mushrooms) {
            if(mushroom.isVisible() == 1)
            {
                GamePlay.score +=10;
                mushroom.setVisible(0);
                CheckedFlag = false;
            }
            else if(mushroom.isVisible() == 2)
            {
                GamePlay.score +=10;
                mushroom.setVisible(0);
                CheckedFlag = false;
            }
        }
        //TODO : to test this code you need to check how centipeded kills and then see if this works!!!
        if (spider.isVisibleSpider() == 3)
        {
            spider.setVisibleSpider(0);
        }
        Random randomx = new Random();
        Random randomy = new Random();
        spider.x = randomx.nextInt(GamePlay.window_width);
        spider.x = randomy.nextInt(GamePlay.window_width);

        player.x = 10;
        player.y = 10;
    }
    public void checkCentipedesMushroomCollision(Centipede centipede_check)
    {

        Rectangle rectCent = centipede_check.getBounds();
        List<Mushrooms> ms = mushrooms;
        for (int i =0; i < ms.size(); i++) {
            Rectangle rectMush = ms.get(i).getBounds();
            if (rectCent.intersects(rectMush)) {
                rectMush.width = -1;//HACKY but cetipede can only go downwards so they will neevr reinteract
                rectMush.height = -1;
                centipede_check.bodyCenti.get(0).y += 10;
                centipede_check.toggleStartLeftDir = !centipede_check.toggleStartLeftDir;
            }
        }
        if(centipede_check.x >= centipede_check.RIGHT_WALL) {
            if(centipede_check.bodyCenti.get(0).y < 15 * GamePlay.window_height / 20 ) {
                centipede_check.bodyCenti.get(0).y += 10;
            }
            centipede_check.toggleStartLeftDir = !centipede_check.toggleStartLeftDir;


        }
        if(centipede_check.x <= centipede_check.LEFT_WALL) {
            if (centipede_check.x < centipede_check.LEFT_WALL) {
                if (centipede_check.bodyCenti.get(0).y < 15 * GamePlay.window_height / 20) {
                    centipede_check.bodyCenti.get(0).y += 10;
                }

                centipede_check.toggleStartLeftDir = !centipede_check.toggleStartLeftDir;
            }
        }
    }
    public void centipedeaction()
    {
        if(collison) {

            for(int i = 0; i < centList.size(); i++) {

                if(centList.get(i).bodyCenti.size() != 0) {
                    centList.get(i).move();
                    checkCentipedesMushroomCollision(centList.get(i));
                    checkChildrenShot(centList.get(i));
                    if(REMOVE)
                    {
                            centList.remove(i);
                            REMOVE = false;
                    }
                }
            }
            if(SCORE_CENTI == true)
            {
                SCORE_CENTI = false;
                GamePlay.score += 600;
                centList.add(new Centipede(0 , 0, 10, 300, 50));
            }
            if(START_NEW_CENTIPEDE)
            {
                centList.add(new Centipede(0 , 0, 10, 300, 50));
                START_NEW_CENTIPEDE = true;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        centipedeaction();
        if(!STOP_CENTIPEDE)
        {
            centipede.move();
            checkCentipedesMushroomCollision(centipede);
            checkShotsCentipedeCollision();
            if(collison)
            {
                centipede = null;
            }

        }
        spider.move();
        updateMissiles();
        updateMushrooms();
        checkShotsMushroomCollision();
        checkSpiderPlayerCollision();
        checkShotsSpiderCollision();
        checkCentipedePlayerCollision();
        repaint();
    }
    public void updateMissiles()
    {
        List<PlayerShots> shots = player.getShots();

        for (int i = 0; i < shots.size(); i++) {

            PlayerShots shots2 = shots.get(i);

            if (shots2.isVisible()) {

                shots2.move();
            } else {

                shots.remove(i);
            }
        }
    }
    public void updateMushrooms()
    {
        if(mushrooms.isEmpty())
        {//#find a way to game over
        }

        for (int i = 0; i < mushrooms.size(); i++) {

            Mushrooms mushroom_temp = mushrooms.get(i);
            if (mushroom_temp.isVisible() == 1 && CheckedFlag == false) {
                //add 2 points shown in doc
                GamePlay.score += 1;
                CheckedFlag = true;

            }
            if (mushroom_temp.isVisible() == 2 && CheckedFlag == false) {
                //add 2 points shown in doc
                GamePlay.score += 1;
                CheckedFlag = true;

            }
            else if (mushroom_temp.isVisible() == 3) {
                //remove the mushroom
                GamePlay.score += 5;
                mushrooms.remove(i);
                // update update points

            }
        }
    }
//    public  void updateCentipede()
//    {
//        if(mushrooms.isEmpty())
//        {//#find a way to game over
//        }
//
//        for (int i = 0; i < centipede.bodyCenti.size(); i++) {
//
//            CentipedeNode centi_temp = centipede.bodyCenti.get(i);
//            if (centi_temp.isVisibleCenti() == 1) {
//                //add 2 points shown in doc
//                GamePlay.score += 2;
//                centipede.setVisibleCenti(0);
//
//            }
//            if (mushroom_temp.isVisible() == 2 && CheckedFlag == false) {
//                //add 2 points shown in doc
//                GamePlay.score += 2;
//                CheckedFlag = true;
//
//            }
//            if (mushroom_temp.isVisible() == 2 && CheckedFlag == false) {
//                //add 2 points shown in doc
//                GamePlay.score += 2;
//                CheckedFlag = true;
//
//            }
//            else if (mushroom_temp.isVisible() == 3) {
//                //remove the mushroom
//                GamePlay.score += 5;
//                mushrooms.remove(i);
//                // update update points
//
//            }
//        }
//    }

}