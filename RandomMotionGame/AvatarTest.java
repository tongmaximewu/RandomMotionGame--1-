
import org.junit.Test;

import Avatar;
import GameWorld;
import MotionStrategy;
import RandomLinearMotionStrategy;
import RandomMotionStrategy;

import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Unit tests for Avatar class.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class AvatarTest {
    private Avatar avatar;
    private MotionStrategy strategy;
    private GameWorld world;
    
    @Before
    public void setUp() {
        strategy = new RandomMotionStrategy();
        world = new GameWorld(800, 600);
        avatar = new Avatar(100, 150, 20);
    }
    
    @Test
    public void testConstructorValid() {
        assertEquals("X coordinate should be set", 100, avatar.getX());
        assertEquals("Y coordinate should be set", 150, avatar.getY());
        assertEquals("Size should be set", 20, avatar.getSize());
        assertNotNull("Color should not be null", avatar.getColor());
        assertNotNull("Strategy should be set by default", avatar.getMovementStrategy());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroSize() {
        new Avatar(100, 100, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeSize() {
        new Avatar(100, 100, -5);
    }
    
    @Test
    public void testSetX() {
        avatar.setX(250);
        assertEquals("X should be updated", 250, avatar.getX());
    }
    
    @Test
    public void testSetY() {
        avatar.setY(350);
        assertEquals("Y should be updated", 350, avatar.getY());
    }
    
    @Test
    public void testSetPosition() {
        avatar.setPosition(200, 300);
        assertEquals("X should be updated", 200, avatar.getX());
        assertEquals("Y should be updated", 300, avatar.getY());
    }
    
    @Test
    public void testSetStrategy() {
        MotionStrategy newStrategy = new RandomLinearMotionStrategy();
        avatar.setMovementStrategy(newStrategy);
        assertEquals("Strategy should be updated", newStrategy, avatar.getMovementStrategy());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetStrategyWithNull() {
        avatar.setMovementStrategy(null);
    }
    
    @Test
    public void testGetBounds() {
        Rectangle bounds = avatar.getBounds();
        assertEquals("Bounds X should match avatar X", avatar.getX(), bounds.x);
        assertEquals("Bounds Y should match avatar Y", avatar.getY(), bounds.y);
        assertEquals("Bounds width should match size", avatar.getSize(), bounds.width);
        assertEquals("Bounds height should match size", avatar.getSize(), bounds.height);
    }
    
    @Test
    public void testMoveWithValidWorld() {
        world.addAvatar(avatar);
        avatar.move(world);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMoveWithNullWorld() {
        avatar.move(null);
    }
    
    @Test
    public void testDrawWithValidGraphics() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        avatar.draw(g);
        g.dispose();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDrawWithNullGraphics() {
        avatar.draw(null);
    }
    
    @Test
    public void testColorInValidRange() {
        int red = avatar.getColor().getRed();
        int green = avatar.getColor().getGreen();
        int blue = avatar.getColor().getBlue();
        
        assertTrue("Red should be >= 100", red >= 100);
        assertTrue("Red should be <= 255", red <= 255);
        assertTrue("Green should be >= 100", green >= 100);
        assertTrue("Green should be <= 255", green <= 255);
        assertTrue("Blue should be >= 100", blue >= 100);
        assertTrue("Blue should be <= 255", blue <= 255);
    }
}
