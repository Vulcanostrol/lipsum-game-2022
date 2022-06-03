package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected float x;
    protected float y;

    protected Texture sprite;
    protected float spriteWidth;
    protected float spriteHeight;

    protected float speedX;
    protected float speedY;

    public void update(float timeDeltaMillis){
        x += speedX * timeDeltaMillis / 1000;
        y += speedY * timeDeltaMillis / 1000;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(sprite, x-spriteWidth/2, y, spriteWidth, spriteHeight);
    }
}
