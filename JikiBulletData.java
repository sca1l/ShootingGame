import java.awt.*;

public class JikiBulletData extends BulletData{
    
    public JikiBulletData(double x, double y, Image img){
        super(x, y, img);
    }
    
    public void move(){
        y-=16;
    }

}
