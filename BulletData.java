import java.awt.*;

abstract class BulletData implements Runnable, Object, Collision{
    protected double x,y;
    protected Image img;
    protected Thread th;
    protected boolean playingFlg = false;

    public BulletData(double x, double y, Image img){
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

    public Point getCollisionStart(){
        return new Point((int)getX(), (int)getY());
    }
    public Point getCollisionEnd(){
        return new Point((int)getX()+getWidth(), (int)getY()+getHeight());
    }
    
    public void removeProcessing(){
        th = null;
    }

    abstract public void move();

    public void draw(Graphics g, Frame f, boolean playingFlg){
        this.playingFlg = playingFlg;
        g.drawImage(getImg(), (int)getX(), (int)getY(), f);
    }

    public void run(){
        while(th != null){
            if(playingFlg) move();
            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
            }
        }
    }

}