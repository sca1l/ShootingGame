import java.net.*;
import java.applet.*;


public class Se{
    AudioClip s;

    public Se(String bgmfilename){
        URL url=Bgm.class.getResource(bgmfilename);
        s = Applet.newAudioClip(url);
    }

    public void loop(){
        if(s!=null)s.loop();
    }
    
    public void play(){
        if(s!=null)s.play();
    }
    
    public void pause(){
        if(s!=null)s.stop();
    }
    
    public void stop(){
        if(s!=null){
            s.stop();
            s = null;
        }
    }
}