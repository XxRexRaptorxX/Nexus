package xxrexraptorxx.nexus.world;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
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
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.glfw.GLFW;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.main.Nexus;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.registries.ModBlocks;
import xxrexraptorxx.nexus.registries.ModItems;
import xxrexraptorxx.nexus.utils.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = References.MODID)
public class Events {


    /**
     * Update Checker
     **/
    private static boolean hasShownUp = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (Config.UPDATE_CHECKER != null && Config.UPDATE_CHECKER.get()) {

            if (!hasShownUp && Minecraft.getInstance().screen == null) {
                var player = Minecraft.getInstance().player;
                if (player == null) return;

                var modContainer = ModList.get().getModContainerById(References.MODID).orElse(null);

                if (modContainer != null) {
                    var versionCheckResult = VersionChecker.getResult(modContainer.getModInfo());

                    if (versionCheckResult.status() == VersionChecker.Status.OUTDATED || versionCheckResult.status() == VersionChecker.Status.BETA_OUTDATED) {
                        MutableComponent url = Component.literal(ChatFormatting.GREEN + "Click here to update!")
                                .withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(URI.create(References.URL))));

                        player.displayClientMessage(
                                Component.literal(ChatFormatting.BLUE + "A newer version of " + ChatFormatting.YELLOW + References.NAME + ChatFormatting.BLUE + " is available!"),
                                false);
                        player.displayClientMessage(url, false);

                        hasShownUp = true;

                    } else if (versionCheckResult.status() == VersionChecker.Status.FAILED) {
                        Nexus.LOGGER.error(References.NAME + "'s version checker failed!");
                        hasShownUp = true;
                    }
                }
            }
        }
    }


    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * Distributes supporter rewards on first login.
     */
    @SubscribeEvent
    public static void SupporterRewards(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (Config.PATREON_REWARDS.get()) {
            // Check if the player already has rewards
            if (!player.getInventory().contains(new ItemStack(Items.PAPER))) {
                if (player instanceof ServerPlayer serverPlayer) { // Ensure the player is a ServerPlayer
                    // Check if the player is logging in for the first time
                    if (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) < 5) {

                        // Perform supporter checks asynchronously
                        CompletableFuture.runAsync(() -> {
                            if (SupporterCheck(URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Supporter"), player)) {
                                giveSupporterReward(player, level);
                            }
                            if (SupporterCheck(URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Premium%20Supporter"), player)) {
                                givePremiumSupporterReward(player, level);
                            }
                            if (SupporterCheck(URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Elite"), player)) {
                                giveEliteReward(player);
                            }
                        });
                    }
                }
            }
        }
    }


    /**
     * Checks if the player is in the supporter list from the given URI.
     *
     * @param uri URI to a file containing supporter names
     * @param player The in-game player
     * @return true if the player is a supporter, otherwise false
     */
    private static boolean SupporterCheck(URI uri, Player player) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse supporter list
            List<String> supporterList = List.of(response.body().split("\\R")); // Split lines
            return supporterList.contains(player.getName().getString());

        } catch (Exception e) {
            Nexus.LOGGER.error("Failed to fetch or process supporter list from URI: {}", uri, e);
            return false;
        }
    }


    private static void giveSupporterReward(Player player, Level level) {
        ItemStack certificate = new ItemStack(Items.PAPER);
        certificate.set(DataComponents.CUSTOM_NAME, Component.literal("Thank you for supporting me in my work!").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(" - XxRexRaptorxX").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GREEN)));

        ItemStack reward = new ItemStack(Items.PLAYER_HEAD);
        var profile = new GameProfile(player.getUUID(), player.getName().getString());
        reward.set(DataComponents.PROFILE, new ResolvableProfile(profile));

        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.15F + 0.8F);
        player.getInventory().add(reward);
        player.getInventory().add(certificate);
    }


    private static void givePremiumSupporterReward(Player player, Level level) {
        ItemStack reward = new ItemStack(Items.DIAMOND_SWORD, 1);
        Registry<Enchantment> enchantmentsRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        reward.enchant(enchantmentsRegistry.getOrThrow(Enchantments.MENDING), 1);
        reward.enchant(enchantmentsRegistry.getOrThrow(Enchantments.SHARPNESS), 3);
        reward.set(DataComponents.ENCHANTMENTS, reward.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
        player.getInventory().add(reward);
    }


    private static void giveEliteReward(Player player) {
        ItemStack star = new ItemStack(Items.NETHER_STAR);

        star.set(DataComponents.CUSTOM_NAME, Component.literal("Elite Star"));
        player.getInventory().add(star);
    }


    /**
     * Distributes effects when activated
     **/
    @SubscribeEvent
    public static void NexusEffectEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level world = event.getLevel();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Player player = event.getEntity();

        if (!world.isClientSide()) {
            if (BuiltInRegistries.BLOCK.getKey(block).toString().contains(References.MODID + ":nexus")) {

                if (item == ModItems.REPAIR_KIT.get()) {
                    NexusBlock.nexusLevelChange(true, world, state, pos, stack, player);

                } else {

                    // Area effect
                    if (Config.NEXUS_EFFECT_WHEN_RIGHT_CLICKED.get()) {
                        world.playSound((Player) null, pos, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.8F);

                        AreaEffectCloud cloud = new AreaEffectCloud(world, pos.getX(), pos.getY() + 0.2F, pos.getZ());
                        cloud.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 500));
                        cloud.setDuration(100);
                        cloud.setRadius(8);
                        // cloud.setFixedColor(0x616161);
                        cloud.setWaitTime(10);
                        cloud.setCustomParticle(ParticleTypes.GLOW);
                        world.addFreshEntity(cloud);
                    }
                }
            }
        }
    }


    /**
     * Under attack message
     **/
    @SubscribeEvent
    public static void NexusHarvestEvent(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        Player player = event.getEntity();
        Block block = level.getBlockState(pos).getBlock();

        if (!level.isClientSide() && Config.NEXUS_UNDER_ATTACK_MESSAGE.get() && !player.isCreative()) {

            if (BuiltInRegistries.BLOCK.getKey(block).toString().contains(References.MODID + ":nexus")) {

                String nexusColor = BuiltInRegistries.BLOCK.getKey(block).toString().substring(12);
                level.getServer().getPlayerList().broadcastSystemMessage(
                        Component.translatable("message." + References.MODID + ".nexus_under_attack").withStyle(ChatFormatting.getByName(nexusColor)), true);
            }
        }
    }


    /**
     * Destroys all blocks is the Safe-Zone of a Nexus
     **/
    @SubscribeEvent
    public static void BlockPlaceEvent(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();

        // safety tests
        if (!world.isClientSide()) {
            if (event.getItemStack().getItem() != Items.AIR && Config.NEXUS_SAFE_ZONE.get() != 0) {
                for (Block blocks : BuiltInRegistries.BLOCK) { // test if the held item is a block
                    if (BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()) == BuiltInRegistries.BLOCK.getKey(blocks)) {


                        // sets the start position
                        int posX = pos.getX();
                        int posY = pos.getY();
                        int posZ = pos.getZ();

                        // changes the tested position
                        for (int x = -Config.NEXUS_SAFE_ZONE.get(); x <= Config.NEXUS_SAFE_ZONE.get(); x++) {

                            for (int y = -Config.NEXUS_SAFE_ZONE.get(); y <= Config.NEXUS_SAFE_ZONE.get(); y++) {

                                for (int z = -Config.NEXUS_SAFE_ZONE.get(); z <= Config.NEXUS_SAFE_ZONE.get(); z++) {
                                    BlockPos block = new BlockPos(posX + x, posY + y, posZ + z);

                                    // tests if current block is a nexus
                                    if (BuiltInRegistries.BLOCK.getKey(world.getBlockState(block).getBlock()).toString().contains(References.MODID + ":nexus")
                                            && block.getY() < pos.getY() + 2) {
                                        world.playSound(player, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.F);
                                        Minecraft.getInstance().player.displayClientMessage(
                                                Component.translatable("message." + References.MODID + ".blocked_position").withStyle(ChatFormatting.RED), true);

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


    // private static int counter = 0;


    /**
     * Stores the coordinates of a Nexus in the world as scoreboard objectives
     **/
    @SubscribeEvent
    public static void NexusPlaceEvent(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Item item = event.getItemStack().getItem();

        if (Config.NEXUS_TRACKING.get() && BuiltInRegistries.ITEM.getKey(item).toString().contains(References.MODID + ":nexus")
                && !BuiltInRegistries.ITEM.getKey(item).toString().contains(References.MODID + ":nexus_tracker")) { // test if placed block is a nexus

            String nexusColor = (item).toString().substring(12).toUpperCase();
            String scoreboardName = nexusColor + "_NEXUS";

            // counter++; > unused
            // String scoreboardName = counter + "_NEXUS_" + nexusColor; //dynamicaly generates a unique scoreboard name

            if (world.getScoreboard().getObjectiveNames().contains(scoreboardName)) { // remove the scoreboard of the nexus color that already exists to avoid errors
                world.getScoreboard().removeObjective(world.getScoreboard().getObjective(scoreboardName));
            }

            // add the coords in an objective
            world.getScoreboard().addObjective(scoreboardName, ObjectiveCriteria.DUMMY, Component.literal(pos.toShortString().replace("[", "").replace("]", "")),
                    ObjectiveCriteria.RenderType.INTEGER, false, null);
        }
    }


    @SubscribeEvent
    public static void addingToolTips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        List<Component> list = event.getToolTip();

        // Nexus
        if (stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(References.MODID, "nexus")))) {
            if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
                list.add(Component.translatable("message." + References.MODID + ".nexus.desc").withStyle(ChatFormatting.GOLD));
                list.add(Component.translatable("message." + References.MODID + ".hold_shift.desc").withStyle(ChatFormatting.GREEN));
            } else {
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_1").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE));
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_2").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_3").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_4").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_5").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("message." + References.MODID + ".gamemode_line_6").withStyle(ChatFormatting.GRAY));
            }

            // Security Barrier/Wall
        } else
            if (BuiltInRegistries.BLOCK.getKey(ModBlocks.SECURTIY_BARRIER.get()).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath())
                    || BuiltInRegistries.BLOCK.getKey(ModBlocks.SECURTIY_WALL.get()).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath())) {
                        list.add(Component.translatable("message." + References.MODID + ".unbreakable").withStyle(ChatFormatting.GRAY));

                    } else {
                        //
                    }
    }

}
