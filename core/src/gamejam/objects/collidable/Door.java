package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.GameManager;
import gamejam.TextureStore;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.event.events.ScoreEvent;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.rooms.Room;
import gamejam.rooms.RoomConfiguration;

public class Door extends Collidable {

    private final Direction direction;

    private TextureRegion textureRegion;
    private boolean collided;

    public Door(float x, float y, Direction direction, boolean isUpgradeDoor) {
        super(RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        if (isUpgradeDoor) {
            this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("upgradedoor") : TextureStore.getTileTextureByName("upgradedoor_vertical");;
        } else {
            this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("door") : TextureStore.getTileTextureByName("door_vertical");
        }
        this.direction = direction;
        collided = false;
        textureRegion = new TextureRegion(this.sprite);
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
                    if(!room.cleared){
                        room.cleared = true;
                        EventQueue.getInstance().invoke(new ScoreEvent(100));
                    }
                    EventQueue.getInstance().invoke(new RoomChangeEvent(direction, false));
                }
                collided = true;
            }
        }
    }
}
