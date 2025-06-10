import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

/**
 * panel类是游戏的核心类，负责游戏循环、渲染和输入处理
 * 这个类展示了以下重要的游戏开发和OOP概念：
 * 
 * 1. 游戏架构：
 *    - 游戏循环（Game Loop）的实现
 *    - 对象管理和更新
 *    - 碰撞检测系统
 * 
 * 2. GUI编程：
 *    - 继承JPanel实现自定义绘制
 *    - 实现KeyListener处理键盘输入
 *    - 使用Graphics2D进行高级绘制
 * 
 * 3. 并发控制：
 *    - 使用CopyOnWriteArrayList避免并发修改异常
 *    - Swing线程安全的考虑
 * 
 * 4. 设计模式：
 *    - 单例模式（通过静态工厂方法）
 *    - 观察者模式（事件监听）
 *    - 游戏对象管理器模式
 */
public class panel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static final int GAME_WIDTH = 1024;   // 游戏窗口宽度
    private static final int GAME_HEIGHT = 768;  // 游戏窗口高度
      // 使用CopyOnWriteArrayList实现线程安全的游戏对象管理
    private final CopyOnWriteArrayList<GameObject> gameObjects;
    private transient PlayerTank playerTank;    // transient表示不序列化此字段
    private int score = 0;                      // 游戏得分
    private boolean gameOver = false;           // 游戏结束标志

    /**
     * 静态工厂方法，创建游戏面板实例
     * 展示了工厂方法设计模式的应用
     */
    public static panel createGamePanel() {
        panel p = new panel();
        p.setupPanel();
        return p;
    }
    
    /**
     * 私有构造器，防止直接实例化
     * 这是单例模式的一部分
     */
    private panel() {
        gameObjects = new CopyOnWriteArrayList<>();
        GameObject.setPanel(this);
        System.out.println("游戏面板已创建！"); // 调试输出
    }
    
    /**
     * 初始化面板设置
     * 展示了Swing的线程安全处理：
     * 1. 使用SwingUtilities.invokeLater确保在EDT中执行
     * 2. 正确设置组件属性
     */
    private void setupPanel() {
        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocus();
            addKeyListener(this);
            initializeGame();
        });
    }

    /**
     * 初始化游戏内容
     * 展示了游戏对象的创建和管理：
     * 1. 创建玩家坦克
     * 2. 创建敌方坦克
     * 3. 创建游戏场景
     */
    private void initializeGame() {
        // 创建玩家坦克
        playerTank = new PlayerTank(GAME_WIDTH / 2, GAME_HEIGHT - 100, Direction.UP, pictures.playerTankUp);
        gameObjects.add(playerTank);
        
        // 创建敌方坦克
        for (int i = 0; i < 3; i++) {
            // 随机选择敌方坦克类型（1号或2号坦克）
            boolean isType1 = Math.random() < 0.5;
            Direction dir = Direction.DOWN;
            EnermyTank enemyTank = new EnermyTank(100 + i * 200, 400, dir);
            gameObjects.add(enemyTank);
        }

        // 创建围墙
        createWalls();
    }

    /**
     * 创建游戏场景中的墙体
     * 展示了：
     * 1. 游戏关卡设计
     * 2. 使用循环创建重复元素
     * 3. 不同类型墙体的放置
     */    private void createWalls() {
        // 边界的大小
        final int CELL_SIZE = 50;

        // 创建边界墙（不可破坏）
        for (int x = 0; x < GAME_WIDTH; x += CELL_SIZE) {
            gameObjects.add(new Wall(x, 0, CELL_SIZE, CELL_SIZE, false));
            gameObjects.add(new Wall(x, GAME_HEIGHT - CELL_SIZE, CELL_SIZE, CELL_SIZE, false));
        }
        for (int y = CELL_SIZE; y < GAME_HEIGHT - CELL_SIZE; y += CELL_SIZE) {
            gameObjects.add(new Wall(0, y, CELL_SIZE, CELL_SIZE, false));
            gameObjects.add(new Wall(GAME_WIDTH - CELL_SIZE, y, CELL_SIZE, CELL_SIZE, false));
        }

        // 创建地图中的水面
        gameObjects.add(new Water(200, 300, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Water(250, 300, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Water(300, 300, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Water(600, 400, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Water(650, 400, CELL_SIZE, CELL_SIZE));

        // 创建草地
        gameObjects.add(new Grass(400, 200, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Grass(450, 200, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Grass(500, 200, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Grass(100, 500, CELL_SIZE, CELL_SIZE));
        gameObjects.add(new Grass(150, 500, CELL_SIZE, CELL_SIZE));

        // 创建可破坏的墙
        gameObjects.add(new Wall(200, 200, CELL_SIZE, CELL_SIZE, true));
        gameObjects.add(new Wall(250, 200, CELL_SIZE, CELL_SIZE, true));
        gameObjects.add(new Wall(400, 300, CELL_SIZE, CELL_SIZE, true));
        gameObjects.add(new Wall(400, 350, CELL_SIZE, CELL_SIZE, true));
        gameObjects.add(new Wall(700, 500, CELL_SIZE, CELL_SIZE, true));
    }

    /**
     * 启动游戏循环
     * 展示了基本的游戏循环实现：
     * 1. 使用Swing Timer实现固定频率更新
     * 2. 更新游戏状态
     * 3. 检测碰撞
     * 4. 重绘画面
     */    public void startGame() {
        System.out.println("游戏开始！"); // 调试输出
        Timer timer = new Timer(10, e -> { // 加快更新频率
            updateGame();
            checkCollisions();
            repaint();
        });
        timer.start();
    }

    /**
     * 更新游戏状态
     * 展示了游戏逻辑更新的各个方面：
     * 1. 清理无效对象
     * 2. 更新所有游戏对象
     * 3. 检查游戏结束条件
     * 4. 维持敌人数量
     */
    private void updateGame() {
        if (gameOver) return;

        // 更新所有游戏对象并清理死亡对象
        gameObjects.forEach(GameObject::update);
        gameObjects.removeIf(obj -> !obj.isAlive());        // 检查玩家状态
        if (!playerTank.isAlive()) {
            gameOver = true;
            return;
        }

        // 检查并维持敌方坦克数量
        long enemyCount = gameObjects.stream()
                .filter(obj -> obj instanceof EnermyTank && obj.isAlive())
                .count();
        
        if (enemyCount < 3) {
            spawnNewEnemyTank();
        }

        // 如果击败了所有敌人，生成新一波敌人
        if (enemyCount == 0) {
            score += 1000; // 完成关卡奖励
            // 创建新的敌人
            for (int i = 0; i < 3; i++) {
                spawnNewEnemyTank();
            }
        }
    }

    /**
     * 碰撞检测系统
     * 展示了：
     * 1. 两层循环的碰撞检测算法
     * 2. 使用Rectangle进行相交测试
     * 3. 触发对象的碰撞响应
     */
    private void checkCollisions() {
        if (gameOver) return;

        for (GameObject obj1 : gameObjects) {
            if (!(obj1 instanceof Collidable) || !obj1.isAlive()) continue;

            for (GameObject obj2 : gameObjects) {
                if (obj1 == obj2 || !(obj2 instanceof Collidable) || !obj2.isAlive()) continue;

                if (((Collidable)obj1).getBounds().intersects(((Collidable)obj2).getBounds())) {
                    ((Collidable)obj1).handleCollision(obj2);
                }
            }
        }
    }

    /**
     * 生成新的敌方坦克
     * 展示了随机数生成和敌人坦克生成逻辑
     */    private void spawnNewEnemyTank() {
        // 在顶部随机位置生成新的敌方坦克
        int x = 100 + (int)(Math.random() * (GAME_WIDTH - 200));
        Direction[] directions = {Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        Direction randomDirection = directions[(int)(Math.random() * directions.length)];
        
        // 使用正确的方向初始化敌方坦克
        EnermyTank enemyTank = new EnermyTank(x, 50, randomDirection);
        gameObjects.add(enemyTank);
    }

    /**
     * 绘制组件
     * 展示了高级的图形绘制技术：
     * 1. 使用Graphics2D进行绘制
     * 2. 实现缩放和偏移
     * 3. 分层渲染（游戏对象和UI）
     */    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 设置渲染品质
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 绘制背景
        g2d.setColor(new Color(33, 33, 33));
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // 绘制所有游戏对象
        gameObjects.forEach(obj -> obj.draw(g2d));

        // 绘制UI
        drawUI(g2d);

        // 如果游戏结束，绘制结束画面
        if (gameOver) {
            drawGameOver(g2d);
        }
    }    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        
        // 绘制分数和生命值
        g.drawString("分数: " + score, 10, 30);
        g.drawString("生命: " + playerTank.getLives(), 10, 60);
    }

    private void drawGameOver(Graphics g) {
        // 创建半透明遮罩
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // 绘制游戏结束文本
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 48));
        String gameOverText = "游戏结束";
        String scoreText = "最终分数: " + score;
        String restartText = "按 R 键重新开始";

        FontMetrics fm = g2d.getFontMetrics();
        int x = (GAME_WIDTH - fm.stringWidth(gameOverText)) / 2;
        int y = GAME_HEIGHT / 2 - 30;

        g2d.drawString(gameOverText, x, y);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 24));
        fm = g2d.getFontMetrics();
        x = (GAME_WIDTH - fm.stringWidth(scoreText)) / 2;
        g2d.drawString(scoreText, x, y + 50);
        x = (GAME_WIDTH - fm.stringWidth(restartText)) / 2;
        g2d.drawString(restartText, x, y + 100);
    }

    /**
     * 键盘按下事件处理
     * 展示了事件处理系统：
     * 1. 游戏控制（重启、全屏）
     * 2. 玩家输入处理
     * 3. 事件委托
     */    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                resetGame();
            }
            return;
        }
        System.out.println("键盘按下事件：" + e.getKeyCode());
        if (playerTank != null) {
            playerTank.keyHandlePresss(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && playerTank != null) {
            System.out.println("键盘释放事件：" + e.getKeyCode());
            playerTank.keyHandleRelease(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} // 不需要实现

    private void resetGame() {
        gameObjects.clear();
        gameOver = false;
        score = 0;
        initializeGame();
    }

    public void addScore(int points) {
        score += points;
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    /**
     * 获取游戏对象列表
     * @return 游戏中所有对象的列表
     */
    public static CopyOnWriteArrayList<GameObject> getGameObjects() {
        return panel.createGamePanel().gameObjects;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GAME_WIDTH, GAME_HEIGHT);
    }
}