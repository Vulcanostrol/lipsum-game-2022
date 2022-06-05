package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;
import gamejam.objects.collidable.Collidable;

public class CollisionEvent implements Event {
    private Collidable collidingObject;
    private Collidable collidesWith;

    @Override
    public String getType() {
        return EventType.COLLISION_EVENT;
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

    @Override
    public String toString() {
        return "CollisionEvent{" +
                "collidingObject=" + collidingObject +
                ", collidesWith=" + collidesWith +
                '}';
    }
}
