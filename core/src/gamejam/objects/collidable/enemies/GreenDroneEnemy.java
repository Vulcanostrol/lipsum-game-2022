package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;

public class GreenDroneEnemy extends DroneEnemy {

    public GreenDroneEnemy(float x, float y) {
        super(x, y);
        spriteSheet = new Texture("entity/green_drone.png");
        RANGE_OF_SIGHT *= 3.0;
        SPEED *= 4.0;
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        currentSprite = animation.getKeyFrame(animationTime, true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
        super.drawHitBox(camera);
    }
}
