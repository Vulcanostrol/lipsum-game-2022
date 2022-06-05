package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.factories.CollidableFactory;
import gamejam.factories.PlayerFactory;
import gamejam.objects.Bomb;
import gamejam.objects.collidable.Collidable;
import gamejam.rooms.RoomConfiguration;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Bomby boi shoots bombs and teleports all over the map just to annoy you
 */
public class CannonEnemy extends AbstractEnemy {
    public static final float FIRE_RATE = 1000;
    public static final float TELEPORT_SPEED = 300;
    private final Texture teleportRedSprite;
    private final Texture teleportBlueSprite;


    private float fireTimer;

    private float teleportTimer;

    private State state;

    private float targetX;
    private float targetY;

    private Random random;

    public CannonEnemy(float initialX, float initialY) {
        super(initialX, initialY, 20*5, 15*5, 18*5, 13*5, 200);
        this.sprite = new Texture("entity/cannon.png");
        this.teleportBlueSprite = new Texture("entity/teleport_blue.png");
        this.teleportRedSprite = new Texture("entity/teleport_red.png");
        random = new Random();
    }

    @Override
    public void update(float timeDeltaMillis) {
        if (state == State.SHOOTING) {
            fireTimer -= timeDeltaMillis;
            if (fireTimer <= 0) {
                new Bomb(x, y,
                        PlayerFactory.getInstance().getPlayer().getX(),
                        PlayerFactory.getInstance().getPlayer().getY());
                state = State.DECIDING;
            }
        } else if (state == State.TELEPORTING) {
            teleportTimer -= timeDeltaMillis;
            if (teleportTimer <= 0) {
                setPosition(targetX, targetY);
                state = State.DECIDING;
            }
        } else {
            if (random.nextFloat() > 0.2) {
                startShooting();
            } else {
                startTeleporting();
            }
        }
        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
        camera.draw(sprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight);
        if (state == State.TELEPORTING) {
            camera.draw(teleportBlueSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight);
            camera.draw(teleportRedSprite, targetX - spriteWidth / 2, targetY, spriteWidth, spriteHeight);
        }
    }

    public void startTeleporting() {
        if (tryFindAndSetTeleportTarget()) {
            state = State.TELEPORTING;
            teleportTimer = TELEPORT_SPEED;
        } else {
            startShooting();
        }
    }

    public void startShooting() {
        state = State.SHOOTING;
        fireTimer = FIRE_RATE;
    }

    private boolean tryFindAndSetTeleportTarget() {
        int roomHeight = (RoomConfiguration.ROOM_TILE_HEIGHT - 2) * RoomConfiguration.TILE_PIXEL_HEIGHT;
        int roomWidth = (RoomConfiguration.ROOM_TILE_WIDTH - 2) * RoomConfiguration.TILE_PIXEL_WIDTH;

        float oldX = x;
        float oldY = y;

        int tries = 3;
        boolean found = false;
        while (tries >= 0) {

            setPosition(random.nextFloat() * roomWidth + RoomConfiguration.TILE_PIXEL_WIDTH,
                    random.nextFloat() * roomHeight + RoomConfiguration.TILE_PIXEL_HEIGHT);

            Stream<Collidable> collidedEnemies = CollidableFactory.getInstance().getAllManagedObjects()
                    .filter(collidable -> collidable.checkCollision(this));
            if (collidedEnemies.count() < 2) {
                // We found a pos
                targetX = x;
                targetY = y;
                found = true;
                break;
            }
            tries -= 1;
        }
        setPosition(oldX, oldY);
        return found;
    }

    private enum State {
        DECIDING,
        SHOOTING,
        TELEPORTING
    }
}
