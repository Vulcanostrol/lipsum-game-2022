package gamejam.chips;

import java.util.ArrayList;
import java.util.List;

public class ChipManager {
    private static ChipManager instance;

    public static ChipManager getInstance() {
        if (instance == null) {
            instance = new ChipManager();
        }
        return instance;
    }

    private List<Chip> activeChips;

    public ChipManager() {
        activeChips = new ArrayList<>();
    }

    public void activateChip(Chip chip) {
        activeChips.add(chip);
        chip.applyEffects();
    }

    public void draw() {
        for (Chip chip : activeChips) {
            chip.updateEffects();
        }
    }

    public void resetChips() {
        for (Chip chip : activeChips) {
            chip.disposeEffects();
        }
        activeChips.clear();
    }
}
