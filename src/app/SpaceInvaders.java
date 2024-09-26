package app;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SpaceInvaders extends JPanel {
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

    SpaceInvaders() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

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
    }
}
