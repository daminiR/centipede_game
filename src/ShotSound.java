import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class ShotSound {
    public ShotSound()
    {
        initSound();
    }
    public void initSound()
    {
        File shot = new File("src/sounds/shoot.wav");
        PlaySound(shot);
    }
    public void PlaySound(File Sound)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();
        }catch(Exception e)
        {

        }

    }
}
