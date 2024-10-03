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
                //50-51
            }
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
            }
        }

        //bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletVelocityY;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) {
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
