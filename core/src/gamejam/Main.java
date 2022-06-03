package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import gamejam.event.EventQueue;
import gamejam.ui.MainMenu;
import gamejam.ui.MenuManager;
import gamejam.ui.OptionsMenu;

public class Main extends Game {

	private final MenuManager menuManager;

	private final GameManager gameManager;

	public Main() {
		super();
		menuManager = new MenuManager();
		menuManager.registerMenu(new MainMenu());
		menuManager.registerMenu(new OptionsMenu());
		gameManager = new GameManager();
	}

	boolean visible;

	@Override
	public void create () {
		TextureStore.instantiate();

		menuManager.switchMenu(0);
	}

	public void resize (int width, int height) {
		menuManager.onResize(width, height);
	}

	@Override
	public void render () {
		// Event handling
		EventQueue.getInstance().handleAll();

		// Rendering
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		menuManager.draw();
		gameManager.draw();
	}
	
	@Override
	public void dispose () {
		menuManager.switchMenu(-1);
	}
}
