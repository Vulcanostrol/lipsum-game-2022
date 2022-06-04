package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import gamejam.chips.*;
import gamejam.event.EfficientCollisionHandler;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;
import gamejam.factories.*;
import gamejam.factories.bullets.BulletFactory;
import gamejam.factories.bullets.PyramidEnemyBulletFactory;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.factories.enemies.DroneEnemyFactory;
import gamejam.factories.enemies.PyramidEnemyFactory;
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
	}

	@Override
	public void create () {
		TextureStore.instantiate();

		// Initiate the Efficient collision handler
		EfficientCollisionHandler.getInstance();

		menuManager.switchMenu(0);

		EventConsumer<KeyEvent> consumer = this::onKeyEvent;
		EventQueue.getInstance().registerConsumer(consumer, EventType.KEY_EVENT);
 	}

	private void onKeyEvent(KeyEvent event) {
		// Debugging tests
		if (event.getKeyCode() == Input.Keys.NUM_1 && event.isKeyDown()) {
			ChipManager.getInstance().activateChip(new BuffChip());
		}
		if (event.getKeyCode() == Input.Keys.NUM_2 && event.isKeyDown()) {
			ChipManager.getInstance().activateChip(new WeirdChip());
		}
		if (event.getKeyCode() == Input.Keys.NUM_3 && event.isKeyDown()) {
			ChipManager.getInstance().activateChip(new SniperChip());
		}
		if (event.getKeyCode() == Input.Keys.NUM_4 && event.isKeyDown()) {
			ChipManager.getInstance().activateChip(new BulletChip());
		}
		if (event.getKeyCode() == Input.Keys.NUM_5 && event.isKeyDown()) {
			ChipManager.getInstance().activateChip(new GodModeChip());
		}
		if (event.getKeyCode() == Input.Keys.NUM_0 && event.isKeyDown()) {
			ChipManager.getInstance().resetChips();
		}
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
		menuManager.switchMenu(-1);
	}
}
