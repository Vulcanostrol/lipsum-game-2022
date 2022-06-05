package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.event.EventQueue;
import gamejam.event.events.EntityDeathEvent;
import gamejam.event.events.ScoreEvent;
import gamejam.factories.EntityFactory;

public abstract class Entity {
    protected float x;
    protected float y;

    protected Texture sprite;
    protected float spriteWidth;
    protected float spriteHeight;

    protected float speedX;
    protected float speedY;

    public Entity(float spriteWidth, float spriteHeight) {
        EntityFactory.getInstance().addManagedObject(this);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.sprite = new Texture("terrain/error.png");
    }

    public void update(float timeDeltaMillis){
        x += speedX * timeDeltaMillis / 1000;
        y += speedY * timeDeltaMillis / 1000;
    }

    public void afterDraw() {};

    public void draw(Camera camera){
        drawShadow(camera);
        camera.draw(sprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight);
    }

    public void setPosition(float xPosition, float yPosition) {
        x = xPosition;
        y = yPosition;
    }

    public void setVelocity(float xVelocity, float yVelocity) {
        speedX = xVelocity;
        speedY = yVelocity;
    }

    public void despawn() {
        EventQueue.getInstance().invoke(new EntityDeathEvent(this));
    }

    public void onDispose() {

    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    protected DropShadowType getDropShadowType() {
        return DropShadowType.NONE;
    }

    protected void drawShadow(Camera camera) {
        DropShadowType dropShadowType = getDropShadowType();

        //The numbers, Mason, what do they mean?
        if (dropShadowType == DropShadowType.NORMAL) {
            camera.draw(TextureStore.getTileTextureByName(dropShadowType.texture),
                    x - 6 * 5, y - 3, 12 * 5, 8 * 5);
        } else if (dropShadowType == DropShadowType.SMALL) {
            camera.draw(TextureStore.getTileTextureByName(dropShadowType.texture),
                    x - 3 * 5, y - 3, 6 * 5, 4 * 5);
        }
    }

    protected enum DropShadowType {
        NONE(null),
        SMALL("dropshadow_small"),
        NORMAL("dropshadow");

        private final String texture;

        DropShadowType(String texture) {
            this.texture = texture;
        }
    }


}
