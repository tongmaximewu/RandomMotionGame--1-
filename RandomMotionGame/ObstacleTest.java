
import org.junit.Test;

import Obstacle;

import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Unit tests for Obstacle class.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class ObstacleTest {
    private Obstacle obstacle;
    
    @Before
    public void setUp() {
        obstacle = new Obstacle(100, 150, 80, 60);
    }
    
    @Test
    public void testConstructorValid() {
        assertEquals("X coordinate should be set", 100, obstacle.getX());
        assertEquals("Y coordinate should be set", 150, obstacle.getY());
        assertEquals("Width should be set", 80, obstacle.getWidth());
        assertEquals("Height should be set", 60, obstacle.getHeight());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroWidth() {
        new Obstacle(100, 100, 0, 50);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeWidth() {
        new Obstacle(100, 100, -10, 50);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroHeight() {
        new Obstacle(100, 100, 50, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeHeight() {
        new Obstacle(100, 100, 50, -10);
    }
    
    @Test
    public void testGetBounds() {
        Rectangle bounds = obstacle.getBounds();
        assertEquals("Bounds X should match obstacle X", 100, bounds.x);
        assertEquals("Bounds Y should match obstacle Y", 150, bounds.y);
        assertEquals("Bounds width should match obstacle width", 80, bounds.width);
        assertEquals("Bounds height should match obstacle height", 60, bounds.height);
    }
    
    @Test
    public void testDrawWithValidGraphics() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        obstacle.draw(g);
        g.dispose();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDrawWithNullGraphics() {
        obstacle.draw(null);
    }
    
    @Test
    public void testGetColor() {
        assertNotNull("Color should not be null", obstacle.getColor());
    }
}