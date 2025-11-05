import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the game world state including dimensions, obstacles, and avatars.
 * Provides collision detection and update logic.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class GameWorld {
    private final int width;
    private final int height;
    private final List<Obstacle> obstacles;
    private final List<Avatar> avatars;
    
    /**
     * Creates a new game world with specified dimensions.
     * 
     * @param width the width of the world
     * @param height the height of the world
     * @throws IllegalArgumentException if width or height is non-positive
     */
    public GameWorld(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.width = width;
        this.height = height;
        this.obstacles = new ArrayList<>();
        this.avatars = new ArrayList<>();
    }
    
    /**
     * Adds an obstacle to the world.
     * 
     * @param obstacle the obstacle to add
     * @throws IllegalArgumentException if obstacle is null
     */
    public void addObstacle(Obstacle obstacle) {
        if (obstacle == null) {
            throw new IllegalArgumentException("Obstacle cannot be null");
        }
        obstacles.add(obstacle);
    }
    
    /**
     * Adds an avatar to the world.
     * 
     * @param avatar the avatar to add
     * @throws IllegalArgumentException if avatar is null
     */
    public void addAvatar(Avatar avatar) {
        if (avatar == null) {
            throw new IllegalArgumentException("Avatar cannot be null");
        }
        avatars.add(avatar);
    }
    
    /**
     * Checks if the given bounds would collide with any obstacle, avatar, or boundary.
     * 
     * @param bounds the rectangle to check for collision
     * @param movingAvatar the avatar that is moving (to exclude from avatar collision checks)
     * @return true if collision would occur, false otherwise
     * @throws IllegalArgumentException if bounds or movingAvatar is null
     */
    public boolean wouldCollide(Rectangle bounds, Avatar movingAvatar) {
        if (bounds == null || movingAvatar == null) {
            throw new IllegalArgumentException("Bounds and movingAvatar cannot be null");
        }
        
        for (Obstacle obs : obstacles) {
            if (obs.getBounds().intersects(bounds)) {
                return true;
            }
        }
        
        for (Avatar other : avatars) {
            if (other != movingAvatar && other.getBounds().intersects(bounds)) {
                return true;
            }
        }
        
        if (bounds.x < 0 || bounds.y < 0 || 
            bounds.x + bounds.width > width || 
            bounds.y + bounds.height > height) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Updates all avatars by executing their movement strategies.
     */
    public void update() {
        for (Avatar avatar : avatars) {
            try {
                avatar.move(this);
            } catch (Exception e) {
                System.err.println("Error moving avatar: " + e.getMessage());
            }
        }
    }
    
    /**
     * Draws all obstacles and avatars in the world.
     * 
     * @param g the graphics context to draw on
     * @throws IllegalArgumentException if graphics context is null
     */
    public void draw(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException("Graphics context cannot be null");
        }
        
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
        
        for (Avatar avatar : avatars) {
            avatar.draw(g);
        }
    }
    
    /**
     * Gets the width of the world.
     * 
     * @return the world width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the world.
     * 
     * @return the world height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Gets the list of obstacles in the world.
     * 
     * @return defensive copy of the obstacles list
     */
    public List<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }
    
    /**
     * Gets the list of avatars in the world.
     * 
     * @return defensive copy of the avatars list
     */
    public List<Avatar> getAvatars() {
        return new ArrayList<>(avatars);
    }
}