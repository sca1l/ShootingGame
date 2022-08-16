import java.awt.*;
import java.util.*;
import java.util.List;

public class ZakoCharData extends CharData{
    private int patternInt,hp;
    private ZakoPattern zp = null;
    private int tamaTimer=0;
    private Image[] imgs;
    private List<EnemBulletData> zbltAry;
    private int shotTimer=0;
    private double jx,jy=0;

    public ZakoCharData(double x, double y, Image img, Image[] imgs, int hp, int patternInt, List<EnemBulletData> zbltAry){
        super(x, y, img);
        this.patternInt = patternInt;
        this.hp = hp;
        this.imgs = imgs;
        this.zbltAry = zbltAry;
        
        zp = new ZakoPattern(x, y, patternInt);
    }
    
    public Point getCollisionStart(){
        return new Point((int)getX(), (int)getY());
    }
    public Point getCollisionEnd(){
        return new Point((int)getX()+getWidth(), (int)getY()+getHeight());
    }
    
    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }
    
    public boolean isDead(){
        if(hp<=0){
            th = null;
            return true;
        }
        return false;
    }

    public void jikineraiShot(){
        double zx,zy, disx,disy, obl, vx,vy;
        
        zx = x+(getWidth()/2);
        zy = y+(getHeight()/2);
        
        disx = jx-zx;
        disy = jy-zy;
        
        obl = Math.sqrt(disx*disx + disy*disy);
        
        vx = disx/obl * 3;
        vy = disy/obl * 3;
        
        synchronized(zbltAry){
            zbltAry.add(new ZakoBulletData(zx, zy, imgs[6], vx, vy, ZakoBulletData.JIKINERAI));
        }
    }

    public void baramakiShot(){
        double zx = x+(getWidth()/2);
        double zy = y+(getHeight()/2);
        synchronized(zbltAry){
            if(shotTimer<12){
                zbltAry.add(new ZakoBulletData(zx, zy, imgs[6], -1, -1, ZakoBulletData.BARAMAKI));
            }else if(shotTimer<24){
                zbltAry.add(new ZakoBulletData(zx, zy, imgs[6], -1,  1, ZakoBulletData.BARAMAKI));
            }else if(shotTimer<36){
                zbltAry.add(new ZakoBulletData(zx, zy, imgs[6],  1,  1, ZakoBulletData.BARAMAKI));
            }else if(shotTimer<48){
                zbltAry.add(new ZakoBulletData(zx, zy, imgs[6],  1, -1, ZakoBulletData.BARAMAKI));
            }
        }
    }

    public void move(){
        if((patternInt == ZakoPattern.TEISHI_B || patternInt == ZakoPattern.TEISHI_C) && shotTimer%24==4 && !isDead()){
            jikineraiShot();
        }
        if((patternInt == ZakoPattern.TEISHI_A || patternInt == ZakoPattern.TEISHI_B) && shotTimer%12==6 && !isDead()){
            baramakiShot();
        }
        
        if(zp != null){
            x = zp.getNextX();
            y = zp.getNextY();
        }
        zp.countUp();
        shotTimer++;
        if(shotTimer>=48) shotTimer=0;
    }
}