package xxrexraptorxx.nexus.main;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xxrexraptorxx.nexus.items.BasicItem;
import xxrexraptorxx.nexus.items.NexusTracker;
import xxrexraptorxx.nexus.items.RepairKit;
import xxrexraptorxx.nexus.main.References;

public class ModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<RepairKit> REPAIR_KIT = ITEMS.register("repair_kit", RepairKit::new);
    public static final RegistryObject<NexusTracker> NEXUS_TRACKER = ITEMS.register("nexus_tracker", NexusTracker::new);
    public static final RegistryObject<BasicItem> TRANSMITTER = ITEMS.register("transmitter", BasicItem::new);
}