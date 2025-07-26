import java.awt.Graphics;
import java.awt.Color;

/**
 * Grass类代表游戏中的草地地形
 * 这个类展示了以下游戏设计概念：
 * 
 * 1. 可穿透地形：
 *    - 坦克可以通过但子弹会摧毁植被
 *    - 展示了不同交互模式的实现
 * 
 * 2. 状态变化系统：
 *    - 被击中后变为空地
 *    - 视觉状态的动态更新
 * 
 * 3. 植被系统：
 *    - 提供掩护但可被破坏
 *    - 增加战术层次
 */
public class Grass extends GameObject {
    private boolean isDestroyed = false;  // 是否被摧毁
    
    /**
     * 草地地形构造器
     * @param x X坐标
     * @param y Y坐标
     * @param width 宽度
     * @param height 高度
     */
    public Grass(int x, int y, int width, int height) {
        super(x, y, width, height, pictures.grass);
    }

    /**
     * 更新草地状态
     * 草地是静态地形，不需要主动更新
     */
    @Override
    public void update() {
        // 草地是静态地形，无需更新
    }

    /**
     * 处理与草地的碰撞
     * 展示了选择性碰撞响应：
     * - 子弹：摧毁草地但子弹继续飞行（穿透效果）
     * - 坦克：可以通过，不受影响
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isCollidable()) return;

        // 子弹击中草地时，草地被摧毁但子弹继续飞行
        if (other instanceof Missile && !isDestroyed) {
            isDestroyed = true;  // 标记草地为已摧毁
            // 注意：不销毁子弹，让子弹继续飞行（穿透效果）
        }
        // 坦克可以自由通过草地，不受阻挡
    }

    /**
     * 绘制草地
     * 根据是否被摧毁显示不同的视觉效果
     */
    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;
        
        if (!isDestroyed) {
            // 绘制完整的草地
            g.drawImage(image, x, y, width, height, null);
        } else {
            // 绘制被摧毁后的空地（浅灰色背景）
            g.setColor(new Color(139, 69, 19));  // 褐色土地
            g.fillRect(x, y, width, height);
            
            // 添加一些残留植被的视觉效果
            g.setColor(new Color(34, 139, 34, 100));  // 半透明绿色
            g.fillOval(x + 5, y + 5, width - 10, height - 10);
        }
    }

    /**
     * 草地对坦克不构成物理阻挡
     * 重写此方法，使坦克可以通过草地
     */
    @Override
    public boolean isCollidable() {
        // 草地对坦克不构成阻挡，但仍参与碰撞检测以响应子弹
        return isAlive && !isDestroyed;
    }
    
    /**
     * 检查草地是否被摧毁
     * @return true如果草地被摧毁
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}