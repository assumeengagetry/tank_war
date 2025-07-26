import java.awt.Graphics;

/**
 * Water类代表游戏中的河水地形
 * 这个类展示了以下游戏设计概念：
 * 
 * 1. 特殊地形机制：
 *    - 坦克接触即死亡的危险地形
 *    - 子弹可以穿透但不受影响
 *    - 展示了不同游戏对象间的交互规则
 * 
 * 2. 碰撞检测的多样性：
 *    - 根据碰撞对象类型执行不同的逻辑
 *    - 选择性伤害系统的实现
 * 
 * 3. 视觉反馈：
 *    - 通过图像传达地形的危险性
 *    - 清晰的视觉提示帮助玩家理解规则
 */
public class Water extends GameObject {
    
    /**
     * 河水地形构造器
     * @param x X坐标
     * @param y Y坐标
     * @param width 宽度
     * @param height 高度
     */
    public Water(int x, int y, int width, int height) {
        super(x, y, width, height, pictures.water);
    }

    /**
     * 更新河水状态
     * 河水是静态地形，不需要更新逻辑
     */
    @Override
    public void update() {
        // 河水是静态地形，无需更新
    }

    /**
     * 处理与河水的碰撞
     * 展示了基于对象类型的选择性碰撞处理：
     * - 坦克：立即死亡
     * - 子弹：无影响，穿透通过
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isCollidable()) return;

        // 只有坦克接触河水才会死亡
        if (other instanceof Tank) {
            other.setAlive(false);  // 坦克接触河水立即死亡
        }
        // 子弹穿透河水，不受影响
        // 其他对象也不受河水影响
    }

    /**
     * 绘制河水
     * 简单绘制河水图像，不需要特殊效果
     */
    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;
        g.drawImage(image, x, y, width, height, null);
    }

    /**
     * 河水永远是可碰撞的
     * 重写此方法确保河水始终参与碰撞检测
     */
    @Override
    public boolean isCollidable() {
        return isAlive;
    }
}