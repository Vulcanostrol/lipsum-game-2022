package gamejam;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureStore {
    private static HashMap<String, Texture> textures;

    public static void instantiate() {
        textures = new HashMap<>();
        textures.put("bobbysoepkip", new Texture("assets/terrain/error.png"));
        textures.put("wall", new Texture("assets/terrain/wall.png"));
        textures.put("wall_vertical", new Texture("assets/terrain/wall_vertical.png"));
        textures.put("cornerwall", new Texture("assets/terrain/cornerwall.png"));
        textures.put("floor", new Texture("assets/terrain/floor.png"));
        textures.put("door", new Texture("assets/terrain/door.png"));
        textures.put("door_vertical", new Texture("assets/terrain/door_vertical.png"));
        textures.put("upgradedoor", new Texture("assets/terrain/upgradedoor.png"));
        textures.put("upgradedoor_vertical", new Texture("assets/terrain/upgradedoor_vertical.png"));
        textures.put("finaldoor", new Texture("assets/terrain/finaldoor.png"));
        textures.put("finaldoor_vertical", new Texture("assets/terrain/finaldoor_vertical.png"));
        textures.put("health_bar_red", new Texture("assets/healthbar/red_pixel.png"));
        textures.put("health_bar_green", new Texture("assets/healthbar/green_pixel.png"));
    }

    public static Texture getTileTextureByName(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }
        System.err.println(String.format("Texture met naam %s bestaat niet", name));
        return textures.get("bobbysoepkip");
    }
}
