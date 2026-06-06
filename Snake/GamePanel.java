import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // --- Costanti schermo ---
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; // 48x48 px
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth  = tileSize * maxScreenCol;  // 768 px
    private final int screenHeight = tileSize * maxScreenRow;  // 576 px

    // Velocità del serpente: mosse al secondo
    // Aumenta questo valore per renderlo più veloce
    private final int SNAKE_SPEED = 1;

    // --- Componenti di gioco ---
    private Thread gameThread;
    private final KeyHandler key = new KeyHandler();
    private final Snake snake    = new Snake(screenWidth, screenHeight, tileSize);
    private final Food  food     = new Food(screenWidth, screenHeight, tileSize);

    private boolean gameOver = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Intervallo in ms tra una mossa e l'altra
        long interval = 1000 / SNAKE_SPEED;
        long lastTime = System.currentTimeMillis();

        while (gameThread != null) {
            long now = System.currentTimeMillis();

            if (now - lastTime >= interval) {
                if (!gameOver) {
                    update();
                }
                repaint();
                lastTime = now;
            }

            // Piccola pausa per non consumare CPU al 100%
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        Direction dir = key.getDirection();

        if (snake.isEating(food.getPosition())) {
            snake.growSnake(dir);
            food.spawnFood(snake.getBody());
        } else {
            snake.moveSnake(dir);
        }

        if (snake.isOutOfBounds() || snake.isCollidingWithSelf()) {
            gameOver = true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameOver) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "GAME OVER";
            int msgWidth = g2.getFontMetrics().stringWidth(msg);
            g2.drawString(msg, (screenWidth - msgWidth) / 2, screenHeight / 2);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            String score = "Punteggio: " + snake.getScore();
            int scoreWidth = g2.getFontMetrics().stringWidth(score);
            g2.drawString(score, (screenWidth - scoreWidth) / 2, screenHeight / 2 + 40);

        } else {
            // Cibo (rosso)
            g2.setColor(Color.RED);
            Point foodPos = food.getPosition();
            g2.fillRect(foodPos.x, foodPos.y, tileSize, tileSize);

            // Serpente (verde con bordo nero)
            for (Point p : snake.getBody()) {
                g2.setColor(Color.GREEN);
                g2.fillRect(p.x, p.y, tileSize, tileSize);
                g2.setColor(Color.BLACK);
                g2.drawRect(p.x, p.y, tileSize, tileSize);
            }

            // Punteggio
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Score: " + snake.getScore(), 10, 30);
        }

        g2.dispose();
    }

    public int getTileSize()     { return tileSize; }
    public int getScreenWidth()  { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }
}