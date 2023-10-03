package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class SecurityWall extends Block {

	public SecurityWall() {
		super(Properties.of(Material.METAL)
				.strength(-1.0F, 3600000.0F)
				.sound(SoundType.METAL)
				.requiresCorrectToolForDrops()
				.noLootTable()
				.color(MaterialColor.WOOD)
		);
	}


	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message.nexus.unbreakable").withStyle(ChatFormatting.GRAY));
	}


	@Override
	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.BLOCK;
	}

}
