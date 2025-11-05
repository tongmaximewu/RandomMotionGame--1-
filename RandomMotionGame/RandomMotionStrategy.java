import java.awt.Rectangle;
import java.util.Random;

/**
 * Implements the original random motion algorithm where the avatar
 * moves in random directions with small random steps continuously.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class RandomMotionStrategy implements MotionStrategy {
    private final Random rand;
    private static final int MAX_DELTA = 5;
    
    /**
     * Creates a new random motion strategy.
     */
    public RandomMotionStrategy() {
        this.rand = new Random();
    }
    
    /**
     * Moves the avatar in a random direction with random magnitude.
     * Movement is constrained by obstacles, other avatars, and world boundaries.
     * 
     * @param avatar the avatar to move
     * @param world the game world containing obstacles, avatars, and boundaries
     * @throws IllegalArgumentException if avatar or world is null
     */
    @Override
    public void move(Avatar avatar, GameWorld world) {
        if (avatar == null || world == null) {
            throw new IllegalArgumentException("Avatar and world cannot be null");
        }
        
        int dx = rand.nextInt(2 * MAX_DELTA + 1) - MAX_DELTA;
        int dy = rand.nextInt(2 * MAX_DELTA + 1) - MAX_DELTA;
        
        int newX = avatar.getX() + dx;
        int newY = avatar.getY() + dy;
        
        Rectangle futureBounds = new Rectangle(newX, newY, avatar.getSize(), avatar.getSize());
        
        if (!world.wouldCollide(futureBounds, avatar)) {
            avatar.setX(newX);
            avatar.setY(newY);
        }
    }
    
    /**
     * Random motion never completes.
     * 
     * @return always false
     */
    @Override
    public boolean isComplete() {
        return false;
    }
    
    /**
     * No state to reset for random motion.
     */
    @Override
    public void reset() {
    }
}