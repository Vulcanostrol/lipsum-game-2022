package gamejam.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.Camera;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;

public class Wall extends Collidable {


    public Wall(float x, float y, float width, float height) {
        super(width, height, width, height);
        setPosition(x, y);
        setVelocity(0 ,0);

    }

    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisWall();
        }
    }

    private void onPlayerCollidedWithThisWall() {
        // System.out.println("hit a wall!");
    }

    @Override
    public void draw(Camera camera) {
        super.drawHitBox(camera);
    }

}
