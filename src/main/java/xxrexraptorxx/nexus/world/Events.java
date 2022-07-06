package xxrexraptorxx.nexus.world;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.utils.Config;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {


    /** Update-Checker **/
    private static boolean hasShownUp = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (Config.UPDATE_CHECKER.get()) {
            if (!hasShownUp && Minecraft.getInstance().screen == null) {
                if (VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.OUTDATED ||
                        VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.BETA_OUTDATED ) {

                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(ChatFormatting.BLUE + "A newer version of " + ChatFormatting.YELLOW + References.NAME + ChatFormatting.BLUE + " is available!"));
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(ChatFormatting.GRAY + References.URL));

                    hasShownUp = true;

                } else if (VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.FAILED) {
                    Nexus.LOGGER.error(References.NAME + "'s version checker failed!");
                    hasShownUp = true;

                }
            }
        }
    }


    @SubscribeEvent
    public static void NexusEffectEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level world = event.getWorld();
        Block block = world.getBlockState(pos).getBlock();

        if (!world.isClientSide) {
            if (ForgeRegistries.BLOCKS.getKey(block).toString().contains(References.MODID + ":nexus")) {
                world.playSound((Player) null, pos, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.8F);

                //Area effect
                if (Config.NEXUS_EFFECT_WHEN_RIGHT_CLICKED.get()) {

                    AreaEffectCloud cloud = new AreaEffectCloud(world, pos.getX(), pos.getY() + 0.2F, pos.getZ());
                    cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500));
                    cloud.setDuration(100);
                    cloud.setRadius(8);
                    cloud.setFixedColor(0x616161);
                    cloud.setWaitTime(10);
                    cloud.setParticle(ParticleTypes.GLOW);
                    world.addFreshEntity(cloud);
                }
            }
        }
    }

/**
    @SubscribeEvent
    public static void NexusHarvestEvent(PlayerEvent.HarvestCheck event) {
        Block block = event.getTargetBlock().getBlock();
        Player player = event.getPlayer();
        Level level = player.getLevel();

        if(!level.isClientSide) {
            if (ForgeRegistries.BLOCKS.getKey(block).toString().contains(References.MODID + ":nexus")) {
                level.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.unsigned(Component.translatable("message.nexus.nexus_under_attack").withStyle(ChatFormatting.getByName(ForgeRegistries.BLOCKS.getKey(block).toString().substring(12)))), new ChatSender(player.getUUID(), Component.literal("!")), ChatType.CHAT);
            }
        }
    }
**/
}