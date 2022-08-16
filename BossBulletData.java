import java.awt.*;

public class BossBulletData extends EnemBulletData{
    private int attackPattern;
    private double angle;
    
    public BossBulletData(double x, double y, Image img, double vx, double vy, double angle, int attackPattern){
        super(x, y, img, vx, vy);
        this.attackPattern = attackPattern;
        this.angle = angle;
    }
    
    public int getAttackPattern(){
        return attackPattern;
    }
    
    public void move(){
        switch(attackPattern){
            case BossCharData.ATTACKNOTHING:
                //kougekinasi
                break;
            case BossCharData.ATTACK_TSUUJOU:
                vx = angleToVx(angle)*3;
                vy = angleToVy(angle)*3;
                break;
            case BossCharData.ATTACK_HOMING:
                homing();
                vx = angleToVx(angle)*4;
                vy = angleToVy(angle)*4;
                break;
            case BossCharData.ATTACK_KIRAI:
                vy+=0.03125;
                break;
            case BossCharData.ATTACK_WINDER:
                vx = angleToVx(angle)*14;
                vy = angleToVy(angle)*14;
                break;
        }
        
        x+=vx;
        y+=vy;
    }
    
    public void homing(){
        double nextAngle = 0;
        
        double jikiAngle = (getJikiAngle(jx,jy,x+getWidth()/2,y-getHeight()/2))/(Math.PI / 180);
        if(jikiAngle>=0){
            nextAngle = angle+1;
        }else{
            nextAngle = angle-1;
        }
        this.angle = nextAngle;
    }
    
    public boolean isNegatable(){
        if(attackPattern == BossCharData.ATTACK_HOMING || attackPattern == BossCharData.ATTACK_KIRAI){
            return true;
        } 
        return false;
    }
}