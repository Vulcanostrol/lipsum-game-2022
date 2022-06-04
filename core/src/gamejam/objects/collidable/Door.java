package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.GameManager;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.rooms.Room;

public class Door extends Collidable {

    private final Direction direction;

    private boolean collided;

    public Door(float x, float y, Direction direction, boolean isUpgradeDoor) {
        super(80, 80, 80, 80);
        setPosition(x, y);
        setVelocity(0 ,0);
        if (isUpgradeDoor) {
            this.sprite = new Texture("terrain/upgradedoor.png");
        } else {
            this.sprite = new Texture("terrain/door.png");
        }
        this.direction = direction;
        collided = false;
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
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
