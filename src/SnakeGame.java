import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;  // used to store Snakes body
import java.util.Random; // used to get random x and y values to place our food in the screen
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener,KeyListener
{
    
    private class Tile {  // Private because it should be only accessible by the snake game class
        int x;
        int y;

        Tile(int x,int y)
        {
            this.x=x;
            this.y=y;
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

    //game logic
    Timer gameloop;
    int velocityx;
    int velocityy;

    boolean gameover=false;
    
    SnakeGame(int boardwidth,int boardheight)
    {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new Tile(5,5);
        snakeBody =new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placefood();

        velocityx=0;
        velocityy=0;

        gameloop = new Timer(100,this);
        gameloop.start();


    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
      //Grid
      for(int i=0;i<boardwidth/tilesize;i++)
      {
        g.drawLine(i*tilesize, 0, i*tilesize, boardheight); // vertical Line
        g.drawLine(0,i*tilesize,boardwidth, i*tilesize);

      }
       
      // food 
        g.setColor(Color.red);
        //g.fillRect(food.x * tilesize,food.y * tilesize ,tilesize,tilesize);
        g.fill3DRect(food.x * tilesize,food.y * tilesize ,tilesize,tilesize,true);



      //snake head
      g.setColor(Color.green);
      //g.fillRect(snakehead.x * tilesize,snakehead.y *tilesize,tilesize,tilesize);
      g.fill3DRect(snakehead.x * tilesize,snakehead.y *tilesize,tilesize,tilesize,true);

      //snake body
      for(int i=0;i<snakeBody.size();i++)
      {
          Tile snakepart = snakeBody.get(i);
          //g.fillRect(snakepart.x * tilesize, snakepart.y * tilesize, tilesize, tilesize);
          g.fill3DRect(snakepart.x * tilesize, snakepart.y * tilesize, tilesize, tilesize,true);
      }

      // score
      g.setFont(new Font("arial",Font.PLAIN,16));
       if(gameover)
       {
        g.setColor(Color.red);
        g.drawString("GAME OVER" + String.valueOf(snakeBody.size()),tilesize-16,tilesize);
       }
       else{
        g.drawString("Score:"+ String.valueOf(snakeBody.size()),tilesize-16,tilesize);
       }
    }

    public void placefood()
    {
      food.x=random.nextInt(boardwidth/tilesize);  // 600/25 =24
      food.y=random.nextInt(boardheight/tilesize); // 600/25 =24

    }

    public boolean collision(Tile tile1, Tile tile2)
    {
      return tile1.x == tile2.x && tile1.y ==tile2.y;

    }

    public void move()
    {
      //eat food
       if(collision(snakehead, food))
       {
        snakeBody.add(new Tile(food.x,food.y));
        placefood();
       }
      //snake body
       for(int i=snakeBody.size()-1; i>=0;i--)
       {
          Tile snakepart = snakeBody.get(i);
          if(i==0)
          {
            snakepart.x=snakehead.x;
            snakepart.y=snakehead.y;
          }
          else{
            Tile prevsnakepart = snakeBody.get(i-1);
            snakepart.x = prevsnakepart.x;
            snakepart.y= prevsnakepart.y;
          }
       }

      //snake head
      snakehead.x += velocityx;
      snakehead.y += velocityy;

      //gameover Condition
      for(int i=0;i<snakeBody.size();i++)
      {
         Tile snakepart =snakeBody.get(i);
         if(collision(snakehead, snakepart))
         {
          gameover=true;
         }

         if(snakehead.x*tilesize <0 || snakehead.x*tilesize > boardwidth 
           ||snakehead.y*tilesize <0 || snakehead.y*tilesize >boardheight)
           {
            gameover=true;
           }
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameover)
        {
          gameloop.stop();
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_UP && velocityy != 1 )
      {
       velocityx = 0;
       velocityy = -1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityy != -1 )
      {
        velocityx = 0;
        velocityy = 1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityx != 1)
      {
        velocityx = -1;
        velocityy = 0;
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityx != -1)
      {
        velocityx = 1;
        velocityy = 0;
      }
     
    }
    @Override
    public void keyTyped(KeyEvent e) {     }

    @Override
    public void keyReleased(KeyEvent e) {    }


    public void resetGame() {
        // Reset snake head position
        snakehead = new Tile(5, 5);
    
        // Clear snake body
        snakeBody.clear();

        // Place new food
        placefood();

        // Reset velocity to zero (no movement)
        velocityx = 0;
        velocityy = 0;

        // Reset game over flag
        gameover = false;

        // Restart the game loop timer if stopped
        if (!gameloop.isRunning()) {
            gameloop.start();
        }
        repaint();
    }
}
