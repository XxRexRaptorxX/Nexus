package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
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

	public static final Integer MAX_DESTRUCTION_LEVEL = 3; 		//one level higher destroys the block
	public static final IntegerProperty DESTRUCTION_LEVEL = IntegerProperty.create("level", 0, MAX_DESTRUCTION_LEVEL + 1) ;
	protected static final VoxelShape CUSTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D);


	public NexusBlock() {
		super(Properties.of(Material.METAL)
				.strength(100.0F, 5000.0F)
				.sound(SoundType.METAL)
				.color(MaterialColor.DIAMOND)
				.lightLevel(value -> 10)
				.noOcclusion()
				.requiresCorrectToolForDrops()
		);
	}


	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> list, TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			list.add(Component.translatable("message.nexus.nexus.desc").withStyle(ChatFormatting.GOLD));
			list.add(Component.translatable("message.nexus.hold_shift.desc").withStyle(ChatFormatting.GREEN));
		} else {
			list.add(Component.translatable("message.nexus.gamemode_line_1").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE));
			list.add(Component.translatable("message.nexus.gamemode_line_2").withStyle(ChatFormatting.GRAY));
			list.add(Component.translatable("message.nexus.gamemode_line_3").withStyle(ChatFormatting.GRAY));
			list.add(Component.translatable("message.nexus.gamemode_line_4").withStyle(ChatFormatting.GRAY));
			list.add(Component.translatable("message.nexus.gamemode_line_5").withStyle(ChatFormatting.GRAY));
			list.add(Component.translatable("message.nexus.gamemode_line_6").withStyle(ChatFormatting.GRAY));
		}
	}


	@Override
	public int getExpDrop(BlockState state, LevelReader level, RandomSource random, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
		return Config.NEXUS_XP_AMOUNT.get();
	}


	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		Random random = new Random();
		ArrayList<ItemStack> rewards = new ArrayList<>();
		String nexusColor = ForgeRegistries.BLOCKS.getKey(this).toString().substring(12);
		ItemStack stack = player.getUseItem();

		if (!level.isClientSide) {
			if (player.isCreative()) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);				//Ghost block fix for creative

			} else { //Destruction level change
				nexusLevelChange(false, level, state, pos, stack, player);

				if (state.getValue(DESTRUCTION_LEVEL) == MAX_DESTRUCTION_LEVEL) {            //Nexus is finally destroyed
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
					level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
					level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDER_DRAGON_DEATH, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
					popExperience(level.getServer().getLevel(player.getLevel().dimension()), pos, Config.NEXUS_XP_AMOUNT.get());
					level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(player.getDisplayName().getString() + " ").withStyle(player.getTeam().getColor()).append(Component.translatable("message.ageofweapons.nexus_destruction").withStyle(ChatFormatting.getByName(nexusColor))), false);

					//Gamemode change when lost
					if(Config.SPECTATOR_MODE_AFTER_LOST_NEXUS.get()) {
						List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();
						for (ServerPlayer serverPlayer : players) {

							if (serverPlayer.getTeam().getColor() == ChatFormatting.getByName(nexusColor)) {
								serverPlayer.setGameMode(GameType.SPECTATOR);
							}
						}
					}

					if (Config.NEXUS_REWARDS.get().size() > 0) {            //Drops
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
		}
		return false;
	}


	/**
	 * Blockstate change of the Nexus and other features.
	 * @param positive = true means repairing & false means damaging
	 */
	public static void nexusLevelChange(Boolean positive, Level level, BlockState state, BlockPos pos, ItemStack stack, Player player) {
		String nexusColor = ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString().substring(12);
		Random random = new Random();

		if(!positive) {			/** Damage Nexus **/
			level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) + 1), 11); //set blockstate to 1 level higher
			level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			state.getBlock().popExperience(level.getServer().getLevel(player.getLevel().dimension()), pos, Config.NEXUS_XP_STAGE_AMOUNT.get());
			player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));

			if(state.getValue(DESTRUCTION_LEVEL) != MAX_DESTRUCTION_LEVEL) level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.nexus_level_" + (state.getValue(DESTRUCTION_LEVEL) + 1)).withStyle(ChatFormatting.getByName(nexusColor)), true); //if state is not max: send damage info text


		} else {                /** Repair Nexus **/
			if (!Config.NEXUS_REPAIRING.get() || state.getValue(DESTRUCTION_LEVEL) == 0) { //test if repairing is on or nexus is fully repaired
				level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.not_repair").withStyle(ChatFormatting.getByName(nexusColor)), true);

			} else {
				level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) - 1), 11); //set blockstate to 1 level lower
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.nexus_repair").withStyle(ChatFormatting.getByName(nexusColor)), true);

				player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
				player.getCooldowns().addCooldown(stack.getItem(), Config.REPAIR_COOLDOWN.get());
				stack.shrink(1);
			}
		}
	}


	/**
	 * Tests if the player is in a team and returns the color of his team.
	 * With fallback, if the player has no team.
	 *
	 * @return ChatFormatting Color
	 */
	private static ChatFormatting getTeamColor(Player player) {
		if(player.getTeam() != null) {
			return player.getTeam().getColor();

		} else {
			return ChatFormatting.WHITE;
		}
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

		} else if (state.getValue(DESTRUCTION_LEVEL).equals(3)) {
			for(int i = 0; i < 3; ++i) {
				double d0 = (double)pos.getX() + random.nextDouble();
				double d1 = (double)pos.getY() + random.nextDouble() * 0.5D + 0.5D;
				double d2 = (double)pos.getZ() + random.nextDouble();
				level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
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
		if(Config.GLOWING_EFFECT_FROM_NEXUS.get()) {
			player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, false, true));
		}
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