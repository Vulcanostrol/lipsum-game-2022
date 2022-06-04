package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import gamejam.event.EventQueue;
import gamejam.factories.PlayerFactory;
import gamejam.factories.TestEntityFactory;
import gamejam.objects.Player;
import gamejam.objects.TestEntity;
import gamejam.ui.MainMenu;
import gamejam.ui.MenuManager;
import gamejam.ui.OptionsMenu;
import gamejam.ui.PausedMenu;

public class Main extends Game {

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
