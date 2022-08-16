import java.awt.*;

public class ZakoPattern{
    private int patternInt;
    private int time;
    private double startX, startY,x,y;
    private double vy;

    static final int TYOKUSEN_A = 0;
    static final int TYOKUSEN_B = 1;
    static final int UNEUNE_A = 2;
    static final int UNEUNE_B = 3;
    static final int TEISHI_A = 4;
    static final int TEISHI_B = 5;
    static final int TEISHI_C = 6;

    public ZakoPattern(double startX, double startY, int patternInt){
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.patternInt = patternInt;
        
        switch(patternInt){
            case TEISHI_A:
                vy = 4;
                break;
            case TEISHI_B:
                vy = 5;
                break;
            case TEISHI_C:
                vy = 6;
                break;
        }
    }

    public double getNextX(){
        switch(patternInt){
            case TYOKUSEN_A:
                x = startX;
                break;
            case TYOKUSEN_B:
                x = startX;
                break;
            case UNEUNE_A:
                x = startX+uneX(4);
                break;
            case UNEUNE_B:
                x = startX+uneX(5);
                break;
            case TEISHI_A:
                x = startX;
                break;
            case TEISHI_B:
                x = startX;
                break;
            case TEISHI_C:
                x = startX;
                break;
        }
        
        return x;
    }
    
    public double getNextY(){
        switch(patternInt){
            case TYOKUSEN_A:
                y = startY + time*1.5;
                break;
            case TYOKUSEN_B:
                y = startY + time*2;
                break;
            case UNEUNE_A:
                y = startY + time*1;
                break;
            case UNEUNE_B:
                y = startY + time*2;
                break;
            case TEISHI_A:
                vy-=0.1;
                if(vy<0)vy=0;
                y+=vy;
                break;
            case TEISHI_B:
                vy-=0.1;
                if(vy<0)vy=0;
                y+=vy;
                break;
            case TEISHI_C:
                vy-=0.1;
                if(vy<0)vy=0;
                y+=vy;
                break;
        }
        
        return y;
    }
    
    public void countUp(){
        time++;
    }
    
    public int uneX(int keisuu){
        int hugou = (time)/(keisuu*keisuu)%2;        
        if(hugou==0){
            hugou=-1;
        }else if(hugou==1){
            hugou=1;
        }
        int sectionTime = (time)%(keisuu*keisuu);
        int center = keisuu*keisuu/2;
        
        return hugou*((sectionTime-center)*(sectionTime-center)-((keisuu*keisuu)/2)*((keisuu*keisuu)/2));
    }
}