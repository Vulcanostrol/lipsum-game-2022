package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.GameManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.levels.Direction;

public class Door extends Collidable {

    private Direction direction;

    private boolean collided = false;

    public Door(float x, float y, Direction direction) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = new Texture("terrain/door.png");
        this.direction = direction;

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisDoor();
        }
    }

    private void onPlayerCollidedWithThisDoor() {
        if (!collided) {
            GameManager.getInstance().moveToRoomByDirection(direction);
            collided = true;
        }
    }
}
