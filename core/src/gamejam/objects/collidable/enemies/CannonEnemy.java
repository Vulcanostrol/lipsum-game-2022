package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.config.RoomConfiguration;
import gamejam.config.ScoreConfiguration;
import gamejam.factories.CollidableFactory;
import gamejam.factories.PlayerFactory;
import gamejam.objects.Bomb;
import gamejam.objects.collidable.Collidable;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Bomby boi shoots bombs and teleports all over the map just to annoy you
 */
public class CannonEnemy extends AbstractEnemy {
    public static final float FIRE_RATE = 3000;
    public static final float TELEPORT_SPEED = 1000;
    private final Animation<TextureRegion> teleportFromAnimation;
    private final Animation<TextureRegion> teleportToAnimation;


    private float fireTimer;

    private float teleportTimer;

    private State state;

    private float targetX;
    private float targetY;

    private final Random random;

    private boolean lookingLeft = false;

    public CannonEnemy(float initialX, float initialY) {
        super(initialX, initialY, 20*5, 15*5, 18*5, 13*5, 200);
        this.sprite = new Texture("entity/cannon.png");
        Texture teleportBlueSprite = new Texture("entity/teleport_blue.png");
        Texture teleportRedSprite = new Texture("entity/teleport_red.png");
        random = new Random();
        TextureRegion[] blueFrames = TextureRegion.split(teleportBlueSprite, 20, 15)[0];
        teleportFromAnimation = new Animation<>(TELEPORT_SPEED/3000.0f, blueFrames);
        TextureRegion[] redFrames = TextureRegion.split(teleportRedSprite, 20, 15)[0];
        teleportToAnimation = new Animation<>(TELEPORT_SPEED/3000.0f, redFrames);
    }

    @Override
    public int getPoints(){
        return ScoreConfiguration.CANNON;
    }

    @Override
    public void update(float timeDeltaMillis) {
        lookingLeft = PlayerFactory.getInstance().getPlayer().getX() < this.x;
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
            if (random.nextFloat() > 0.4) {
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
        camera.draw(sprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        if (state == State.TELEPORTING) {
            TextureRegion blueFrame = teleportFromAnimation.getKeyFrame((TELEPORT_SPEED - teleportTimer) / 1000.0f, false);
            TextureRegion redFrame = teleportToAnimation.getKeyFrame((TELEPORT_SPEED - teleportTimer) / 1000.0f, false);

            camera.draw(blueFrame, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
            camera.draw(redFrame, targetX - spriteWidth / 2, targetY, spriteWidth, spriteHeight, lookingLeft, false);
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
