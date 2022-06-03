package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;
import gamejam.input.InputHandler;

public class MainMenu extends Menu {

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setPosition(640, 300);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Label gameTitle = new Label("Game Jam Game 2022", labelStyle);
        verticalGroup.addActor(gameTitle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        TextButton playButton = new TextButton("Play", textButtonStyle);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("Event: start game!");
            }
        });
        verticalGroup.addActor(playButton);

        TextButton optionsButton = new TextButton("Options", textButtonStyle);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EventQueue.getInstance().add(new MenuChangeEvent(1));
            }
        });
        verticalGroup.addActor(optionsButton);

        TextButton quitButton = new TextButton("Quit", textButtonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        verticalGroup.addActor(quitButton);

        stage.addActor(verticalGroup);
    }
}
