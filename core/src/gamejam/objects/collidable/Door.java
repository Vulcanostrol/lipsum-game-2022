package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.GameManager;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.GameManager;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.rooms.Room;
import gamejam.rooms.RoomConfiguration;

public class Door extends Collidable {

    private final Direction direction;

    private TextureRegion tr;
    private EventConsumer<CollisionEvent> collisionConsumer;
    private boolean collided;

    public Door(float x, float y, Direction direction, boolean isUpgradeDoor) {
        super(RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        if (isUpgradeDoor) {
            this.sprite = new Texture("terrain/upgradedoor.png");
        } else {
            this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("door") : TextureStore.getTileTextureByName("door_vertical");
        }
        this.direction = direction;
        collided = false;
    }

    @Override
    public void draw(Camera camera) {
        TextureRegion tr = new TextureRegion(this.sprite);
        switch (this.direction) {
            case NORTH:
            case WEST:
                camera.draw(this.sprite, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT);
                break;
            case SOUTH:
                camera.draw(tr, this.x - this.spriteWidth / 2, this.y - this.spriteHeight, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, false ,true);
                break;
            case EAST:
                camera.draw(tr, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, true, false);
                break;
        }
//        camera.draw(sprite, x - collisionWidth / 2, y, spriteWidth, spriteHeight);
    }

    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() instanceof Player || event.getCollidesWith() instanceof Player) {
            onPlayerCollidedWithThisDoor();
        }
    }

    private void onPlayerCollidedWithThisDoor() {
        if (!AbstractEnemyFactory.getInstance().getAllManagedObjects().findAny().isPresent()) {
            if (!collided) {
                Room room = GameManager.getInstance().getCurrentLevel().getCurrentRoom();
                if (!room.cleared && room.isUpgradeRoom) {
                    room.cleared = true;
                    EventQueue.getInstance().invoke(new RoomChangeEvent(direction, true));
                } else {
                    EventQueue.getInstance().invoke(new RoomChangeEvent(direction, false));
                }
                collided = true;
            }
        }
    }
}
