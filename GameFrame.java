import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

public class GameFrame extends AnimationFrame implements Runnable, MouseListener, MouseMotionListener, WindowListener, KeyListener{
    public static final int FRAME_W = 300;
    public static final int FRAME_H = 400;
    private Thread th = null;

    public static final int GAME_OP = 0;
    public static final int GAME_PLAY = 1;
    public static final int GAME_PAUSE = 2;
    public static final int GAME_OVER = 3;
    public static final int GAME_CLEAR = 4;
    public static final int GAME_ED = 5;
    private int gameStatus = GAME_OP;
    
    public static final int BOSS_HP = 1549;
    
    private Point startDrag, startPos;
    private Image[] imgs = new Image[11];
    private Image dummy;
    private boolean keySpace = false;
    private boolean shotFlg = true;
    private JikiCharData player = null;
    private List<JikiBulletData> bltAry = new ArrayList<JikiBulletData>();
    private List<EnemBulletData> zbltAry = new ArrayList<EnemBulletData>();
    private List<Star> strAry = new ArrayList<Star>();
    private List<ZakoCharData> zakoAry = new ArrayList<ZakoCharData>();
    private BossCharData boss = null;
    private Se shotSE,deadSE = null;
    private Bgm doutyuuBGM,opBGM,bossBGM = null;
    private int score = 0;
    private Pop pop = null;
    private boolean bossAppeared = false;
    private boolean bossExploaded = false;
    private int showScoreStep = 0;
    private float flashAlpha = 0.0625f;
    
    private Iterator<Star> itStr;
    private Iterator<ZakoCharData> itZako;
    private Iterator<JikiBulletData> itBlt;
    private Iterator<EnemBulletData> itZblt;

