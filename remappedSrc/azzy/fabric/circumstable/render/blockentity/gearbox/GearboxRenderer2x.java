package azzy.fabric.circumstable.render.blockentity.gearbox;

import azzy.fabric.circumstable.render.blockentity.GearboxRenderer;
import azzy.fabric.circumstable.render.util.RenderHelper;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.GearboxEntity;
import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.block.entity.BaseMachine.FACING;
import static azzy.fabric.circumstable.block.entity.GearboxMachine.TYPE;

public class GearboxRenderer2x<T extends GearboxEntity> extends GearboxRenderer<T> {

    public GearboxRenderer2x(BlockEntityRenderDispatcher dispatcher, Identifier texture) {
        super(dispatcher, texture);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);

        texture = new Identifier(MOD_ID, "textures/block/hsla_steel.png");

        Direction facing = entity.getCachedState().get(FACING);
        boolean inverted = entity.getCachedState().get(TYPE);

        matrices.push();
        if(inverted){
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
        }

        matrices.translate(0.5, 0.5, 0.5);

        RenderHelper.applyPermutations(matrices, facing, entity, tickDelta, false, false);
        matrices.translate(0, 0, -6/16f);

        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f),  (5/16f), texture, RenderHelper.Scaling.CENTER, true);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(0, -0.002f, 0);
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), (5/16f) + 0.001f, texture, RenderHelper.Scaling.CENTER, true);

        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(0, 0, 3/64f);
        renderGear(matrices, vertexConsumers, light, texture, 1f, 1);
        matrices.translate(0, 0, -3/64f);
        matrices.translate(0, 0, 12/16f);

        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f),  (5/16f), texture, RenderHelper.Scaling.CENTER, true);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(0, -0.002f, 0);
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), (5/16f) + 0.001f, texture, RenderHelper.Scaling.CENTER, true);

        matrices.translate(0, 0, -3/64f);
        renderGear(matrices, vertexConsumers, light, texture, 0.8f, 1);
        matrices.translate(0, 0, 3/64f);
        matrices.pop();

        matrices.push();

        matrices.translate(0.5, 0.3, 0.3);

        RenderHelper.applyPermutations(matrices, facing, entity, tickDelta, true, true);
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), (14/16f), texture, RenderHelper.Scaling.CENTER, true);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(0, -0.002f, 0);
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), (14/16f) + 0.001f, texture, RenderHelper.Scaling.CENTER, true);

        matrices.translate(0, 0, -6/16f);
        matrices.translate(0, 0, 3/64f);
        renderGear(matrices, vertexConsumers, light, texture, 0.8f, 1);
        matrices.translate(0, 0, -3/64f);

        matrices.translate(0, 0, 12/16f);
        matrices.translate(0, 0, -3/64f);
        renderGear(matrices, vertexConsumers, light, texture, 1f, 1);
        matrices.translate(0, 0, 3/64f);

        matrices.pop();
    }

}
