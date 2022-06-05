package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.objects.Entity;

public class Bomb extends Entity {
    public static final float SPEED = 200;
    public static final float MAX_HEIGHT = 300;
    private float maxDistance;

    private float targetX;
    private float targetY;

    public Bomb(float x, float y, float targetX, float targetY) {
        super(10*5, 10*5);
        sprite = new Texture("entity/bomb.png");
        this.targetX = targetX;
        this.targetY = targetY;

        maxDistance = distance(x - targetX, y - targetY);
        setPosition(x, y);
        setVelocity(
                SPEED * (x/ maxDistance),
                SPEED * (y/ maxDistance)
        );
    }

    @Override
    public void update(float timeDeltaMillis) {
        float height = computeHeight();
        if (height <= 0) {
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
        float normDist = distance(x - targetX, y - targetY)/ maxDistance;
        float normHeight = (float) (4 * (0.25 - Math.pow(0.5 - normDist, 2)));
        return MAX_HEIGHT * normHeight;
    }

}
