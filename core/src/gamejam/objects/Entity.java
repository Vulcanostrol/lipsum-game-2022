package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    private float x;
    private float y;

    private Texture hitBoxRedTexture;
    private Texture hitBoxGreenTexture;
    private boolean hasCollided;
    private float collisionWidth;
    private float collisionHeight;

    private Texture sprite;
    private float spriteWidth;
    private float spriteHeight;

    private float speedX;
    private float speedY;

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
        this.hitBoxRedTexture = new Texture("HitboxRed.png");
        this.hitBoxGreenTexture = new Texture("HitboxGreen.png");
        this.hasCollided = false;
        this.collisionHeight = 25;
        this.collisionWidth = 25;
        this.sprite = new Texture("badlogic.jpg");
        this.spriteWidth = 25;
        this.spriteHeight = 50;
        this.speedX = 0;
        this.speedY = 0;
    }

    public Entity(float x, float y, float speedX, float speedY){
        this(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update(float timeDeltaMillis){
        x += speedX * timeDeltaMillis / 1000;
        y += speedY * timeDeltaMillis / 1000;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(sprite, x-spriteWidth/2, y, spriteWidth, spriteHeight);
        if(hasCollided){
            spriteBatch.draw(hitBoxRedTexture, x-collisionWidth/2, y, collisionHeight, collisionWidth);
        } else {
            spriteBatch.draw(hitBoxGreenTexture, x-collisionWidth/2, y, collisionHeight, collisionWidth);
        }
    }

    public void updateHasCollided(){
        this.hasCollided = true;
    }

    public boolean checkCollision(Entity e){
        return  hasCollided ||
                (this.x < e.x + e.collisionWidth &&
                this.x + this.collisionWidth > e.x &&
                this.y < e.y + e.collisionHeight &&
                this.collisionHeight + this.y > e.y);
    }


}
