package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.PlayerMoveEvent;

/**
 * The Drone Enemy tries to fly straight to the player, to collide with it and
 * damage it like a kamikaze pilot.
 */
public class DroneEnemy extends AbstractEnemy {
    // true when the drone can see the player.
    private boolean playerPositionKnown = false;

    // from how far away the drone can see the player.
    private final float RANGE_OF_SIGHT = 300f;

    // angle at which the robot moves once it collides with something, and it is not following the player.
    private float roamingDirection = (float) Math.random();

    public static final float SPEED = 0.1f;

    private float playerX = -Float.MAX_VALUE;
    private float playerY = -Float.MAX_VALUE;

    public DroneEnemy(float x, float y) {
        super(x, y, 32, 26, 32, 26, 100);
        this.sprite = new Texture("entity/drone.png");

        EventConsumer<PlayerMoveEvent> playerMoveEventEventConsumer = this::onPlayerMoveEvent;
        EventQueue.getInstance().registerConsumer(playerMoveEventEventConsumer, EventType.PLAYER_MOVE);

        //todo: only listen to collisions with self
        EventConsumer<CollisionEvent> collisionEventEventConsumer = this::onCollisionEvent;
    }

    private void onCollisionEvent(CollisionEvent collisionEvent) {
        if (!playerPositionKnown && collisionEvent.getCollidesWith() == this || collisionEvent.getCollidingObject() == this) {
            this.roamingDirection = (float) Math.random();
        }
    }

    private void onPlayerMoveEvent(PlayerMoveEvent playerMoveEvent) {
        this.playerX = playerMoveEvent.newX;
        this.playerY = playerMoveEvent.newY;

        this.playerPositionKnown = Math.abs(playerX - x) < RANGE_OF_SIGHT && Math.abs(playerY - y) < RANGE_OF_SIGHT;


        if (this.playerPositionKnown) {
            System.out.println(String.format("Drone sees player at %s, %s", playerX, playerY));
        }
    }

    @Override
    public void update(float deltaTimeMilis) {
        float dx = 0;
        float dy = 0;

        if (playerPositionKnown) {
            dx += x < playerX ? 1 : -1;
            dy += y < playerY ? 1 : -1;
        } else {
            dx += Math.cos(roamingDirection * 2 * Math.PI);
            dy += Math.sin(roamingDirection * 2 * Math.PI);
        }

        x += SPEED * dx * deltaTimeMilis;
        y += SPEED * dy * deltaTimeMilis;
    }

}