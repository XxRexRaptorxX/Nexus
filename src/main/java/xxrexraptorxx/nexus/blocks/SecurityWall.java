package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class SecurityWall extends Block {

	public SecurityWall() {
		super(Properties.of()
				.strength(-1.0F, 3600000.0F)
				.sound(SoundType.METAL)
				.mapColor(MapColor.COLOR_LIGHT_GRAY)
				.requiresCorrectToolForDrops()
				.instrument(NoteBlockInstrument.BELL)
				.pushReaction(PushReaction.BLOCK)
				.noLootTable()
		);
	}


	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message.nexus.unbreakable").withStyle(ChatFormatting.GRAY));
	}

}
