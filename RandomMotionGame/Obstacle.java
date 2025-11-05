import java.awt.*;

/**
 * Represents a static rectangular obstacle in the game world.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class Obstacle {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private static final Color OBSTACLE_COLOR = Color.GRAY;

    /**
     * Creates a new obstacle at the specified position with given dimensions.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the obstacle
     * @param height the height of the obstacle
     * @throws IllegalArgumentException if width or height is non-positive
     */
    public Obstacle(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the bounding rectangle of this obstacle.
     * 
     * @return the bounds rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    /**
     * Gets the x coordinate of the obstacle.
     * 
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the y coordinate of the obstacle.
     * 
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the width of the obstacle.
     * 
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the obstacle.
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of the obstacle.
     * 
     * @return the obstacle color
     */
    public Color getColor() {
        return OBSTACLE_COLOR;
    }

    /**
     * Draws the obstacle as a filled gray rectangle.
     * 
     * @param g the graphics context
     * @throws IllegalArgumentException if graphics context is null
     */
    public void draw(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException("Graphics context cannot be null");
        }
        g.setColor(OBSTACLE_COLOR);
        g.fillRect(x, y, width, height);
    }
}