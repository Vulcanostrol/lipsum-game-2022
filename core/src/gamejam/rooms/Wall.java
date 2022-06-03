package gamejam.rooms;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends RoomTile {

    private WallTileType wallTileType;

    public Wall(WallTileType wallTileType, int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.WALL, minX, maxX, minY, maxY);
        this.wallTileType = wallTileType;

        switch(wallTileType) {
            case WALL_EAST:
                this.texturePath = "assets/terrain/eastwall.png";
                break;
            case WALL_WEST:
                this.texturePath = "assets/terrain/westwall.png";
                break;
            case WALL_SOUTH:
                this.texturePath = "assets/terrain/southwall.png";
                break;
            case WALL_NORTH:
                this.texturePath = "assets/terrain/northwall.png";
                break;
            case WALL_NORTHEAST:
                this.texturePath = "assets/terrain/northeastwall.png";
                break;
            case WALL_NORTHWEST:
                this.texturePath = "assets/terrain/northwestwall.png";
                break;
            case WALL_SOUTHEAST:
                this.texturePath = "assets/terrain/southeastwall.png";
                break;
            case WALL_SOUTHWEST:
                this.texturePath = "assets/terrain/southwestwall.png";
                break;
        }
        this.tileTexture = new Texture(this.texturePath);
    }


}
