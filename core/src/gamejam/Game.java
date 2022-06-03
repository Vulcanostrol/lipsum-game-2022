package gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.factories.TestEntityFactory;
import gamejam.rooms.Room;

public class Game {

    private Room room;

    long previousTime;

    SpriteBatch spriteBatch;


    public Game() {
        room = new Room();

        spriteBatch = new SpriteBatch();
        //time
        previousTime = System.currentTimeMillis();
    }

    public void render() {
        room.draw();
        //Update
        long newTime = System.currentTimeMillis();
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.update(newTime - previousTime));
        previousTime = newTime;

        //Collision
        this.checkCollisions();

        //Draw
        spriteBatch.begin();
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.draw(spriteBatch));
        spriteBatch.end();

    }

    private void checkCollisions(){
        TestEntityFactory.getInstance().getAllManagedObjects().forEach(e1 -> {
            CollidableFactory.getInstance().getAllManagedObjects().forEach(e2 -> {
                if(e1!=e2 && e1.checkCollision(e2)){
                    e1.setHasCollided();
                }
            });
        });
    }

}
