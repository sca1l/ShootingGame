import java.awt.*;
import java.util.List;

public class BossCharData extends CharData{
    private boolean isAppearEnd = false;
    private int patternCounter = 0;
    private double vx = 2;
    private double vy = 3;
    private int ax = -1;
    private int ay = -1;
    private int hp;
    private Image[] imgs;
    private List<EnemBulletData> zbltAry;
    
    public static final int ATTACKNOTHING = 0;
    public static final int ATTACK_TSUUJOU = 1;
    public static final int ATTACK_KIRAI = 2;
    public static final int ATTACK_HOMING = 3;
    public static final int ATTACK_BEFORE_WINDER = 4;
    public static final int ATTACK_WINDER = 5;
    public static final int ATTACK_HAKKYOU = 6;
    private int attackMode = ATTACKNOTHING;
    
    private double winderAddAngle = 0;
    private int winderCount = 0;
    private int winderStep = 0;
    
    private int addX;
    private int addY;
    
    public BossCharData(double x, double y, Image img, Image[] imgs, int hp, List<EnemBulletData> zbltAry){
        super(x,y,img);
        this.hp = hp;
        this.imgs = imgs;
        this.zbltAry = zbltAry;
    }
    
    public void attack(){
        addX = getWidth()/2;
        addY = getHeight()/2;
        switch(attackMode){
            case ATTACKNOTHING:
                patternCounter++;
                if(hp>GameFrame.BOSS_HP*4/5 && patternCounter>30){
                    patternCounter = 0;
                    attackMode = ATTACK_TSUUJOU;
                }else if(hp>GameFrame.BOSS_HP*3/5 && patternCounter>60){
                    patternCounter = 0;
                    attackMode = ATTACK_KIRAI;
                }else if(hp>GameFrame.BOSS_HP*2/5 && patternCounter>60){
                    patternCounter = 0;
                    attackMode = ATTACK_HOMING;
                }else if(hp>GameFrame.BOSS_HP*1/5 && patternCounter>60){
                    patternCounter = 0;
                    attackMode = ATTACK_BEFORE_WINDER;
                }else if(hp<=GameFrame.BOSS_HP*1/5 && patternCounter>20){
                    patternCounter = 0;
                    attackMode = ATTACK_HAKKYOU;
                }
                break;
            case ATTACK_TSUUJOU:
                attackTsuujou();
                if(hp<GameFrame.BOSS_HP*4/5){
                    patternCounter = 0;
                    attackMode = ATTACKNOTHING;
                }
                break;
            case ATTACK_KIRAI:
                attackKirai();
                if(hp<GameFrame.BOSS_HP*3/5){
                    patternCounter = 0;
                    attackMode = ATTACKNOTHING;
                }
                break;
            case ATTACK_HOMING:
                attackHoming();
                if(hp<GameFrame.BOSS_HP*2/5){
                    patternCounter = 0;
                    attackMode = ATTACKNOTHING;
                }
                break;
            case ATTACK_BEFORE_WINDER:
                if(patternCounter > 120){
                    patternCounter = 0;
                    attackMode = ATTACK_WINDER;
                    winderCount = 0;
                }
                break;
            case ATTACK_WINDER:
                attackWinder();
                break;
            case ATTACK_HAKKYOU:
                attackTsuujou();
                attackKirai();
                break;
        }
    }
    
