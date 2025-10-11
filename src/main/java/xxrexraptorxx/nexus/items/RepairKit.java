package xxrexraptorxx.nexus.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import xxrexraptorxx.magmacore.utils.FormattingHelper;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.utils.Config;

import java.util.function.Consumer;

public class RepairKit extends Item {

    public RepairKit(Item.Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> list, TooltipFlag flag) {
        list.accept(FormattingHelper.setMessageComponent(References.MODID, "repair_kit.desc").withStyle(ChatFormatting.GOLD));

        if (!Config.getNexusRepairingEnabled()) {
            list.accept(FormattingHelper.setMessageComponent(References.MODID, "function_disabled").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
