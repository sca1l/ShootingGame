import java.awt.*;

public class JikiCharData extends CharData{
    private boolean keyUp,keyDown,keyLeft,keyRight,keyShift = false;
    private int zanki;
    public static final int mutekijikan = 120;
    private int mutekiTimer=mutekijikan;

    public JikiCharData(double x, double y, Image img, int zanki){
        super(x, y, img);
        this.zanki = zanki;
    }

    public Point getCollisionStart(){
        return new Point((int)getX()+14, (int)getY()+16);
    }
    public Point getCollisionEnd(){
        return new Point((int)getX()+14+4, (int)getY()+16+4);
    }

    public void setKeyUp(boolean keyUp){
        this.keyUp = keyUp;
    }

    public void setKeyDown(boolean keyDown){
        this.keyDown = keyDown;
    }

    public void setKeyLeft(boolean keyLeft){
        this.keyLeft = keyLeft;
    }

    public void setKeyRight(boolean keyRight){
        this.keyRight = keyRight;
    }

    public void setKeyShift(boolean keyShift){
        this.keyShift = keyShift;
    }
    
    public int getZanki(){
        return zanki;
    }
    
    public void setZanki(int zanki){
        this.zanki = zanki;
    }
    
    public boolean decrementZanki(){
        if(mutekiTimer>=mutekijikan && zanki>=1){
            zanki--;
            x=134;
            y=350;
            mutekiTimer=0;
            return true;
        }
        return false;
    }
    
    public void draw(Graphics g, Frame f, boolean playingFlg){
        this.playingFlg = playingFlg;
        if(mutekiTimer>=mutekijikan || mutekiTimer%3==1) g.drawImage(getImg(), (int)getX(), (int)getY(), f);
    }

    public void move(){
        if(keyShift){
            if(keyUp) y--;
            if(keyDown) y++;
            if(keyLeft) x--;
            if(keyRight) x++;
        }else{
            if(keyUp) y-=3;
            if(keyDown) y+=3;
            if(keyLeft) x-=3;
            if(keyRight) x+=3;
        }
        if(x<0) x=0;
        else if(x>300-getWidth()) x=300-getWidth();
        if(y<0) y=0;
        else if(y>400-getHeight()) y=400-getHeight();
        
        mutekiTimer++;
    }

}