    public void attackTsuujou(){
        synchronized(zbltAry){
            if(patternCounter%20 == 0){
                for(int i=0; i<6; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0, 144+4.5+i*12 ,ATTACK_TSUUJOU));
                }
            }else if(patternCounter%20 == 5){
                for(int i=0; i<3; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0, 108+4.5+i*12 ,ATTACK_TSUUJOU));
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0, 216+4.5+i*12 ,ATTACK_TSUUJOU));
                }
            }else if(patternCounter%20 == 10){
                for(int i=0; i<3; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0,  72+4.5+i*12 ,ATTACK_TSUUJOU));
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0, 252+4.5+i*12 ,ATTACK_TSUUJOU));
                }
            }else if(patternCounter%20 == 15){
                for(int i=0; i<3; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0,  36+4.5+i*12 ,ATTACK_TSUUJOU));
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[6], 0, 0, 288+4.5+i*12 ,ATTACK_TSUUJOU));
                }
            }
        }
    }
    
    public void attackKirai(){
        synchronized(zbltAry){
            if(patternCounter>=50){
                zbltAry.add(new BossBulletData(getX()+addX-100, getY()+addY, imgs[6], 0, 0, 180 ,ATTACK_TSUUJOU));
                zbltAry.add(new BossBulletData(getX()+addX+100, getY()+addY, imgs[6], 0, 0, 180 ,ATTACK_TSUUJOU));
            }
            if(patternCounter%15 == 0){
                for(int i=0; i<3; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[8], -i/4.0-0.4, 1.5, 0 ,ATTACK_KIRAI));
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[8],  i/4.0+0.4, 1.5, 0 ,ATTACK_KIRAI));
                }
            }
        }
    }
    
    public void attackHoming(){
        synchronized(zbltAry){
            if(patternCounter%50 == 0){
                for(int i=0; i<16; i++){
                    zbltAry.add(new BossBulletData(getX()+addX, getY()+addY, imgs[8], 0, 0, 0+i*22.5 ,ATTACK_HOMING));
                }
            }
        }
    }
    
    public void attackWinder(){
        synchronized(zbltAry){
            for(int i=0; i<5; i++){
                zbltAry.add(new BossBulletData(getX()+addX+50, getY()+addY, imgs[6], 0, 0, 145+i*35+winderAddAngle ,ATTACK_WINDER));
                zbltAry.add(new BossBulletData(getX()+addX-50, getY()+addY, imgs[6], 0, 0, 215-i*35+winderAddAngle ,ATTACK_WINDER));
            }
        }
        switch(winderStep){
            case 0:
                if(winderCount >= 40){
                    winderCount = 0;
                    winderStep = 1;
                }
                break;
            case 1:
                winderAddAngle -= 0.5;
                if(winderAddAngle < -25){
                    winderCount = 0;
                    winderStep = 2;
                }
                break;
            case 2:
                winderAddAngle += 0.5;
                if(winderAddAngle > 25){
                    winderCount = 0;
                    winderStep = 3;
                }
                break;
            case 3:
                winderAddAngle -= 0.5;
                if(winderAddAngle < 0){
                    winderCount = 0;
                    winderStep = 4;
                }
                break;
            case 4:
                if(winderCount > 40){
                    winderCount = 0;
                    attackMode = ATTACKNOTHING;
                    winderStep = 0;
                }
                break;
        }
        winderCount++;
    }

    public Point getCollisionStart(){
        return new Point((int)getX(), (int)getY());
    }
    public Point getCollisionEnd(){
        return new Point((int)getX()+getWidth(), (int)getY()+getHeight());
    }
    
    public void decrementHp(){
        hp-=1;
    }
    
    public boolean isDead(){
        if(hp<=0){
            th = null;
            return true;
        }
        return false;
    }
    
    public void move(){
        attack();
        
        if(!isAppearEnd){
            x+=4.5;
            vy-=0.05;
            if(vy>0){
                y-=vy;
            }else{
            vy = 0;
                isAppearEnd = true;
            }
        }else{
            if(attackMode == ATTACK_BEFORE_WINDER){
                if(getX()+addX > 151){
                    x-=0.5;
                }else if(getX()+addX < 149){
                    x+=0.5;
                }
            }else if(attackMode == ATTACK_WINDER){
                vx = 2;
                vy = 0;
            }else{
                if(Math.abs(vx)>2){
                    ax=ax*-1;
                }
                vx += 0.0625*ax;
                x+=vx;
                if(attackMode == ATTACK_HAKKYOU){
                    if(Math.abs(vy)>1.5){
                        ay=ay*-1;
                    }
                    vy += 0.0625*ay;
                    y+=vy;
                }
            }
        }
        
        patternCounter++;
    }
    
    public void kill(){
        th = null;
    }
}
