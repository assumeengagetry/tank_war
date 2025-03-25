import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class pictures {
    public static BufferedImage playerTankUp;
    public static BufferedImage playerTankDown;
    public static BufferedImage playerTankLeft;
    public static BufferedImage playerTankRight;
    public static BufferedImage enermy1TankUp;
    public static BufferedImage enermy1TankDown;
    public static BufferedImage enermy1TankLeft;
    public static BufferedImage enermy1TankRight;
    public static BufferedImage enermy2TankUp;
    public static BufferedImage enermy2TankDown;
    public static BufferedImage enermy2TankLeft;
    public static BufferedImage enermy2TankRight;
    public static BufferedImage grass;
    public static BufferedImage steel;
    public static BufferedImage steels;
    public static BufferedImage wall;
    public static BufferedImage walls;
    public static BufferedImage tankMissile;
    public static BufferedImage water;
    
    static {
        try {
            playerTankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3U.gif"));
            playerTankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3D.gif"));
            playerTankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3L.gif"));
            playerTankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3R.gif"));
            enermy1TankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1U.gif"));
            enermy1TankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1D.gif"));
            enermy1TankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1L.gif"));
            enermy1TankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1R.gif"));
            enermy2TankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2U.gif"));
            enermy2TankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2D.gif"));
            enermy2TankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2L.gif"));
            enermy2TankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2R.gif"));
            grass = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\grass.gif"));
            steel = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\steel.gif"));
            steels = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\steels.gif"));
            wall = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\wall.gif"));
            walls = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\walls.gif"));
            tankMissile = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\tankMissile.gif"));
            water = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\water.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
