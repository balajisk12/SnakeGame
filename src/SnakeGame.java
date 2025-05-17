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

    //snake
    Tile snakehead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    Timer gameloop;
    int velocityx;
    int velocityy;

    boolean gameover = false;

    public void placefood() {
        food.x = random.nextInt(boardwidth / tilesize);
        food.y = random.nextInt(boardheight / tilesize);
    }
    
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

        placefood();
        
        velocityx = 0;
        velocityy = 0;

        gameloop = new Timer(100, this);
        gameloop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Draw grid lines
        for (int i = 0; i < boardwidth / tilesize; i++) {
            g.drawLine(i * tilesize, 0, i * tilesize, boardheight); // vertical lines
            g.drawLine(0, i * tilesize, boardwidth, i * tilesize);  // horizontal lines
        }

    g.setColor(Color.red);
    g.fill3DRect(food.x * tilesize, food.y * tilesize, tilesize, tilesize, true);

    g.setColor(Color.green);
    g.fill3DRect(snakehead.x * tilesize, snakehead.y * tilesize, tilesize, tilesize, true);
   
    for (Tile snakepart : snakeBody) {
        g.fill3DRect(snakepart.x * tilesize, snakepart.y * tilesize, tilesize, tilesize, true);
    }

    // Draw score or game over message
    g.setFont(new Font("arial", Font.PLAIN, 16));
    if (gameover) {
        g.setColor(Color.red);
        g.drawString("GAME OVER " + snakeBody.size(), tilesize - 16, tilesize);
    } else {
        g.setColor(Color.white);
        g.drawString("Score: " + snakeBody.size(), tilesize - 16, tilesize);
    }
}

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    
    public void move() {
        
    if (collision(snakehead, food)) {
        snakeBody.add(new Tile(food.x, food.y));
        placefood();
    }
        
    for (int i = snakeBody.size() - 1; i >= 0; i--) {
        Tile snakepart = snakeBody.get(i);
        if (i == 0) {
            snakepart.x = snakehead.x;
            snakepart.y = snakehead.y;
        } else {
            Tile prevsnakepart = snakeBody.get(i - 1);
            snakepart.x = prevsnakepart.x;
            snakepart.y = prevsnakepart.y;
        }
    }
        
    snakehead.x += velocityx;
    snakehead.y += velocityy;

      
    for (int i = 0; i < snakeBody.size(); i++) {
        Tile snakepart = snakeBody.get(i);
        if (collision(snakehead, snakepart)) {
            gameover = true;
        }
    }

    if (snakehead.x < 0 || snakehead.x >= boardwidth / tilesize ||
        snakehead.y < 0 || snakehead.y >= boardheight / tilesize) {
        gameover = true;
    }

}

    
@Override
public void actionPerformed(ActionEvent e) {
    move();
    repaint();

    if (gameover) {
        gameloop.stop();
    }
}

}


