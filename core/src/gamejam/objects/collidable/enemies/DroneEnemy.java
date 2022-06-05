package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.config.ScoreConfiguration;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.PlayerMoveEvent;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;

import java.util.Random;

/**
 * The Drone Enemy tries to fly straight to the player, to collide with it and
 * damage it like a kamikaze pilot.
 */
public class DroneEnemy extends AbstractEnemy {
    // true when the drone can see the player.
    private boolean playerPositionKnown = false;

    // from how far away the drone can see the player.
    private final float RANGE_OF_SIGHT = 500f;

    // angle at which the robot moves once it collides with something, and it is not following the player.
    private float roamingDirection = (float) Math.random();

    public static final float SPEED = 100f;

    private float playerX = -Float.MAX_VALUE;
    private float playerY = -Float.MAX_VALUE;

    public static final int SPRITE_WIDTH = 21 * 5;
    public static final int SPRITE_HEIGHT = 24 * 5;
    public static final int COLLISION_WIDTH = 19 * 5;
    public static final int COLLISION_HEIGHT = 11 * 5;

    private static Texture spriteSheet = null;
    private float animationTime = 0f;
    private Animation<TextureRegion> animation;
    private TextureRegion currentSprite;

    private EventConsumer<PlayerMoveEvent> playerMoveEventEventConsumer;

    public DroneEnemy(float x, float y) {
        super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT, 100);
        if (spriteSheet == null) {
            spriteSheet = new Texture("entity/drone.png");
        }
        TextureRegion[] frames = TextureRegion.split(spriteSheet, 21, 24)[0];
        animation = new Animation<>(0.2f, frames);
        animationTime += (new Random()).nextFloat();

        playerMoveEventEventConsumer = this::onPlayerMoveEvent;
        EventQueue.getInstance().registerConsumer(playerMoveEventEventConsumer, EventType.PLAYER_MOVE);
    }

    @Override
    public int getPoints(){
        return ScoreConfiguration.DRONE;
    }

    public void onCollisionEvent(CollisionEvent collisionEvent) {
        if (playerPositionKnown) {
            // flying towards player, don't really care
            return;
        }
        Collidable other = collisionEvent.getCollidesWith() == this ? collisionEvent.getCollidingObject() : collisionEvent.getCollidesWith();

        if (other instanceof Player) {
            //don't care about collision with player
            return;
        }

        Random random = new Random();
        this.roamingDirection = (this.roamingDirection + 0.5f + (random.nextFloat() / 5f)) % 1f;
    }

    private void onPlayerMoveEvent(PlayerMoveEvent playerMoveEvent) {
        this.playerX = playerMoveEvent.newX;
        this.playerY = playerMoveEvent.newY;

        boolean playerStillInRange = Math.abs(playerX - x) < RANGE_OF_SIGHT && Math.abs(playerY - y) < RANGE_OF_SIGHT;
        if (!playerStillInRange && playerPositionKnown)  {
            // lost track of player, just go fly somewhere
            playerPositionKnown = false;
            roamingDirection = (float) Math.random();
            System.out.printf("roamingdirection is now %f%n", roamingDirection);
        } else if (playerStillInRange) {
            this.playerPositionKnown = true;
        }
    }

    @Override
    public void update(float deltaTimeMilis) {
        float dx = 0;
        float dy = 0;

        if (playerPositionKnown) {
            if (Math.abs(x - playerX) > 0.1f) {
                dx += x < playerX ? 1 : -1;
            }
            if (Math.abs(y - playerY) > 0.1f) {
                dy += y < playerY ? 1 : -1;
            }
        } else {
            dx += Math.cos(roamingDirection * 2 * Math.PI);
            dy += Math.sin(roamingDirection * 2 * Math.PI);
        }

        animationTime += deltaTimeMilis * 0.001;

        super.setVelocity(SPEED*dx, SPEED*dy);
        super.update(deltaTimeMilis);
//        x += SPEED * dx * deltaTimeMilis;
//        y += SPEED * dy * deltaTimeMilis;
    }

    @Override
    public void onDispose() {
        super.onDispose();
        EventQueue eventQueue = EventQueue.getInstance();
        eventQueue.deregisterConsumer(playerMoveEventEventConsumer, EventType.PLAYER_MOVE);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        currentSprite = animation.getKeyFrame(animationTime, true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
        super.drawHitBox(camera);
    }
}
