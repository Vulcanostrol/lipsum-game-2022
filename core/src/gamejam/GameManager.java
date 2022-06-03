package gamejam;

import gamejam.Game;
import gamejam.factories.EntityFactory;

public class GameManager {

    private static Game game;

    private static boolean gameActive = false;

    public GameManager() {

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
