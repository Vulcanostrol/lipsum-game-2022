package gamejam.objects.collidable;


import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.objects.Entity;

import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * SelfCollidable entities collide with all other Collidable and SelfCollidable entities
 * Objects of this class are more expensive computationally and should be stuff like enemies, players, etc.
 */
public abstract class SelfCollidable extends Collidable {
    public SelfCollidable(float spriteWidth, float spriteHeight, float collisionWidth, float collisionHeight) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
    }

    @Override
    public void update(float timeDeltaMillis){
        float movement_x = speedX * timeDeltaMillis / 1000;
        float movement_y = speedY * timeDeltaMillis / 1000;

        x+=movement_x;

        if(checkCollisions()){
            x-=movement_x;
        }
        y+=movement_y;
        if(checkCollisions()){
            y-=movement_y;
        }
    }

    private boolean checkCollisions(){
        Stream<Boolean> ret = CollidableFactory.getInstance().getAllManagedObjects().map(this::checkSingleCollision);

        // In case you wonder: "Why does this idiot not use anyMatch":
        //      anyMatch will return when it sees 1 true in the stream.
        //      Since our collision check has side effects, those will no longer happen after the first collision
        //      This hack below will ensure all values are evaluated
        return ret.collect(Collectors.toSet()).contains(Boolean.TRUE);
    }
    
    private boolean checkSingleCollision(Collidable collidable) {
        if (this != collidable && this.checkCollision(collidable)) {
            if (!collisions.contains(collidable)) {
                collisions.add(collidable);
                this.setHasCollided();
                collidable.setHasCollided();
                CollisionEvent event = new CollisionEvent(this, collidable);
                EventQueue.getInstance().invoke(event);
                return !canMoveThrough(collidable);
            }
        }
        return false;
    }

    private static boolean canMoveThrough(Entity entity) {
        return entity instanceof Traversable;
    }
}
