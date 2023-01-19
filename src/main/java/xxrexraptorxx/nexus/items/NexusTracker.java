package xxrexraptorxx.nexus.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xxrexraptorxx.nexus.utils.Config;

import java.util.List;

public class NexusTracker extends Item {

    public NexusTracker() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(1)
                .durability(10)
        );
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("message.nexus.tracker.desc").withStyle(ChatFormatting.GRAY));

        if (!Config.NEXUS_TRACKING.get()) {
            list.add(Component.translatable("message.nexus.function_disabled").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }


    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }
}
