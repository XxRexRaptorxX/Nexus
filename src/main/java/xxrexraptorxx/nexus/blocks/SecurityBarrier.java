package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.utils.Config;

import java.util.function.Consumer;


public class SecurityBarrier extends HalfTransparentBlock implements TooltipProvider{

	protected static final VoxelShape CUSTOM_COLLISION_AABB = Block.box(0.0625D, 0.0625D, 0.0625D, 15.9375D, 15.9375D, 15.9375D);


	public SecurityBarrier(Properties properties) {
		super(properties);
	}


	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return CUSTOM_COLLISION_AABB;
	}


	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn, InsideBlockEffectApplier p_405359_) {
		if (!level.isClientSide && Config.BARRIER_DAMAGE.get()) {
			if (entityIn instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) entityIn;

				entity.hurt(level.damageSources().generic(), 1.0F);
			}
		}
	}


	@Override
	public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> list, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
		list.accept(Component.translatable("message." + References.MODID + ".unbreakable").withStyle(ChatFormatting.GRAY));
	}
}
