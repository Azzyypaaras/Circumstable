package azzy.fabric.circumstable.item;

import azzy.fabric.circumstable.staticentities.blockentity.FailingTransferEntity;
import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.staticentities.blockentity.SpeenTransferEntity;
import azzy.fabric.circumstable.util.interaction.HeatHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AngularItem extends Item {

    public AngularItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if(context.getWorld().getBlockEntity(pos) instanceof MachineEntity){
            MachineEntity entity = (MachineEntity) context.getWorld().getBlockEntity(context.getBlockPos());
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Block: " + I18n.translate(world.getBlockState(pos).getBlock().getTranslationKey())), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Current Speed: " + entity.getSpeed() + "rad/s"), null);
            if(entity instanceof SpeenTransferEntity)
                MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Failure Speed: " + ((SpeenTransferEntity) entity).getMaxSpeed() + "rad/s"), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
        }

        return ActionResult.FAIL;
    }
}
