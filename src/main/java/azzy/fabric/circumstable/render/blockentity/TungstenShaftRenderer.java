package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.TungstenShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_TITANIUM;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_TUNGSTEN;

public class TungstenShaftRenderer extends ShaftRenderer<TungstenShaftEntity>{

    public TungstenShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, STICK_TUNGSTEN);
    }
}
