package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;

public class RedDroneEnemy extends DroneEnemy {

    public RedDroneEnemy(float x, float y) {
        super(x, y);
        spriteSheet = new Texture("entity/red_drone.png");
        RANGE_OF_SIGHT *= 2.0;
        SPEED *= 2.0;
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        currentSprite = animation.getKeyFrame(animationTime, true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
        super.drawHitBox(camera);
    }
}
