package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.Item;

import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_STEEL;

public class SteelShaftRenderer extends ShaftRenderer<SteelShaftEntity>{

    public SteelShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, STICK_STEEL);
    }
}
