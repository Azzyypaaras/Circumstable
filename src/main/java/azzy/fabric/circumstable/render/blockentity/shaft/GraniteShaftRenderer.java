package azzy.fabric.circumstable.render.blockentity.shaft;

import azzy.fabric.circumstable.render.blockentity.ShaftRenderer;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GraniteShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;

import static azzy.fabric.circumstable.registry.ItemRegistry.*;

public class GraniteShaftRenderer extends ShaftRenderer<GraniteShaftEntity> {

    public GraniteShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, new Identifier("minecraft", "textures/block/polished_granite.png"));
    }
}
