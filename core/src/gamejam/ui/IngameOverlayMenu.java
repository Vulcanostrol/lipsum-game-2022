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
import com.badlogic.gdx.utils.Align;
import gamejam.GameManager;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;
import gamejam.factories.PlayerFactory;
import gamejam.input.InputHandler;
import gamejam.objects.collidable.Player;

public class IngameOverlayMenu extends Menu {

    private Label health;
    private Label score;

    @Override
    public void create() {
        // This menu specifically does NOT take away input.
        Gdx.input.setInputProcessor(InputHandler.getInstance());

        stage = new Stage();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = font;
        health = new Label("Health: - / -", labelStyle);
        health.setPosition(10, 10);
        health.setAlignment(Align.bottomLeft);

        score = new Label("Score: - ", labelStyle);
        score.setPosition(10, Gdx.graphics.getHeight() - 50);
        score.setAlignment(Align.topLeft);

        stage.addActor(health);
        stage.addActor(score);
    }

    @Override
    public void draw() {
        super.draw();
        Player player = PlayerFactory.getInstance().getPlayer();
        int hp = (int) Math.ceil(player.getHealth());
        int maxHp = (int) Math.ceil(player.getMaxHealth());
        health.setText("Health: " + hp + " / " + maxHp);
        score.setText("Score: " + GameManager.getInstance().getScore());
    }
}
