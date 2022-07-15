package xxrexraptorxx.nexus.world;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.main.ModItems;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.utils.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
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

                    Minecraft.getInstance().player.displayClientMessage(new TextComponent(ChatFormatting.BLUE + "A newer version of " + ChatFormatting.YELLOW + References.NAME + ChatFormatting.BLUE + " is available!"), false);
                    Minecraft.getInstance().player.displayClientMessage(new TextComponent(ChatFormatting.GRAY + References.URL), false);

                    hasShownUp = true;

                } else if (VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.FAILED) {
                    Nexus.LOGGER.error(References.NAME + "'s version checker failed!");
                    hasShownUp = true;

                }
            }
        }
    }


    /** Distributes effects when activated **/
    @SubscribeEvent
    public static void NexusEffectEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level world = event.getWorld();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Player player = event.getPlayer();

        if (!world.isClientSide) {
            if (ForgeRegistries.BLOCKS.getKey(block).toString().contains(References.MODID + ":nexus")) {

                if (item == ModItems.REPAIR_KIT.get()) {
                    NexusBlock.nexusLevelChange(true, world, state, pos, stack, player);

                } else {

                    //Area effect
                    if (Config.NEXUS_EFFECT_WHEN_RIGHT_CLICKED.get()) {
                        world.playSound((Player) null, pos, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.8F);

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
    }


    /** Under attack message **/
    @SubscribeEvent
    public static void NexusHarvestEvent(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getWorld();
        Player player = event.getPlayer();
        Block block = event.getWorld().getBlockState(pos).getBlock();
        String nexusColor = ForgeRegistries.BLOCKS.getKey(block).toString().substring(12);

        if(!level.isClientSide && Config.NEXUS_UNDER_ATTACK_MESSAGE.get() && !player.isCreative()) {

            if (ForgeRegistries.BLOCKS.getKey(block).toString().contains(References.MODID + ":nexus")) {
                level.getServer().getPlayerList().broadcastMessage(new TranslatableComponent("message.nexus.nexus_under_attack").withStyle(ChatFormatting.getByName(nexusColor)), ChatType.CHAT, UUID.randomUUID());
            }
        }
    }


    /**
     * Distributes the supporter rewards on first join.
     */
    @SubscribeEvent
    public static void SupporterRewards(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        Level world = player.getLevel();

        if (Config.PATREON_REWARDS.get()) {

            try {
                URL SUPPORTER_URL = new URL("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Supporter");
                URL PREMIUM_SUPPORTER_URL = new URL("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Premium%20Supporter");
                URL ELITE_URL = new URL("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Elite");

                //test if a player already has rewards
                if (!player.getInventory().contains(new ItemStack(Items.PAPER))) {

                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    //test if player joins the first time
                    if (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) < 5) {

                        //test if player is supporter
                        if (SupporterCheck(SUPPORTER_URL, player)) {

                            ItemStack certificate = new ItemStack(Items.PAPER).setHoverName((new TextComponent("Thank you for supporting me in my work!").withStyle(ChatFormatting.GOLD).append(new TextComponent(" - XxRexRaptorxX").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GREEN))));

                            CompoundTag ownerNBT = new CompoundTag();
                            ItemStack reward = new ItemStack(Items.PLAYER_HEAD);
                            ownerNBT.putString("SkullOwner", player.getName().getString());
                            reward.setTag(ownerNBT);

                            player.getLevel().playSound((Player) null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, world.random.nextFloat() * 0.15F + 0.8F);
                            player.addItem(reward);
                            player.addItem(certificate);
                        }

                        //test if player is premium supporter
                        if (SupporterCheck(PREMIUM_SUPPORTER_URL, player)) {
                            ItemStack reward = new ItemStack(Items.DIAMOND_SWORD, 1).setHoverName(new TextComponent("Rex's Night Sword").withStyle(ChatFormatting.DARK_GRAY));
                            reward.enchant(Enchantments.MENDING, 1);
                            reward.enchant(Enchantments.SHARPNESS, 3);
                            player.addItem(reward);
                        }

                        //test if player is elite
                        if (SupporterCheck(ELITE_URL, player)) {
                            player.addItem(new ItemStack(Items.NETHER_STAR).setHoverName(new TextComponent("Elite Star")));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tests if a player is a supporter
     *
     * @param url url to a file that contains the supporter names
     * @param player ingame player
     * @return true/false
     */
    private static boolean SupporterCheck(URL url, Player player) {
        try {
            Scanner scanner = new Scanner(url.openStream());
            List<String> supporterList = scanner.tokens().toList();

            for (String name: supporterList) {
                //test if player is in supporter list
                if (player.getName().getString().equals(name)) {
                    return true;
                }
            }

            scanner.close();

        } catch (MalformedURLException e) {
            Nexus.LOGGER.error("Supporter list URL not found! >>" + url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}