package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xxrexraptorxx.nexus.utils.Config;

import java.util.List;


public class SecurityBarrier extends HalfTransparentBlock {

	protected static final VoxelShape CUSTOM_COLLISION_AABB = Block.box(0.0625D, 0.0625D, 0.0625D, 15.9375D, 15.9375D, 15.9375D);


	public SecurityBarrier() {
		super(Properties.of()
				.strength(-1.0F, 3600000.0F)
				.mapColor(MapColor.COLOR_LIGHT_BLUE)
				.requiresCorrectToolForDrops()
				.instrument(NoteBlockInstrument.BASS)
				.pushReaction(PushReaction.BLOCK)
				.noLootTable()
				.lightLevel(value -> 6)
				.noOcclusion()
		);
	}


	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message.nexus.unbreakable").withStyle(ChatFormatting.GRAY));
	}


	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return CUSTOM_COLLISION_AABB;
	}


	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
		if (!level.isClientSide && Config.BARRIER_DAMAGE.get()) {
			if (entityIn instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) entityIn;

				entity.hurt(level.damageSources().generic(), 1.0F);
			}
		}
	}
	}
