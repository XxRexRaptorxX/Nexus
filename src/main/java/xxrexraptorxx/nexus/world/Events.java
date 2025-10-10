package xxrexraptorxx.nexus.world;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.glfw.GLFW;
import xxrexraptorxx.magmacore.utils.FormattingHelper;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.registries.ModBlocks;
import xxrexraptorxx.nexus.registries.ModItems;
import xxrexraptorxx.nexus.utils.Config;

import java.util.List;

@EventBusSubscriber(modid = References.MODID)
public class Events {

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
                level.getServer().getPlayerList()
                        .broadcastSystemMessage(FormattingHelper.setMessageComponent(References.MODID, ".nexus_under_attack", ChatFormatting.getByName(nexusColor)), true);
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
                                        Minecraft.getInstance().player
                                                .displayClientMessage(FormattingHelper.setMessageComponent(References.MODID, ".blocked_position", ChatFormatting.RED), true);

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
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".nexus.desc", ChatFormatting.GOLD));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".hold_shift.desc", ChatFormatting.GREEN));

            } else {
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_1", ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_2", ChatFormatting.GRAY));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_3", ChatFormatting.GRAY));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_4", ChatFormatting.GRAY));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_5", ChatFormatting.GRAY));
                list.add(FormattingHelper.setMessageComponent(References.MODID, ".gamemode_line_6", ChatFormatting.GRAY));
            }

            // Security Barrier/Wall
        } else
            if (BuiltInRegistries.BLOCK.getKey(ModBlocks.SECURTIY_BARRIER.get()).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath())
                    || BuiltInRegistries.BLOCK.getKey(ModBlocks.SECURTIY_WALL.get()).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath())) {
                        list.add(FormattingHelper.setMessageComponent(References.MODID, ".unbreakable", ChatFormatting.GRAY));

                    } else {
                        //
                    }
    }

}
