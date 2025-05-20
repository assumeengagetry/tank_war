import java.awt.image.BufferedImage;

/**
 * EnermyTank类代表游戏中的敌方坦克
 * 这个类展示了面向对象编程(OOP)中的以下概念：
 * 
 * 1. 继承与扩展：
 *    - 继承自Tank类，获得基本的坦克功能
 *    - 添加了AI行为，展示了如何扩展父类功能
 * 
 * 2. 封装的实践：
 *    - 使用private final常量定义AI行为参数
 *    - 将AI逻辑封装在update方法中
 * 
 * 3. 多态的应用：
 *    - 重写父类的update方法，实现AI控制逻辑
 *    - 在运行时表现出不同于玩家坦克的行为
 * 
 * 4. 随机性与AI：
 *    - 使用Math.random()实现简单的AI决策
 *    - 展示了如何创建自主行为的游戏对象
 */
public class EnermyTank extends Tank {
    // AI行为参数
    private static final long DIRECTION_CHANGE_INTERVAL = 3000; // 3秒改变一次方向
    private static final double FIRE_PROBABILITY = 0.02;       // 每次更新时开火的概率
    private static final int INITIAL_HP = 100;                 // 初始生命值
    private static final int MOVEMENT_SPEED = 1;               // 移动速度
    
    // 状态追踪
    private long lastDirectionChange = 0;    // 上次改变方向的时间
    private static final long FIRE_COOLDOWN = 1000; // 开火冷却时间（毫秒）

    /**
     * 敌方坦克的构造器
     * @param x 初始X坐标
     * @param y 初始Y坐标
     * @param direction 初始朝向
     */
    public EnermyTank(int x, int y, Direction direction) {
        super(x, y, direction, getInitialImage(direction));
        this.hp = INITIAL_HP;
        this.speed = MOVEMENT_SPEED; // 直接设置速度属性
    }

    /**
     * 根据方向获取对应的坦克图片
     * 这是一个辅助方法，展示了如何封装重复使用的逻辑
     */
    private static BufferedImage getInitialImage(Direction direction) {
        return switch (direction) {
            case UP -> pictures.enermy1TankUp;
            case DOWN -> pictures.enermy1TankDown;
            case LEFT -> pictures.enermy1TankLeft;
            case RIGHT -> pictures.enermy1TankRight;
        };
    }

    /**
     * 更新敌方坦克的状态
     * 这个方法展示了：
     * 1. AI决策逻辑的实现
     * 2. 时间控制的使用
     * 3. 随机行为的生成
     */
    @Override
    public void update() {
        if (!isAlive) return;
        
        // AI决策：随机改变方向
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDirectionChange >= DIRECTION_CHANGE_INTERVAL) {
            changeDirection();
            lastDirectionChange = currentTime;
        }

        // 移动处理
        move(direction);
        
        // AI决策：随机发射子弹
        if (canFire() && Math.random() < FIRE_PROBABILITY) {
            fire();
        }

        // 更新子弹
        super.update();
    }

    /**
     * 改变坦克方向并更新图片
     * 展示了如何将相关的操作封装在一个方法中
     */
    private void changeDirection() {
        Direction[] directions = Direction.values();
        Direction newDirection = directions[(int)(Math.random() * directions.length)];
        direction = newDirection;
        image = getInitialImage(newDirection);
    }

    /**
     * 检查是否可以开火
     * 展示了游戏机制的实现
     */
    private boolean canFire() {
        return System.currentTimeMillis() - lastFireTime >= FIRE_COOLDOWN;
    }

    /**
     * 处理碰撞
     * 展示了多态和类型转换的安全使用
     */
    @Override
    public void handleCollision(GameObject other) {
        if (!isAlive) return;

        if (other instanceof Missile missile) {
            // 使用模式匹配instanceof（Java 16+特性）
            if (missile.getSource() instanceof PlayerTank) {
                hp -= 50; // 受到玩家导弹的伤害
                if (hp <= 0) {
                    isAlive = false;
                }
            }
        } else if (other instanceof Wall) {
            // 撞墙后改变方向
            changeDirection();
        }
    }

    /**
     * 重写toString方法
     * 这是一个良好的编程实践，有助于调试
     */
    @Override
    public String toString() {
        return String.format("EnermyTank[x=%d,y=%d,direction=%s,hp=%d]", 
            x, y, direction, hp);
    }
}
