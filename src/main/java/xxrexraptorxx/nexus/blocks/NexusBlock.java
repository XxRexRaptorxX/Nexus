package xxrexraptorxx.nexus.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.utils.Config;
import xxrexraptorxx.nexus.utils.NexusColors;

import java.util.List;
import java.util.Random;


public class NexusBlock extends Block {

	public static final Integer MAX_DESTRUCTION_LEVEL = 3; 		//one level higher destroys the block
	public static final IntegerProperty DESTRUCTION_LEVEL = IntegerProperty.create("level", 0, MAX_DESTRUCTION_LEVEL + 1);
	public static final EnumProperty<NexusColors> COLOR = EnumProperty.create("color", NexusColors.class);
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	protected static final VoxelShape CUSTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D);


	public NexusBlock() {
		super(Properties.of()
				.strength(100.0F, 5000.0F)
				.sound(SoundType.METAL)
				.mapColor(MapColor.DIAMOND)
				.lightLevel(value -> 10)
				.noOcclusion()
				.requiresCorrectToolForDrops()
				.instrument(NoteBlockInstrument.DRAGON)
				.pushReaction(PushReaction.BLOCK)
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
		String nexusColor = ForgeRegistries.BLOCKS.getKey(this).toString().substring(12);
		ItemStack stack = player.getUseItem();

		if (!level.isClientSide) {
			if (player.isCreative()) { //creative mode part
				changeNexusBlockstates(level, pos, state, null, true); //Ghost block fix for creative

				//remove the scoreboard objective with the coords of the nexus
				if (level.getScoreboard().getObjectiveNames().contains(nexusColor.toUpperCase() + "_NEXUS")) {
					level.getScoreboard().removeObjective(level.getScoreboard().getObjective(nexusColor.toUpperCase() + "_NEXUS"));
				}

			} else { //Destruction level change
				nexusLevelChange(false, level, state, pos, stack, player);

				if (state.getValue(DESTRUCTION_LEVEL) == MAX_DESTRUCTION_LEVEL) {            //Nexus is finally destroyed
					changeNexusBlockstates(level, pos, state, null, true);
					level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
					level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDER_DRAGON_DEATH, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
					popExperience(level.getServer().getLevel(player.level().dimension()), pos, Config.NEXUS_XP_AMOUNT.get());
					player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));

					CommandSourceStack source = new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(pos), Vec2.ZERO, (ServerLevel) level, 2, "nexus", Component.literal("Nexus").withStyle(ChatFormatting.getByName(nexusColor)), level.getServer(), player);

					if (player.getTeam() != null && player.getTeam().getColor() != null) { //fallback if the player has no team or team color
						level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(player.getDisplayName().getString() + " ").withStyle(player.getTeam().getColor()).append(Component.translatable("message.nexus.nexus_destruction").withStyle(ChatFormatting.getByName(nexusColor))), true);
						level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.system(Component.literal(player.getDisplayName().getString() + " ").withStyle(player.getTeam().getColor()).append(Component.translatable("message.nexus.nexus_destruction").withStyle(ChatFormatting.getByName(nexusColor))).getString()), source, ChatType.bind(ChatType.CHAT, source));

					} else {
						level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(player.getDisplayName().getString() + " ").append(Component.translatable("message.nexus.nexus_destruction").withStyle(ChatFormatting.getByName(nexusColor))), true);
						level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.system(Component.literal(player.getDisplayName().getString() + " ").append(Component.translatable("message.nexus.nexus_destruction").withStyle(ChatFormatting.getByName(nexusColor))).getString()), source, ChatType.bind(ChatType.CHAT, source));
					}

					//Gamemode change when lost
					if(Config.SPECTATOR_MODE_AFTER_LOST_NEXUS.get()) {
						try {
							List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();
							for (ServerPlayer serverPlayer : players) {

								if (serverPlayer.getTeam().getColor() == ChatFormatting.getByName(nexusColor)) {
									serverPlayer.setGameMode(GameType.SPECTATOR);
								}
							}
						} catch (Exception e) {
							Nexus.LOGGER.error(e);
							Nexus.LOGGER.error("To avoid this error, please add all players to a team and assign compatible colors to the teams!");
						}
					}

					//Drops
					if (Config.NEXUS_REWARDS.get().size() > 0) {
						for (String item : Config.NEXUS_REWARDS.get()) {
							try {
								ItemEntity drop = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 0.5D,
										new ItemStack(ForgeRegistries.ITEMS.getValue(
												//                                          get the mod prefix              |        get the item registry name      |         get the item amount
												new ResourceLocation(item.substring(item.indexOf('*') + 1, item.indexOf(':')), item.substring(item.indexOf(':') + 1))), Integer.parseInt(item.substring(0, item.indexOf('*')))));
								level.addFreshEntity(drop);
							} catch (Exception e) {
								Nexus.LOGGER.error("Invalid item entry in the Nexus Mod 'nexus_rewards' config option!");
							}
						}
					}

					//remove the scoreboard objective with the coords of the nexus
					if (level.getScoreboard().getObjectiveNames().contains(nexusColor.toUpperCase() + "_NEXUS")) {
						level.getScoreboard().removeObjective(level.getScoreboard().getObjective(nexusColor.toUpperCase() + "_NEXUS"));
					}
				}
			}
		}
		return false;
	}


	/**
	 * Changes of the Nexus and other features depending on its damage state.
	 * @param state = the nexus block
	 * @param positive = true means repairing & false means damaging
	 */
	public static void nexusLevelChange(Boolean positive, Level level, BlockState state, BlockPos pos, ItemStack stack, Player player) {
		String nexusColor = ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString().substring(12);
		Random random = new Random();

		if(!positive) {			/** Damage Nexus **/
			changeNexusBlockstates(level, pos, state, false, false);
			level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			state.getBlock().popExperience(level.getServer().getLevel(player.level().dimension()), pos, Config.NEXUS_XP_STAGE_AMOUNT.get());
			player.awardStat(Stats.ITEM_BROKEN.get(state.getBlock().asItem()));

			if(state.getValue(DESTRUCTION_LEVEL) != MAX_DESTRUCTION_LEVEL) {
				CommandSourceStack source = new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(pos), Vec2.ZERO, (ServerLevel) level, 2, "nexus", Component.literal("Nexus").withStyle(ChatFormatting.getByName(nexusColor)), level.getServer(), player);

				level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.nexus_level_" + (state.getValue(DESTRUCTION_LEVEL) + 1)).withStyle(ChatFormatting.getByName(nexusColor)), true); //if state is not max: send damage info text
				level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.system(Component.translatable("message.nexus.nexus_level_" + (state.getValue(DESTRUCTION_LEVEL) + 1)).withStyle(ChatFormatting.getByName(nexusColor)).getString()), source, ChatType.bind(ChatType.CHAT, source));
			}

		} else {                /** Repair Nexus **/
			if (!Config.NEXUS_REPAIRING.get() || state.getValue(DESTRUCTION_LEVEL) == 0) { //test if repairing is on or nexus is fully repaired
				player.sendSystemMessage(Component.translatable("message.nexus.not_repair").withStyle(ChatFormatting.getByName(nexusColor)));

			} else {
				changeNexusBlockstates(level, pos, state, true, false);
				level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.nexus_repair").withStyle(ChatFormatting.getByName(nexusColor)), true);

				player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
				player.getCooldowns().addCooldown(stack.getItem(), Config.REPAIR_COOLDOWN.get());
				stack.shrink(1);
			}
		}
	}

	/**
	 * Blockstate change of the Nexus.
	 * @param state = the nexus block
	 * @param positive = true means repairing & false means damaging
	 * @param destroyed = if the nexus should be destroyed
	 */
	private static void changeNexusBlockstates(Level level, BlockPos pos, BlockState state, @Nullable Boolean positive, Boolean destroyed) {
		if (destroyed) {
			if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
				level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 11);
			} else {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
				level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 11);
			}
		} else {
			if (positive) {
				if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
					level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) - 1).setValue(HALF, DoubleBlockHalf.LOWER), 11); //set blockstate to 1 level lower
					level.setBlock(pos.above(), state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) - 1).setValue(HALF, DoubleBlockHalf.UPPER), 11); //set blockstate to 1 level lower
				} else {
					level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) - 1).setValue(HALF, DoubleBlockHalf.UPPER), 11); //set blockstate to 1 level lower
					level.setBlock(pos.below(), state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) - 1).setValue(HALF, DoubleBlockHalf.LOWER), 11); //set blockstate to 1 level lower
				}
			} else {
				if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
					level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) + 1).setValue(HALF, DoubleBlockHalf.LOWER), 11); //set blockstate to 1 level lower
					level.setBlock(pos.above(), state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) + 1).setValue(HALF, DoubleBlockHalf.UPPER), 11); //set blockstate to 1 level lower
				} else {
					level.setBlock(pos, state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) + 1).setValue(HALF, DoubleBlockHalf.UPPER), 11); //set blockstate to 1 level lower
					level.setBlock(pos.below(), state.setValue(DESTRUCTION_LEVEL, state.getValue(DESTRUCTION_LEVEL) + 1).setValue(HALF, DoubleBlockHalf.LOWER), 11); //set blockstate to 1 level lower
				}
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


	//@Override
	//public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
	//	return CUSTOM_SHAPE;
	//}


	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextDouble() < 0.10D) {
			level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundSource.BLOCKS, 0.1F, 1.0F, false);
		}

		for (int i = 0; i < 3; ++i) {
			double x0 = (double) pos.getX() + random.nextDouble();
			double x1 = (double) pos.getY() + random.nextDouble() * 0.5D + 1.8D;
			double x2 = (double) pos.getZ() + random.nextDouble();
			double d0 = (double) pos.getX() + random.nextDouble();
			double d1 = (double) pos.getY() + random.nextDouble() * 0.5D + 0.5D;
			double d2 = (double) pos.getZ() + random.nextDouble();

			if (state.getValue(DESTRUCTION_LEVEL).equals(1)) {
				level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			} else if (state.getValue(DESTRUCTION_LEVEL).equals(2)) {
				level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			} else if (state.getValue(DESTRUCTION_LEVEL).equals(3)) {
				level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
			level.addParticle(ParticleTypes.CRIT, x0, x1, x2, 0.0D, 0.0D, 0.0D);
		}

		double d0 = (double) pos.getX() - 4 + random.nextInt(8);
		double d1 = (double) pos.getY() + random.nextInt(5);
		double d2 = (double) pos.getZ() - 4 + random.nextInt(8);
		level.addParticle(ParticleTypes.GLOW, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}




	@Override
	public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
		if(Config.GLOWING_EFFECT_FROM_NEXUS.get()) {
			player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, false, false, true));
		}
			return super.getDestroyProgress(state, player, level, pos);
	}


	/** Blockstate stuff **/

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DESTRUCTION_LEVEL).add(HALF).add(COLOR);
	}


	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState()
				.setValue(DESTRUCTION_LEVEL, 0)
				.setValue(HALF, DoubleBlockHalf.LOWER)
				.setValue(COLOR, NexusColors.valueOf(ForgeRegistries.BLOCKS.getKey(this).toString().substring(12).toUpperCase()));
	}

	/** Double Block stuff **/

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide && player.isCreative()) {
			preventCreativeDropFromBottomPart(level, pos, state, player);
		}

		super.playerWillDestroy(level, pos, state, player);
	}


	// => from DoublePlantBlock
	protected static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
		DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pPos.below();
			BlockState blockstate = pLevel.getBlockState(blockpos);
			if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				pLevel.setBlock(blockpos, blockstate1, 35);
				pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}


	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		BlockPos blockpos = pPos.below();
		BlockState blockstate = pLevel.getBlockState(blockpos);
		return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
	}


	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}
}