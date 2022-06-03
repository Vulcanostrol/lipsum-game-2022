package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamejam.factories.CollidableFactory;
import gamejam.factories.EntityFactory;
import gamejam.factories.TestEntityFactory;
import gamejam.objects.Entity;

import java.util.stream.Stream;
import gamejam.event.Event;
import gamejam.event.EventQueue;
import gamejam.objects.TestEntity;
import gamejam.ui.MainMenu;
import gamejam.ui.MenuManager;
import gamejam.ui.OptionsMenu;

public class Main extends Game {

	private final MenuManager menuManager;

	public Main() {
		super();
		menuManager = new MenuManager();
		menuManager.registerMenu(new MainMenu());
		menuManager.registerMenu(new OptionsMenu());
	}

	SpriteBatch spriteBatch;

	long previousTime;

	@Override
	public void create () {
		menuManager.switchMenu(0);

		spriteBatch = new SpriteBatch();
		// Entity creation
		EntityFactory.getInstance().addSubFactory(CollidableFactory.getInstance());
		CollidableFactory.getInstance().addSubFactory(TestEntityFactory.getInstance());
		TestEntity e1 = new TestEntity(100, 200);
		TestEntity e2 = new TestEntity(100, 250);
		TestEntity e3 = new TestEntity(500, 200, -100, 0);
		TestEntityFactory.getInstance().addManagedObject(e1);
		TestEntityFactory.getInstance().addManagedObject(e2);
		TestEntityFactory.getInstance().addManagedObject(e3);

		//time
		previousTime = System.currentTimeMillis();
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

		//Update
		long newTime = System.currentTimeMillis();
		EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.update(newTime - previousTime));
		previousTime = newTime;

		//Collision
		this.checkCollisions();

		//Draw
		spriteBatch.begin();
		EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.draw(spriteBatch));
		spriteBatch.end();

	}

	private void checkCollisions(){
		TestEntityFactory.getInstance().getAllManagedObjects().forEach(e1 -> {
			CollidableFactory.getInstance().getAllManagedObjects().forEach(e2 -> {
				if(e1!=e2 && e1.checkCollision(e2)){
					e1.setHasCollided();
				}
			});
		});
	}

	@Override
	public void dispose () {
		menuManager.switchMenu(-1);
	}
}
