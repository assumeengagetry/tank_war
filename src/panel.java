import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*; 

public class panel extends JPanel implements KeyListener{
    private PlayerTank playerTank = new PlayerTank(100, 100); 
    private ArrayList<EnermyTank> enermyTanks; 
    private ArrayList<Missile> missiles; 
    Wall wall = new Wall(100, 200, 50, 50); // Assuming the wall covers the entire panel

    public panel() {
        setBackground(Color.BLACK); 

        playerTank = new PlayerTank(100, 100); 
        
        missiles = new ArrayList<>(); 
    
        enermyTanks = new ArrayList<>(); 
        for(int i = 0; i < 3 ; i++){
            enermyTanks.add(new EnermyTank(100 + i * 200,400, Direction.DOWN)); // Create 3 enemy tanks at different positions
        }

        addKeyListener(this);

        setFocusable(true); // Make sure the panel can receive key events
}


    public void startGame() {
    while(true){
            updateGame(); 
            repaint(); 
            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            }
    }
    }
    public void updateGame() {
        playerTank.update();
        for(EnermyTank tank : enermyTanks) {
            tank.update();
        }
        for(int i = missiles.size() - 1 ;i >= 0; i --){
            Missile missile  = missiles.get(i);
            missile.update();
            if(!missile.isAlive()) { 
                missiles.remove(i); 
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        wall.draw(g);
        playerTank.draw(g); 
        for(EnermyTank tank : enermyTanks) {
            tank.draw(g); 
        }
        for(Missile missile : missiles) {
            missile.draw(g); 
        }
    }

    public void keyPressed(KeyEvent e){
        playerTank.keyHandlePresss(e.getKeyCode());

    }
    public void keyReleased(KeyEvent e){
        playerTank.keyHandleRelease(e.getKeyCode());
    }
    public void keyTyped(KeyEvent e){
        // Not used in this implementation
    }
}