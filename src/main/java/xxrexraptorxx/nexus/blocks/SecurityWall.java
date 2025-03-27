package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.nexus.main.References;

import java.util.function.Consumer;


public class SecurityWall extends Block implements TooltipProvider {

	public SecurityWall(Properties properties) {
		super(properties);
	}


	@Override
	public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> list, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
		list.accept(Component.translatable("message." + References.MODID + ".unbreakable").withStyle(ChatFormatting.GRAY));
	}

}
