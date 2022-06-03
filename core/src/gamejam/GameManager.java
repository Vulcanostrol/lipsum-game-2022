package gamejam;

import gamejam.Game;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.EntityFactory;

public class GameManager {

    private static Game game;

    private static boolean gameActive = false;

    public GameManager() {
        EventConsumer<CollisionEvent> consumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(consumer, "CollisionChange");
    }

    private void onCollisionEvent(CollisionEvent event) {
        event.getCollidingObject().setVelocity(0, 0);
        event.getCollidingObject().setVelocity(0, 0);
    }

    public static void setupGame() {
        gameActive = true;
        game = new Game();
    }

    public static void draw() {
        if (!gameActive) return;
        game.render();
    }



}
