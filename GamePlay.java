import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements ActionListener,KeyListener{
    private MapGenerator map;
    private boolean play = false;
    private int score = 0;
    private int total_bricks=36;

    private Timer timer;
    private int delay = 10;

    private int player_x = 310;     //position of paddle
    //position of ball
    private int ball_position_x = 120;
    private int ball_position_y = 350;
    private int ball_direction_x = -1;
    private int ball_direction_y = -2;

    public GamePlay(){
        map = new MapGenerator(4,9);
        addKeyListener(this); //to detect keys
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start(); //to start the game cycle

    }

    public void keyPressed(KeyEvent e){
        //when right key is pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //checking if it doesn't go outside the panel
            if(player_x >= 600) {
                player_x = 600;
            }
            else {
                moveRight();
            }
        }

        //when right key is pressed
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            //checking if it doesn't go outside the panel
            if(player_x < 10) {
                player_x = 10;
            }
            else {
                moveLeft();
            }
        }

        //if enter key is pressed
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                //to restart the game
                play = true;
                ball_position_x = 120;
                ball_position_y = 350;
                ball_direction_x = -1;
                ball_direction_y = -2;
                player_x = 310;
                score = 0;
                total_bricks = 36;
                map = new MapGenerator(4, 9);

                repaint();
            }
        }
    }
    
    public void moveLeft() {
        play = true;
        player_x -=15;
    }

    public void moveRight() {
        play = true;
        player_x+= 15;
    }
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            //to move the ball when game is started
            ball_position_x += ball_direction_x;
            ball_position_y += ball_direction_y;

            if (ball_position_x < 0) {
                ball_direction_x = -ball_direction_x;
            }
            if (ball_position_y < 0) {
                ball_direction_y = -ball_direction_y;
            }
            if (ball_position_x > 670) {
                ball_direction_x = -ball_direction_x;
            }

            //collision between ball and paddle
            if (new Rectangle(ball_position_x, ball_position_y, 20, 20).intersects(new Rectangle(player_x, 550, 100, 8))) {
                ball_direction_y = -ball_direction_y;
            }


            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ball_position_x, ball_position_y, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            score += 5;
                            total_bricks--;

                            // when ball hit right or left of brick
                            if (ball_position_x + 19 <= brickRect.x || ball_position_y + 1 >= brickRect.x + brickRect.width) {
                                ball_direction_x = -ball_direction_x;
                            }
                            // when ball hits top or bottom of brick
                            else {
                                ball_direction_y = -ball_direction_y;
                            }

                            break A;
                        }
                    }
                }
            }
            repaint();
        }
    }
    public void paint(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D) g);

        //draw border
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(681,0,3,592);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(player_x,550,100,8);

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ball_position_x,ball_position_y,20,20);

        // the scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD, 25));
        g.drawString(""+score, 590,30);

        // when you won the game
        if(total_bricks <= 0)
        {
            play = false;
            ball_direction_x = 0;
            ball_direction_y = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("VICTORYY!!!", 260,300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        // when you lose the game
        if(ball_position_y > 570)
        {
            play = false;
            ball_direction_x = 0;
            ball_direction_y = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190,300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
