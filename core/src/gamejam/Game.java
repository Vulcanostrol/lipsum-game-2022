package gamejam;

import gamejam.levels.Level;

import java.util.ArrayList;

public class Game {

    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;

    public Game() {
        // Initialize a base level
        currentLevel = new Level();
        levels.add(currentLevel);
    }

    public void render() {
        currentLevel.render();
    }

}
