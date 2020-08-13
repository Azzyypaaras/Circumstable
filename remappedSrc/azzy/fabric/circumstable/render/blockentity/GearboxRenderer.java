package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.block.entity.ShaftMachine;
import azzy.fabric.circumstable.render.util.FFRenderLayers;
import azzy.fabric.circumstable.render.util.IORenderer;
import azzy.fabric.circumstable.render.util.RenderHelper;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GearboxEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Collection;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class GearboxRenderer<T extends GearboxEntity> extends BlockEntityRenderer<T> implements IORenderer<T> {

    protected Identifier texture;

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

    }

    public void renderGear(MatrixStack matrices, VertexConsumerProvider consumers, int light, Identifier texture, float scale, int symmetries){
        if(scale < 2)
            matrices.scale(1, 1, 1.5f);
        RenderHelper.renderScaledCuboid(matrices, consumers, 255, light, (4 / 16f) * scale, (4 / 16f) * scale, (1 / 16f) * scale, texture, RenderHelper.Scaling.CENTER, true);
        if(symmetries == 1 || symmetries == 0){
            matrices.scale(1.001f, 1.001f, 1.001f);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
            RenderHelper.renderScaledCuboid(matrices, consumers, 255, light, (4 / 16f) * scale, (4 / 16f) * scale, (1 / 16f) * scale, texture, RenderHelper.Scaling.CENTER, true);
            matrices.scale(0.999f, 0.999f, 0.999f);
        } else {
            for (int i = (symmetries - 1) * 3; i > 0; i--) {
                matrices.scale(1.001f, 1.001f, 1.001f);
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45f / symmetries));
                RenderHelper.renderScaledCuboid(matrices, consumers, 255, light, (4 / 16f) * scale, (4 / 16f) * scale, (1 / 16f) * scale, texture, RenderHelper.Scaling.CENTER, true);
            }
            for (int i = (symmetries - 1) * 3; i > 0; i--)
                matrices.scale(0.999f, 0.999f, 0.999f);
        }
        if(scale < 2)
            matrices.scale(1, 1, 2/3f);
    }
}
