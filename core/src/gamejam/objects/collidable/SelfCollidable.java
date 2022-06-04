package gamejam.objects.collidable;

import gamejam.objects.collidable.Collidable;

import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.factories.SelfCollidableFactory;
import gamejam.objects.collidable.enemies.AbstractEnemy;
import gamejam.objects.collidable.enemies.DroneEnemy;

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
        Stream<Boolean> rets = CollidableFactory.getInstance().getAllManagedObjects().map(e2 -> {
            if(this != e2 && this.checkCollision(e2)){
                    if(!collisions.contains(e2)) {
                        collisions.add(e2);
                        this.setHasCollided();
                        e2.setHasCollided();
                        CollisionEvent event = new CollisionEvent(this, e2);
                        EventQueue.getInstance().invoke(event);
                    }
                    return true;
                }
                return false;
        });
        return rets.anyMatch(e -> e);
    }
}
