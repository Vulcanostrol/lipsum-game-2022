package gamejam;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureStore {
    private static HashMap<String, Texture> textures;

    public static void instantiate() {
        textures = new HashMap<>();
        textures.put("bobbysoepkip", new Texture("assets/terrain/error.png"));
        textures.put("eastwall", new Texture("assets/terrain/eastwall.png"));
        textures.put("westwall", new Texture("assets/terrain/westwall.png"));
        textures.put("northwall", new Texture("assets/terrain/northwall.png"));
        textures.put("southwall", new Texture("assets/terrain/southwall.png"));
        textures.put("northeastwall", new Texture("assets/terrain/northeastwall.png"));
        textures.put("northwestwall", new Texture("assets/terrain/northwestwall.png"));
        textures.put("southeastwall", new Texture("assets/terrain/southeastwall.png"));
        textures.put("southwestwall", new Texture("assets/terrain/southwestwall.png"));
        textures.put("floor", new Texture("assets/terrain/floor.png"));
    }

    public static Texture getTileTextureByName(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }
        System.err.println(String.format("Texture met naam %s bestaat niet", name));
        return textures.get("bobbysoepkip");
    }
}
