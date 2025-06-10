import java.awt.image.BufferedImage;
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
            playerTankUp = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy3U.gif"));
            playerTankDown = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy3D.gif"));
            playerTankLeft = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy3L.gif"));
            playerTankRight = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy3R.gif"));
            
            // 加载第一类敌方坦克图片
            enermy1TankUp = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy1U.gif"));
            enermy1TankDown = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy1D.gif"));
            enermy1TankLeft = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy1L.gif"));
            enermy1TankRight = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy1R.gif"));
            
            // 加载第二类敌方坦克图片
            enermy2TankUp = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy2U.gif"));
            enermy2TankDown = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy2D.gif"));
            enermy2TankLeft = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy2L.gif"));
            enermy2TankRight = ImageIO.read(pictures.class.getResourceAsStream("/resources/enemy2R.gif"));
            
            // 加载地形和道具图片
            grass = ImageIO.read(pictures.class.getResourceAsStream("/resources/grass.gif"));
            steel = ImageIO.read(pictures.class.getResourceAsStream("/resources/steel.gif"));
            steels = ImageIO.read(pictures.class.getResourceAsStream("/resources/steels.gif"));
            wall = ImageIO.read(pictures.class.getResourceAsStream("/resources/wall.gif"));
            walls = ImageIO.read(pictures.class.getResourceAsStream("/resources/walls.gif"));
            tankMissile = ImageIO.read(pictures.class.getResourceAsStream("/resources/tankmissile.gif"));
            water = ImageIO.read(pictures.class.getResourceAsStream("/resources/water.gif"));

            // 验证图片加载是否成功
            if (playerTankUp == null) {
                throw new IOException("无法加载玩家坦克图片");
            }
        } catch (IOException e) {
            System.err.println("加载游戏资源失败！请确保resources目录中包含所有必需的图片文件。");
            System.err.println("错误详情：" + e.getMessage());
            System.err.println("当前工作目录：" + System.getProperty("user.dir"));
            e.printStackTrace();            System.err.println("图片文件列表：");
            for (String resourcePath : new String[] {
                "/resources/enemy3U.gif", "/resources/enemy3D.gif", "/resources/enemy3L.gif", "/resources/enemy3R.gif",
                "/resources/enemy1U.gif", "/resources/enemy1D.gif", "/resources/enemy1L.gif", "/resources/enemy1R.gif",
                "/resources/grass.gif", "/resources/steel.gif", "/resources/steels.gif",
                "/resources/wall.gif", "/resources/walls.gif", "/resources/tankmissile.gif",
                "/resources/water.gif"
            }) {
                System.err.println(resourcePath + ": " + 
                    (pictures.class.getResourceAsStream(resourcePath) != null ? "找到" : "未找到"));
            }
            
            // 如果资源加载失败，游戏无法继续运行
            throw new RuntimeException("游戏资源加载失败", e);
        }
    }
    
    /**
     * 私有构造器防止实例化
     * 因为这个类只包含静态成员，不需要创建实例
     */
    private pictures() {}  // 防止实例化
}
