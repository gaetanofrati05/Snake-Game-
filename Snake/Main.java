import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // adatta la finestra alle dimensioni di GamePanel

        window.setLocationRelativeTo(null); // centra la finestra sullo schermo
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}