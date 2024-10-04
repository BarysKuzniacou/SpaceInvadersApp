package app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    //board
    final int tileSize = 32;
    final int rows = 16;
    final int columns = 16;
    int boardWidth = tileSize * columns;
    int boardHeight = tileSize * rows;

    Image shipImg;
    Image alienWhitImg;
    Image alienCyanImg;
    Image alienMagentaImg;
    Image alienYellowImg;

    ArrayList<Image> alienImgArray;

    //ship
    int shipWidth = tileSize * 2; //64px
    int shipHeight = tileSize; //32px
    int shipX = tileSize * columns / 2 - tileSize;
    int shipY = boardHeight - tileSize * 2;
    int shipVelocityX = tileSize;
    Block ship;

    //aliens
    ArrayList<Block> alienArray;
    int alienWidth = tileSize * 2;
    int alienHeight = tileSize;
    int alienX = tileSize;
    int alienY = tileSize;

    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0;
    int alienVelocityX = 1;

    //bullets
    ArrayList<Block> bulletArray;
    int bulletWidth = tileSize/8;
    int bulletHeight = tileSize/2;
    int bulletVelocityY = -10;

    Timer gameLoop;

    int score = 0;

    boolean gameOver = false;

    SpaceInvaders() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        //load images
        shipImg = new ImageIcon(getClass().getResource("/img/ship.png")).getImage();
        alienWhitImg = new ImageIcon(getClass().getResource("/img/alienw.png")).getImage();
        alienCyanImg = new ImageIcon(getClass().getResource("/img/alienc.png")).getImage();
        alienMagentaImg = new ImageIcon(getClass().getResource("/img/alienm.png")).getImage();
        alienYellowImg = new ImageIcon(getClass().getResource("/img/alieny.png")).getImage();

        alienImgArray = new ArrayList<Image>();
        alienImgArray.add(alienWhitImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienYellowImg);

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);
        alienArray = new ArrayList<Block>();
        bulletArray = new ArrayList<Block>();

        //game timer
        gameLoop = new Timer(1000/60, this);
        createAliens();
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive == true) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        //bullets
        g.setColor(Color.WHITE);
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            if (bullet.used == false) {
                //hollow bullets
                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
                //white bullets
                //g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
                score -= 1;
            }
        }

        //score
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver == true) {
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        } else if (gameOver == false) {
            g.drawString("Score: " + String.valueOf(score), 10, 35);
        }
    }

    public void move() {
        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive == true) {
                alien.x += alienVelocityX;

                //if alien touches the borders
                if (alien.x + alienWidth >= boardWidth || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2;

                    //move all aliens down by one row
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }

                if (alien.y >= ship.y) {
                    gameOver = true;
                }
            }
        }

        //bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletVelocityY;

            //bullet collision with alien
            for (int j = 0; j < alienArray.size(); j++) {
                Block alien = alienArray.get(j);
                if (bullet.used == false && alien.alive && detectCollision(bullet, alien)) {
                    bullet.used = true;
                    alien.alive = false;
                    alienCount--;
                    score += 100;
                }
            }
        }

        //clear bullets
        while ((bulletArray.size() > 0) && (bulletArray.get(0).used || bulletArray.get(0).y < 0)) {
            bulletArray.remove(0); //remove the first element of array
        }

        //next level
        if (alienCount == 0) {
            //increase the number of aliens in columns and rows by 1
            score += alienColumns * alienRows * 100;
            alienColumns = Math.min(alienColumns + 1, columns / 2 - 2);
            alienRows = Math.min(alienRows + 1, rows - 6);
            alienArray.clear();
            bulletArray.clear();
            alienVelocityX = 1;
            createAliens();
        }
    }

    public void createAliens() {
        Random random = new Random();
        for (int r = 0; r < alienRows; r++) {
            for (int c = 0; c < alienColumns; c++) {
                int randomImgIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(
                    alienX + c * alienWidth, 
                    alienY + r * alienHeight, 
                    alienWidth, 
                    alienHeight, 
                    alienImgArray.get(randomImgIndex)
                );
                alienArray.add(alien);
            }
            alienCount = alienArray.size();
        }
    }

    public boolean detectCollision(Block bulet, Block alien) {
        return bulet.x < alien.x + alien.width &&
        bulet.x + bulet.width > alien.x &&
        bulet.y < alien.y + alien.height &&
        bulet.y + bulet.height > alien.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver == true) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {

        if (gameOver == true) {
            ship.x = shipX;
            alienArray.clear();
            bulletArray.clear();
            score = 0;
            alienVelocityX = 1;
            alienColumns = 3;
            alienRows = 2;
            gameOver = false;
            createAliens();
            gameLoop.start();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) {
            ship.x -= shipVelocityX; //move left one tile
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + shipWidth + shipVelocityX <= boardWidth) {
            ship.x += shipVelocityX; //move right one tile
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Block bullet = new Block(ship.x + shipWidth * 15/32, ship.y, bulletWidth, bulletHeight, null);
            bulletArray.add(bullet);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
