package azzy.fabric.circumstable.render.blockentity;

import azzy.fabric.circumstable.block.entity.ShaftMachine;
import azzy.fabric.circumstable.render.util.IORenderer;
import azzy.fabric.circumstable.render.util.RenderHelper;
import azzy.fabric.circumstable.staticentities.blockentity.FailingTransferEntity;
import azzy.fabric.circumstable.staticentities.blockentity.SpeenTransferEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.SteelShaftEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

import java.util.Collection;
import java.util.List;

import static azzy.fabric.circumstable.Circumstable.CLog;
import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.registry.ItemRegistry.STICK_STEEL;
import static azzy.fabric.circumstable.render.util.FFRenderLayers.OVERLAY;

public class ShaftRenderer<T extends FailingTransferEntity> extends BlockEntityRenderer<T> implements IORenderer<T>{

    private final Identifier texture;

    public ShaftRenderer(BlockEntityRenderDispatcher dispatcher, Identifier texture) {
        super(dispatcher);
        this.texture = texture;
    }

    @Override
    public boolean rendersOutsideBoundingBox(T blockEntity) {
        return true;
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        Direction facing = entity.getWorld().getBlockState(entity.getPos()).get(ShaftMachine.FACING);

        renderIO(matrices, vertexConsumers, entity, tickDelta);

        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);

        if(facing == Direction.NORTH || facing == Direction.SOUTH){
            matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion((((entity.getWorld().getTime() + tickDelta) * entity.getSpeed())/1100)));
        }
        else if(facing == Direction.EAST || facing == Direction.WEST){
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion((((entity.getWorld().getTime() + tickDelta) * entity.getSpeed())/1100)));
        }
        else{
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion((((entity.getWorld().getTime() + tickDelta) * entity.getSpeed())/1100)));
        }

        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), 1 + (1/16f), texture, RenderHelper.Scaling.CENTER, true);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(0, -0.002f, 0);
        RenderHelper.renderScaledCuboid(matrices, vertexConsumers, 255, light, (1.5f/16f), (1.5f/16f), 1 + (1/16f) + 0.001f, texture, RenderHelper.Scaling.CENTER, true);

        matrices.pop();
    }

    @Override
    public Collection<Direction> getInputs(T entity) {
        return entity.getInputs();
    }

    @Override
    public Collection<Direction> getOutputs(T entity) {
        return entity.getOutputs();
    }
}
