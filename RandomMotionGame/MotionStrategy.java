/**
 * Interface defining the contract for all avatar movement algorithms.
 * This allows different movement behaviors to be easily swapped and extended.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public interface MotionStrategy {
    
    /**
     * Moves the avatar according to the specific movement algorithm.
     * 
     * @param avatar the avatar to move
     * @param world the game world containing obstacles, boundaries, and other avatars
     * @throws IllegalArgumentException if avatar or world is null
     */
    void move(Avatar avatar, GameWorld world);
    
    /**
     * Checks if this strategy has completed its movement sequence.
     * 
     * @return true if movement is complete, false otherwise
     */
    boolean isComplete();
    
    /**
     * Resets the strategy to its initial state.
     */
    void reset();
}