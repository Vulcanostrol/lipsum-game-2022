package gamejam.objects.collidable.explosion;

import com.badlogic.gdx.graphics.Texture;
import gamejam.objects.Bomb;

public class BombExplosion extends Explosion {

    public BombExplosion(float x, float y) {
        super(x, y, 40*10, 32*10, 30*10, 26*10, 30, true, 400, new Texture("entity/explosion.png"), 40, 32);
    }
}
