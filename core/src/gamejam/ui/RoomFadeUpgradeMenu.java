package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamejam.GameManager;
import gamejam.chips.Chip;
import gamejam.chips.ChipManager;
import gamejam.chips.RandomChip;
import gamejam.chips.effects.ChipEffect;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.MenuChangeEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.levels.Direction;

import java.util.ArrayList;
import java.util.List;

public class RoomFadeUpgradeMenu extends Menu {

    public static final float ANIMATION_TIME = 0.5f;
    private static Direction roomChangeDirection;

    static {
        EventConsumer<RoomChangeEvent> consumer = RoomFadeUpgradeMenu::onRoomChangeEvent;
        EventQueue.getInstance().registerConsumer(consumer, EventType.ROOM_CHANGED);
    }

    private static void onRoomChangeEvent(RoomChangeEvent event) {
        roomChangeDirection = event.getDirection();
        if (event.isUpgrade()) {
            EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.ROOM_FADE_UPGRADE));
        }
    }

    // Menu itself, no static stuff anymore.

    private Image overlay;
    private float currentAlpha;
    private float deltaMultiplier;
    private boolean choosing;

    private List<Chip> chips;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        choosing = true;

        chips = new ArrayList<>();
        chips.add(new RandomChip());
        chips.add(new RandomChip());

        currentAlpha = 0f;
        deltaMultiplier = 1f;

        HorizontalGroup horizontalGroup = new HorizontalGroup();

        for (Chip chip : chips) {
            VerticalGroup verticalGroup = new VerticalGroup();

            BitmapFont fontLarge = new BitmapFont();
            fontLarge.getData().setScale(2);
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.WHITE;
            labelStyle.font = fontLarge;

            Label chipNameLabel = new Label(chip.getChipName(), labelStyle);
            verticalGroup.addActor(chipNameLabel);

            BitmapFont font = new BitmapFont();
            Label.LabelStyle effectLabelStyle = new Label.LabelStyle();
            effectLabelStyle.fontColor = Color.WHITE;
            effectLabelStyle.font = font;

            for (ChipEffect effect : chip.getChipEffects()) {
                Label effectLabel = new Label(effect.description(), effectLabelStyle);
                verticalGroup.addActor(effectLabel);
            }

            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = font;

            TextButton nextLevelButton = new TextButton("Choose", textButtonStyle);
            nextLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    if (choosing) {
                        ChipManager.getInstance().activateChip(chip);
                        nextLevel();
                        choosing = false;
                    }
                }
            });
            verticalGroup.addActor(nextLevelButton);

            verticalGroup.space(5f);
            horizontalGroup.addActor(verticalGroup);
        }

        overlay = new Image(new Texture("overlay.png"));
        overlay.setFillParent(true);
        overlay.setColor(new Color(0f, 0f, 0f, 0f));
        stage.addActor(overlay);

        horizontalGroup.space(100f);

        horizontalGroup.setPosition(
                Gdx.graphics.getWidth() * 0.5f - horizontalGroup.getPrefWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.5f + horizontalGroup.getPrefHeight() * 0.5f
        );

        stage.addActor(horizontalGroup);
    }

    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        currentAlpha += delta * deltaMultiplier / ANIMATION_TIME;
        overlay.setColor(new Color(0f, 0f, 0f, currentAlpha));
        if (currentAlpha >= 1) {
            onOverlayComplete();
            currentAlpha = 1f;
        }
        if (currentAlpha <= 0) {
            onComplete();
            currentAlpha = 0f;
        }
        super.draw();
    }

    private void onOverlayComplete() {
        deltaMultiplier = 0;
    }

    private void nextLevel() {
        deltaMultiplier = -1;
        GameManager.getInstance().moveToRoomByDirection(roomChangeDirection);
        choosing = false;
    }

    private void onComplete() {
        EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.INGAME_OVERLAY));
    }
}
