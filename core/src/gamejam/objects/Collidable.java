package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Collidable extends Entity {
    protected Texture hitBoxRedTexture;
    protected Texture hitBoxGreenTexture;
    protected boolean hasCollided;
    protected float collisionWidth;
    protected float collisionHeight;

    @Override
    public void update(float timeDeltaMillis){
        super.update(timeDeltaMillis);
        hasCollided = false;
    }

    @Override
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
        if(hasCollided){
            spriteBatch.draw(hitBoxRedTexture, x-collisionWidth/2, y, collisionHeight, collisionWidth);
        } else {
            spriteBatch.draw(hitBoxGreenTexture, x-collisionWidth/2, y, collisionHeight, collisionWidth);
        }
    }

    public void setHasCollided(){
        this.hasCollided = true;
    }

    public boolean checkCollision(Collidable e){
        return  hasCollided ||
                (this.x - this.collisionWidth / 2 < e.x + e.collisionWidth / 2 &&
                this.x + this.collisionWidth / 2> e.x - e.collisionWidth / 2 &&
                this.y < e.y + e.collisionHeight /2 &&
                this.collisionHeight + this.y > e.y);
    }


}
