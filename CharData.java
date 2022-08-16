import java.awt.*;

abstract class CharData implements Runnable, Object, Collision{
    protected double x,y;
    protected Image img;
    protected Thread th;
    protected boolean playingFlg = false;

    public CharData(double x, double y, Image img){
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

    abstract public Point getCollisionStart();
    abstract public Point getCollisionEnd();

    abstract public void move();

    public void draw(Graphics g, Frame f, boolean playingFlg){
        this.playingFlg = playingFlg;
        g.drawImage(getImg(), (int)getX(), (int)getY(), f);
    }

    public void run(){
        while(th != null){
            move();
            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
            }
        }
    }

}