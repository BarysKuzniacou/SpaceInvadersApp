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

    int shipWidth = tileSize * 2; //64px
    int shipHeight = tileSize; //32px
    int shipX = tileSize * columns / 2 - tileSize;
    int shipY = boardHeight - tileSize * 2;

    int shipVelocityX = tileSize;

    Block ship;

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

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) { //31-09
        if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) {
            ship.x -= shipVelocityX; //move left one tile
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + shipWidth + shipVelocityX <= boardWidth) {
            ship.x += shipVelocityX; //move right one tile
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
