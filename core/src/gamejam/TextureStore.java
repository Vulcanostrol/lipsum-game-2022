package gamejam;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureStore {
    private static HashMap<String, Texture> textures;

    public static void instantiate() {
        textures = new HashMap<>();
        textures.put("bobbysoepkip", new Texture("terrain/error.png"));
        textures.put("wall", new Texture("terrain/wall.png"));
        textures.put("wall_vertical", new Texture("terrain/wall_vertical.png"));
        textures.put("cornerwall", new Texture("terrain/cornerwall.png"));
        textures.put("floor", new Texture("terrain/floor.png"));
        textures.put("door", new Texture("terrain/door.png"));
        textures.put("door_vertical", new Texture("terrain/door_vertical.png"));
        textures.put("upgradedoor", new Texture("terrain/upgradedoor.png"));
        textures.put("upgradedoor_vertical", new Texture("terrain/upgradedoor_vertical.png"));
        textures.put("finaldoor", new Texture("terrain/finaldoor.png"));
        textures.put("finaldoor_vertical", new Texture("terrain/finaldoor_vertical.png"));
        textures.put("health_bar_red", new Texture("healthbar/red_pixel.png"));
        textures.put("health_bar_green", new Texture("healthbar/green_pixel.png"));
        // Upgrade rooms
        textures.put("wall_upgrade", new Texture("terrain/wall_upgrade.png"));
        textures.put("wall_vertical_upgrade", new Texture("terrain/wall_vertical_upgrade.png"));
        textures.put("cornerwall_upgrade", new Texture("terrain/cornerwall_upgrade.png"));
        textures.put("finaldoor_upgrade", new Texture("terrain/finaldoor_upgrade.png"));
        textures.put("finaldoor_vertical_upgrade", new Texture("terrain/finaldoor_vertical_upgrade.png"));
        textures.put("door_upgrade", new Texture("terrain/door_upgrade.png"));
        textures.put("door_vertical_upgrade", new Texture("terrain/door_vertical_upgrade.png"));
        textures.put("upgradedoor_upgrade", new Texture("terrain/upgradedoor_upgrade.png"));
        textures.put("upgradedoor_vertical_upgrade", new Texture("terrain/upgradedoor_vertical_upgrade.png"));
    }

    public static Texture getTileTextureByName(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }
        System.err.println(String.format("Texture met naam %s bestaat niet", name));
        return textures.get("bobbysoepkip");
    }
}
