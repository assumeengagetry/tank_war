import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * pictures类负责管理游戏中所有的图片资源
 * 这个类展示了以下重要的编程概念：
 * 
 * 1. 资源管理：
 *    - 集中管理所有游戏图片资源
 *    - 使用静态字段实现资源共享
 *    - 一次加载，多处使用的效率优化
 * 
 * 2. 静态（Static）：
 *    - 所有字段都是静态的，属于类而不是实例
 *    - 使用静态初始化块进行一次性初始化
 *    - 展示了单例模式的一种变体
 * 
 * 3. 异常处理：
 *    - 使用try-catch块处理IO异常
 *    - 展示了Java异常处理机制
 * 
 * 4. 图片资源：
 *    - 使用BufferedImage存储图片数据
 *    - 通过ImageIO工具类加载图片
 *    - 资源文件的组织和访问
 */
public class pictures {
    // 玩家坦克的四个方向图片
    public static BufferedImage playerTankUp;
    public static BufferedImage playerTankDown;
    public static BufferedImage playerTankLeft;
    public static BufferedImage playerTankRight;
    
    // 第一类敌方坦克的四个方向图片
    public static BufferedImage enermy1TankUp;
    public static BufferedImage enermy1TankDown;
    public static BufferedImage enermy1TankLeft;
    public static BufferedImage enermy1TankRight;
    
    // 第二类敌方坦克的四个方向图片
    public static BufferedImage enermy2TankUp;
    public static BufferedImage enermy2TankDown;
    public static BufferedImage enermy2TankLeft;
    public static BufferedImage enermy2TankRight;
    
    // 地形和道具图片
    public static BufferedImage grass;     // 草地
    public static BufferedImage steel;     // 钢铁（不可破坏的墙）
    public static BufferedImage steels;    // 钢铁墙组
    public static BufferedImage wall;      // 普通墙
    public static BufferedImage walls;     // 墙组
    public static BufferedImage tankMissile; // 坦克导弹
    public static BufferedImage water;     // 水面

    /**
     * 静态初始化块
     * 当类第一次被加载时执行
     * 负责加载所有游戏需要的图片资源
     * 
     * 注意事项：
     * 1. 使用相对路径更好
     * 2. 可以添加加载进度提示
     * 3. 应该验证资源是否成功加载
     */
    static {
        try {
            // 加载玩家坦克图片
            playerTankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3U.gif"));
            playerTankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3D.gif"));
            playerTankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3L.gif"));
            playerTankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy3R.gif"));
            
            // 加载第一类敌方坦克图片
            enermy1TankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1U.gif"));
            enermy1TankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1D.gif"));
            enermy1TankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1L.gif"));
            enermy1TankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy1R.gif"));
            
            // 加载第二类敌方坦克图片
            enermy2TankUp = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2U.gif"));
            enermy2TankDown = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2D.gif"));
            enermy2TankLeft = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2L.gif"));
            enermy2TankRight = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\enemy2R.gif"));
            
            // 加载地形和道具图片
            grass = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\grass.gif"));
            steel = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\steel.gif"));
            steels = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\steels.gif"));
            wall = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\wall.gif"));
            walls = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\walls.gif"));
            tankMissile = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\tankMissile.gif"));
            water = ImageIO.read(new File("E:\\_SCUfuckingme_\\tank_war\\src\\resources\\water.gif"));
        } catch (IOException e) {
            // 资源加载失败时打印堆栈跟踪
            // 在实际应用中，应该添加更好的错误处理
            e.printStackTrace();
        }
    }
    
    /**
     * 私有构造器防止实例化
     * 因为这个类只包含静态成员，不需要创建实例
     */
    private pictures() {}  // 防止实例化
}
