package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;
import gamejam.event.events.MenuChangeEvent;
import gamejam.input.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    public static final int NO_MENU = -1;
    public static final int MAIN_MENU = 0;
    public static final int OPTIONS_MENU = 1;
    public static final int PAUSED_MENU = 2;
    public static final int ROOM_FADE_MENU = 3;
    public static final int ROOM_FADE_UPGRADE = 4;
    public static final int LEVEL_FADE_UPGRADE = 5;
    public static final int INGAME_OVERLAY = 6;

    private final List<Menu> registeredMenus;
    private int currentMenuId;

    public MenuManager() {
        registeredMenus = new ArrayList<>();
        currentMenuId = -1;

        // Menu change listeners
        EventConsumer<MenuChangeEvent> consumer = this::handleMenuChangeEvent;
        EventQueue.getInstance().registerConsumer(consumer, EventType.MENU_CHANGE);

        // Pause menu
        EventConsumer<KeyEvent> keyConsumer = this::onKeyEvent;
        EventQueue.getInstance().registerConsumer(keyConsumer, EventType.KEY_EVENT);
    }

    public void handleMenuChangeEvent(MenuChangeEvent event) {
        switchMenu(event.getMenuId());
    }

    private void onKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == Input.Keys.ESCAPE && event.isKeyDown()) {
            EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.PAUSED_MENU));
        }
    }

    public void registerMenu(Menu menuToRegister) {
        registeredMenus.add(menuToRegister);
    }

    public void switchMenu(int id) {
        if (id == currentMenuId) return;
        if (currentMenuId >= 0) {
            registeredMenus.get(currentMenuId).dispose();
        }
        currentMenuId = id;
        System.out.println("Switched to different menu: " + currentMenuId);
        if (id < 0) {
            Gdx.input.setInputProcessor(InputHandler.getInstance());
            return;
        }
        registeredMenus.get(currentMenuId).create();
    }

    public void draw() {
        if (currentMenuId < 0) return;
        registeredMenus.get(currentMenuId).draw();
    }

    public void onResize(int width, int height) {
        if (currentMenuId < 0) return;
        registeredMenus.get(currentMenuId).onResize(width, height);
    }
}
