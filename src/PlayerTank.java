import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class PlayerTank extends origin_object{
    private static final int MAX_MISSILES = 5; 
    private static final int RELOAD_TIME = 5000;
    private static final int GAP_TIME = 100;
    private int  currentMissiles =  MAX_MISSILES;
    private long lastFiredTime = 0; 
    private boolean isReloading = false;
    private long reloadTime = 0;
    private BufferedImage image; 
    private ArrayList<Missile> missiles = new ArrayList<>(); 
    private Direction direction; 
    private boolean[] keys; 

    public PlayerTank(int x, int y) {
        super(x, y, 50, 50, pictures.playerTankUp); 
        
        keys = new boolean[256]; 
}

    public void keyHandlePresss(int keyCode){
        keys[keyCode] = true; 
    }
    public void keyHandleRelease(int keyCode){
        keys[keyCode] = false; 
    }



@Override
    public void update() {
        if(isReloading && System.currentTimeMillis() - reloadTime >= RELOAD_TIME){
            isReloading = false;
            currentMissiles = MAX_MISSILES; 
        }
        if(keys[KeyEvent.VK_UP]) {
            y -= 5; 
            image = pictures.playerTankUp;
            direction = Direction.UP; 
        }
        if(keys[KeyEvent.VK_DOWN]) {
            y += 5;
            image = pictures.playerTankDown;
            direction = Direction.DOWN; 
        }
        if(keys[KeyEvent.VK_LEFT]) {
            x -= 5;
            image = pictures.playerTankLeft;
            direction = Direction.LEFT; 
        }
        if(keys[KeyEvent.VK_RIGHT]) {
            x += 5;
            image = pictures.playerTankRight;
            direction = Direction.RIGHT; 
        }
        if(!isReloading && currentMissiles>= 0 && keys[KeyEvent.VK_SPACE] && System.currentTimeMillis() - lastFiredTime >= GAP_TIME) {
            fireMissile();
            
            lastFiredTime = System.currentTimeMillis(); 
        }
    }


    private void fireMissile(){
        if(currentMissiles > 0){
            missiles.add(new Missile(x+ width/2, y+ height/2, direction) ); 
            currentMissiles--;
            if(currentMissiles == 0){
                isReloading = true;
                reloadTime = System.currentTimeMillis();
            }
        }
    }

@Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null); 
    }


}    