    public GameFrame(String title){
        super(title, FRAME_W, FRAME_H);

        setResizable(false);
        setUndecorated(true);
        addWindowListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setSize(FRAME_W,FRAME_H);
        setVisible(true);
        
        for(int i=0; i<imgs.length; i++){
            imgs[i] = getToolkit().getImage("rsc/"+i+".png");
        }
        dummy = getToolkit().getImage("");
        
        reset();
        
        th = new Thread(this);
        th.start();
    }
    
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        startDrag = getScreenLocation(e);
        startPos = getLocation();
    }
    public void mouseReleased(MouseEvent e){}

    public void mouseDragged(MouseEvent e){
        Point cursor = getScreenLocation(e);
        int xdiff = cursor.x - startDrag.x;
        int ydiff = cursor.y - startDrag.y;
        setLocation(startPos.x+xdiff, startPos.y+ydiff);
    }
    public void mouseMoved(MouseEvent e){}
    
    public void windowClosing(WindowEvent e){
        dispose();
        System.exit(0);
    }
    public void windowOpened(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
    
    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        switch(keycode){
            case KeyEvent.VK_ENTER:
                if(gameStatus == GAME_OP){
                    gameStatus = GAME_PLAY;
                    opBGM.stop();
                    doutyuuBGM.loop();
                }else if(gameStatus == GAME_PAUSE){
                    gameStatus = GAME_PLAY;
                }else if(gameStatus == GAME_CLEAR){
                    showScoreStep +=1;
                }else if(gameStatus == GAME_OVER){
                    reset();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(gameStatus == GAME_OP){
                    dispose();
                    System.exit(0);
                }else if(gameStatus != GAME_PAUSE){
                    gameStatus = GAME_PAUSE;
                }else{
                    dispose();
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_UP:
                player.setKeyUp(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setKeyDown(true);
                break;
            case KeyEvent.VK_LEFT:
                player.setKeyLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setKeyRight(true);
                break;
            case KeyEvent.VK_SHIFT:
                player.setKeyShift(true);
                break;
            case KeyEvent.VK_SPACE:
                keySpace = true;
                break;
            case KeyEvent.VK_F1:
                if(gameStatus == GAME_PAUSE){
                    doutyuuBGM.stop();
                    bossBGM.stop();
                    reset();
                }
                break;
        }
    }

    public void        keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        switch(keycode){
            case KeyEvent.VK_UP:
                player.setKeyUp(false);
                break;
            case KeyEvent.VK_DOWN:
                player.setKeyDown(false);
                break;
            case KeyEvent.VK_LEFT:
                player.setKeyLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setKeyRight(false);
                break;
            case KeyEvent.VK_SHIFT:
                player.setKeyShift(false);
                break;
            case KeyEvent.VK_SPACE:
                keySpace = false;
                break;
        }
    }
    public void        keyTyped(KeyEvent e){}
    
    public Point getScreenLocation(MouseEvent e){
        Point p = getLocationOnScreen();
        return new Point(e.getX()+p.x, e.getY()+p.y);
    }

    public void shot(){
        if(shotFlg && gameStatus == GAME_PLAY){
            bltAry.add(new JikiBulletData((int)player.getX()+14, (int)player.getY()+4, imgs[1]));
            shotSE.play();
            shotFlg = false;
        }else{
            shotFlg = true;
        }
    }
    
    public void reset(){
        player = new JikiCharData(134,350,imgs[0],5);
        if(boss!=null) boss.kill();
        boss = null;
        
        itZako = zakoAry.iterator();
        while(itZako.hasNext()){
            ZakoCharData zako = itZako.next();
            zako.setHp(0);
            if(zako.isDead()){
                 itZako.remove();
            }
        }
        
        zakoAry.clear();
        zbltAry.clear();
        bltAry.clear();
        strAry.clear();
        
        shotSE = new Se("rsc/p.wav");
        deadSE = new Se("rsc/dead.wav");
        doutyuuBGM = new Bgm("rsc/doutyuu.wav");
        opBGM = new Bgm("rsc/op.wav");
        bossBGM = new Bgm("rsc/boss.wav");
        opBGM.loop();
        
        if(pop!=null) pop.kill();
        pop = new Pop(imgs,zakoAry,zbltAry);
        pop.reset();
        
        bossAppeared = false;
        bossExploaded = false;
        score = 0;
        showScoreStep = 0;
        
        gameStatus = GAME_OP;
    }

    public void aniPaint(Graphics g){
        switch(gameStatus){
            case GAME_OP:
                paintOp(g);
                break;
            case GAME_PLAY:
                paintPlay(g);
                break;
            case GAME_PAUSE:
                paintPause(g);
                break;
            case GAME_OVER:
                paintOver(g);
                break;
            case GAME_CLEAR:
                paintClear(g);
                break;
            /*case GAME_ED:
                paintEd(g);
                break;*/
        }
    }

    public void paintOp(Graphics g){
        g.setColor(Color.WHITE);
        g.drawImage(imgs[10],0,0,this);
        g.drawString("press ENTER to start", 90 , 200);
    }
    
    public void paintStageBase(Graphics g, boolean playingFlg){
        pop.setPlayingFlg(playingFlg);
        g.setColor(new Color(10,10,10));
        g.fillRect(0,0,FRAME_W,FRAME_H);

        itStr = strAry.iterator();
        while(itStr.hasNext()){
            Star hosi = itStr.next();
            hosi.draw(g, this, playingFlg);
        }

        itZako = zakoAry.iterator();  
        while(itZako.hasNext()){
            ZakoCharData zako = itZako.next();
            zako.draw(g, this, playingFlg);
        }
        
        if(bossAppeared && boss!=null && !bossExploaded){
            boss.draw(g, this, playingFlg);
        }
        
        if(!(gameStatus == GAME_OVER)){
            player.draw(g, this, playingFlg);
        }
        
        itBlt = bltAry.iterator();
        while(itBlt.hasNext()){
            BulletData tama = itBlt.next();
            tama.draw(g, this, playingFlg);
        }
        
        synchronized(zbltAry){
            itZblt = null;
            itZblt = zbltAry.iterator();
            while(itZblt.hasNext()){
                EnemBulletData ztama = itZblt.next();
                ztama.draw(g, this, playingFlg);
            }
        }
    }
    
    public void paintScore(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Score:"+score, 20, 20);
        g.drawString("Player:"+player.getZanki(), 20 , 30);
    }


    public void paintPlay(Graphics g){
        paintStageBase(g, true);
        paintScore(g);
    }

    public void paintPause(Graphics g){
        paintStageBase(g, false);
        paintScore(g);
        
        g.setColor(Color.GRAY);
        g.drawString("PAUSE", 136, 189);
        g.drawString("CONTINUE : ENTER",102,209);
        g.drawString("EXIT : ESC",102, 219);
        g.drawString("RESET : F1" ,102, 229);
        g.setColor(Color.RED);
        g.drawString("PAUSE", 137, 190);
        g.drawString("CONTINUE : ENTER", 103,210);
        g.drawString("EXIT : ESC",103, 220);
        g.drawString("RESET : F1" ,103, 230);
    }
    
    public void paintClear(Graphics g){
        paintStageBase(g, true);
        
        int bocX = (int)(boss.getX() + boss.getWidth()/2);
        int bocY = (int)(boss.getY() + boss.getHeight()/2);
        
        if(!bossExploaded){
            flashAlpha += 0.008f;
        }
        
        if(flashAlpha < 1){
            Graphics2D g2 = (Graphics2D) g;
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, flashAlpha);
            g2.setComposite(composite);
            
            g2.setColor(Color.WHITE);
            g2.fillRect(0,0,FRAME_W,FRAME_H);
        }else if(showScoreStep == 0){
            flashAlpha = 1;
            bossExploaded = true;
            showScoreStep = 1;
        }
        
        g.setColor(Color.WHITE);
        switch(showScoreStep){
            case 6:
                bossBGM.stop();
                reset();
            case 5:
                g.drawString("THANK YOU FOR PLAYING!!", 75, 250);
            case 4:
                g.drawString("SCORE: " + score, 115, 230);
            case 3:
                g.drawString("BONUS: " + player.getZanki()*100, 115, 220);
            case 2:
                g.drawString("ZANKI: " + player.getZanki(), 115, 210);
            case 1:
                g.drawString("=GAME CLEAR=", 102, 190);
                break;
        }
    }
    
    public void paintOver(Graphics g){
        paintStageBase(g, true);
        
        g.setColor(Color.RED);
        g.drawString("GAME OVER...", 115, 190);
        g.drawString("GAME OVER...", 116, 191);
    }

    public void run(){
        while(th!=null){
            process();
            repaint();
            
            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
            }
        }
    }

    public void process(){
        if(keySpace) shot();
        
        if(pop.isDoutyuEnd() && !bossAppeared){
            boss = new BossCharData(-256,100,imgs[7],imgs,BOSS_HP,zbltAry);
            
            doutyuuBGM.stop();
            bossBGM.loop();

            itZako = null;
            itZako = zakoAry.iterator();
            while(itZako.hasNext()){
                ZakoCharData zako = itZako.next();
                zako.setHp(0);
            }
            
            bossAppeared=true;
        }
        
        if(player.getZanki() <= 0 && gameStatus != GAME_OVER && gameStatus != GAME_PAUSE){
            doutyuuBGM.stop();
            bossBGM.stop();
            //overBGM.loop();
            player.setImg(dummy);
            gameStatus = GAME_OVER;
        }
        
        if(boss != null && boss.isDead() && gameStatus != GAME_CLEAR && gameStatus != GAME_PAUSE){
            score += player.getZanki()*100;
            zbltAry.clear();
            gameStatus = GAME_CLEAR;
        }
        
        int rdm = (int)(Math.random() * 100);
        if(gameStatus == GAME_PLAY && rdm < 5) strAry.add(new Star((int)(Math.random() * FRAME_W),-6,imgs[2]));

        itBlt = bltAry.iterator();
        while(itBlt.hasNext()){
            JikiBulletData tama = itBlt.next();
            itZako = zakoAry.iterator();
            while(itZako.hasNext()){
                ZakoCharData zako = itZako.next();
                if(GameUtility.hitCheck(tama,zako)){
                    tama.removeProcessing();
                    itBlt.remove();
                    score++;
                    zako.setHp(zako.getHp()-1);
                    if(zako.isDead()){
                        score += 50;
                    }
                    break;
                }
            }
            if(boss != null && GameUtility.hitCheck(tama,boss)){
                tama.removeProcessing();
                itBlt.remove();
                boss.decrementHp();
                score++;
            }
        }
        
        itZako = zakoAry.iterator();
        while(itZako.hasNext()){
            ZakoCharData zako = itZako.next();
            if(zako.isDead()){
                 itZako.remove();
            }
        }
        
        itZako = zakoAry.iterator();
        while(itZako.hasNext()){
            ZakoCharData zako = itZako.next();
            if(GameUtility.hitCheck(player,zako)){
                if(player.decrementZanki()) deadSE.play();
            }
        }
        
        synchronized(zbltAry){
            itZblt = zbltAry.iterator();
            while(itZblt.hasNext()){
                EnemBulletData ztama = itZblt.next();
                if(!GameUtility.isArea(ztama, FRAME_W, FRAME_H)){
                    ztama.removeProcessing();
                    itZblt.remove();
                }
            }
        
            itZblt = zbltAry.iterator();
            while(itZblt.hasNext()){
                EnemBulletData ztama = itZblt.next();
                if(GameUtility.hitCheck(player,ztama)){
                    if(player.decrementZanki()){
                        ztama.removeProcessing();
                        itZblt.remove();
                        deadSE.play();
                    }
                }
            }
            
            itZblt = zbltAry.iterator();
            while(itZblt.hasNext()){
                EnemBulletData ztama = itZblt.next();
                Point js = player.getCollisionStart();
                Point je = player.getCollisionEnd();
                ztama.setJikiPoint((js.x+je.x)/2, (js.y+je.y)/2);
                itBlt = bltAry.iterator();
                while(itBlt.hasNext()){
                    BulletData tama = itBlt.next();
                    if(ztama.isNegatable() && GameUtility.hitCheck(tama,ztama)){
                        tama.removeProcessing();
                        itBlt.remove();
                        ztama.removeProcessing();
                        itZblt.remove();
                        score++;
                        break;
                    }
                }
            }
        }

        itBlt = bltAry.iterator();
        while(itBlt.hasNext()){
            BulletData tama = itBlt.next();
            if(!GameUtility.isArea(tama, FRAME_W, FRAME_H)){
                tama.removeProcessing();
                itBlt.remove();
            }
        }

        itStr = strAry.iterator();
        while(itStr.hasNext()){
            Star hosi = itStr.next();
            if(!GameUtility.isArea(hosi, FRAME_W, FRAME_H)){
                itStr.remove();
            }
        }
        
    }

}