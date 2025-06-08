package br.com.xenos.entity.ai.goal;

import br.com.xenos.entity.XenosEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class BreakBlockGoal extends Goal {
    private final XenosEntity xenos;
    private BlockPos targetBlock;

    public BreakBlockGoal(XenosEntity xenos) {
        this.xenos = xenos;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.xenos.isBusy()) {
            return false;
        }
        this.targetBlock = findTargetBlock();
        return this.targetBlock != null;
    }

    @Override
    public void start() {
        if (this.targetBlock != null) {
            this.xenos.setBusy(true);
            this.xenos.getNavigation().moveTo(this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ(), 1.0D);
        }
    }

    @Override
    public void stop() {
        this.xenos.setBusy(false);
        this.targetBlock = null;
    }

    @Override
    public void tick() {
        if (this.targetBlock != null && this.xenos.getNavigation().isDone()) {
            this.xenos.level().destroyBlock(this.targetBlock, true, this.xenos);
            // Look for another log block nearby
            this.targetBlock = findTargetBlock();
            if (this.targetBlock == null) {
                this.stop();
            } else {
                this.xenos.getNavigation().moveTo(this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ(), 1.0D);
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetBlock != null && !this.xenos.isBusy();
    }

    private BlockPos findTargetBlock() {
        BlockPos xenosPos = this.xenos.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(xenosPos.offset(-8, -3, -8), xenosPos.offset(8, 3, 8))) {
            BlockState state = this.xenos.level().getBlockState(pos);
            if (state.is(BlockTags.LOGS)) {
                return pos;
            }
        }
        return null;
    }
}