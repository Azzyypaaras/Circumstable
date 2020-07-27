package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.block.entity.BlastFurnaceMachine;
import azzy.fabric.circumstable.util.container.BlastFurnaceScreen;
import azzy.fabric.circumstable.util.controller.BlastFurnaceController;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class GuiRegistry {

    @Environment(EnvType.CLIENT)
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID, (syncID, id, player, buf) -> new BlastFurnaceScreen(new BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player));
    }
}
