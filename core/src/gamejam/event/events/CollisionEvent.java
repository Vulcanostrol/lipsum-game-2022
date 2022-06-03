package gamejam.event.events;

import gamejam.event.Event;
import gamejam.objects.Collidable;

public class CollisionEvent implements Event {
    private Collidable collidingObject;
    private Collidable collidesWith;

    @Override
    public String getType() {
        return "CollisionChange";
    }

    public CollisionEvent(Collidable collidingObject, Collidable collidesWith){
        this.collidingObject = collidingObject;
        this.collidesWith = collidesWith;
    }

    public Collidable getCollidingObject() {
        return collidingObject;
    }

    public Collidable getCollidesWith() {
        return collidesWith;
    }
}
