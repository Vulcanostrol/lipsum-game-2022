package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.GameManager;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.config.RoomConfiguration;
import gamejam.config.ScoreConfiguration;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.LevelChangeEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.event.events.ScoreEvent;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;
import gamejam.rooms.Room;

public class FinalDoor extends Collidable {

    private Direction direction;

    private boolean collided = false;
    private final boolean upgradeWall;

    private EventConsumer<CollisionEvent> collisionConsumer;
    private TextureRegion textureRegion;

    public FinalDoor(float x, float y, Direction direction, boolean inUpgradeRoom) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        upgradeWall = inUpgradeRoom;
        this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("finaldoor" + upgradeRoomTextureSuffix()) : TextureStore.getTileTextureByName("finaldoor_vertical" + upgradeRoomTextureSuffix());
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
            case NORTH:
            case WEST:
                camera.draw(this.sprite, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT);
                break;
            case SOUTH:
                camera.draw(textureRegion, this.x - this.spriteWidth / 2, this.y + this.spriteHeight, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, false ,true);
                break;
            case EAST:
                camera.draw(textureRegion, this.x - this.spriteWidth / 2, this.y, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, true, false);
                break;
        }
        super.drawHitBox(camera);
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
                EventQueue.getInstance().invoke(new ScoreEvent(ScoreConfiguration.NEXT_LEVEL));
                collided = true;
            }
        }
    }

    private String upgradeRoomTextureSuffix() {
        return upgradeWall ? "_upgrade" : "";
    }
}
