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
        super(x, y, 8, 8, pictures.tankMissile);  // 使用导弹图片，稍微小一些
        this.speed = 12;         // 导弹速度比坦克快
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

        // 处理边界碰撞，实现光线反射效果
        boolean bounced = false;
        if (x <= 0 || x >= 800 - width) {
            x = oldX;         // 碰撞时恢复到原位置
            // 实现水平反射
            direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            bounced = true;
        }
        if (y <= 0 || y >= 600 - height) {
            y = oldY;
            // 实现垂直反射
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
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
     * 3. 基于对象类型的不同反应
     * 
     * @param other 发生碰撞的另一个游戏对象
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isCollidable() || !isAlive) return;
        
        // 与发射者不碰撞
        if (other == tankOwner) return;
        
        // 与草地碰撞：穿透但摧毁植被（在Grass类中处理）
        if (other instanceof Grass) {
            // 导弹继续飞行，不消失（穿透效果）
            return;
        }
        
        // 与河水碰撞：穿透不受影响
        if (other instanceof Water) {
            // 导弹穿透河水，不消失
            return;
        }
        
        // 与墙体碰撞：反射或消失
        if (other instanceof Wall) {
            Wall wall = (Wall) other;
            if (wall.isBreakable()) {
                // 与可破坏墙体碰撞后消失
                isAlive = false;
            } else {
                // 与不可破坏墙体碰撞后反射
                handleWallReflection(wall);
            }
            return;
        }
        
        // 与其他对象碰撞都会消失
        isAlive = false;
    }
    
    /**
     * 处理与墙体的反射
     * 实现光线反射物理效果
     */
    private void handleWallReflection(Wall wall) {
        // 简单的反射逻辑：根据导弹位置和墙体位置确定反射方向
        int missileCenter = x + width / 2;
        int wallCenter = wall.x + wall.width / 2;
        
        if (Math.abs(missileCenter - wallCenter) > Math.abs((y + height / 2) - (wall.y + wall.height / 2))) {
            // 水平反射
            direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
        } else {
            // 垂直反射
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
        }
        
        maxBounces--;
        if (maxBounces <= 0) {
            isAlive = false;
        }
    }

    /**
     * 绘制导弹
     * 展示了条件渲染和增强的视觉效果
     * 
     * @param g Graphics对象，用于绘制
     */
    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;
        
        // 绘制导弹主体
        g.drawImage(image, x, y, width, height, null);
        
        // 添加尾迹效果（根据方向绘制）
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255, 255, 0, 100));  // 半透明黄色
        
        int trailLength = 6;
        switch (direction) {
            case UP -> g2d.fillOval(x, y + height, width, trailLength);
            case DOWN -> g2d.fillOval(x, y - trailLength, width, trailLength);
            case LEFT -> g2d.fillOval(x + width, y, trailLength, height);
            case RIGHT -> g2d.fillOval(x - trailLength, y, trailLength, height);
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
    
    /**
     * 获取导弹当前的飞行方向
     */
    public Direction getDirection() {
        return direction;
    }
    
    /**
     * 获取剩余反弹次数
     */
    public int getRemainingBounces() {
        return maxBounces;
    }
}

