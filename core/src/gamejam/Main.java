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
import gamejam.rooms.Room;

public class Main extends Game {

	Stage stage;
	Table table;
	BitmapFont font;
	Label.LabelStyle labelStyle;
	Label label;
	Container<Label> wrapper;
	TextButton.TextButtonStyle textButtonStyle;
	TextButton button;

	Room room;

	boolean visible;

	@Override
	public void create () {
		TextureStore.instantiate();
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());

		this.room = new Room();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		room.draw();
	}
	
	@Override
	public void dispose () {
//		stage.dispose();

	}
}
