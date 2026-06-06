import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private Direction direction = Direction.RIGHT; // initial direction 

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W && direction != Direction.DOWN) {
            direction = Direction.UP;
        }
        if (code == KeyEvent.VK_S && direction != Direction.UP) {
            direction = Direction.DOWN;
        }
        if (code == KeyEvent.VK_A && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
        if (code == KeyEvent.VK_D && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public Direction getDirection() {
        return direction;
    }
}