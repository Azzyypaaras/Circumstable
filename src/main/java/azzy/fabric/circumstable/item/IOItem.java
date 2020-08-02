package azzy.fabric.circumstable.item;

import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.staticentities.blockentity.SpeenTransferEntity;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IOItem extends Item {

    public IOItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if(world.getBlockEntity(pos) instanceof MachineEntity){
            MachineEntity entity = (MachineEntity) world.getBlockEntity(pos);
            entity.renderIO();
        }

        return ActionResult.FAIL;
    }
}
