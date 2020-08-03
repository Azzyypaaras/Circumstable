package azzy.fabric.circumstable.render.blockentity.gearbox;

import azzy.fabric.circumstable.render.blockentity.GearboxRenderer;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GearboxEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GearboxRenderer2x<T extends GearboxEntity> extends GearboxRenderer<T> {

    public GearboxRenderer2x(BlockEntityRenderDispatcher dispatcher, Identifier texture) {
        super(dispatcher, texture);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
    }
}
