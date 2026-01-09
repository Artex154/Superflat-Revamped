package be.artex.item.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SlimeMealItem extends Item {
    public static final int GRASS_SPREAD_WIDTH = 2;
    public static final int GRASS_SPREAD_HEIGHT = 1;
    public static final int GRASS_COUNT_MULTIPLIER = 3;

    public SlimeMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        ItemStack itemStack = useOnContext.getItemInHand();

        if (growCrop(itemStack, level, blockPos)) {
            if (level.isClientSide())
                return InteractionResult.PASS;

            itemStack.causeUseVibration(useOnContext.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
            level.levelEvent(1505, blockPos, 15);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean growCrop(ItemStack itemStack, Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        Block blockType = blockState.getBlock();

        if (!(blockType instanceof BonemealableBlock bonemealableBlock))
            return false;

        if (!bonemealableBlock.isValidBonemealTarget(level, blockPos, blockState))
            return false;

        if (!(level instanceof ServerLevel))
            return false;

        if (bonemealableBlock.isBonemealSuccess(level, level.random, blockPos, blockState))
            bonemealableBlock.performBonemeal((ServerLevel)level, level.random, blockPos, blockState);

        itemStack.shrink(1);

        return true;
    }
}
