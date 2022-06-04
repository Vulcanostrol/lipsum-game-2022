package gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.factories.PlayerFactory;
import gamejam.factories.TestEntityFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.objects.Player;
import gamejam.objects.TestEntity;

import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private boolean gameActive;

    public void setupGame() {
        gameActive = true;

        // Initialize a base level
        currentLevel = new Level();
        levels.add(currentLevel);

        spriteBatch = new SpriteBatch();
        //time
        previousTime = System.currentTimeMillis();
    }

    public void moveToRoomByDirection(Direction direction) {
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        boolean success = currentLevel.moveToRoomByDirection(direction);

//        PlayerFactory.getInstance().removeManagedObjects();
//        TestEntityFactory.getInstance().removeManagedObjects();

        if (success) {
            // Entity creation
            Player player = new Player(300, 100);
            TestEntity e1 = new TestEntity(100, 200);
            TestEntity e2 = new TestEntity(100, 250);
            TestEntity e3 = new TestEntity(500, 200, 0, 0);
            PlayerFactory.getInstance().addManagedObject(player);
            TestEntityFactory.getInstance().addManagedObject(e1);
            TestEntityFactory.getInstance().addManagedObject(e2);
            TestEntityFactory.getInstance().addManagedObject(e3);
        }
    }

    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;

    long previousTime;

    SpriteBatch spriteBatch;

    public void draw() {
        if (!gameActive) return;

        currentLevel.render();

        // Update
        long newTime = System.currentTimeMillis();
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.update(newTime - previousTime));
        previousTime = newTime;

        // Collision
        checkCollisions();

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
                    CollisionEvent event = new CollisionEvent(e1, e2);
                    EventQueue.getInstance().invoke(event);
                }
            });
        });
    }
}
