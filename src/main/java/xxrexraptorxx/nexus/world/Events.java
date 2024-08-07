package xxrexraptorxx.nexus.world;

import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.registries.ModItems;
import xxrexraptorxx.nexus.utils.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@EventBusSubscriber(modid = References.MODID, bus = EventBusSubscriber.Bus.GAME)
public class Events {


    /** Update-Checker **/
    private static boolean hasShownUp = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (Config.UPDATE_CHECKER.get()) {
            if (!hasShownUp && Minecraft.getInstance().screen == null) {
                if (VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.OUTDATED ||
                        VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.BETA_OUTDATED ) {

                    MutableComponent url = Component.literal(ChatFormatting.GREEN + "Click here to update!");
                    url.withStyle(url.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, References.URL)));

                    Minecraft.getInstance().player.displayClientMessage(Component.literal(ChatFormatting.BLUE + "A newer version of " + ChatFormatting.YELLOW + References.NAME + ChatFormatting.BLUE + " is available!"), false);
                    Minecraft.getInstance().player.displayClientMessage(url, false);

                    hasShownUp = true;

                } else if (VersionChecker.getResult(ModList.get().getModContainerById(References.MODID).get().getModInfo()).status() == VersionChecker.Status.FAILED) {
                    Nexus.LOGGER.error(References.NAME + "'s version checker failed!");
                    hasShownUp = true;

                }
            }
        }
    }


    /**
     * Distributes the supporter rewards on first join.
     */
    @SubscribeEvent
    public static void SupporterRewards(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();

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

                            ItemStack certificate = new ItemStack(Items.PAPER);
                            certificate.set(DataComponents.CUSTOM_NAME, Component.literal("Thank you for supporting me in my work!").withStyle(ChatFormatting.GOLD).append(Component.literal(" - XxRexRaptorxX").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GREEN)));

                            ItemStack reward = new ItemStack(Items.PLAYER_HEAD);
                            var profile = new GameProfile(player.getUUID(), player.getName().getString());
                            reward.set(DataComponents.PROFILE, new ResolvableProfile(profile));

                            level.playSound((Player) null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.15F + 0.8F);
                            player.addItem(reward);
                            player.addItem(certificate);
                        }

                        //test if player is premium supporter
                        if (SupporterCheck(PREMIUM_SUPPORTER_URL, player)) {
                            ItemStack reward = new ItemStack(Items.DIAMOND_SWORD, 1);
                            Registry<Enchantment> enchantmentsRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);

                            reward.enchant(enchantmentsRegistry.getHolderOrThrow(Enchantments.MENDING), 1);
                            reward.enchant(enchantmentsRegistry.getHolderOrThrow(Enchantments.SHARPNESS), 3);
                            reward.set(DataComponents.ENCHANTMENTS, reward.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));

                            reward.set(DataComponents.CUSTOM_NAME, Component.literal("Rex's Night Sword").withStyle(ChatFormatting.DARK_GRAY));

                            player.addItem(reward);
                        }

                        //test if player is elite
                        if (SupporterCheck(ELITE_URL, player)) {
                            ItemStack star = new ItemStack(Items.NETHER_STAR);
                            star.set(DataComponents.CUSTOM_NAME, Component.literal("Elite Star"));

                            player.addItem(star);
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



    /** Distributes effects when activated **/
    @SubscribeEvent
    public static void NexusEffectEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level world = event.getLevel();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Player player = event.getEntity();

        if (!world.isClientSide) {
            if (BuiltInRegistries.BLOCK.getKey(block).toString().contains(References.MODID + ":nexus")) {

                if (item == ModItems.REPAIR_KIT.get()) {
                    NexusBlock.nexusLevelChange(true, world, state, pos, stack, player);

                } else {

                    //Area effect
                    if (Config.NEXUS_EFFECT_WHEN_RIGHT_CLICKED.get()) {
                        world.playSound((Player) null, pos, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.8F);

                        AreaEffectCloud cloud = new AreaEffectCloud(world, pos.getX(), pos.getY() + 0.2F, pos.getZ());
                        cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 500));
                        cloud.setDuration(100);
                        cloud.setRadius(8);
                        //cloud.setFixedColor(0x616161);
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
        Level level = event.getLevel();
        Player player = event.getEntity();
        Block block = level.getBlockState(pos).getBlock();

        if(!level.isClientSide && Config.NEXUS_UNDER_ATTACK_MESSAGE.get() && !player.isCreative()) {

            if (BuiltInRegistries.BLOCK.getKey(block).toString().contains(References.MODID + ":nexus")) {

                String nexusColor = BuiltInRegistries.BLOCK.getKey(block).toString().substring(12);
                level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.nexus.nexus_under_attack").withStyle(ChatFormatting.getByName(nexusColor)), true);
            }
        }
    }


    /** Destroys all blocks is the Safe-Zone of a Nexus **/
    @SubscribeEvent
    public static void BlockPlaceEvent(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();

        //safety tests
        if (!world.isClientSide) {
            if (event.getItemStack().getItem() != Items.AIR && Config.NEXUS_SAFE_ZONE.get() != 0) {
                for (Block blocks : BuiltInRegistries.BLOCK) {                               //test if the held item is a block
                    if (BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()) == BuiltInRegistries.BLOCK.getKey(blocks)) {


                        //sets the start position
                        int posX = pos.getX();
                        int posY = pos.getY();
                        int posZ = pos.getZ();

                        //changes the tested position
                        for (int x = -Config.NEXUS_SAFE_ZONE.get(); x <= Config.NEXUS_SAFE_ZONE.get(); x++) {

                            for (int y = -Config.NEXUS_SAFE_ZONE.get(); y <= Config.NEXUS_SAFE_ZONE.get(); y++) {

                                for (int z = -Config.NEXUS_SAFE_ZONE.get(); z <= Config.NEXUS_SAFE_ZONE.get(); z++) {
                                    BlockPos block = new BlockPos(posX + x, posY + y, posZ + z);

                                    //tests if current block is a nexus
                                    if (BuiltInRegistries.BLOCK.getKey(world.getBlockState(block).getBlock()).toString().contains(References.MODID + ":nexus") && block.getY() < pos.getY() + 2) {
                                        world.playSound(player, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.F);
                                        Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.nexus.blocked_position").withStyle(ChatFormatting.RED), true);

                                        event.setCanceled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //private static int counter = 0;

    /** Stores the coordinates of a Nexus in the world as scoreboard objectives **/
    @SubscribeEvent
    public static void NexusPlaceEvent(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Item item = event.getItemStack().getItem();

        if (Config.NEXUS_TRACKING.get() && BuiltInRegistries.ITEM.getKey(item).toString().contains(References.MODID + ":nexus") &&
                !BuiltInRegistries.ITEM.getKey(item).toString().contains(References.MODID + ":nexus_tracker")) {  //test if placed block is a nexus

            String nexusColor = (item).toString().substring(12).toUpperCase();
            String scoreboardName = nexusColor + "_NEXUS";

            //counter++;        > unused
            //String scoreboardName = counter + "_NEXUS_" + nexusColor; //dynamicaly generates a unique scoreboard name

            if (world.getScoreboard().getObjectiveNames().contains(scoreboardName)) { //remove the scoreboard of the nexus color that already exists to avoid errors
                world.getScoreboard().removeObjective(world.getScoreboard().getObjective(scoreboardName));
            }

            //add the coords in an objective
            world.getScoreboard().addObjective(scoreboardName, ObjectiveCriteria.DUMMY, Component.literal(
                    pos.toShortString().replace("[", "").replace("]", "")),
                    ObjectiveCriteria.RenderType.INTEGER, false, null);
        }
    }

}