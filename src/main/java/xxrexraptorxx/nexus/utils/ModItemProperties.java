package xxrexraptorxx.nexus.utils;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import xxrexraptorxx.nexus.main.ModItems;

public class ModItemProperties {

    public static void addItemProperties() {
        createBowProperties(ModItems.RECURVE_BOW.get());
        createBowProperties(ModItems.IRON_BOW.get());
        createBowProperties(ModItems.COMPOUND_BOW.get());

        createShieldProperties(ModItems.SHIELD_WOOD.get());
        createShieldProperties(ModItems.SHIELD_STONE.get());
        createShieldProperties(ModItems.SHIELD_IRON.get());
        createShieldProperties(ModItems.SHIELD_GOLD.get());
        createShieldProperties(ModItems.SHIELD_DIAMOND.get());
    }


    private static void createBowProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }


    private static void createShieldProperties(Item item) {
        ItemProperties.register(Items.SHIELD, new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
            return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
        });
    }
}
