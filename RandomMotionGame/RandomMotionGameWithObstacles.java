import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main class for the Random Motion Game with Obstacles.
 * Creates and displays the game window with interactive strategy switching.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class RandomMotionGameWithObstacles extends JFrame {
    
    /**
     * Constructs the game window and initializes the game panel.
     */
    public RandomMotionGameWithObstacles() {
        setTitle("Random Motion Game with Obstacles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GamePanel());
        setVisible(true);
    }

    /**
     * Entry point for the application.
     * Launches the game on the Swing event dispatch thread.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandomMotionGameWithObstacles();
            }
        });
    }
}