package br.com.xenos.entity.ai.goal;

import br.com.xenos.entity.XenosEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public class PickupItemGoal extends Goal {
    private final XenosEntity xenos;
    private ItemEntity targetItem;

    public PickupItemGoal(XenosEntity xenos) {
        this.xenos = xenos;
    }

    @Override
    public boolean canUse() {
        if (this.xenos.isBusy()) {
            return false;
        }
        List<ItemEntity> items = this.xenos.level().getEntitiesOfClass(ItemEntity.class, this.xenos.getBoundingBox().inflate(8.0D));
        if (items.isEmpty()) {
            return false;
        }
        this.targetItem = items.get(0); // Simple logic: pick up the first item found
        return true;
    }

    @Override
    public void start() {
        if (this.targetItem != null) {
            this.xenos.setBusy(true);
            this.xenos.getNavigation().moveTo(this.targetItem, 1.0D);
        }
    }

    @Override
    public void stop() {
        this.xenos.setBusy(false);
        this.targetItem = null;
    }

    @Override
    public void tick() {
        if (this.targetItem != null && this.xenos.getNavigation().isDone()) {
            this.xenos.pickUpItem(this.targetItem);
            this.stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetItem != null && !this.targetItem.isRemoved();
    }
}