package gamejam.objects;

/**
 * SelfCollidable entities collide with all other Collidable and SelfCollidable entities
 * Objects of this class are more expensive computationally and should be stuff like enemies, players, etc.
 */
public abstract class SelfCollidable extends Collidable {
    public SelfCollidable(float spriteWidth, float spriteHeight, float collisionWidth, float collisionHeight) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
    }
}
