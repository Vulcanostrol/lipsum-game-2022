package gamejam;

import com.badlogic.gdx.Input;
import gamejam.chips.*;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;
import gamejam.event.events.LevelChangeEvent;
import gamejam.event.events.PlayerDeathEvent;
import gamejam.ui.MenuManager;

public class DebugMode {

    private static final int[] CHEAT_SEQUENCE = {
            Input.Keys.UP, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT,
            Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.B, Input.Keys.A
    };

    private static boolean enabled;
    private static int cheatSequenceIndex;

    static {
        EventConsumer<KeyEvent> consumer = DebugMode::onKeyPress;
        EventQueue.getInstance().registerConsumer(consumer, EventType.KEY_EVENT);
    }

    private static void onKeyPress(KeyEvent event) {
        // Cheat code
        if (event.isKeyDown()) {
            if (event.getKeyCode() == CHEAT_SEQUENCE[cheatSequenceIndex]) {
                cheatSequenceIndex += 1;
                if (cheatSequenceIndex == CHEAT_SEQUENCE.length) {
                    cheatSequenceIndex = 0;
                    enable();
                }
            } else {
                cheatSequenceIndex = 0;
            }
        }

        if (isEnabled()) {
            // Debugging tests
            if (event.getKeyCode() == Input.Keys.NUM_1 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new BuffChip());
            }
            if (event.getKeyCode() == Input.Keys.NUM_2 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new WeirdChip());
            }
            if (event.getKeyCode() == Input.Keys.NUM_3 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new SniperChip());
            }
            if (event.getKeyCode() == Input.Keys.NUM_4 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new BulletChip());
            }
            if (event.getKeyCode() == Input.Keys.NUM_5 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new ScuffedChip());
            }

            // God mode and reset
            if (event.getKeyCode() == Input.Keys.NUM_9 && event.isKeyDown()) {
                ChipManager.getInstance().activateChip(new GodModeChip());
            }
            if (event.getKeyCode() == Input.Keys.NUM_0 && event.isKeyDown()) {
                ChipManager.getInstance().resetChips();
            }

            // Events

            if (event.getKeyCode() == Input.Keys.NUMPAD_1 && event.isKeyDown()) {
                EventQueue.getInstance().invoke(new LevelChangeEvent());
            }
            if (event.getKeyCode() == Input.Keys.NUMPAD_2 && event.isKeyDown()) {
                EventQueue.getInstance().invoke(new PlayerDeathEvent());
            }
        }
    }

    public static void enable() {
        enabled = true;
    }

    public static void disable() {
        enabled = false;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
