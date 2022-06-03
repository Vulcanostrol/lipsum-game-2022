package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;

public class Door extends Collidable {

    public Door(float x, float y) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = new Texture("terrain/door.png");

        EventConsumer<CollisionEvent> collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, x - collisionWidth / 2, y, spriteWidth, spriteHeight);
        super.draw(spriteBatch);
    }

    private void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisDoor();
        }
    }

    private void onPlayerCollidedWithThisDoor() {
        System.out.println("hit a door!");
    }
}
