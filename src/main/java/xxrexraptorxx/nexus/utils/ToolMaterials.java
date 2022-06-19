package xxrexraptorxx.nexus.utils;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ToolMaterials {

    /**                     VANILLA
     *             lvl|uses|speed|damage|enchant
     *    WOOD       0, 59,   2.0,  0.0,  15
     *    STONE      1, 131,  4.0,  1.0,  5
     *    IRON       2, 250,  6.0,  2.0,  14
     *    DIAMOND    3, 1561, 8.0,  3.0,  10
     *    GOLD       0, 32,   12.0, 0.0,  22
     *    NETHERITE  4, 2031, 9.0,  4.0,  15
     */

    //Stone Age
    public static final ForgeTier AX = new ForgeTier(1, 100, 3.0f, 5.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier HAND_AX = new ForgeTier(1, 110, 1.0f, 3.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier CUDGEL_WOOD = new ForgeTier(0, 50, 4.0f, 3.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier CUDGEL_STONE = new ForgeTier(1, 120, 4.0f, 5.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier CAVEMAN_SPEAR = new ForgeTier(1, 80, 4.0f, 4.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier CAVEMAN_KNIFE = new ForgeTier(0, 120, 4.0f, 3.5f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier BONE_KNIFE = new ForgeTier(0, 90, 4.0f, 3.0f, 0, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.BONE));

    //Antiquity
    public static final ForgeTier DAGGER_WOOD = new ForgeTier(0, 55, 4.0f, 3.0f, 18, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier DAGGER_STONE = new ForgeTier(1, 120, 4.0f, 4.0f, 8, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier DAGGER_IRON = new ForgeTier(2, 230, 4.0f, 5.0f, 17, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier DAGGER_GOLD = new ForgeTier(0, 25, 4.0f, 3.0f, 13, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier DAGGER_DIAMOND = new ForgeTier(3, 1300, 4.0f, 6.0f, 25, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier KNIFE_WOOD = new ForgeTier(0, 50, 4.0f, 2.8f, 18, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier KNIFE_STONE = new ForgeTier(1, 110, 4.0f, 3.8f, 8, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier KNIFE_IRON = new ForgeTier(2, 220, 4.0f, 4.8f, 17, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier KNIFE_GOLD = new ForgeTier(0, 20, 4.0f, 2.8f, 13, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier KNIFE_DIAMOND = new ForgeTier(3, 1250, 4.0f, 5.8f, 25, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier SKEWER_WOOD = new ForgeTier(0, 50, 4.0f, 2.8f, 18, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier SKEWER_STONE = new ForgeTier(1, 100, 4.0f, 3.8f, 8, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier SKEWER_IRON = new ForgeTier(2, 200, 4.0f, 4.8f, 17, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier SKEWER_GOLD = new ForgeTier(0, 20, 4.0f, 2.8f, 13, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier SKEWER_DIAMOND = new ForgeTier(3, 1200, 4.0f, 5.8f, 25, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));

    //Middle Ages
    public static final ForgeTier BATTLE_AXE_WOOD = new ForgeTier(0, 70, 1.0f, 4.8f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier BATTLE_AXE_STONE = new ForgeTier(1, 150, 3.0f, 5.8f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier BATTLE_AXE_IRON = new ForgeTier(2, 270, 5.0f, 6.8f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier BATTLE_AXE_GOLD = new ForgeTier(0, 50, 11.0f, 4.8f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier BATTLE_AXE_DIAMOND = new ForgeTier(3, 1650, 7.0f, 7.8f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier FLAIL_WOOD = new ForgeTier(0, 40, 1.0f, 3.0f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier FLAIL_STONE = new ForgeTier(1, 120, 3.0f, 4.0f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier FLAIL_IRON = new ForgeTier(2, 240, 5.0f, 5.0f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier FLAIL_GOLD = new ForgeTier(0, 30, 11.0f, 6.0f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier FLAIL_DIAMOND = new ForgeTier(3, 1400, 7.0f, 7.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier HALBERT_WOOD = new ForgeTier(0, 60, 1.0f, 4.3f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier HALBERT_STONE = new ForgeTier(1, 130, 3.0f, 5.5f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier HALBERT_IRON = new ForgeTier(2, 250, 5.0f, 6.5f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier HALBERT_GOLD = new ForgeTier(0, 30, 11.0f, 4.3f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier HALBERT_DIAMOND = new ForgeTier(3, 1550, 7.0f, 7.5f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier LONGSWORD_WOOD = new ForgeTier(0, 69, 1.0f, 4.2f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier LONGSWORD_STONE = new ForgeTier(1, 141, 3.0f, 5.2f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier LONGSWORD_IRON = new ForgeTier(2, 260, 5.0f, 6.2f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier LONGSWORD_GOLD = new ForgeTier(0, 42, 11.0f, 4.2f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier LONGSWORD_DIAMOND = new ForgeTier(3, 1650, 7.0f, 7.2f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier HEAVY_STAFF_WOOD = new ForgeTier(0, 80, 1.0f, 3.0f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier HEAVY_STAFF_STONE = new ForgeTier(1, 150, 3.0f, 4.0f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier HEAVY_STAFF_IRON = new ForgeTier(2, 280, 5.0f, 5.0f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier HEAVY_STAFF_GOLD = new ForgeTier(0, 50, 11.0f, 3.0f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier HEAVY_STAFF_DIAMOND = new ForgeTier(3, 1700, 7.0f, 6.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier SPEAR_WOOD = new ForgeTier(0, 40, 1.0f, 1.0f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier SPEAR_STONE = new ForgeTier(1, 110, 3.0f, 2.0f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier SPEAR_IRON = new ForgeTier(2, 230, 5.0f, 3.0f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier SPEAR_GOLD = new ForgeTier(0, 20, 11.0f, 1.0f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier SPEAR_DIAMOND = new ForgeTier(3, 1300, 7.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier WAR_AXE_WOOD = new ForgeTier(0, 80, 0.5f, 5.5f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier WAR_AXE_STONE = new ForgeTier(1, 160, 2.0f, 6.5f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier WAR_AXE_IRON = new ForgeTier(2, 280, 4.0f, 7.5f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier WAR_AXE_GOLD = new ForgeTier(0, 60, 10.0f, 5.5f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier WAR_AXE_DIAMOND = new ForgeTier(3, 1700, 6.0f, 8.5f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier WAR_HAMMER_WOOD = new ForgeTier(0, 100, 0.5f, 5.2f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier WAR_HAMMER_STONE = new ForgeTier(1, 180, 1.0f, 6.2f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier WAR_HAMMER_IRON = new ForgeTier(2, 300, 3.0f, 7.2f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier WAR_HAMMER_GOLD = new ForgeTier(0, 80, 6.0f, 5.2f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier WAR_HAMMER_DIAMOND = new ForgeTier(3, 1800, 4.0f, 8.2f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier SCYTHE_WOOD = new ForgeTier(0, 65, 1.0f, 3.5f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier SCYTHE_STONE = new ForgeTier(1, 135, 3.0f, 4.5f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier SCYTHE_IRON = new ForgeTier(2, 255, 5.0f, 5.5f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier SCYTHE_GOLD = new ForgeTier(0, 35, 11.0f, 3.5f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier SCYTHE_DIAMOND = new ForgeTier(3, 1550, 7.0f, 6.5f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));
    public static final ForgeTier LANCE_WOOD = new ForgeTier(0, 50, 1.0f, 4.0f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier LANCE_STONE = new ForgeTier(1, 120, 3.0f, 5.0f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier LANCE_IRON = new ForgeTier(2, 230, 5.0f, 6.0f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier LANCE_GOLD = new ForgeTier(0, 25, 11.0f, 4.0f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier LANCE_DIAMOND = new ForgeTier(3, 1400, 7.0f, 7.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));

    //Early Modern Age
    public static final ForgeTier MULTI_TOOL_WOOD = new ForgeTier(0, 59, 2.0f, 3.0f, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS));
    public static final ForgeTier MULTI_TOOL_STONE = new ForgeTier(1, 131, 4.0f, 4.0f, 5, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final ForgeTier MULTI_TOOL_IRON = new ForgeTier(2, 250, 6.0f, 5.0f, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));
    public static final ForgeTier MULTI_TOOL_GOLD = new ForgeTier(0, 32, 12.0f, 3.0f, 22, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ForgeTier MULTI_TOOL_DIAMOND = new ForgeTier(3, 1561, 8.0f, 6.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));

    //Modern Age
    public static final ForgeTier INDUSTRIAL = new ForgeTier(3, 2000, 9.0f, 1.0f, 0, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_SCRAP));
    public static final ForgeTier INDUSTRIAL_PULVERIZER = new ForgeTier(4, 2000, 20.0f, 1.0f, 0, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_SCRAP));

    //Future
    /**                     VANILLA
     *             lvl|uses|speed|damage|enchant
     *    WOOD       0, 59,   2.0,  0.0,  15
     *    STONE      1, 131,  4.0,  1.0,  5
     *    IRON       2, 250,  6.0,  2.0,  14
     *    GOLD       0, 32,   12.0, 0.0,  22
     *    DIAMOND    3, 1561, 8.0,  3.0,  10
     *    NETHERITE  4, 2031, 9.0,  4.0,  15
     *///                         [ignore]
}
