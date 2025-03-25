import java.awt.*;


public class Missile extends origin_object {
    private Direction direction;
    private int speed = 10; 
    private boolean alive = true; 
    private int maxBounces = 5; 
    private Image image; 

    public Missile(int x, int y, Direction direction) {
        super(x, y, 10, 10, pictures.tankMissile); 
        this.direction = direction;
    }

    @Override
    public void update() {
        
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }

        
        boolean bounced = false;
        if (x <= 0) {
            x = 0;
            direction = Direction.RIGHT;
            bounced = true;
        }
        if (x >= 800 - width) { 
            x = 800 - width;
            direction = Direction.LEFT;
            bounced = true;
        }
        if (y <= 0) {
            y = 0;
            direction = Direction.DOWN;
            bounced = true;
        }
        if (y >= 600 - height) { 
            y = 600 - height;
            direction = Direction.UP;
            bounced = true;
        }

        
        if (bounced) {
            if (maxBounces <= 0) {
                alive = false; 
            } else {
                maxBounces--;
            }
        }
    }

    public void handleCollision(origin_object target) {
        
        if (target instanceof Wall) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
            } else {
                direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            }
            maxBounces--;
            if (maxBounces <= 0) {
                alive = false; 
            }
        } 
        
        else if (target instanceof PlayerTank || target instanceof EnermyTank) {
            alive = false; 
        }
    }

    @Override
    public void draw(Graphics g) {
        if (alive) {
            g.drawImage(image, x, y, width, height, null); 
        }
    }

    
    public boolean isAlive() {
        return alive;
    }
}

