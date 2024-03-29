package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import gamejam.GameManager;
import gamejam.ScoreSubmit;
import gamejam.TextureStore;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;
import gamejam.event.events.SetupGameEvent;

public class DeathMenu extends Menu {

    public static int scoreToDisplay;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(20);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Label gameTitle = new Label("You died!", labelStyle);
        verticalGroup.addActor(gameTitle);

        Label score = new Label("Your score: " + scoreToDisplay, labelStyle);
        verticalGroup.addActor(score);

        Label name = new Label("Enter name: " , labelStyle);
        verticalGroup.addActor(name);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = new Color(0xaaaaaaff);
        TextField userField = new TextField("unnamed", style);
        verticalGroup.addActor(userField);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        TextButton quitButton = new TextButton("Submit and back to main menu", textButtonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                String user = userField.getText();
                int score = scoreToDisplay;

                // POST request.
                ScoreSubmit.submitScore(user, score);

                scoreToDisplay = 0;
                EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.MAIN_MENU));

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
