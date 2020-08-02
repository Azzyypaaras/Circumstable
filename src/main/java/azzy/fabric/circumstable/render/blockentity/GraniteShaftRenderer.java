package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.GraniteShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

import static azzy.fabric.circumstable.registry.ItemRegistry.*;

public class GraniteShaftRenderer extends ShaftRenderer<GraniteShaftEntity>{

    public GraniteShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, STICK_GRANITE);
    }
}
