import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Unit tests for GameWorld class.
 * 
 * @author COMP 303 Assignment 2
 * @version 1.0
 */
public class GameWorldTest {
    private GameWorld world;
    private Avatar avatar;
    private Obstacle obstacle;
    
    @Before
    public void setUp() {
        world = new GameWorld(800, 600);
        avatar = new Avatar(100, 100, 20);
        obstacle = new Obstacle(200, 200, 100, 80);
    }
    
    @Test
    public void testConstructorValid() {
        assertEquals("Width should be set", 800, world.getWidth());
        assertEquals("Height should be set", 600, world.getHeight());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroWidth() {
        new GameWorld(0, 600);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeHeight() {
        new GameWorld(800, -100);
    }
    
    @Test
    public void testAddObstacle() {
        world.addObstacle(obstacle);
        List<Obstacle> obstacles = world.getObstacles();
        assertEquals("Should have 1 obstacle", 1, obstacles.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddObstacleWithNull() {
        world.addObstacle(null);
    }
    
    @Test
    public void testAddAvatar() {
        world.addAvatar(avatar);
        List<Avatar> avatars = world.getAvatars();
        assertEquals("Should have 1 avatar", 1, avatars.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddAvatarWithNull() {
        world.addAvatar(null);
    }
    
    @Test
    public void testWouldCollideWithObstacle() {
        world.addObstacle(obstacle);
        world.addAvatar(avatar);
        
        Rectangle collisionBounds = new Rectangle(200, 200, 20, 20);
        assertTrue("Should detect collision with obstacle", world.wouldCollide(collisionBounds, avatar));
    }
    
    @Test
    public void testWouldNotCollide() {
        world.addObstacle(obstacle);
        world.addAvatar(avatar);
        
        Rectangle nonCollisionBounds = new Rectangle(400, 400, 20, 20);
        assertFalse("Should not detect collision", world.wouldCollide(nonCollisionBounds, avatar));
    }
    
    @Test
    public void testWouldCollideWithAnotherAvatar() {
        Avatar avatar2 = new Avatar(150, 150, 20);
        world.addAvatar(avatar);
        world.addAvatar(avatar2);
        
        Rectangle bounds = new Rectangle(150, 150, 20, 20);
        assertTrue("Should detect collision with another avatar", world.wouldCollide(bounds, avatar));
    }
    
    @Test
    public void testWouldNotCollideWithSelf() {
        world.addAvatar(avatar);
        
        Rectangle bounds = avatar.getBounds();
        assertFalse("Should not detect self-collision", world.wouldCollide(bounds, avatar));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWouldCollideWithNullBounds() {
        world.addAvatar(avatar);
        world.wouldCollide(null, avatar);
    }
    
    @Test
    public void testUpdate() {
        world.addAvatar(avatar);
        world.update();
    }
    
    @Test
    public void testDrawWithValidGraphics() {
        world.addObstacle(obstacle);
        world.addAvatar(avatar);
        
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        world.draw(g);
        g.dispose();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDrawWithNullGraphics() {
        world.draw(null);
    }
}