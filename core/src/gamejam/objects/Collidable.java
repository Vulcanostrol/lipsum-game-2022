package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Collidable extends Entity {
    protected Texture hitBoxRedTexture;
    protected Texture hitBoxGreenTexture;
    protected boolean hasCollided;
    protected float collisionWidth;
    protected float collisionHeight;
    protected List<Collidable> collisions;

    public Collidable(float spriteWidth, float spriteHeight, float collisionWidth, float collisionHeight) {
        super(spriteWidth, spriteHeight);
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
        this.hasCollided = false;
        this.hitBoxRedTexture = new Texture("HitboxRed.png");
        this.hitBoxGreenTexture = new Texture("HitboxGreen.png");

        EventConsumer<CollisionEvent> collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);

        this.collisions = new ArrayList<>();
    }

    @Override
    public void update(float timeDeltaMillis){
        float movement_x = speedX * timeDeltaMillis / 1000;
        float movement_y = speedY * timeDeltaMillis / 1000;
        //Should be reworked
        boolean canMoveX = true;
        boolean canMoveY = true;
        for (Collidable c:collisions){
            float dist_x = x - c.x;
            float dist_y = y - c.y;
            if(dist_x > 0 ^ movement_x > 0){
                canMoveX = false;
            }
            if(dist_y > 0 ^ movement_y > 0){
                canMoveY = false;
            }
        }
        if(canMoveX){
            x += movement_x;
        }
        if(canMoveY){
            y += movement_y;
        }
        resetCollision();
    }

    private void onCollisionEvent(CollisionEvent event) {
        if(event.getCollidingObject() == this){
            collisions.add(event.getCollidesWith());
        }
    }

    protected void resetCollision(){
        collisions.clear();
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
                this.y < e.y + e.collisionHeight &&
                this.collisionHeight + this.y > e.y);
    }
}
