import java.awt.Color;
import java.awt.Graphics;

/**
 * Wall类代表游戏中的墙体障碍物
 * 这个类展示了面向对象编程(OOP)中的多个重要概念：
 * 
 * 1. 继承（Inheritance）：
 *    - 继承自GameObject，获得基本的游戏对象功能
 *    - 展示了如何实现一个静态游戏对象
 * 
 * 2. 封装（Encapsulation）：
 *    - 使用private和final修饰符保护内部状态
 *    - 通过方法提供对状态的受控访问
 * 
 * 3. 状态管理：
 *    - 维护墙体的可破坏性和耐久度
 *    - 通过视觉反馈展示状态变化
 * 
 * 4. 游戏设计模式：
 *    - 展示了如何实现具有不同特性的游戏对象
 *    - 通过视觉效果增强游戏体验
 */
public class Wall extends GameObject {
    // 墙体的核心属性
    private final boolean isBreakable; // final表示一旦创建就不能改变是否可破坏的属性
    private int durability;           // 当前耐久度，可以随着受损而减少

    /**
     * 墙体构造器
     * 展示了：
     * 1. 如何使用父类构造器
     * 2. 如何根据参数初始化对象状态
     * 3. 条件运算符的使用
     * 
     * @param x X坐标
     * @param y Y坐标
     * @param width 宽度
     * @param height 高度
     * @param breakable 是否可以被破坏
     */
    public Wall(int x, int y, int width, int height, boolean breakable) {
        // 根据是否可破坏选择不同的墙体图片
        super(x, y, width, height, breakable ? pictures.wall : pictures.steel);
        this.isBreakable = breakable;
        // 可破坏的墙有3点耐久度，不可破坏的墙设为最大值
        this.durability = breakable ? 3 : Integer.MAX_VALUE;
    }

    /**
     * 更新墙体状态
     * 这是一个空实现，因为墙是静态对象
     * 展示了有时方法可以是空的，这是正常的
     */
    @Override
    public void update() {
        // 墙是静态对象，不需要更新逻辑
    }

    /**
     * 处理碰撞
     * 展示了：
     * 1. 类型检查（instanceof）的使用
     * 2. 条件判断的组合
     * 3. 状态更新的处理
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isCollidable()) return;

        // 只有当碰撞物是导弹且墙是可破坏的时才减少耐久度
        if (other instanceof Missile && isBreakable) {
            durability--;
            if (durability <= 0) {
                setAlive(false);  // 耐久度为0时销毁墙体
            }
        }
    }

    /**
     * 绘制墙体
     * 展示了：
     * 1. 图形渲染技术
     * 2. 如何通过视觉效果反映对象状态
     * 3. Color类的高级用法（使用alpha通道）
     */
    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;

        // 绘制基本的墙体图片
        g.drawImage(image, x, y, width, height, null);
        
        // 为可破坏的墙添加视觉反馈
        if (isBreakable && durability < 3) {
            // 根据耐久度选择不同的叠加颜色
            Color overlayColor = switch (durability) {
                case 2 -> new Color(255, 255, 0, 100);  // 黄色，轻微损坏
                case 1 -> new Color(255, 0, 0, 100);    // 红色，严重损坏
                default -> new Color(0, 0, 0, 0);       // 透明
            };
            
            // 使用半透明颜色覆盖来显示损坏状态
            g.setColor(overlayColor);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * 判断墙是否可被破坏
     * 这是访问器方法的一个例子
     */
    public boolean isBreakable() {
        return isBreakable;
    }

    /**
     * 获取当前耐久度
     * 展示了如何安全地访问对象的内部状态
     */
    public int getDurability() {
        return durability;
    }
}
