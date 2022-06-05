package gamejam.objects.collidable;

import gamejam.Camera;
import gamejam.event.events.CollisionEvent;

public class Wall extends SelfCollidable {


    public Wall(float x, float y, float width, float height) {
        super(width, height, width, height);
        setPosition(x, y);
        setVelocity(0 ,0);

    }

    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Player ||
                event.getCollidesWith() == this && event.getCollidingObject() instanceof Player) {
            onPlayerCollidedWithThisWall();
        }
    }

    private void onPlayerCollidedWithThisWall() {
        // System.out.println("hit a wall!");
    }

    @Override
    public void draw(Camera camera) {
        super.drawHitBox(camera);
    }

}
