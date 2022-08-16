import java.awt.*;

public class ZakoBulletData extends EnemBulletData{
    public static final int JIKINERAI = 0;
    public static final int BARAMAKI = 1;
    
    private int kidou;
    
    public ZakoBulletData(double x, double y, Image img, double vx, double vy, int kidou){
        super(x, y, img, vx, vy);
        this.kidou = kidou;
    }
    
    public void move(){
        if(jikiSetted && !onece && kidou == JIKINERAI){
            double zx = getX();
            double zy = getY();
            vx = jikiToVx(jx,jy,zx,zy)*3;
            vy = jikiToVy(jx,jy,zx,zy)*3;
            onece = true;
        }
        setX(getX()+vx);
        setY(getY()+vy);
    }
    
    public boolean isNegatable(){
        return false;
    }
}
