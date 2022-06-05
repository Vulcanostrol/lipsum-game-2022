package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;
import gamejam.event.events.SetupGameEvent;

public class MainMenu extends Menu {

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup();

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Label gameTitle = new Label("Chipsum", labelStyle);
        verticalGroup.addActor(gameTitle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        TextButton playButton = new TextButton("Play", textButtonStyle);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.INGAME_OVERLAY));
                EventQueue.getInstance().invoke(new SetupGameEvent());
            }
        });
        verticalGroup.addActor(playButton);

        TextButton optionsButton = new TextButton("Options", textButtonStyle);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.OPTIONS_MENU));
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

        verticalGroup.setPosition(
                Gdx.graphics.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.5f + verticalGroup.getPrefHeight() * 0.5f
        );

        stage.addActor(verticalGroup);
    }
}
