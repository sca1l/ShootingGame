import java.awt.*;

abstract class AnimationFrame extends Frame{
    private int frame_w;
    private int frame_h;
    
    Image offs;
    Graphics offg;
    
    public AnimationFrame(String title,int frame_w,int frame_h){
        super(title);
        this.frame_w = frame_w;
        this.frame_h = frame_h;
    }
    
    public void addNotify(){
        super.addNotify();
        offs = createImage(frame_w,frame_h);
        offg = offs.getGraphics();
    }
    
    public void update(Graphics g){
        paint(g);
    }
    
    abstract public void aniPaint(Graphics g);
    
    public void paint(Graphics g){
        offg.setColor(Color.WHITE);
        offg.fillRect(0,0,frame_w,frame_h);
        offg.setColor(Color.BLACK);
        aniPaint(offg);
        g.drawImage(offs,0,0,this);
    }
}