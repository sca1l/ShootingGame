import java.awt.*;
import java.util.*;
import java.util.List;

public class Pop implements Runnable{
    private int popTimer = 0;
    private List<ZakoCharData> zakoAry;
    private List<EnemBulletData> zbltAry;
    private Image[] imgs;
    private Thread th = null;
    private boolean playingFlg = false;
    
    public Pop(Image[] imgs, List<ZakoCharData> zakoAry, List<EnemBulletData> zbltAry){
        this.zakoAry = zakoAry;
        this.zbltAry = zbltAry;
        this.imgs = imgs;
        
        th = new Thread(this);
        th.start();
    }
    
    public void popManager(){
        switch(popTimer){
            case 360:
                zakoAry.add(new ZakoCharData(110, -19, imgs[3], imgs, 20,ZakoPattern.UNEUNE_A,zbltAry));
                break;
            case 600:
                zakoAry.add(new ZakoCharData(110, -19, imgs[3], imgs, 10,ZakoPattern.UNEUNE_B,zbltAry));
                break;
            case 620:
                zakoAry.add(new ZakoCharData( 80, -19, imgs[4], imgs, 15,ZakoPattern.TYOKUSEN_A,zbltAry));
                zakoAry.add(new ZakoCharData(180, -19, imgs[4], imgs, 15,ZakoPattern.TYOKUSEN_A,zbltAry));
                break;
            case 920:
                zakoAry.add(new ZakoCharData(180, -19, imgs[4], imgs, 10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 950:
                zakoAry.add(new ZakoCharData(150, -19, imgs[4], imgs, 10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 980:
                zakoAry.add(new ZakoCharData(130, -19, imgs[4], imgs, 10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 1010:
                zakoAry.add(new ZakoCharData(100, -19, imgs[4], imgs, 10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 1040:
                zakoAry.add(new ZakoCharData( 70, -19, imgs[4], imgs,10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 1200:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 1300:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_A,zbltAry));
                break;
            case 1400:
                zakoAry.add(new ZakoCharData(104, -31, imgs[5], imgs,20,ZakoPattern.TEISHI_B,zbltAry));
                zakoAry.add(new ZakoCharData(164, -31, imgs[5], imgs,20,ZakoPattern.TEISHI_B,zbltAry));
                break;
            case 1600:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 1650:
                zakoAry.add(new ZakoCharData( 94, -31, imgs[5], imgs,25,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 1700:
                zakoAry.add(new ZakoCharData(174, -31, imgs[5], imgs,20,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 1750:
                zakoAry.add(new ZakoCharData( 54, -31, imgs[5], imgs,15,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 1800:
                zakoAry.add(new ZakoCharData(214, -31, imgs[5], imgs,10,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 2300:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_C,zbltAry));
                zakoAry.add(new ZakoCharData(110, -19, imgs[3], imgs,10,ZakoPattern.UNEUNE_B,zbltAry));
                break;
            case 2400:
                zakoAry.add(new ZakoCharData( 64, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_B,zbltAry));
                zakoAry.add(new ZakoCharData(204, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_B,zbltAry));
                break;
            case 2550:
                zakoAry.add(new ZakoCharData(100, -19, imgs[4], imgs, 15,ZakoPattern.TYOKUSEN_B,zbltAry));
                zakoAry.add(new ZakoCharData(160, -19, imgs[4], imgs, 15,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 2700:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_B,zbltAry));
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,30,ZakoPattern.TEISHI_C,zbltAry));
                break;
            case 2900:
                zakoAry.add(new ZakoCharData( 60, -19, imgs[3], imgs, 10,ZakoPattern.UNEUNE_B,zbltAry));
                zakoAry.add(new ZakoCharData(160, -19, imgs[3], imgs, 10,ZakoPattern.UNEUNE_B,zbltAry));
                break;
            case 3000:
                zakoAry.add(new ZakoCharData(130, -19, imgs[4], imgs, 15,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 3230:
                zakoAry.add(new ZakoCharData(134, -31, imgs[5], imgs,10,ZakoPattern.TEISHI_B,zbltAry));
                break;
            case 3330:
                zakoAry.add(new ZakoCharData(130, -19, imgs[4], imgs, 10,ZakoPattern.TYOKUSEN_B,zbltAry));
                break;
            case 3430:
                zakoAry.add(new ZakoCharData(110, -19, imgs[3], imgs, 20,ZakoPattern.UNEUNE_B,zbltAry));
                break;
        }
    }
    
    public boolean isDoutyuEnd(){
        if(popTimer >= 3850){
            return true;
        }
        return false;
    }
    
    public void setPlayingFlg(boolean flg){
        this.playingFlg = flg;
    }
    
    public void kill(){
        th = null;
    }
    
    public void reset(){
        popTimer = 0;
        playingFlg = false;
    }
    
    public void run(){
        while(th != null){
            if(playingFlg){
                popManager();
                popTimer++;
            }
            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
            }
        }
    }
    
}