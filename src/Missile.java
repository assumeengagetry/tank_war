import java.awt.*;

/**
 * Missile（导弹）类代表游戏中的发射物
 * 这个类展示了面向对象编程(OOP)中的多个重要概念：
 * 
 * 1. 继承（Inheritance）：
 *    - 继承自GameObject类，获得基本的游戏对象属性和行为
 *    - 展示了如何扩展父类功能，添加特定的导弹行为
 * 
 * 2. 组合（Composition）：
 *    - 通过tankOwner引用持有对发射者（Tank）的引用
 *    - 展示了对象之间的"属于"关系
 * 
 * 3. 封装（Encapsulation）：
 *    - 私有字段保护内部状态
 *    - 通过方法控制访问和修改
 * 
 * 4. 物理模拟：
 *    - 实现了简单的运动和碰撞物理
 *    - 展示了游戏中的物理系统实现方式
 */
public class Missile extends GameObject {
    // 导弹特有的属性
    private Direction direction;   // 导弹的飞行方向
    private Tank tankOwner;       // 发射此导弹的坦克
    private int maxBounces = 5;   // 最大反弹次数，用于限制导弹的生命周期

    /**
     * 导弹的构造器
     * 展示了如何在继承的同时添加自定义初始化：
     * 1. 调用父类构造器设置基本属性
     * 2. 初始化导弹特有的属性
     * 
     * @param x 初始X坐标
     * @param y 初始Y坐标
     * @param direction 飞行方向
     * @param owner 发射者
     */
    public Missile(int x, int y, Direction direction, Tank owner) {
        super(x, y, 10, 10, pictures.tankMissile); 
        this.speed = 10;         // 导弹速度比坦克快
        this.direction = direction;
        this.tankOwner = owner;
    }

    /**
     * 更新导弹状态
     * 这个方法展示了：
     * 1. 运动物理的实现
     * 2. 碰撞检测和处理
     * 3. 游戏对象生命周期管理
     */
    @Override
    public void update() {
        if (!isAlive) return;

        // 根据方向更新位置，展示了switch表达式的现代用法
        int oldX = x, oldY = y;  // 保存旧位置用于碰撞处理
        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }

        // 处理边界碰撞，展示了物理系统的实现
        boolean bounced = false;
        if (x <= 0 || x >= 800 - width) {
            x = oldX;         // 碰撞时恢复到原位置
            bounced = true;
        }
        if (y <= 0 || y >= 600 - height) {
            y = oldY;
            bounced = true;
        }

        // 生命周期管理：超过最大反弹次数后消失
        if (bounced && --maxBounces <= 0) {
            isAlive = false;
        }
    }

    /**
     * 处理与其他游戏对象的碰撞
     * 展示了：
     * 1. 多态的应用 - 不同对象有不同的碰撞处理
     * 2. 对象交互 - 如何处理对象间的碰撞
     * 
     * @param other 发生碰撞的另一个游戏对象
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isCollidable() || !isAlive) return;
        
        // 与除发射者外的任何对象碰撞都会消失
        if (other != tankOwner) {
            isAlive = false;
        }
    }

    /**
     * 绘制导弹
     * 展示了条件渲染 - 只有存活的导弹才会被绘制
     * 
     * @param g Graphics对象，用于绘制
     */
    @Override
    public void draw(Graphics g) {
        if (isAlive) {
            g.drawImage(image, x, y, width, height, null); 
        }
    }

    /**
     * 获取导弹的发射者
     * 这是一个访问器方法的例子，展示了封装原则
     * 
     * @return 发射此导弹的坦克
     */
    public Tank getOwner() {
        return tankOwner;
    }

    /**
     * 获取导弹的发射源
     * 这是一个访问器方法的例子，展示了封装原则
     * 
     * @return 发射此导弹的坦克
     */
    public Tank getSource() {
        return tankOwner;
    }
}

