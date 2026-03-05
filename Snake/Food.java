import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Food {

    private Point position;
    private final int tileSize;
    private final int screenWidth;
    private final int screenHeight;
    private final Random random = new Random();

    public Food(int screenWidth, int screenHeight, int tileSize) {
        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        spawnFood(new LinkedList<>()); // prima generazione senza corpo serpente
    }

    //Generate foods in casual position aligned with the panel ,
    //avoid the cell already occuped by the snake 
    public void spawnFood(LinkedList<Point> snakeBody) {
        int cols = screenWidth / tileSize;
        int rows = screenHeight / tileSize;

        Point candidate;
        do {
            int col = random.nextInt(cols);
            int row = random.nextInt(rows);
            candidate = new Point(col * tileSize, row * tileSize);
        } while (snakeBody.contains(candidate)); 

        position = candidate;
    }

    public Point getPosition() {
        return position;
    }
}