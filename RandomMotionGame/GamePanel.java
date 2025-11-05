import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main game panel that renders and updates the game state.
 * Contains the game world and manages the update timer.
 * Supports interactive strategy switching via keyboard.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final int NUM_AVATARS = 10;
    private static final int AVATAR_SIZE = 20;
    private static final int TIMER_DELAY = 100;
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    
    private final GameWorld world;
    private final javax.swing.Timer timer;
    private String currentStrategyName;

    /**
     * Creates a new game panel and initializes the game world.
     */
    public GamePanel() {
        setBackground(Color.BLACK);
        
        world = new GameWorld(PANEL_WIDTH, PANEL_HEIGHT);
        currentStrategyName = "RandomMotionStrategy";
        
        initializeObstacles();
        initializeAvatars();
        setupKeyBindings();
        
        timer = new javax.swing.Timer(TIMER_DELAY, this);
        timer.start();
    }
    
    /**
     * Initializes the obstacles in the game world.
     */
    private void initializeObstacles() {
        world.addObstacle(new Obstacle(200, 150, 100, 200));
        world.addObstacle(new Obstacle(500, 300, 150, 100));
        world.addObstacle(new Obstacle(350, 50, 80, 80));
    }
    
    /**
     * Initializes the avatars in the game world with random motion strategy.
     * Ensures avatars don't spawn inside obstacles.
     */
    private void initializeAvatars() {
        Random rand = new Random();
        
        for (int i = 0; i < NUM_AVATARS; i++) {
            int x, y;
            Avatar avatar;
            boolean validPosition;
            int attempts = 0;
            
            do {
                x = rand.nextInt(world.getWidth() - AVATAR_SIZE);
                y = rand.nextInt(world.getHeight() - AVATAR_SIZE);
                avatar = new Avatar(x, y, AVATAR_SIZE);
                
                validPosition = !world.wouldCollide(avatar.getBounds(), avatar);
                attempts++;
                
                if (attempts > 100) {
                    System.err.println("Warning: Could not find valid position for avatar " + i);
                    break;
                }
            } while (!validPosition && attempts <= 100);
            
            world.addAvatar(avatar);
        }
    }
    
    /**
     * Sets up keyboard bindings for strategy switching.
     */
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "randomMotion");
        actionMap.put("randomMotion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRandomMotion();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "linearMotion");
        actionMap.put("linearMotion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLinearMotion();
            }
        });
    }

    /**
     * Switches all avatars to random motion strategy.
     * Each avatar gets its own strategy instance.
     */
    public void setRandomMotion() {
        try {
            for (Avatar avatar : world.getAvatars()) {
                avatar.setMovementStrategy(new RandomMotionStrategy());
            }
            currentStrategyName = "RandomMotionStrategy";
            System.out.println("Switched to Random Motion");
        } catch (Exception e) {
            System.err.println("Error switching to random motion: " + e.getMessage());
        }
    }

    /**
     * Switches all avatars to linear motion strategy.
     * Each avatar gets its own strategy instance for independent movement.
     */
    public void setLinearMotion() {
        try {
            for (Avatar avatar : world.getAvatars()) {
                avatar.setMovementStrategy(new RandomLinearMotionStrategy());
            }
            currentStrategyName = "RandomLinearMotionStrategy";
            System.out.println("Switched to Linear Motion");
        } catch (Exception e) {
            System.err.println("Error switching to linear motion: " + e.getMessage());
        }
    }

    /**
     * Paints the game panel by drawing all world elements and UI text.
     * 
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        try {
            world.draw(g);
            
            g.setColor(Color.WHITE);
            g.drawString("Press 'R' for Random Motion, 'L' for Linear Motion", 10, 15);
            g.drawString("Current Strategy: " + currentStrategyName, 10, 30);
        } catch (Exception e) {
            System.err.println("Error drawing game: " + e.getMessage());
        }
    }

    /**
     * Handles timer events by updating the game state and repainting.
     * 
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            world.update();
            repaint();
        } catch (Exception ex) {
            System.err.println("Error updating game: " + ex.getMessage());
        }
    }
}