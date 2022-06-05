package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.objects.collidable.explosion.BombExplosion;

public class Bomb extends Entity {
    public static final float SPEED = 400;
    public static final float MAX_HEIGHT = 300;
    private final float maxDistance;

    private final float initialX;
    private final float initialY;

    public Bomb(float x, float y, float targetX, float targetY) {
        super(10*5, 10*5);
        sprite = new Texture("entity/bomb.png");
        initialX = x;
        initialY = y;
        maxDistance = distance(x - targetX, y - targetY);
        setPosition(x, y);
        setVelocity(
                SPEED * ((targetX - x)/ maxDistance),
                SPEED * ((targetY - y)/ maxDistance)
        );
    }

    @Override
    public void update(float timeDeltaMillis) {
        float normDist = distance(x - initialX, y - initialY)/ maxDistance;;
        if (normDist > 1) {
            new BombExplosion(x, y);
            despawn();
        }
        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(Camera camera) {
        camera.draw(sprite, x - spriteWidth/2, y + computeHeight(), spriteWidth, spriteHeight);
    }

    private static float distance(float x, float y) {
        return (float) Math.sqrt(x*x + y*y);
    }

    private float computeHeight() {
        float normDist = distance(x - initialX, y - initialY)/ maxDistance;;
        float normHeight = (float) (4 * (0.25 - Math.pow(0.5 - normDist, 2)));
        return maxDistance * normHeight * 0.25f;
    }

}
