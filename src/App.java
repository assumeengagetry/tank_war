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
            frame.setResizable(false); // 禁止调整大小
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            gamePanel.startGame();
        });
    }
  
}

