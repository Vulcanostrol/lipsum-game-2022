package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.GameManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.levels.Direction;

public class Pillar extends Collidable {

    private boolean collided = false;

    private EventConsumer<CollisionEvent> collisionConsumer;

    public Pillar(float x, float y) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = new Texture("terrain/pillar.png");

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

    }
}
