package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NexusBlock extends Block {

	public static final IntegerProperty DESTRUCTION_LEVEL = BlockStateProperties.LEVEL;
	protected static final VoxelShape CUSTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D);


	public NexusBlock() {
		super(Properties.of(Material.METAL)
				.strength(Config.NEXUS_HARDNESS.get().floatValue(), 5000.0F)
				.sound(SoundType.METAL)
				.color(MaterialColor.DIAMOND)
				.lightLevel(value -> 10)
				.noOcclusion()
				.requiresCorrectToolForDrops()
		);
	}


	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message.nexus.nexus.desc").withStyle(ChatFormatting.GOLD));
	}


	@Override
	public int getExpDrop(BlockState state, LevelReader level, RandomSource random, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
		return Config.NEXUS_XP_AMOUNT.get();
	}


	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		ArrayList<ItemStack> rewards = new ArrayList<>();
		Random random = new Random();

		if(!level.isClientSide && !player.isCreative()) {
			if (state.getValue(DESTRUCTION_LEVEL).equals(0)) {
				level.setBlock(pos, this.defaultBlockState().setValue(DESTRUCTION_LEVEL, 1), 11);
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				popExperience(level.getServer().getLevel(player.getLevel().dimension()), pos, Config.NEXUS_XP_STAGE_AMOUNT.get());
				level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.unsigned(Component.translatable("message.nexus.nexus_level_1.desc").withStyle(ChatFormatting.getByName(ForgeRegistries.BLOCKS.getKey(this).toString().substring(19)))), new ChatSender(player.getUUID(), Component.literal("!")), ChatType.CHAT);


			} else if (state.getValue(DESTRUCTION_LEVEL).equals(1)) {
				level.setBlock(pos, this.defaultBlockState().setValue(DESTRUCTION_LEVEL, 2), 11);
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				popExperience(level.getServer().getLevel(player.getLevel().dimension()), pos, Config.NEXUS_XP_STAGE_AMOUNT.get());
				level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.unsigned(Component.translatable("message.nexus.nexus_level_2.desc").withStyle(ChatFormatting.getByName(ForgeRegistries.BLOCKS.getKey(this).toString().substring(19)))), new ChatSender(player.getUUID(), Component.literal("!")), ChatType.CHAT);

			} else {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDER_DRAGON_DEATH, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				popExperience(level.getServer().getLevel(player.getLevel().dimension()), pos, Config.NEXUS_XP_AMOUNT.get());
				level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.unsigned(Component.translatable(player.getDisplayName() + "message.nexus.nexus_level_3.desc").withStyle(ChatFormatting.getByName(ForgeRegistries.BLOCKS.getKey(this).toString().substring(19)))), new ChatSender(player.getUUID(), Component.literal("!")), ChatType.CHAT);

				if (Config.NEXUS_REWARDS.get().size() > 0) {
					for (String item : Config.NEXUS_REWARDS.get()) {
						try {
							rewards.add(new ItemStack(ForgeRegistries.ITEMS.getValue(
									//                                          get the mod prefix              |        get the item registry name      |         get the item amount
									new ResourceLocation(item.substring(item.indexOf('*') + 1, item.indexOf(':')), item.substring(item.indexOf(':') + 1))), Integer.parseInt(item.substring(0, item.indexOf('*')))));

						} catch (Exception e) {
							Nexus.LOGGER.error("Invalid item entry in the Nexus Mod 'nexus_rewards' config option!");
						}
					}
				}
			}
		}
		return false;
	}


	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return CUSTOM_SHAPE;
	}


	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return CUSTOM_SHAPE;
	}


	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		for(int i = 0; i < 3; ++i) {
			double d0 = (double)pos.getX() + random.nextDouble();
			double d1 = (double)pos.getY() + random.nextDouble() * 0.5D + 1.8D;
			double d2 = (double)pos.getZ() + random.nextDouble();
			level.addParticle(ParticleTypes.CRIT, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}

		if (state.getValue(DESTRUCTION_LEVEL).equals(1)) {
			for(int i = 0; i < 3; ++i) {
				double d0 = (double)pos.getX() + random.nextDouble();
				double d1 = (double)pos.getY() + random.nextDouble() * 0.5D + 0.5D;
				double d2 = (double)pos.getZ() + random.nextDouble();
				level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}

		} else if (state.getValue(DESTRUCTION_LEVEL).equals(2)) {
			for(int i = 0; i < 3; ++i) {
				double d0 = (double)pos.getX() + random.nextDouble();
				double d1 = (double)pos.getY() + random.nextDouble() * 0.5D + 0.5D;
				double d2 = (double)pos.getZ() + random.nextDouble();
				level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
			for(int i = 0; i < 3; ++i) {
				double d0 = (double)pos.getX() + random.nextDouble();
				double d1 = (double)pos.getY() + random.nextDouble() * 0.5D + 0.5D;
				double d2 = (double)pos.getZ() + random.nextDouble();
				level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}


	@Override
	public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
		player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, false, true));
			return super.getDestroyProgress(state, player, level, pos);
	}



	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DESTRUCTION_LEVEL);
	}


	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(DESTRUCTION_LEVEL, 0);
	}

}