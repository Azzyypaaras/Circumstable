package azzy.fabric.circumstable.render.util;

import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Matrix4f;

import static azzy.fabric.circumstable.render.util.FFRenderLayers.OVERLAY;

public class RenderHelper {

    public static void renderOverlayCuboid(MatrixStack matrices, VertexConsumerProvider consumers, int r, int g, int b, int a, float sizeX, float sizeY, float sizeZ, boolean offset){

        VertexConsumer consumer = consumers.getBuffer(OVERLAY);
        Matrix4f model = matrices.peek().getModel();

        if(offset) {
            matrices.translate(-0.001, -0.001, -0.001);
            matrices.scale(1.002f, 1.002f, 1.002f);
        }

        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();

        matrices.translate(1, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(0, 0, -1);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();

        matrices.translate(1, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.translate(-1, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.translate(0, 1, 0);
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
    }
}
