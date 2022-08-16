import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;

public class Bgm{
    Clip c;

    public Bgm(String bgmfilename){
        File path = new File(bgmfilename);
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
            AudioFormat af = ais.getFormat();
            DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
            c = (Clip)AudioSystem.getLine(dataLine);
            c.open(ais);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void loop(){
        if(c!=null)c.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void pause(){
        if(c!=null)c.stop();
    }
    
    public void stop(){
        if(c!=null){
            c.stop();
            c = null;
        }
    }
}