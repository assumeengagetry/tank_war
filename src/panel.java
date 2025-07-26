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
    private static final int GAME_WIDTH = 800;   // 游戏窗口宽度
    private static final int GAME_HEIGHT = 600;  // 游戏窗口高度
    
    // 使用CopyOnWriteArrayList实现线程安全的游戏对象管理
    private final CopyOnWriteArrayList<GameObject> gameObjects;
    private transient PlayerTank playerTank;    // transient表示不序列化此字段
    private int score = 0;                      // 游戏得分
    private boolean gameOver = false;           // 游戏结束标志
    
    // 用于全屏缩放的变量
    private double scaleFactor = 1.0;           // 缩放因子
    private int xOffset = 0;                    // X轴偏移
    private int yOffset = 0;                    // Y轴偏移

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
    }
    
    /**
     * 初始化面板设置
     * 展示了Swing的线程安全处理：
     * 1. 使用SwingUtilities.invokeLater确保在EDT中执行
     * 2. 正确设置组件属性
     */
    private void setupPanel() {
        SwingUtilities.invokeLater(() -> {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
            setFocusable(true);
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
        playerTank = new PlayerTank(100, 100, Direction.UP, pictures.playerTankUp);
        gameObjects.add(playerTank);
        
        // 创建敌方坦克
        for (int i = 0; i < 3; i++) {
            EnermyTank enemyTank = new EnermyTank(100 + i * 200, 400, Direction.DOWN);
            gameObjects.add(enemyTank);
        }

        // 创建地形和障碍物
        createWalls();
        
        // 创建一些装饰性草地（不在游戏对象列表中，只作为背景）
        // 注：这些装饰性草地不会与对象交互，仅作视觉效果
    }

    /**
     * 创建游戏场景中的墙体和地形
     * 展示了：
     * 1. 丰富的游戏关卡设计
     * 2. 多种地形类型的使用
     * 3. 不同类型墙体的放置
     * 4. 战术性地形布局
     */
    private void createWalls() {
        // 创建边界墙（不可破坏的钢墙）
        for (int x = 0; x < GAME_WIDTH; x += 50) {
            gameObjects.add(Wall.createSteelWall(x, 0, 50, 50));
            gameObjects.add(Wall.createSteelWall(x, GAME_HEIGHT - 50, 50, 50));
        }
        for (int y = 50; y < GAME_HEIGHT - 50; y += 50) {
            gameObjects.add(Wall.createSteelWall(0, y, 50, 50));
            gameObjects.add(Wall.createSteelWall(GAME_WIDTH - 50, y, 50, 50));
        }

        // 创建中心区域的砖墙群
        gameObjects.add(Wall.createBrickGroup(200, 150, 100, 100));
        gameObjects.add(Wall.createBrickGroup(500, 150, 100, 100));
        
        // 创建各种类型的钢墙组合
        gameObjects.add(Wall.createSteelGroup(300, 300, 100, 50));
        gameObjects.add(Wall.createSteelCrosswise(150, 350, 50, 50));
        gameObjects.add(Wall.createSteelVertical(600, 280, 50, 100));
        
        // 创建一些单独的砖墙
        gameObjects.add(Wall.createBrickWall(100, 300, 40, 40));
        gameObjects.add(Wall.createBrickWall(680, 200, 40, 40));
        
        // 创建草地区域（提供掉蔽）
        createGrassAreas();
        
        // 创建河水区域（危险地带）
        createWaterAreas();
    }
    
    /**
     * 创建草地区域
     * 提供掉蔽但可被摧毁的地形
     */
    private void createGrassAreas() {
        // 上方草地群
        for (int x = 250; x <= 350; x += 25) {
            for (int y = 80; y <= 120; y += 25) {
                gameObjects.add(new Grass(x, y, 25, 25));
            }
        }
        
        // 左下角草地
        for (int x = 80; x <= 180; x += 30) {
            for (int y = 450; y <= 520; y += 30) {
                gameObjects.add(new Grass(x, y, 30, 30));
            }
        }
        
        // 右下角草地
        for (int x = 600; x <= 700; x += 25) {
            for (int y = 400; y <= 500; y += 25) {
                gameObjects.add(new Grass(x, y, 25, 25));
            }
        }
    }
    
    /**
     * 创建河水区域
     * 危险地带，坦克接触即死
     */
    private void createWaterAreas() {
        // 中央河流
        for (int x = 350; x <= 450; x += 25) {
            gameObjects.add(new Water(x, 200, 25, 25));
            gameObjects.add(new Water(x, 225, 25, 25));
        }
        
        // 左侧水池
        for (int x = 50; x <= 120; x += 25) {
            for (int y = 200; y <= 250; y += 25) {
                gameObjects.add(new Water(x, y, 25, 25));
            }
        }
        
        // 右侧小水池
        gameObjects.add(new Water(650, 350, 50, 50));
        gameObjects.add(new Water(700, 350, 50, 50));
    }

    /**
     * 启动游戏循环
     * 展示了基本的游戏循环实现：
     * 1. 使用Swing Timer实现固定频率更新
     * 2. 更新游戏状态
     * 3. 检测碰撞
     * 4. 重绘画面
     */
    public void startGame() {
        Timer timer = new Timer(16, e -> {
            if (!gameOver) {
                updateGame();
                checkCollisions();
                repaint();
            }
        });
        timer.start();
    }

    /**
     * 更新缩放和偏移值
     * 展示了如何实现全屏自适应：
     * 1. 计算最佳缩放比例
     * 2. 计算居中偏移
     * 3. 保持宽高比
     */
    private void updateScaleAndOffset() {
        // 计算新的缩放比例
        scaleFactor = Math.min(
            getWidth() / (double) GAME_WIDTH,
            getHeight() / (double) GAME_HEIGHT
        );
        
        // 计算居中偏移
        xOffset = (int)((getWidth() - GAME_WIDTH * scaleFactor) / 2);
        yOffset = (int)((getHeight() - GAME_HEIGHT * scaleFactor) / 2);
    }

    /**
     * 碰撞检测系统
     * 展示了：
     * 1. 两层循环的碰撞检测算法
     * 2. 使用Rectangle进行相交测试
     * 3. 触发对象的碰撞响应
     */
    private void checkCollisions() {
        for (GameObject obj1 : gameObjects) {
            if (!obj1.isCollidable()) continue;

            for (GameObject obj2 : gameObjects) {
                if (obj1 == obj2 || !obj2.isCollidable()) continue;

                if (obj1.getBounds().intersects(obj2.getBounds())) {
                    obj1.handleCollision(obj2);
                    obj2.handleCollision(obj1);
                }
            }
        }
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
        // 安全地移除死亡对象，避免空指针
        gameObjects.removeIf(obj -> obj != null && !obj.isAlive());
        
        // 安全地更新所有对象
        for (GameObject obj : gameObjects) {
            if (obj != null && obj.isAlive()) {
                obj.update();
            }
        }

        // 检查玩家坦克状态
        if (playerTank != null && !playerTank.isAlive()) {
            gameOver = true;
        }

        // 检查是否需要生成新的敌方坦克
        long enemyCount = gameObjects.stream()
            .filter(obj -> obj instanceof EnermyTank)
            .count();
            
        if (enemyCount < 3) {
            spawnNewEnemyTank();
        }
    }

    /**
     * 生成新的敌方坦克
     * 展示了随机数生成和敌人坦克生成逻辑
     */
    private void spawnNewEnemyTank() {
        int x = (int) (Math.random() * (GAME_WIDTH - 100)) + 50;
        EnermyTank enemyTank = new EnermyTank(x, 50, Direction.DOWN);
        gameObjects.add(enemyTank);
    }

    /**
     * 绘制组件
     * 展示了高级的图形绘制技术：
     * 1. 使用Graphics2D进行绘制
     * 2. 实现缩放和偏移
     * 3. 分层渲染（游戏对象和UI）
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // 计算缩放和偏移
        updateScaleAndOffset();
        
        // 应用变换
        g2d.translate(xOffset, yOffset);
        g2d.scale(scaleFactor, scaleFactor);
        
        // 清除背景
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        // 安全地绘制所有游戏对象
        for (GameObject obj : gameObjects) {
            if (obj != null && obj.isAlive()) {
                obj.draw(g2d);
            }
        }
        
        // 绘制UI
        drawUI(g2d);
        
        // 还原变换
        g2d.scale(1/scaleFactor, 1/scaleFactor);
        g2d.translate(-xOffset, -yOffset);
    }

    /**
     * 绘制游戏UI
     * 展示了：
     * 1. 文本渲染
     * 2. 字体设置
     * 3. 得分显示
     */
    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);

        if (gameOver) {
            drawGameOver(g);
        }
    }

    /**
     * 绘制游戏结束画面
     * 展示了：
     * 1. 半透明效果的实现
     * 2. 文本居中绘制
     * 3. 多行文本布局
     */
    private void drawGameOver(Graphics g) {
        String message = "Game Over - Score: " + score;
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics metrics = g.getFontMetrics();
        int x = (GAME_WIDTH - metrics.stringWidth(message)) / 2;
        int y = (GAME_HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(message, x, y);
        
        String restart = "Press ENTER to restart";
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        metrics = g.getFontMetrics();
        x = (GAME_WIDTH - metrics.stringWidth(restart)) / 2;
        y += metrics.getHeight() + 10;
        g.drawString(restart, x, y);
    }

    /**
     * 键盘按下事件处理
     * 展示了事件处理系统：
     * 1. 游戏控制（重启、全屏）
     * 2. 玩家输入处理
     * 3. 事件委托
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            resetGame();
            return;
        }
        
        // F11键切换全屏
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            App.toggleFullscreen();
            revalidate(); // 重新验证布局
            return;
        }
        
        if (playerTank != null) {
            playerTank.keyHandlePresss(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (playerTank != null) {
            playerTank.keyHandleRelease(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 不需要实现
    }

    /**
     * 重置游戏状态
     * 展示了游戏重新开始的逻辑
     */
    private void resetGame() {
        gameOver = false;
        score = 0;
        gameObjects.clear();
        initializeGame();
    }

    @Override
    public Dimension getPreferredSize() {
        Container parent = getParent();
        if (parent != null && parent.getWidth() > 0) {
            // 在全屏模式下，返回父容器的大小
            return parent.getSize();
        }
        // 否则返回默认大小
        return new Dimension(GAME_WIDTH, GAME_HEIGHT);
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
}