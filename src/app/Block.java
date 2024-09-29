package app;

import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.Random;
//import javax.swing.*;

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
