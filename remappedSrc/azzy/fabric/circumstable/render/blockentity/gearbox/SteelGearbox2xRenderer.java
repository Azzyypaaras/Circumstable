package azzy.fabric.circumstable.render.blockentity.gearbox;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.gearboxes.SteelGearboxEntity2;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;

public class SteelGearbox2xRenderer extends GearboxRenderer2x<SteelGearboxEntity2> {

    public SteelGearbox2xRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher, new Identifier(""));
    }
}
