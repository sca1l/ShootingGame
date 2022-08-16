import java.awt.*;

abstract public class EnemBulletData extends BulletData{
    protected double vx,vy,jx,jy = 0;
    protected boolean jikiSetted,onece = false;
    
    public EnemBulletData(double x, double y, Image img, double vx, double vy){
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
    }
    
    abstract public void move();
    
    public void setJikiPoint(double jx, double jy){
        this.jx = jx;
        this.jy = jy;
        jikiSetted = true;
    }
    
    public static double jikiToVx(double jx, double jy, double zx, double zy){
        double disx,disy, obl, vx;
        
        disx = jx-zx;
        disy = jy-zy;
        
        obl = Math.sqrt(disx*disx + disy*disy);
        
        vx = disx/obl;
        return vx;
    }
    
    public static double jikiToVy(double jx, double jy, double zx, double zy){
        double disx,disy, obl, vy;
        
        disx = jx-zx;
        disy = jy-zy;
        
        obl = Math.sqrt(disx*disx + disy*disy);
        
        vy = disy/obl;
        return vy;
    }
    
    public static double vToAngle(double vx, double vy){
        return Math.atan2(vx, vy);
    }
    
    public static double angleToVx(double angle){
        double vx = jikiToVx(Math.tan(angle*(Math.PI / 180)), 1, 0, 0);
        if(angle<=90 || angle>270){
            vx = -vx;
        }
        return vx;
    }
    
    public static double angleToVy(double angle){
        double vy = jikiToVy(Math.tan(angle*(Math.PI / 180)), 1, 0, 0);
        if(angle<=90 || angle>270){
            vy = -vy;
        }
        return vy;
    }
    
    public static double getJikiAngle(double jx, double jy, double bx, double by){
        double ja = vToAngle(jikiToVx(jx,jy,bx,by), jikiToVy(jx,jy,bx,by));
        return ja;
    }
    
    abstract public boolean isNegatable();
}
