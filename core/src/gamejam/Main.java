package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import gamejam.event.EventQueue;
import gamejam.factories.*;
import gamejam.objects.collidable.enemies.AbstractEnemy;
import gamejam.ui.MainMenu;
import gamejam.ui.MenuManager;
import gamejam.ui.OptionsMenu;
import gamejam.ui.PausedMenu;

public class Main extends Game {

	static {
		// Force loading of all factories, otherwise java will pretend they don't exist
		EntityFactory.getInstance();
		BulletFactory.getInstance();
		CollidableFactory.getInstance();
		DoorFactory.getInstance();
		EntityFactory.getInstance();
		PlayerFactory.getInstance();
		SelfCollidableFactory.getInstance();
		TestEntityFactory.getInstance();
		WallFactory.getInstance();
		AbstractEnemyFactory.getInstance();
		DroneEnemyFactory.getInstance();
	}

	private final MenuManager menuManager;

	public Main() {
		super();
		menuManager = new MenuManager();
		menuManager.registerMenu(new MainMenu());
		menuManager.registerMenu(new OptionsMenu());
		menuManager.registerMenu(new PausedMenu());
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
		GameManager.getInstance().draw();
		menuManager.draw();
	}

	@Override
	public void dispose () {
		menuManager.switchMenu(-1);
	}
}
