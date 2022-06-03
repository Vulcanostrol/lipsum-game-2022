package gamejam;

import gamejam.rooms.Room;

public class Game {

    private Room room;

    public Game() {
        room = new Room();
    }

    public void render() {
        room.draw();
    }

}
