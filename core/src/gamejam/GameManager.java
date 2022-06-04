package gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.factories.PlayerFactory;
import gamejam.factories.TestEntityFactory;
import gamejam.levels.Direction;
import gamejam.factories.SelfCollidableFactory;
import gamejam.levels.Level;
import gamejam.objects.Player;
import gamejam.objects.TestEntity;
import gamejam.rooms.RoomConfiguration;

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

        camera = new Camera();
        //time
        previousTime = System.currentTimeMillis();
    }

    public void moveToRoomByDirection(Direction direction) {
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        boolean success = currentLevel.moveToRoomByDirection(direction);

//        PlayerFactory.getInstance().removeManagedObjects();
//        TestEntityFactory.getInstance().removeManagedObjects();

        if (success) {
            int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
            int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;

            switch (direction) {
                case EAST:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * 2;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
                    break;
                case WEST:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * (RoomConfiguration.ROOM_TILE_WIDTH - 2);
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
                    break;
                case NORTH:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * 2;
                    break;
                case SOUTH:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * (RoomConfiguration.ROOM_TILE_HEIGHT - 2);
                    break;
            }

            // Entity creation
            Player player = new Player(newPlayerX, newPlayerY);
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

    Camera camera;

    public void draw() {
        if (!gameActive) return;


        // Update
        long newTime = System.currentTimeMillis();
        long deltaTimeMillis = newTime - previousTime;
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.update(deltaTimeMillis));
        camera.begin(deltaTimeMillis);
        previousTime = newTime;

        //Draw
        currentLevel.render(camera);
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.draw(camera));
        camera.end();
    }
}
