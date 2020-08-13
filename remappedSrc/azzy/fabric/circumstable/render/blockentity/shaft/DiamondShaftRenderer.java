package azzy.fabric.circumstable.render.blockentity.shaft;

import azzy.fabric.circumstable.render.blockentity.ShaftRenderer;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.DiamondShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GraniteShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;

import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_DIAMOND;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_GRANITE;

public class DiamondShaftRenderer extends ShaftRenderer<DiamondShaftEntity> {

    public DiamondShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, new Identifier("minecraft", "textures/block/diamond_block.png"));
    }
}
