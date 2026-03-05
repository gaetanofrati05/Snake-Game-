import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private final int tileSize;
    private final int screenWidth;
    private final int screenHeight;

    public Snake(int screenWidth, int screenHeight, int tileSize) {
        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        body = new LinkedList<>();
        // Generate the head at the centre of the screen aligned with the panel 
        int startX = (screenWidth / 2 / tileSize) * tileSize;
        int startY = (screenHeight / 2 / tileSize) * tileSize;
        body.addFirst(new Point(startX, startY));
    }

   //Calculate new position in base the direction 
    private Point getNewHead(Direction direction) {
        Point head = body.getFirst();
        return switch (direction) {
            case UP    -> new Point(head.x, head.y - tileSize);
            case DOWN  -> new Point(head.x, head.y + tileSize);
            case LEFT  -> new Point(head.x - tileSize, head.y);
            case RIGHT -> new Point(head.x + tileSize, head.y);
        };
    }

   
    public void moveSnake(Direction direction) {
        Point newHead = getNewHead(direction);
        body.addFirst(newHead);
        body.removeLast();
    }

    
    public void growSnake(Direction direction) {
        Point newHead = getNewHead(direction);
        body.addFirst(newHead);
    }

   
    public boolean isOutOfBounds() {
        Point head = body.getFirst();
        return head.x < 0 || head.x >= screenWidth ||
               head.y < 0 || head.y >= screenHeight;
    }

    
    public boolean isCollidingWithSelf() {
        Point head = body.getFirst();
        
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

   
    public boolean isEating(Point food) {
        return body.getFirst().equals(food);
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public int getScore() {
        return body.size();
    }
}