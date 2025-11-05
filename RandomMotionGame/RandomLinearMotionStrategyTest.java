
import org.junit.Test;

import Avatar;
import GameWorld;
import Obstacle;
import RandomLinearMotionStrategy;

import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for RandomLinearMotionStrategy.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class RandomLinearMotionStrategyTest {
    private RandomLinearMotionStrategy strategy;
    private GameWorld world;
    private Avatar avatar;
    
    @Before
    public void setUp() {
        strategy = new RandomLinearMotionStrategy();
        world = new GameWorld(800, 600);
        avatar = new Avatar(400, 300, 20);
        avatar.setMovementStrategy(strategy);
    }
    
    @Test
    public void testInitiallyNotComplete() {
        assertFalse("Strategy should not be complete initially", strategy.isComplete());
    }
    
    @Test
    public void testCompletesAfterFourSegments() {
        GameWorld smallWorld = new GameWorld(200, 200);
        smallWorld.addObstacle(new Obstacle(0, 90, 200, 20));
        smallWorld.addObstacle(new Obstacle(90, 0, 20, 200));
        
        Avatar testAvatar = new Avatar(100, 100, 20);
        testAvatar.setMovementStrategy(strategy);
        smallWorld.addAvatar(testAvatar);
        
        int moveCount = 0;
        while (!strategy.isComplete() && moveCount < 1000) {
            strategy.move(testAvatar, smallWorld);
            moveCount++;
        }
        
        assertTrue("Strategy should complete after 4 segments", strategy.isComplete());
        assertTrue("Should complete in reasonable number of moves", moveCount < 1000);
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
    public void testNoMovementWhenComplete() {
        GameWorld smallWorld = new GameWorld(100, 100);
        smallWorld.addObstacle(new Obstacle(0, 45, 100, 10));
        smallWorld.addObstacle(new Obstacle(45, 0, 10, 100));
        
        Avatar testAvatar = new Avatar(50, 50, 20);
        testAvatar.setMovementStrategy(strategy);
        smallWorld.addAvatar(testAvatar);
        
        while (!strategy.isComplete()) {
            strategy.move(testAvatar, smallWorld);
        }
        
        int finalX = testAvatar.getX();
        int finalY = testAvatar.getY();
        
        strategy.move(testAvatar, smallWorld);
        
        assertEquals("X should not change when complete", finalX, testAvatar.getX());
        assertEquals("Y should not change when complete", finalY, testAvatar.getY());
    }
    
    @Test
    public void testReset() {
        GameWorld smallWorld = new GameWorld(100, 100);
        smallWorld.addObstacle(new Obstacle(0, 45, 100, 10));
        smallWorld.addObstacle(new Obstacle(45, 0, 10, 100));
        
        Avatar testAvatar = new Avatar(50, 50, 20);
        testAvatar.setMovementStrategy(strategy);
        smallWorld.addAvatar(testAvatar);
        
        while (!strategy.isComplete()) {
            strategy.move(testAvatar, smallWorld);
        }
        
        assertTrue("Strategy should be complete before reset", strategy.isComplete());
        
        strategy.reset();
        
        assertFalse("Strategy should not be complete after reset", strategy.isComplete());
    }
    
    @Test
    public void testMovesInStraightLines() {
        GameWorld openWorld = new GameWorld(1000, 1000);
        Avatar testAvatar = new Avatar(500, 500, 20);
        testAvatar.setMovementStrategy(strategy);
        openWorld.addAvatar(testAvatar);
        
        int startX = testAvatar.getX();
        int startY = testAvatar.getY();
        
        strategy.move(testAvatar, openWorld);
        
        int firstMoveX = testAvatar.getX();
        int firstMoveY = testAvatar.getY();
        
        int deltaX = firstMoveX - startX;
        int deltaY = firstMoveY - startY;
        
        assertTrue("Movement should be orthogonal", deltaX == 0 || deltaY == 0);
    }
    
    @Test
    public void testMovementWithinBounds() {
        world.addAvatar(avatar);
        for (int i = 0; i < 500 && !strategy.isComplete(); i++) {
            strategy.move(avatar, world);
            assertTrue("X should be non-negative", avatar.getX() >= 0);
            assertTrue("Y should be non-negative", avatar.getY() >= 0);
            assertTrue("X should be within bounds", avatar.getX() + avatar.getSize() <= world.getWidth());
            assertTrue("Y should be within bounds", avatar.getY() + avatar.getSize() <= world.getHeight());
        }
    }
    
    @Test
    public void testEdgeCollision() {
        Avatar cornerAvatar = new Avatar(2, 2, 20);
        RandomLinearMotionStrategy cornerStrategy = new RandomLinearMotionStrategy();
        cornerAvatar.setMovementStrategy(cornerStrategy);
        GameWorld tinyWorld = new GameWorld(50, 50);
        tinyWorld.addAvatar(cornerAvatar);
        
        for (int i = 0; i < 100 && !cornerStrategy.isComplete(); i++) {
            cornerStrategy.move(cornerAvatar, tinyWorld);
        }
        
        assertTrue("Should eventually complete", cornerStrategy.isComplete());
    }
    
    @Test
    public void testNoCollisionWithOtherAvatars() {
        Avatar avatar2 = new Avatar(410, 300, 20);
        avatar2.setMovementStrategy(new RandomLinearMotionStrategy());
        world.addAvatar(avatar);
        world.addAvatar(avatar2);
        
        for (int i = 0; i < 50 && !strategy.isComplete(); i++) {
            strategy.move(avatar, world);
            assertFalse("Avatars should not overlap", 
                       avatar.getBounds().intersects(avatar2.getBounds()));
        }
    }
}
