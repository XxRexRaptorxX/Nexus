package xxrexraptorxx.nexus.main;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xxrexraptorxx.nexus.blocks.ExplosiveMine;
import xxrexraptorxx.nexus.blocks.Nexus;
import xxrexraptorxx.nexus.blocks.WeaponBox;
import xxrexraptorxx.nexus.utils.CreativeTabs;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);


    public static final TagKey<Block> MINEABLE_WITH_MULTI_TOOL = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(References.MODID, "mineable/multi_tool"));


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //BASIC
    public static final RegistryObject<WeaponBox> WEAPON_BOX_I = BLOCKS.register("weapon_box_1", WeaponBox::new);
    public static final RegistryObject<Item> WEAPON_BOX_I_BLOCKITEM = ITEMS.register("weapon_box_1", () -> new BlockItem(WEAPON_BOX_I.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<WeaponBox> WEAPON_BOX_II = BLOCKS.register("weapon_box_2", WeaponBox::new);
    public static final RegistryObject<Item> WEAPON_BOX_II_BLOCKITEM = ITEMS.register("weapon_box_2", () -> new BlockItem(WEAPON_BOX_II.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<WeaponBox> WEAPON_BOX_III = BLOCKS.register("weapon_box_3", WeaponBox::new);
    public static final RegistryObject<Item> WEAPON_BOX_III_BLOCKITEM = ITEMS.register("weapon_box_3", () -> new BlockItem(WEAPON_BOX_III.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_RED = BLOCKS.register("nexus_red", Nexus::new);
    public static final RegistryObject<Item> NEXUS_RED_BLOCKITEM = ITEMS.register("nexus_red", () -> new BlockItem(NEXUS_RED.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_BLUE     = BLOCKS.register("nexus_blue", Nexus::new);
    public static final RegistryObject<Item> NEXUS_BLUE_BLOCKITEM = ITEMS.register("nexus_blue", () -> new BlockItem(NEXUS_BLUE.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_GREEN = BLOCKS.register("nexus_green", Nexus::new);
    public static final RegistryObject<Item> NEXUS_GREEN_BLOCKITEM = ITEMS.register("nexus_green", () -> new BlockItem(NEXUS_GREEN.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_YELLOW = BLOCKS.register("nexus_yellow", Nexus::new);
    public static final RegistryObject<Item> NEXUS_YELLOW_BLOCKITEM = ITEMS.register("nexus_yellow", () -> new BlockItem(NEXUS_YELLOW.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));


    //EARLY MODERN AGE
    public static final RegistryObject<ExplosiveMine> EXPLOSIVE_MINE = BLOCKS.register("explosive_mine", ExplosiveMine::new);
    public static final RegistryObject<Item> EXPLOSIVE_MINE_BLOCKITEM = ITEMS.register("explosive_mine", () -> new BlockItem(EXPLOSIVE_MINE.get(), new Item.Properties().tab(CreativeTabs.EARLY_MODERN_AGE_TAB)));

    public static final RegistryObject<ExplosiveMine> TOXIC_MINE = BLOCKS.register("toxic_mine", ExplosiveMine::new);
    public static final RegistryObject<Item> TOXIC_MINE_BLOCKITEM = ITEMS.register("toxic_mine", () -> new BlockItem(TOXIC_MINE.get(), new Item.Properties().tab(CreativeTabs.EARLY_MODERN_AGE_TAB)));


}