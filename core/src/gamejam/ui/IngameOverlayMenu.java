package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import gamejam.GameManager;
import gamejam.factories.PlayerFactory;
import gamejam.input.InputHandler;
import gamejam.objects.collidable.Player;
import gamejam.rooms.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IngameOverlayMenu extends Menu {

    private Label health;

    @Override
    public void create() {
        // This menu specifically does NOT take away input.
        Gdx.input.setInputProcessor(InputHandler.getInstance());

        stage = new Stage();

        addHealth();
        addMinimap();

        stage.addActor(health);
    }

    private void addHealth() {
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = font;
        health = new Label("Health: - / -", labelStyle);
        health.setPosition(10, 10);
        health.setAlignment(Align.bottomLeft);
    }

    private void addMinimap() {
        Room[][] rooms = getRemovedPaddingRooms();
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(25);
        for (int x = 0; x < rooms.length; x++) {
            VerticalGroup verticalGroup = new VerticalGroup();
            verticalGroup.space(25);
            for (int y = 0; y < rooms[x].length; y++) {
                Texture texture;
                if (rooms[x][y] == null) {
                    texture = new Texture("minimap/empty.png");
                } else if (rooms[x][y] == GameManager.getInstance().getCurrentLevel().getCurrentRoom()) {
                    texture = new Texture("minimap/current.png");
                } else if (rooms[x][y].visited && rooms[x][y].isFinalRoom()) {
                    texture = new Texture("minimap/final.png");
                } else if (rooms[x][y].visited && rooms[x][y].isUpgradeRoom) {
                    texture = new Texture("minimap/upgrade.png");
                } else if (rooms[x][y].visited) {
                    texture = new Texture("minimap/visited.png");
                } else {
                    texture = new Texture("minimap/unvisited.png");
                }
                Image image = new Image(texture);
                if (rooms[x][y] == null) {
                    image.setVisible(false);
                }
                image.setScale(20);
                verticalGroup.addActor(image);
            }
            verticalGroup.reverse();
            horizontalGroup.addActor(verticalGroup);
        }
        horizontalGroup.setPosition(
                Gdx.graphics.getWidth() - horizontalGroup.getPrefWidth() - 50,
                Gdx.graphics.getHeight() - horizontalGroup.getPrefHeight()
        );
        stage.addActor(horizontalGroup);
    }

    private Room[][] transposeMatrix(Room[][] rooms) {
        int rows = rooms.length;
        int columns = rooms[0].length;
        Room[][] temp = new Room[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temp[j][i] = rooms[i][j];
            }
        }
        return temp;
    }

    private Room[][] toArrays(List<List<Room>> lists) {
        Room[][] array = new Room[lists.size()][];

        int i = 0;
        for (List<Room> nestedList : lists) {
            array[i++] = nestedList.toArray(new Room[nestedList.size()]);
        }

        return array;
    }

    private Room[][] getRemovedPaddingRooms() {
        Room[][] rooms = GameManager.getInstance().getCurrentLevel().rooms;
        List<Room[]> roomsList = new ArrayList<>();
        // Populating
        for (Room[] column : rooms) {
            if (!Arrays.stream(column).allMatch(Objects::isNull)) {
                roomsList.add(column);
            }
        }
        rooms = transposeMatrix(roomsList.toArray(new Room[0][0]));
        roomsList.clear();
        for (Room[] row : rooms) {
            if (!Arrays.stream(row).allMatch(Objects::isNull)) {
                roomsList.add(row);
            }
        }
        return transposeMatrix(roomsList.toArray(new Room[0][0]));
    }



    @Override
    public void draw() {
        super.draw();
        Player player = PlayerFactory.getInstance().getPlayer();
        int hp = (int) Math.ceil(player.getHealth());
        int maxHp = (int) Math.ceil(player.getMaxHealth());
        health.setText("Health: " + hp + " / " + maxHp);
    }
}
