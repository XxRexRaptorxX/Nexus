package xxrexraptorxx.nexus.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.utils.Config;

import java.util.List;
import java.util.Random;

public class NexusTracker extends Item {

    public NexusTracker() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(1)
                .durability(10)
        );
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("message.nexus.tracker.desc").withStyle(ChatFormatting.GRAY));

        if (!Config.NEXUS_TRACKING.get()) {
            list.add(Component.translatable("message.nexus.function_disabled").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }


    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }


    @Override
    public InteractionResult useOn(UseOnContext event) {
        Player player = event.getPlayer();
        Level level = event.getLevel();
        ItemStack stack = event.getItemInHand();
        BlockPos playerPos = player.getOnPos();
        Random random = new Random();

        if (!level.isClientSide) {
            if (level.getScoreboard().getObjectiveNames().contains("RED_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("RED_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.RED), false);
            }
            if (level.getScoreboard().getObjectiveNames().contains("BLUE_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("BLUE_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.BLUE), false);
            }
            if (level.getScoreboard().getObjectiveNames().contains("GREEN_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("GREEN_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.GREEN), false);
            }
            if (level.getScoreboard().getObjectiveNames().contains("YELLOW_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("YELLOW_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.YELLOW), false);
            }
            if (level.getScoreboard().getObjectiveNames().contains("BLACK_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("BLACK_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.DARK_GRAY), false);
            }
            if (level.getScoreboard().getObjectiveNames().contains("WHITE_NEXUS")) {
                Minecraft.getInstance().player.displayClientMessage(Component.literal(String.valueOf(
                                distance(playerPos, level.getScoreboard().getOrCreateObjective("WHITE_NEXUS").getFormattedDisplayName().getString())))
                        .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.WHITE), false);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        stack.setDamageValue(stack.getDamageValue() + 1);

        if (player instanceof Player) {
            ((Player) player).getCooldowns().addCooldown(this, 20);
        }

        if (stack.getDamageValue() == stack.getMaxDamage()) {
            level.playSound((Player) null, player.position().x, player.position().y, player.position().z, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            stack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }


    /**
     * Calculates the distance between 2 coordinates
     */
    private static int distance(BlockPos playerPos, String nexusPos) {
        int x1 = playerPos.getX();
        int y1 = playerPos.getY();
        int z1 = playerPos.getZ();

        String[] coords = nexusPos.replace("[", "").replace("]", "").split(", ");

        int x2 = Integer.parseInt(coords[0]);
        int y2 = Integer.parseInt(coords[1]);
        int z2 = Integer.parseInt(coords[2]);

        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }
}


