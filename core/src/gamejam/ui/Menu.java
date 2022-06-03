package gamejam.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Menu {
    protected Stage stage;

    public abstract void create();

    public void dispose() {
        stage.dispose();
    }

    public void draw() {
        stage.draw();
    }

    /**
     * Called whenever the game window viewport changes size.
     */
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}