package gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.levels.Level;

import java.util.ArrayList;

public class Game {

    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;

    long previousTime;

    SpriteBatch spriteBatch;


    public Game() {
        // Initialize a base level
        currentLevel = new Level();
        levels.add(currentLevel);

        spriteBatch = new SpriteBatch();
        //time
        previousTime = System.currentTimeMillis();
    }

    public void render() {
        currentLevel.render();

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
        CollidableFactory.getInstance().getAllManagedObjects().forEach(e1 -> {
            CollidableFactory.getInstance().getAllManagedObjects().forEach(e2 -> {
                if(e1 != e2 && e1.checkCollision(e2)){
                    e1.setHasCollided();
                    e2.setHasCollided();
                    System.out.println("Collision");
                    System.out.println(e1.getX());
                    System.out.println(e1.getY());
                    System.out.println(e2.getX());
                    System.out.println(e2.getY());
                    CollisionEvent event = new CollisionEvent(e1, e2);
                    EventQueue.getInstance().invoke(event);
                }
            });
        });
    }
}