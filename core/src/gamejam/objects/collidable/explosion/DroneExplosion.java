package gamejam.objects.collidable.explosion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.objects.collidable.enemies.DroneEnemy;

public class DroneExplosion extends Explosion {
    public DroneExplosion(float x, float y) {
        super(x, y, DroneEnemy.SPRITE_WIDTH * 1.5f, DroneEnemy.SPRITE_HEIGHT * 1.5f,
                DroneEnemy.COLLISION_WIDTH * 1.5f, DroneEnemy.COLLISION_HEIGHT * 1.5f,
                100, true, 400, new Texture("entity/drone_explosion.png"), 21, 24
        );
    }
}
