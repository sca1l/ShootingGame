import java.awt.*;

class GameUtility{
    public static boolean hitCheck(Collision obj1 , Collision obj2){
        Point p1S = obj1.getCollisionStart();
        Point p1E = obj1.getCollisionEnd();
        Point p2S = obj2.getCollisionStart();
        Point p2E = obj2.getCollisionEnd();

        Rectangle r1 = new Rectangle(p1S.x, p1S.y, p1E.x-p1S.x, p1E.y-p1S.y);
        Rectangle r2 = new Rectangle(p2S.x, p2S.y, p2E.x-p2S.x, p2E.y-p2S.y);

        if(r1.intersects(r2)){
            return true;
        }
        return false;

    }

    public static boolean isArea(Object obj1 , int w,int h){
        Rectangle area = new Rectangle(0,0,w,h);
        Rectangle ob = new Rectangle((int)obj1.getX(),(int)obj1.getY(),obj1.getWidth(),obj1.getHeight());

        if(area.intersects(ob)){
            return true;
        }
        return false;
    }

}