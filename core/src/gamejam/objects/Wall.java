package gamejam.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;

public class Wall extends Collidable {
    private final EventConsumer<CollisionEvent> collisionConsumer;


    public Wall(float x, float y, float width, float height) {
        super(width, height, width, height);
        setPosition(x, y);
        setVelocity(0 ,0);

        collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    private void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisWall();
        }
    }

    private void onPlayerCollidedWithThisWall() {
        // System.out.println("hit a wall!");
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.drawHitBox(spriteBatch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        EventQueue.getInstance().deregisterConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }
}
