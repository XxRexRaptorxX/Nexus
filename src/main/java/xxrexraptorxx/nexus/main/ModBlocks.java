package xxrexraptorxx.nexus.main;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.utils.CreativeTabs;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<NexusBlock> NEXUS_RED = BLOCKS.register("nexus_red", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_RED_BLOCKITEM = ITEMS.register("nexus_red", () -> new BlockItem(NEXUS_RED.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<NexusBlock> NEXUS_BLUE     = BLOCKS.register("nexus_blue", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_BLUE_BLOCKITEM = ITEMS.register("nexus_blue", () -> new BlockItem(NEXUS_BLUE.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<NexusBlock> NEXUS_GREEN = BLOCKS.register("nexus_green", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_GREEN_BLOCKITEM = ITEMS.register("nexus_green", () -> new BlockItem(NEXUS_GREEN.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<NexusBlock> NEXUS_YELLOW = BLOCKS.register("nexus_yellow", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_YELLOW_BLOCKITEM = ITEMS.register("nexus_yellow", () -> new BlockItem(NEXUS_YELLOW.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<NexusBlock> NEXUS_WHITE = BLOCKS.register("nexus_white", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_WHITE_BLOCKITEM = ITEMS.register("nexus_white", () -> new BlockItem(NEXUS_WHITE.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<NexusBlock> NEXUS_BLACK = BLOCKS.register("nexus_black", NexusBlock::new);
    public static final RegistryObject<Item> NEXUS_BLACK_BLOCKITEM = ITEMS.register("nexus_black", () -> new BlockItem(NEXUS_BLACK.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

}