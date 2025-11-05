
import org.junit.Test;

import Avatar;
import GameWorld;
import Obstacle;
import RandomMotionStrategy;

import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for RandomMotionStrategy.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class RandomMotionStrategyTest {
    private RandomMotionStrategy strategy;
    private GameWorld world;
    private Avatar avatar;
    
    @Before
    public void setUp() {
        strategy = new RandomMotionStrategy();
        world = new GameWorld(800, 600);
        avatar = new Avatar(400, 300, 20);
        avatar.setMovementStrategy(strategy);
    }
    
    @Test
    public void testNeverCompletes() {
        assertFalse("Random motion should never complete", strategy.isComplete());
        strategy.move(avatar, world);
        assertFalse("Random motion should still not complete after move", strategy.isComplete());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMoveWithNullAvatar() {
        strategy.move(null, world);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMoveWithNullWorld() {
        strategy.move(avatar, null);
    }
    
    @Test
    public void testMovementWithinBounds() {
        world.addAvatar(avatar);
        for (int i = 0; i < 100; i++) {
            strategy.move(avatar, world);
            assertTrue("X should be non-negative", avatar.getX() >= 0);
            assertTrue("Y should be non-negative", avatar.getY() >= 0);
            assertTrue("X should be within bounds", avatar.getX() + avatar.getSize() <= world.getWidth());
            assertTrue("Y should be within bounds", avatar.getY() + avatar.getSize() <= world.getHeight());
        }
    }
    
    @Test
    public void testNoMovementThroughObstacles() {
        GameWorld smallWorld = new GameWorld(100, 100);
        smallWorld.addObstacle(new Obstacle(40, 0, 20, 100));
        Avatar testAvatar = new Avatar(10, 50, 20);
        testAvatar.setMovementStrategy(strategy);
        smallWorld.addAvatar(testAvatar);
        
        for (int i = 0; i < 50; i++) {
            strategy.move(testAvatar, smallWorld);
            assertTrue("Avatar should not be inside obstacle", 
                      testAvatar.getX() + testAvatar.getSize() <= 40 || testAvatar.getX() >= 60);
        }
    }
    
    @Test
    public void testNoCollisionWithOtherAvatars() {
        Avatar avatar2 = new Avatar(420, 300, 20);
        avatar2.setMovementStrategy(new RandomMotionStrategy());
        world.addAvatar(avatar);
        world.addAvatar(avatar2);
        
        for (int i = 0; i < 50; i++) {
            int oldX = avatar.getX();
            int oldY = avatar.getY();
            strategy.move(avatar, world);
            
            assertFalse("Avatars should not overlap", 
                       avatar.getBounds().intersects(avatar2.getBounds()));
        }
    }
    
    @Test
    public void testReset() {
        strategy.reset();
        assertFalse("Random motion should still not complete after reset", strategy.isComplete());
    }
    
    @Test
    public void testLeftBoundaryCollision() {
        Avatar edgeAvatar = new Avatar(0, 300, 20);
        edgeAvatar.setMovementStrategy(strategy);
        world.addAvatar(edgeAvatar);
        
        for (int i = 0; i < 20; i++) {
            strategy.move(edgeAvatar, world);
            assertTrue("Avatar should not go past left boundary", edgeAvatar.getX() >= 0);
        }
    }
    
    @Test
    public void testTopBoundaryCollision() {
        Avatar edgeAvatar = new Avatar(400, 0, 20);
        edgeAvatar.setMovementStrategy(strategy);
        world.addAvatar(edgeAvatar);
        
        for (int i = 0; i < 20; i++) {
            strategy.move(edgeAvatar, world);
            assertTrue("Avatar should not go past top boundary", edgeAvatar.getY() >= 0);
        }
    }
}
