package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import gamejam.chips.ChipManager;
import gamejam.event.EfficientCollisionHandler;
import gamejam.event.EventQueue;
import gamejam.factories.*;
import gamejam.factories.bullets.BulletFactory;
import gamejam.factories.bullets.PyramidEnemyBulletFactory;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.factories.enemies.CannonEnemyFactory;
import gamejam.factories.enemies.DroneEnemyFactory;
import gamejam.factories.enemies.PyramidEnemyFactory;
import gamejam.factories.explosion.BombExplosionFactory;
import gamejam.factories.explosion.ExplosionFactory;
import gamejam.ui.*;

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
		PyramidEnemyFactory.getInstance();
		PyramidEnemyBulletFactory.getInstance();
		CannonEnemyFactory.getInstance();
		BombFactory.getInstance();
		ExplosionFactory.getInstance();
		BombExplosionFactory.getInstance();
		EntityDeathManager.init();
	}

	private final MenuManager menuManager;

	public Main() {
		super();
		menuManager = new MenuManager();
		menuManager.registerMenu(new MainMenu());				// 0
		menuManager.registerMenu(new OptionsMenu());			// 1
		menuManager.registerMenu(new PausedMenu());				// 2
		menuManager.registerMenu(new RoomFadeMenu());			// 3
		menuManager.registerMenu(new RoomFadeUpgradeMenu());	// 4
		menuManager.registerMenu(new LevelFadeUpgradeMenu());	// 5
		menuManager.registerMenu(new IngameOverlayMenu());		// 6
	}

	@Override
	public void create () {
		TextureStore.instantiate();

		// Initiate the Efficient collision handler
		EfficientCollisionHandler.getInstance();

		menuManager.switchMenu(MenuManager.MAIN_MENU);
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
		ChipManager.getInstance().draw();
		GameManager.getInstance().draw();
		menuManager.draw();
	}

	@Override
	public void dispose () {
		menuManager.switchMenu(MenuManager.NO_MENU);
	}
}
