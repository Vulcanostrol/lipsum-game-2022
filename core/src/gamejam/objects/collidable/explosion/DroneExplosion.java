package gamejam.objects.collidable.explosion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.objects.collidable.enemies.DroneEnemy;

public class DroneExplosion extends Explosion {

    private Texture spriteSheet;
    private Animation<TextureRegion> animation;
    private TextureRegion currentSprite;

    public DroneExplosion(float x, float y) {
        super(x, y, DroneEnemy.SPRITE_WIDTH * 1.5f, DroneEnemy.SPRITE_HEIGHT * 1.5f, DroneEnemy.COLLISION_WIDTH * 1.5f, DroneEnemy.COLLISION_HEIGHT * 1.5f, 100, true, 400);
        this.spriteSheet = new Texture("entity/drone_explosion.png");
        TextureRegion[] frames = TextureRegion.split(spriteSheet, 21, 24)[0];
        animation = new Animation<TextureRegion>(25f, frames);
    }

    @Override
    public void draw(Camera camera) {
        TextureRegion[] keyFrames = animation.getKeyFrames();
        int frame = (keyFrames.length - 1) - ((int) (keyFrames.length * (timeLeft / durationMS)));

        currentSprite = keyFrames[frame];

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
    }
}
