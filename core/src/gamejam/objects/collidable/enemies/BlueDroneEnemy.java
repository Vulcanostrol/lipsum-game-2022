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

public class BlueDroneEnemy extends DroneEnemy {

    public BlueDroneEnemy(float x, float y) {
        super(x, y);
        spriteSheet = new Texture("entity/blue_drone.png");
        RANGE_OF_SIGHT *= 1.5;
        SPEED *= 1.5;
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        currentSprite = animation.getKeyFrame(animationTime, true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
        super.drawHitBox(camera);
    }

    @Override
    public int getPoints(){
        return ScoreConfiguration.BLUE_DRONE;
    }
}
