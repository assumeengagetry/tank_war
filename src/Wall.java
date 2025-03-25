import java.awt.Graphics;

public class Wall extends origin_object { 
    
    private int width;
    private int height;

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height, pictures.wall); 
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null); 
    }
}
