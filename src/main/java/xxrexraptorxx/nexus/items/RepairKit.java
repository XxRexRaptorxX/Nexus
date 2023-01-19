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

public class RepairKit extends Item {

    public RepairKit() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
        );
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("message.nexus.repair_kit.desc").withStyle(ChatFormatting.GOLD));

        if (!Config.NEXUS_REPAIRING.get()) {
            list.add(Component.translatable("message.nexus.function_disabled").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
