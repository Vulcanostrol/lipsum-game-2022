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

public class OptionsMenu extends Menu {

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setPosition(640, 300);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;

        BitmapFont fontLarge = new BitmapFont();
        fontLarge.getData().setScale(2);
        labelStyle.font = fontLarge;
        Label menuTitle = new Label("Options", labelStyle);
        verticalGroup.addActor(menuTitle);

        BitmapFont fontSmall = new BitmapFont();
        fontSmall.getData().setScale(1);
        labelStyle.font = fontSmall;
        Label sampleSettingsLabel = new Label("some settings will go here", labelStyle);
        verticalGroup.addActor(sampleSettingsLabel);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = fontSmall;

        TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EventQueue.getInstance().add(new MenuChangeEvent(0));
            }
        });
        verticalGroup.addActor(backButton);

        stage.addActor(verticalGroup);
    }
}
