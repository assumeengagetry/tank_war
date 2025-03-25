import java.awt.Graphics;
import java.awt.Image;

public class EnermyTank extends origin_object {
    private Direction direction; 
    private int speed = 1; 
    private Image image; 

    public EnermyTank(int x, int y, Direction direction) {
        super(x, y, 50, 50, pictures.enermy1TankUp); 
        this.direction = direction;
    }

    @Override
    public void update() {
        
        switch (direction) {
            case UP:
                y -= speed;
                image = pictures.enermy1TankUp; 
                break;
            case DOWN:
                y += speed;
                image = pictures.enermy1TankDown; 
                break;
            case LEFT:
                x -= speed;
                image = pictures.enermy1TankLeft; 
                break;
            case RIGHT:
                x += speed;
                image = pictures.enermy1TankRight; 
                break;
        }

        
        if (x <= 0 || x >= 800 - width) { 
            direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
        }
        if (y <= 0 || y >= 600 - height) { 
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null); 
    }
}
