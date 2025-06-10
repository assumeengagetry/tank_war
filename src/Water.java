import java.awt.Graphics;

/**
 * Water类代表游戏中的水面，坦克不能穿过
 */
public class Water extends GameObject {
    
    /**
     * 水面构造器
     * @param x X坐标
     * @param y Y坐标
     * @param width 宽度
     * @param height 高度
     */
    public Water(int x, int y, int width, int height) {
        super(x, y, width, height, pictures.water);
    }

    @Override
    public void update() {
        // 水面是静态物体，不需要更新
    }

    @Override
    public void draw(Graphics g) {
        if (!isAlive || image == null) return;
        // 绘制水面图片
        g.drawImage(image, x, y, width, height, null);
    }

    @Override
    public void handleCollision(GameObject other) {
        // 水面不能被穿过，在碰撞检测时处理
    }
}
