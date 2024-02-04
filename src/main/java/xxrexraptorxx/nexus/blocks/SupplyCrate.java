package xxrexraptorxx.nexus.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.utils.Config;

import javax.annotation.Nullable;
import java.util.Random;


public class SupplyCrate extends FallingBlock {

	public SupplyCrate() {
		super(Properties.of()
				.strength(0.1F, 0.0F)
				.sound(SoundType.WOOD)
				.lightLevel(value -> 6)
				.mapColor(MapColor.WOOD)
				.instrument(NoteBlockInstrument.BASS)
		);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
		Random random = new Random();

		if (!level.isClientSide) {
		popExperience(level.getServer().getLevel(player.level().dimension()), pos, Config.SUPPLY_CRATE_XP_AMOUNT.get());

			//test if config list is not empty & loot amount is not deactivated
			if (Config.SUPPLY_CRATE_LOOT.get().size() > 0 && Config.SUPPLY_CRATE_LOOT_AMOUNT.get() > 0) {

				try {
					//itemstack amount
					for (int i = 0; i < Config.SUPPLY_CRATE_LOOT_AMOUNT.get(); i++) {
						//get random loot entry from config list
						String item = Config.SUPPLY_CRATE_LOOT.get().get(random.nextInt(Config.SUPPLY_CRATE_LOOT.get().size()));

						//process the entry and drop the item
						ItemEntity drop = new ItemEntity(level, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D,
								new ItemStack(BuiltInRegistries.ITEM.get(
										//                                          get the mod prefix              |        get the item registry name      |         get the item amount
										new ResourceLocation(item.substring(item.indexOf('*') + 1, item.indexOf(':')), item.substring(item.indexOf(':') + 1))), Integer.parseInt(item.substring(0, item.indexOf('*')))));
						level.addFreshEntity(drop);
					}
				} catch (Exception e) {
					Nexus.LOGGER.error("Invalid item entry in the Nexus Mod 'supply_crate_loot' config option!");
				}

			}
		}

		level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.2F, 0.5F / (random.nextFloat() * 0.4F));
		level.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
	}


	@Override
	protected MapCodec<? extends FallingBlock> codec() {
		return null;
	}
}