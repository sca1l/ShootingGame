import java.awt.*;

public interface Object{
    public void setX(double x);
    public void setY(double y);
    public void setImg(Image img);
    public double getX();
    public double getY();
    public Image getImg();
    public int getWidth();
    public int getHeight();
    public void draw(Graphics g, Frame f, boolean playingFlg);
}
