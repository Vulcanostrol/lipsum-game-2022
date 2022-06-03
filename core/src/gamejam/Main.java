package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import gamejam.factories.EntityFactory;
import gamejam.objects.Entity;

public class Main extends Game {

	Stage stage;
	Table table;
	BitmapFont font;
	Label.LabelStyle labelStyle;
	Label label;
	Container<Label> wrapper;
	TextButton.TextButtonStyle textButtonStyle;
	TextButton button;

	boolean visible;

	EntityFactory entityFactory;
	SpriteBatch spriteBatch;

	long previousTime;

	@Override
	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		font = new BitmapFont();

		// Table
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Container
		labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;
		label = new Label("Hello, Lipsum!", labelStyle);
		wrapper = new Container<>(label);
		wrapper.setTransform(true);
		wrapper.setPosition(500, 500);
		wrapper.setRotation(45);
//		wrapper.setScaleX(1.5f);
		table.addActor(wrapper);

		// Button
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		button = new TextButton("Sample button", textButtonStyle);
		button.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				visible = !visible;
				table.setVisible(visible);
			}
		});
		stage.addActor(button);

		spriteBatch = new SpriteBatch();
		// Entity creation
		entityFactory = new EntityFactory();
		Entity e1 = new Entity(100, 200);
		Entity e2 = new Entity(100, 250);
		Entity e3 = new Entity(500, 200, -100, 0);
		entityFactory.addManagedObject(e1);
		entityFactory.addManagedObject(e2);
		entityFactory.addManagedObject(e3);

		//time
		previousTime = System.currentTimeMillis();
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		stage.draw();

		long newTime = System.currentTimeMillis();
		entityFactory.update(newTime-previousTime);
		previousTime = newTime;

		spriteBatch.begin();
		entityFactory.draw(spriteBatch);
		spriteBatch.end();

	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
