package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.GameManager;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.LevelChangeEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;
import gamejam.rooms.Room;
import gamejam.rooms.RoomConfiguration;

public class FinalDoor extends Collidable {

    private Direction direction;

    private boolean collided = false;

    private EventConsumer<CollisionEvent> collisionConsumer;
    private TextureRegion textureRegion;

    public FinalDoor
            (float x, float y, Direction direction) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("finaldoor") : TextureStore.getTileTextureByName("finaldoor_vertical");
        this.direction = direction;

        collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);
        textureRegion = new TextureRegion(this.sprite);
    }

    public void dispose() {
        EventQueue.getInstance().deregisterConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    @Override
    public void draw(Camera camera) {
        switch (this.direction) {
            // TODO: North draw code?
            case NORTH:
            case WEST:
                camera.draw(this.sprite, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT);
                break;
            case SOUTH:
                camera.draw(textureRegion, this.x - this.spriteWidth / 2, this.y - this.spriteHeight, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, false ,true);
                break;
            case EAST:
                camera.draw(textureRegion, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, true, false);
                break;
        }
        super.drawHitBox(camera);
//        super.draw(camera);
//        camera.draw(sprite, x - collisionWidth / 2, y, spriteWidth, spriteHeight);
    }

    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisFinalDoor();
        }
    }

    private void onPlayerCollidedWithThisFinalDoor() {
        if (!AbstractEnemyFactory.getInstance().getAllManagedObjects().findAny().isPresent()) {
            if (!collided) {
                GameManager.getInstance().getCurrentLevel().getCurrentRoom().cleared = true;
                EventQueue.getInstance().invoke(new LevelChangeEvent());
                collided = true;
            }
        }
    }
}
