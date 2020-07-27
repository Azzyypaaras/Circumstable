package azzy.fabric.circumstable.util.container;

import azzy.fabric.circumstable.util.controller.BlastFurnaceController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BlastFurnaceScreen extends CottonInventoryScreen<BlastFurnaceController> {

    public BlastFurnaceScreen(BlastFurnaceController description, PlayerEntity player) {
        super(description, player);
    }

}
