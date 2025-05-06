import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardwidth;
    int boardheight;
    int tilesize = 25;

    Tile snakehead;
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    Timer gameloop;
    int velocityx;
    int velocityy;

    boolean gameover = false;

    SnakeGame(int boardwidth, int boardheight) {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;

        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();

        velocityx = 0;
        velocityy = 0;
    }
}


