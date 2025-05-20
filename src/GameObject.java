/**
 * GameObject 类是游戏中所有对象的基类（父类）
 * 这是一个抽象类的例子，展示了OOP中的以下概念：
 * 1. 抽象（Abstraction）：通过abstract关键字定义抽象类和抽象方法
 * 2. 继承（Inheritance）：其他游戏对象（如坦克、子弹）都继承自这个类
 * 3. 封装（Encapsulation）：使用protected修饰符保护属性，通过getter/setter方法访问
 * 4. 多态（Polymorphism）：通过实现Collidable接口实现多态
 */
import java.awt.Graphics; 
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable {
    // 受保护的属性，体现封装原则
    // protected关键字允许子类访问这些属性，同时对其他类隐藏实现细节
    protected int x;  // 对象在游戏中的X坐标
    protected int y;  // 对象在游戏中的Y坐标
    protected int width;  // 对象的宽度
    protected int height;  // 对象的高度
    protected BufferedImage image;  // 对象的图片资源
    protected boolean isAlive;  // 对象是否存活
    protected int speed;  // 对象的移动速度
    protected boolean collidable = true;  // 对象是否可以碰撞

    /**
     * 主构造器 - 创建游戏对象的完整版本
     * 构造器是特殊的方法，用于初始化对象的状态
     * @param x 初始X坐标
     * @param y 初始Y坐标
     * @param width 对象宽度
     * @param height 对象高度
     * @param image 对象图片
     */
    public GameObject(int x, int y, int width, int height, BufferedImage image) {
        // this 关键字引用当前对象的属性，避免参数名和属性名冲突
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isAlive = true;
        this.speed = 0;
    }

    /**
     * 次构造器 - 创建简化版本的游戏对象
     * 这是构造器重载的例子，提供了更简便的对象创建方式
     */
    public GameObject(int x, int y) {
        this(x, y, 0, 0, null); // 调用主构造器
    }

    /**
     * 抽象方法 - 强制子类实现自己的更新逻辑
     * 这是抽象方法的例子，体现了OOP中的抽象概念
     * 每个游戏对象都需要定义自己如何更新状态
     */
    public abstract void update();

    /**
     * 抽象方法 - 强制子类实现自己的绘制逻辑
     * @param g Graphics对象，用于在屏幕上绘制内容
     */
    public abstract void draw(Graphics g);
    
    /**
     * 实现自Collidable接口的方法
     * 这是接口实现的例子，展示了如何实现碰撞检测
     * @return 返回表示对象碰撞边界的Rectangle
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * 处理与其他游戏对象的碰撞
     * 这是多态的例子 - 不同的游戏对象可以有不同的碰撞处理逻辑
     * @param other 发生碰撞的另一个游戏对象
     */
    @Override
    public void handleCollision(GameObject other) {
        // 默认的碰撞处理，子类可以覆盖这个方法
    }

    /**
     * 检查对象是否可以发生碰撞
     * @return 如果对象可碰撞且存活，返回true
     */
    @Override
    public boolean isCollidable() {
        return collidable && isAlive;
    }

    /**
     * Getter和Setter方法
     * 这些方法体现了封装原则，提供了安全的方式来访问和修改对象的属性
     */
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
    public void setCollidable(boolean collidable) { this.collidable = collidable; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSpeed(int speed) { this.speed = speed; }
    public int getSpeed() { return speed; }
}
