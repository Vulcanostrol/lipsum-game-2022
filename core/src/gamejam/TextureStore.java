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
    }

    public static Texture getTileTextureByName(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }
        System.err.println(String.format("Texture met naam %s bestaat niet", name));
        return textures.get("bobbysoepkip");
    }
}
