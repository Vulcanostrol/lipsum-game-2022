package gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamejam.event.Event;
import gamejam.event.EventQueue;
import gamejam.input.InputHandler;

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

	EventQueue eventQueue = EventQueue.getInstance();
	InputHandler inputHandler = new InputHandler();

	@Override
	public void create () {
		stage = new Stage();

		// Gdx.input.setInputProcessor(stage);

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

		// event queue dinkie
		eventQueue.add(new Event() {
			@Override
			public String getType() {
				return "ik ben een event";
			}
		});
		eventQueue.handleAll();
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
