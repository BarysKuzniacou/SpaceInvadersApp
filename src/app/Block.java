package app;

import java.awt.*;

public class Block {
    int x;
    int y;
    int width;
    int height;
    Image img;
    boolean alive = true; //used only for aliens
    boolean used = false; //used for bullets

    Block(int x,  int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}
