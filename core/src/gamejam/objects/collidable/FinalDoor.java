package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.GameManager;
import gamejam.Camera;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.levels.Direction;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;

public class FinalDoor
        extends Collidable {

    private Direction direction;

    private boolean collided = false;

    private EventConsumer<CollisionEvent> collisionConsumer;

    public FinalDoor
            (float x, float y, Direction direction) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = new Texture("terrain/finaldoor.png");
        this.direction = direction;

        collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    public void dispose() {
        EventQueue.getInstance().deregisterConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
//        camera.draw(sprite, x - collisionWidth / 2, y, spriteWidth, spriteHeight);
    }

    private void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisFinalDoor();
        }
    }

    private void onPlayerCollidedWithThisFinalDoor
            () {
        if (!collided) {
            GameManager.getInstance().moveToNextLevel();
            collided = true;
        }
    }
}
