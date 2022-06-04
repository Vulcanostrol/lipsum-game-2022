package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.objects.Entity;

/**
 * Collidable entities can collide with all SelfCollidable entities.
 * If an entity is collidable but not SelfCollidable, it will not collide with other collidables.
 *
 * Entities that should NOT be SelfCollidable are stuff like bullets and walls.
 */
public abstract class Collidable extends Entity {
    protected Texture hitBoxRedTexture;
    protected Texture hitBoxGreenTexture;
    protected boolean hasCollided;
    protected float collisionWidth;
    protected float collisionHeight;

    public Collidable(float spriteWidth, float spriteHeight, float collisionWidth, float collisionHeight) {
        super(spriteWidth, spriteHeight);
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
        this.hasCollided = false;
        this.hitBoxRedTexture = new Texture("HitboxRed.png");
        this.hitBoxGreenTexture = new Texture("HitboxGreen.png");
    }

    @Override
    public void update(float timeDeltaMillis){
        super.update(timeDeltaMillis);
        resetCollision();
    }

    protected void resetCollision(){
        hasCollided = false;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
        drawHitBox(spriteBatch);
    }

    protected void drawHitBox(SpriteBatch spriteBatch){
        if (hasCollided) {
            spriteBatch.draw(hitBoxRedTexture, x - collisionWidth / 2, y, collisionWidth, collisionHeight);
        } else {
            spriteBatch.draw(hitBoxGreenTexture, x - collisionWidth / 2, y, collisionWidth, collisionHeight);
        }
    }


    public void setHasCollided() {
        this.hasCollided = true;
    }

    public boolean checkCollision(Collidable e) {
        return (this.x - this.collisionWidth / 2 < e.x + e.collisionWidth / 2 &&
                this.x + this.collisionWidth / 2 > e.x - e.collisionWidth / 2 &&
                this.y < e.y + e.collisionHeight / 2 &&
                this.collisionHeight + this.y > e.y);
    }
}