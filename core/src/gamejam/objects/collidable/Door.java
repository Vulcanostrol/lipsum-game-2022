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
import gamejam.levels.Direction;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;
import gamejam.rooms.RoomConfiguration;

public class Door extends Collidable {

    private Direction direction;

    private boolean collided = false;

    private TextureRegion tr;

    public Door(float x, float y, Direction direction) {
        super(RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = (direction == Direction.NORTH || direction == Direction.SOUTH) ? TextureStore.getTileTextureByName("door") : TextureStore.getTileTextureByName("door_vertical");
        this.direction = direction;

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
