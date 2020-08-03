package azzy.fabric.circumstable.render.blockentity.shaft;

import azzy.fabric.circumstable.render.blockentity.ShaftRenderer;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_STEEL;

public class SteelShaftRenderer extends ShaftRenderer<SteelShaftEntity> {

    public SteelShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, new Identifier(MOD_ID, "textures/block/hsla_steel.png"));
    }
}
