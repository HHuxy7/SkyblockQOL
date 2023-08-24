package skyblockqol.mixins;

import skyblockqol.skyblockqol;
import skyblockqol.config.skyblockqolConfig;
import skyblockqol.features.ImpactDisplay;
import skyblockqol.features.ReaperDisplay;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "showDurabilityBar(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"),
            cancellable = true, remap = false)
    public void shouldShowDur(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (skyblockqolConfig.INSTANCE.getDisplayReaperCD())
            ReaperDisplay.INSTANCE.shouldDisplay(stack, cir);
        if (skyblockqolConfig.INSTANCE.getDisplayImpactCD())
            ImpactDisplay.INSTANCE.shouldDisplay(stack, cir);
    }
    @Inject(method = "getDurabilityForDisplay(Lnet/minecraft/item/ItemStack;)D", at = @At("HEAD"),
            cancellable = true, remap = false)
    public void getItemHealthDisplayVal(ItemStack stack, CallbackInfoReturnable<Double> cir) {
        if (skyblockqolConfig.INSTANCE.getDisplayReaperCD())
            ReaperDisplay.INSTANCE.calcDurability(stack, cir);
        if (skyblockqolConfig.INSTANCE.getDisplayImpactCD())
            ImpactDisplay.INSTANCE.calcDurability(stack, cir);
    }
}
