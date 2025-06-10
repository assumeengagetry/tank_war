import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * Tank类是游戏中坦克对象的基类
 * 这个类展示了OOP中的以下概念：
 * 1. 继承（Inheritance）：
 *    - 继承自GameObject类，获得了基础的游戏对象功能
 *    - 添加了坦克特有的属性和行为
 * 
 * 2. 多态（Polymorphism）：
 *    - 重写(Override)了父类的方法，提供坦克特有的实现
 *    - PlayerTank和EnemyTank都可以继承这个类，实现不同的行为
 * 
 * 3. 封装（Encapsulation）：
 *    - 将坦克的状态（如方向、生命值）封装在类中
 *    - 提供方法来安全地修改这些状态
 */
public abstract class Tank extends GameObject {
    // 坦克特有的属性，使用protected允许子类访问
    protected Direction direction;  // 坦克的朝向
    protected int hp;              // 坦克的生命值
    protected List<Missile> missiles = new ArrayList<>();  // 坦克发射的导弹列表
    protected long lastFireTime;   // 上次开火时间，用于控制射速

    /**
     * 坦克类的构造器
     * 调用父类（GameObject）的构造器，并初始化坦克特有的属性
     * @param x 初始X坐标
     * @param y 初始Y坐标
     * @param direction 初始朝向
     * @param image 坦克图片
     */
    public Tank(int x, int y, Direction direction, BufferedImage image) {
        super(x, y, image.getWidth(), image.getHeight(), image);
        this.direction = direction;
        this.hp = 100;  // 初始生命值
    }

    /**
     * 重写父类的update方法，实现坦克的更新逻辑
     * 这是方法重写的例子，展示了多态的应用
     */
    @Override
    public void update() {
        // 更新导弹位置
        missiles.removeIf(missile -> !missile.isAlive());
        missiles.forEach(GameObject::update);
    }

    /**
     * 重写父类的draw方法，实现坦克的绘制逻辑
     * @param g Graphics对象，用于绘制
     */    @Override
    public void draw(Graphics g) {
        if (!isAlive || image == null) return;
        
        // 绘制坦克
        g.drawImage(image, x, y, width, height, null);
        
        // 绘制所有存活的导弹
        missiles.forEach(missile -> missile.draw(g));
    }

    /**
     * 处理坦克的碰撞逻辑
     * 这是多态的另一个例子，不同类型的坦克可能有不同的碰撞处理
     * @param other 碰撞的另一个游戏对象
     */
    @Override
    public void handleCollision(GameObject other) {
        // 检查碰撞类型并作出相应处理
        // ...existing code...
    }

    /**
     * 坦克开火的方法
     * 这是坦克特有的行为
     * @return 如果成功开火返回true，否则返回false
     */    protected boolean fire() {
        // 检查开火冷却时间
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime < 300) {  // 开火冷却时间300毫秒
            return false;
        }
        
        // 计算导弹的发射位置（从坦克中心发射）
        int missileX = x + width / 2;
        int missileY = y + height / 2;
        
        // 创建新的导弹
        Missile missile = new Missile(missileX, missileY, direction, this);
        missiles.add(missile);
        lastFireTime = currentTime;
        
        System.out.println("坦克发射导弹：位置(" + missileX + "," + missileY + "), 方向:" + direction);
        return true;
    }

    /**
     * 坦克移动的方法
     * @param direction 移动方向
     */    protected void move(Direction direction) {
        if (!isAlive) return;
        
        // 保存原位置
        int oldX = x;
        int oldY = y;
        
        // 根据方向更新位置
        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }
        
        // 边界检查
        if (x < 0 || x > GameObject.getPanel().getGameWidth() - width ||
            y < 0 || y > GameObject.getPanel().getGameHeight() - height) {
            x = oldX;
            y = oldY;
            System.out.println("边界碰撞检测：坦克碰到边界");
            return;
        }
        
        // 碰撞检测
        panel currentPanel = GameObject.getPanel();
        if (currentPanel != null) {
            Rectangle myBounds = new Rectangle(x, y, width, height);
            
            for (GameObject obj : currentPanel.getGameObjects()) {
                // 跳过自己和已死亡的对象
                if (obj == this || !obj.isAlive() || !obj.isCollidable() || obj instanceof Grass) {
                    continue;
                }
                
                // 检查与其他对象的碰撞
                if ((obj instanceof Wall || obj instanceof Water || obj instanceof Tank) && 
                    myBounds.intersects(obj.getBounds())) {
                    System.out.println("碰撞检测：坦克与" + obj.getClass().getSimpleName() + "发生碰撞");
                    x = oldX;
                    y = oldY;
                    return;
                }
            }
        }
    }
      // 移除旧的碰撞检测方法，因为碰撞检测现在直接在move方法中处理

    // Getter和Setter方法
    // 提供安全的方式来访问和修改坦克的状态
    // ...existing code...
}
