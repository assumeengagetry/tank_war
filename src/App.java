import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    private static JFrame frame;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("坦克大战");
            panel gamePanel = panel.createGamePanel();
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(gamePanel);
            frame.setResizable(true); // 允许调整大小以支持全屏
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            gamePanel.startGame();
        });
    }
    
    // 提供切换全屏的方法
    public static void toggleFullscreen() {
        GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
            
        if (frame.isUndecorated()) {
            // 退出全屏
            device.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setVisible(true);
        } else {
            // 进入全屏
            frame.dispose();
            frame.setUndecorated(true);
            frame.setVisible(true);
            device.setFullScreenWindow(frame);
        }
    }
}

