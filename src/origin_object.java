import java.awt.Graphics; // Importing Graphics for drawing
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class origin_object {
    
    protected int x; // X coordinate of the object
    protected int y; // Y coordinate of the object
    protected int width; // Width of the object
    protected int height; // Height of the object
    protected BufferedImage image; // Image representing the object
    protected boolean isAlive;


    public origin_object(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isAlive = true; // Default to alive when created
    }
    public origin_object(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void update(); // Abstract method to update the object's state
    public abstract void draw(Graphics g); // Abstract method to draw the object on the screen
    public boolean isAlive() { // Method to check if the object is alive
        return isAlive;
    }
    public Rectangle getbounds() {
        return new Rectangle(x , y , width , height); // Returns a rectangle representing the object's bounds
    }

    public void setAlive(boolean isAlive) { // Method to set the object's alive status
        this.isAlive = isAlive;
    }




}
