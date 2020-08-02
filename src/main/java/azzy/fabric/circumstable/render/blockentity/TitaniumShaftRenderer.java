package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.TitaniumShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_STEEL;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_TITANIUM;

public class TitaniumShaftRenderer extends ShaftRenderer<TitaniumShaftEntity>{

    public TitaniumShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, STICK_TITANIUM);
    }
}
