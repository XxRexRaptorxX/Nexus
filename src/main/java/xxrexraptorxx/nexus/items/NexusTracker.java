package xxrexraptorxx.nexus.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.network.ModPackets;
import xxrexraptorxx.nexus.network.packets.MessageC2SPacket;
import xxrexraptorxx.nexus.utils.Config;

import java.util.Random;
import java.util.function.Consumer;

public class NexusTracker extends Item {

    public NexusTracker(Item.Properties properties) {
        super(properties
                .rarity(Rarity.UNCOMMON)
                .stacksTo(1)
                .durability(10)
        );
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> list, TooltipFlag flag) {
        list.accept(Component.translatable("message." + References.MODID + ".tracker.desc").withStyle(ChatFormatting.GRAY));

        if (!Config.NEXUS_TRACKING.get()) {
            list.accept(Component.translatable("message." + References.MODID + ".function_disabled").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Random random = new Random();

            if (Config.NEXUS_TRACKING.get()) {
                level.playSound((Player) null, player.position().x, player.position().y, player.position().z, SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 1.0F, 2.0F / (random.nextFloat() * 0.4F + 0.8F));

                if (level.isClientSide) {
                    ModPackets.sendToServer(new MessageC2SPacket());
                } else {
                    player.awardStat(Stats.ITEM_USED.get(this));
                }

                if (player instanceof Player) {
                    ((Player) player).getCooldowns().addCooldown(player.getUseItem(), Config.TRACKING_COOLDOWN.get());
                }

                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
    }


    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }


    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }


    /**
     * Calculates the distance between 2 coordinates
     */
    public static int distance(BlockPos playerPos, String nexusPos) {
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


