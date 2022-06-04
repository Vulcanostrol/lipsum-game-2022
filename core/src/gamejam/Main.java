package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import gamejam.chips.BuffChip;
import gamejam.chips.WeirdChip;
import gamejam.chips.effects.SniperChip;
import gamejam.event.EfficientCollisionHandler;
import gamejam.chips.BulletChip;
import gamejam.chips.ChipManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;
import gamejam.factories.*;
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
