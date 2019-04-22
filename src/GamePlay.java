import java.awt.EventQueue;
import javax.swing.JFrame;

public class GamePlay extends JFrame {
    public static int window_width = 600;
    public static int window_height = 800;
    public static int score = 0;
    public static int LIVES = 4;
    public static int lives = 5; // to start with have 5
    public GamePlay() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setTitle("Centipede Arcade Game");
        setSize(window_width, window_height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GamePlay ex = new GamePlay();
            ex.setVisible(true);
        });




    }
}