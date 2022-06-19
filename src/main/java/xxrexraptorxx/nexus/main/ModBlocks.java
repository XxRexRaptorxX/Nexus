package xxrexraptorxx.nexus.main;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xxrexraptorxx.nexus.blocks.Nexus;
import xxrexraptorxx.nexus.utils.CreativeTabs;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<Nexus> NEXUS_RED = BLOCKS.register("nexus_red", Nexus::new);
    public static final RegistryObject<Item> NEXUS_RED_BLOCKITEM = ITEMS.register("nexus_red", () -> new BlockItem(NEXUS_RED.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_BLUE     = BLOCKS.register("nexus_blue", Nexus::new);
    public static final RegistryObject<Item> NEXUS_BLUE_BLOCKITEM = ITEMS.register("nexus_blue", () -> new BlockItem(NEXUS_BLUE.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_GREEN = BLOCKS.register("nexus_green", Nexus::new);
    public static final RegistryObject<Item> NEXUS_GREEN_BLOCKITEM = ITEMS.register("nexus_green", () -> new BlockItem(NEXUS_GREEN.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

    public static final RegistryObject<Nexus> NEXUS_YELLOW = BLOCKS.register("nexus_yellow", Nexus::new);
    public static final RegistryObject<Item> NEXUS_YELLOW_BLOCKITEM = ITEMS.register("nexus_yellow", () -> new BlockItem(NEXUS_YELLOW.get(), new Item.Properties().tab(CreativeTabs.BASE_TAB)));

}