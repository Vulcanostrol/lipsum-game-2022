package gamejam.event;

import gamejam.event.events.CollisionEvent;

/**
 *  This class is the sole receiver of CollisionEvents.
 *  It will hand over these collisions to the respective Collidables mentioned in the event
 */
public class EfficientCollisionHandler {
    private static EfficientCollisionHandler efficientCollisionHandler = null;

    public EfficientCollisionHandler() {
        EventConsumer<CollisionEvent> collisionEventEventConsumer = this::handleCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionEventEventConsumer, EventType.COLLISION_EVENT);
    }

    public static EfficientCollisionHandler getInstance() {
        if (efficientCollisionHandler == null) {
            efficientCollisionHandler = new EfficientCollisionHandler();
        }
        return efficientCollisionHandler;
    }

    public void handleCollisionEvent(CollisionEvent collisionEvent) {
        collisionEvent.getCollidesWith().onCollisionEvent(collisionEvent);
        collisionEvent.getCollidingObject().onCollisionEvent(collisionEvent);
    }

}
