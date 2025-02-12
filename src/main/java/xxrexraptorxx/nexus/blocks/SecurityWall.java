package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.nexus.main.References;

import java.util.List;


public class SecurityWall extends Block {

	public SecurityWall(Properties properties) {
		super(properties);
	}


	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message." + References.MODID + ".unbreakable").withStyle(ChatFormatting.GRAY));
	}

}
