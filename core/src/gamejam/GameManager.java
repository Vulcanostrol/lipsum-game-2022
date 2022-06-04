package gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.factories.SelfCollidableFactory;
import gamejam.levels.Level;

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

        //Draw
        spriteBatch.begin();
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.draw(spriteBatch));
        spriteBatch.end();
    }
}
