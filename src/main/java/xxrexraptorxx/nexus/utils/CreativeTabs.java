package xxrexraptorxx.nexus.utils;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xxrexraptorxx.nexus.main.ModItems;
import xxrexraptorxx.nexus.main.References;

public class CreativeTabs {

    public static final CreativeModeTab BASE_TAB = new CreativeModeTab(References.MODID + "_base_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.HANDLE.get());
        }

    };


    public static final CreativeModeTab STONE_AGE_TAB = new CreativeModeTab(References.MODID + "_stone_age_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.HAND_AX.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> itemList) {
            super.fillItemList(itemList);

            if (Config.ADD_VANILLA_ITEMS_TO_MOD_TABS.get()) {
                itemList.add(new ItemStack(Items.BOW));
            }
        }
    };


    public static final CreativeModeTab ANTIQUITY_TAB = new CreativeModeTab(References.MODID + "_antiquity_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DAGGER_IRON.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> itemList) {
            super.fillItemList(itemList);

            if (Config.ADD_VANILLA_ITEMS_TO_MOD_TABS.get()) {
                itemList.add(new ItemStack(Items.WOODEN_PICKAXE));
                itemList.add(new ItemStack(Items.STONE_PICKAXE));
                itemList.add(new ItemStack(Items.IRON_PICKAXE));
                itemList.add(new ItemStack(Items.GOLDEN_PICKAXE));
                itemList.add(new ItemStack(Items.DIAMOND_PICKAXE));
                itemList.add(new ItemStack(Items.WOODEN_AXE));
                itemList.add(new ItemStack(Items.STONE_AXE));
                itemList.add(new ItemStack(Items.IRON_AXE));
                itemList.add(new ItemStack(Items.GOLDEN_AXE));
                itemList.add(new ItemStack(Items.DIAMOND_AXE));
                itemList.add(new ItemStack(Items.WOODEN_HOE));
                itemList.add(new ItemStack(Items.STONE_HOE));
                itemList.add(new ItemStack(Items.IRON_HOE));
                itemList.add(new ItemStack(Items.GOLDEN_HOE));
                itemList.add(new ItemStack(Items.DIAMOND_HOE));
                itemList.add(new ItemStack(Items.LEATHER_HELMET));
                itemList.add(new ItemStack(Items.LEATHER_CHESTPLATE));
                itemList.add(new ItemStack(Items.LEATHER_LEGGINGS));
                itemList.add(new ItemStack(Items.LEATHER_BOOTS));
                itemList.add(new ItemStack(Items.IRON_HELMET));
                itemList.add(new ItemStack(Items.IRON_CHESTPLATE));
                itemList.add(new ItemStack(Items.IRON_LEGGINGS));
                itemList.add(new ItemStack(Items.IRON_BOOTS));
                itemList.add(new ItemStack(Items.GOLDEN_HELMET));
                itemList.add(new ItemStack(Items.GOLDEN_CHESTPLATE));
                itemList.add(new ItemStack(Items.GOLDEN_LEGGINGS));
                itemList.add(new ItemStack(Items.GOLDEN_BOOTS));
            }
        }
    };


    public static final CreativeModeTab MIDDLE_AGES_TAB = new CreativeModeTab(References.MODID + "_middle_ages_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BATTLE_AXE_IRON.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> itemList) {
            super.fillItemList(itemList);

            if (Config.ADD_VANILLA_ITEMS_TO_MOD_TABS.get()) {

                itemList.add(new ItemStack(Items.SHIELD));
                itemList.add(new ItemStack(Items.TRIDENT));
                itemList.add(new ItemStack(Items.CROSSBOW));
                itemList.add(new ItemStack(Items.WOODEN_SWORD));
                itemList.add(new ItemStack(Items.STONE_SWORD));
                itemList.add(new ItemStack(Items.IRON_SWORD));
                itemList.add(new ItemStack(Items.GOLDEN_SWORD));
                itemList.add(new ItemStack(Items.DIAMOND_SWORD));
                itemList.add(new ItemStack(Items.NETHERITE_SWORD));
                itemList.add(new ItemStack(Items.NETHERITE_PICKAXE));
                itemList.add(new ItemStack(Items.NETHERITE_AXE));
                itemList.add(new ItemStack(Items.NETHERITE_HOE));
                itemList.add(new ItemStack(Items.NETHERITE_HELMET));
                itemList.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
                itemList.add(new ItemStack(Items.NETHERITE_LEGGINGS));
                itemList.add(new ItemStack(Items.NETHERITE_BOOTS));


            }
        }
    };


    public static final CreativeModeTab EARLY_MODERN_AGE_TAB = new CreativeModeTab(References.MODID + "_early_modern_age_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MULTI_TOOL_IRON.get());
        }
    };


    public static final CreativeModeTab MODERN_AGE_TAB = new CreativeModeTab(References.MODID + "_modern_age_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.INDUSTRIAL_MINER.get());
        }
    };


    public static final CreativeModeTab FUTURE_TAB = new CreativeModeTab(References.MODID + "_future_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.INDUSTRIAL_MULTI_TOOL.get());
        }
    };


    public static final CreativeModeTab EDO_PERIOD_TAB = new CreativeModeTab(References.MODID + "_edo_period_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.INDUSTRIAL_MULTI_TOOL.get());
        }
    };

}
