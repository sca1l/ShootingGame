import java.awt.*;

public class Star implements Runnable,Object{
    private double x,y;
    private Image img;
    private Thread th = null;
    protected boolean playingFlg = false;
    
    public Star(double x, double y, Image img){
        this.x = x;
        this.y = y;
        this.img = img;
        
        th = new Thread(this);
        th.start();
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setImg(Image img){
        this.img = img;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Image getImg(){
        return img;
    }

    public int getWidth(){
        return img.getWidth(null);
    }

    public int getHeight(){
        return img.getHeight(null);
    }

    public void run(){
        while(th!=null){
            if(playingFlg) move();
            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
            }
        }
    }

    public void move(){
        y += 1;
    }

    public void draw(Graphics g, Frame f, boolean playingFlg){
        this.playingFlg = playingFlg;
        g.drawImage(getImg(), (int)getX(), (int)getY(), f);
    }

}