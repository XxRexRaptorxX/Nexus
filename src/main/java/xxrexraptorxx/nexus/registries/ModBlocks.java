package xxrexraptorxx.nexus.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.nexus.blocks.NexusBlock;
import xxrexraptorxx.nexus.blocks.SecurityBarrier;
import xxrexraptorxx.nexus.blocks.SecurityWall;
import xxrexraptorxx.nexus.blocks.SupplyCrate;
import xxrexraptorxx.nexus.main.References;

public class ModBlocks {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(References.MODID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(References.MODID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final DeferredBlock<NexusBlock> NEXUS_RED = BLOCKS.register("nexus_red", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_RED_BLOCKITEM = ITEMS.register("nexus_red", () -> new BlockItem(NEXUS_RED.get(), new Item.Properties()));

    public static final DeferredBlock<NexusBlock> NEXUS_BLUE     = BLOCKS.register("nexus_blue", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_BLUE_BLOCKITEM = ITEMS.register("nexus_blue", () -> new BlockItem(NEXUS_BLUE.get(), new Item.Properties()));

    public static final DeferredBlock<NexusBlock> NEXUS_GREEN = BLOCKS.register("nexus_green", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_GREEN_BLOCKITEM = ITEMS.register("nexus_green", () -> new BlockItem(NEXUS_GREEN.get(), new Item.Properties()));

    public static final DeferredBlock<NexusBlock> NEXUS_YELLOW = BLOCKS.register("nexus_yellow", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_YELLOW_BLOCKITEM = ITEMS.register("nexus_yellow", () -> new BlockItem(NEXUS_YELLOW.get(), new Item.Properties()));

    public static final DeferredBlock<NexusBlock> NEXUS_WHITE = BLOCKS.register("nexus_white", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_WHITE_BLOCKITEM = ITEMS.register("nexus_white", () -> new BlockItem(NEXUS_WHITE.get(), new Item.Properties()));

    public static final DeferredBlock<NexusBlock> NEXUS_BLACK = BLOCKS.register("nexus_black", NexusBlock::new);
    public static final DeferredItem<Item> NEXUS_BLACK_BLOCKITEM = ITEMS.register("nexus_black", () -> new BlockItem(NEXUS_BLACK.get(), new Item.Properties()));

    public static final DeferredBlock<SupplyCrate> SUPPLY_CRATE = BLOCKS.register("supply_crate", SupplyCrate::new);
    public static final DeferredItem<Item> SUPPLY_CRATE_BLOCKITEM = ITEMS.register("supply_crate", () -> new BlockItem(SUPPLY_CRATE.get(), new Item.Properties()));

    public static final DeferredBlock<SecurityWall> SECURTIY_WALL = BLOCKS.register("security_wall", SecurityWall::new);
    public static final DeferredItem<Item> SECURITY_WALL_BLOCKITEM = ITEMS.register("security_wall", () -> new BlockItem(SECURTIY_WALL.get(), new Item.Properties()));

    public static final DeferredBlock<SecurityBarrier> SECURTIY_BARRIER = BLOCKS.register("security_barrier", SecurityBarrier::new);
    public static final DeferredItem<Item> SECURITY_BARRIER_BLOCKITEM = ITEMS.register("security_barrier", () -> new BlockItem(SECURTIY_BARRIER.get(), new Item.Properties()));

}