package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.render.util.IORenderer;
import azzy.fabric.circumstable.render.util.RenderHelper;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GearboxEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Collection;

public class GearboxRenderer<T extends GearboxEntity> extends BlockEntityRenderer<T> implements IORenderer<T> {

    protected final Identifier texture;

    public GearboxRenderer(BlockEntityRenderDispatcher dispatcher, Identifier texture) {
        super(dispatcher);
        this.texture = texture;
    }

    @Override
    public Collection<Direction> getInputs(T entity) {
        return entity.getInputs();
    }

    @Override
    public Collection<Direction> getOutputs(T entity) {
        return entity.getOutputs();
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        renderIO(matrices, vertexConsumers, entity, tickDelta);

        matrices.push();
        matrices.translate(0, 10, 0);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.getWorld().getTime() + tickDelta));
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, RenderHelper.MAX_LIGHT, 1, 0.5f, 0.65f, new Identifier("circumstable", "textures/block/tungsten_block.png"), RenderHelper.Scaling.CENTER, true);
        matrices.pop();
    }
}
