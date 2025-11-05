import java.awt.Rectangle;
import java.util.Random;

/**
 * Implements the Random Linear Motion algorithm:
 * 1. Pick a random direction (up/down/left/right only)
 * 2. Move in straight line until hitting an obstacle, avatar, or boundary
 * 3. Pick another random direction and repeat
 * 4. Stop after 4 direction changes
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class RandomLinearMotionStrategy implements MotionStrategy {
    private final Random rand;
    private Direction currentDirection;
    private int segmentsRemaining;
    private static final int TOTAL_SEGMENTS = 4;
    private static final int SPEED = 2;
    
    /**
     * Enumeration of orthogonal movement directions.
     */
    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);
        
        private final int dx;
        private final int dy;
        
        /**
         * Creates a direction with the given deltas.
         * 
         * @param dx horizontal change
         * @param dy vertical change
         */
        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        
        /**
         * Gets the horizontal component of this direction.
         * 
         * @return horizontal delta
         */
        public int getDx() {
            return dx;
        }
        
        /**
         * Gets the vertical component of this direction.
         * 
         * @return vertical delta
         */
        public int getDy() {
            return dy;
        }
    }
    
    /**
     * Creates a new random linear motion strategy.
     */
    public RandomLinearMotionStrategy() {
        this.rand = new Random();
        this.segmentsRemaining = TOTAL_SEGMENTS;
        pickNewDirection();
    }
    
    /**
     * Selects a new random orthogonal direction.
     */
    private void pickNewDirection() {
        Direction[] directions = Direction.values();
        currentDirection = directions[rand.nextInt(directions.length)];
    }
    
    /**
     * Moves the avatar in a straight line in the current direction.
     * If an obstacle, avatar, or boundary is hit, changes to a new random 
     * direction and decrements the segment counter.
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
        
        if (isComplete()) {
            return;
        }
        
        int newX = avatar.getX() + currentDirection.getDx() * SPEED;
        int newY = avatar.getY() + currentDirection.getDy() * SPEED;
        
        Rectangle futureBounds = new Rectangle(newX, newY, avatar.getSize(), avatar.getSize());
        
        if (world.wouldCollide(futureBounds, avatar)) {
            segmentsRemaining--;
            if (segmentsRemaining > 0) {
                pickNewDirection();
            }
        } else {
            avatar.setX(newX);
            avatar.setY(newY);
        }
    }
    
    /**
     * Checks if all 4 movement segments have been completed.
     * 
     * @return true if all segments are complete
     */
    @Override
    public boolean isComplete() {
        return segmentsRemaining <= 0;
    }
    
    /**
     * Resets the strategy to move 4 segments again.
     */
    @Override
    public void reset() {
        segmentsRemaining = TOTAL_SEGMENTS;
        pickNewDirection();
    }
}