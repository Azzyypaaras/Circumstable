package azzy.fabric.circumstable.render.blockentity.shaft;

import azzy.fabric.circumstable.render.blockentity.ShaftRenderer;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.TitaniumShaftEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_STEEL;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_TITANIUM;

public class TitaniumShaftRenderer extends ShaftRenderer<TitaniumShaftEntity> {

    public TitaniumShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, new Identifier(MOD_ID, "textures/block/titanium_block.png"));
    }
}
