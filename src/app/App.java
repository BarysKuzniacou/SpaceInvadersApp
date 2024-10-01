package app;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        final int tileSize = 32;
        final int rows = 16;
        final int columns = 16;
        int boardWidth = tileSize * columns;
        int boardHeight = tileSize * rows;

        JFrame frame = new JFrame("Space Invaders App");

        //frame.setVisible(true); //делает видимым окно
        frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null); //открывает окно в центре экрана
		frame.setResizable(false); //запрет на изменение размера экрана
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрывает программу после нажатия на крестик
		
        SpaceInvaders spaceInvaders = new SpaceInvaders();
        frame.add(spaceInvaders);
        frame.pack();
        spaceInvaders.requestFocus();
        frame.setVisible(true);
    }
}
