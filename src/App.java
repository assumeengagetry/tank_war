import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tank Wars");
        panel gamePanel = new panel(); // Assuming GamePanel is a class that extends JPanel or similar
        
        frame.add(gamePanel); // Add the game panel to the frame
        frame.setResizable(false); // Prevent resizing of the window
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        
        gamePanel.startGame(); // Start the game logic, assuming startGame is a method in GamePanel

        // Additional setup can be done here, such as adding components to the frame.
        
        // Note: In a real application, you would likely want to use SwingUtilities.invokeLater to ensure
        // that the GUI is created on the Event Dispatch Thread (EDT).
    }
    }

