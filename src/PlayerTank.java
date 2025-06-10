import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * PlayerTank类代表玩家控制的坦克
 * 这个类展示了面向对象编程(OOP)中的多个重要概念
 * 
 * 1. 继承（Inheritance）的实际应用：
 *    - 继承自Tank类，获得基本的坦克功能如移动和射击
 *    - 通过super关键字调用父类的构造器和方法
 * 
 * 2. 封装（Encapsulation）的实践：
 *    - private修饰符保护内部状态（生命值、分数等）
 *    - protected修饰符允许子类访问某些属性
 *    - public方法提供对这些属性的安全访问
 * 
 * 3. 多态（Polymorphism）的运用：
 *    - 重写父类的方法以实现特定行为
 *    - 可以在需要Tank的地方使用PlayerTank
 * 
 * 4. 特化（Specialization）：
 *    - 添加玩家特有的属性（生命数、分数）
 *    - 实现键盘控制等玩家特有的功能
 */
public class PlayerTank extends Tank {
    // 常量定义
    private static final int INITIAL_LIVES = 3;
    private static final int MAX_MISSILES = 5;
    private static final long RELOAD_TIME = 3000; // 3秒重新装填时间
    private static final long FIRE_COOLDOWN = 300; // 开火冷却时间（毫秒）
    
    // 玩家特有的属性
    private int lives;                     // 生命数
    private int score;                     // 得分
    private boolean isInvincible;          // 无敌状态
    private boolean[] keys;                // 按键状态数组
    private int currentMissiles;           // 当前导弹数量
    private boolean isReloading;           // 是否正在重新装填
    private long reloadStartTime;          // 开始重新装填的时间
    
    /**
     * 玩家坦克的构造器
     * @param x 初始X坐标
     * @param y 初始Y坐标
     * @param direction 初始朝向
     * @param image 坦克图片
     */    public PlayerTank(int x, int y, Direction direction, BufferedImage image) {
        super(x, y, direction, image);
        this.lives = INITIAL_LIVES;
        this.score = 0;
        this.isInvincible = false;
        this.keys = new boolean[256];  // 初始化按键状态数组
        this.currentMissiles = MAX_MISSILES;
        this.isReloading = false;
        this.speed = 5; // 设置坦克移动速度
    }
    
    /**
     * 处理按键按下事件
     * 这里展示了如何使用数组来跟踪按键状态
     */    public void keyHandlePresss(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
            System.out.println("键盘按下：" + keyCode); // 调试输出
        }
    }
    
    /**
     * 处理按键释放事件
     */
    public void keyHandleRelease(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
            System.out.println("键盘释放：" + keyCode); // 调试输出
        }
    }
    
    /**
     * 更新玩家坦克的状态
     * 这个方法展示了：
     * 1. 状态管理 - 处理各种游戏状态
     * 2. 输入处理 - 响应玩家的按键输入
     * 3. 行为控制 - 控制坦克的移动和射击
     */
    @Override
    public void update() {
        if (!isAlive) return;
        
        // 检查装填状态
        if (isReloading && System.currentTimeMillis() - reloadStartTime >= RELOAD_TIME) {
            isReloading = false;
            currentMissiles = MAX_MISSILES;
        }
        int oldX = x, oldY = y;
        boolean moved = false;
        // 只允许一次move，按优先级顺序
        if (keys[KeyEvent.VK_UP]) {
            direction = Direction.UP;
            image = pictures.playerTankUp;
            move(Direction.UP);
            moved = true;
        } else if (keys[KeyEvent.VK_DOWN]) {
            direction = Direction.DOWN;
            image = pictures.playerTankDown;
            move(Direction.DOWN);
            moved = true;
        } else if (keys[KeyEvent.VK_LEFT]) {
            direction = Direction.LEFT;
            image = pictures.playerTankLeft;
            move(Direction.LEFT);
            moved = true;
        } else if (keys[KeyEvent.VK_RIGHT]) {
            direction = Direction.RIGHT;
            image = pictures.playerTankRight;
            move(Direction.RIGHT);
            moved = true;
        }
        if (moved && (oldX != x || oldY != y)) {
            System.out.println("玩家坦克移动: " + oldX + "," + oldY + " -> " + x + "," + y);
        }
        // 处理射击
        if (keys[KeyEvent.VK_SPACE] && 
            System.currentTimeMillis() - lastFireTime >= FIRE_COOLDOWN) {
            int missileX = x + width / 2;
            int missileY = y + height / 2;
            Missile missile = new Missile(missileX, missileY, direction, this);
            missiles.add(missile);
            lastFireTime = System.currentTimeMillis();
            System.out.println("发射导弹！位置: " + missileX + "," + missileY);
        }
        missiles.removeIf(missile -> !missile.isAlive());
        missiles.forEach(GameObject::update);
    }
    
    /**
     * 处理碰撞
     * 展示了条件判断和状态更新
     */
    @Override
    public void handleCollision(GameObject other) {
        if (isInvincible) return;
        
        // 根据other的类型处理不同的碰撞情况
        if (other instanceof Missile) {
            // 处理被导弹击中的情况
            loseLife();
        }
    }
    
    /**
     * 增加分数
     * 这是一个玩家特有的功能
     */
    public void addScore(int points) {
        this.score += points;
    }
    
    /**
     * 失去一条生命并处理相关逻辑
     * 展示了游戏机制的实现
     */
    public boolean loseLife() {
        lives--;
        if (lives > 0) {
            isInvincible = true;  // 重生后短暂无敌
            // 重置位置和状态
            return true;
        }
        isAlive = false;
        return false;
    }
    
    // Getter方法
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public boolean isInvincible() { return isInvincible; }
    
    /**
     * 设置无敌状态
     * 这是一个状态管理的例子
     */
    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }
}