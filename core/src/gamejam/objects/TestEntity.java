package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestEntity extends Collidable{

    public TestEntity(float x, float y){
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

    public TestEntity(float x, float y, float speedX, float speedY){
        this(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }
}
