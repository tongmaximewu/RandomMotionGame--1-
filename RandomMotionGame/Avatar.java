import java.awt.*;
import java.util.*;

/**
 * Represents a moving avatar in the game with an assigned motion strategy.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class Avatar {
    private int x;
    private int y;
    private final int size;
    private final Color color;
    private MotionStrategy motionStrategy;

    /**
     * Creates a new avatar at the specified position.
     * 
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param size the size of the avatar
     * @throws IllegalArgumentException if size is non-positive
     */
    public Avatar(int x, int y, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = generateRandomColor();
        this.motionStrategy = new RandomMotionStrategy();
    }

    /**
     * Generates a random color for the avatar with brightness between 100-255.
     * 
     * @return a random color
     */
    private static Color generateRandomColor() {
        Random rand = new Random();
        int r = 100 + rand.nextInt(156);
        int g = 100 + rand.nextInt(156);
        int b = 100 + rand.nextInt(156);
        return new Color(r, g, b);
    }

    /**
     * Updates the avatar position using its motion strategy.
     * 
     * @param world the game world context
     * @throws IllegalArgumentException if world is null
     */
    public void move(GameWorld world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null");
        }
        
        if (motionStrategy != null && !motionStrategy.isComplete()) {
            motionStrategy.move(this, world);
        }
    }

    /**
     * Draws the avatar on the graphics context.
     * 
     * @param g the Graphics context to draw on
     * @throws IllegalArgumentException if graphics context is null
     */
    public void draw(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException("Graphics context cannot be null");
        }
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    /**
     * Gets the x coordinate of the avatar.
     * 
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the avatar.
     * 
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the size of the avatar.
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the color of the avatar.
     * 
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the x coordinate of the avatar.
     * 
     * @param x the new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate of the avatar.
     * 
     * @param y the new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the position of the avatar.
     * 
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the bounding rectangle of the avatar.
     * 
     * @return the bounds rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    /**
     * Sets the movement strategy for this avatar.
     * 
     * @param motionStrategy the new movement strategy to use
     * @throws IllegalArgumentException if motionStrategy is null
     */
    public void setMovementStrategy(MotionStrategy motionStrategy) {
        if (motionStrategy == null) {
            throw new IllegalArgumentException("Motion strategy cannot be null");
        }
        this.motionStrategy = motionStrategy;
    }

    /**
     * Gets the current movement strategy.
     * 
     * @return the current movement strategy
     */
    public MotionStrategy getMovementStrategy() {
        return motionStrategy;
    }
}