package xxrexraptorxx.nexus.registries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.nexus.items.BasicItem;
import xxrexraptorxx.nexus.items.NexusTracker;
import xxrexraptorxx.nexus.items.RepairKit;
import xxrexraptorxx.nexus.main.References;

public class ModItems {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(References.MODID);


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final DeferredItem<RepairKit> REPAIR_KIT = ITEMS.register("repair_kit", RepairKit::new);
    public static final DeferredItem<NexusTracker> NEXUS_TRACKER = ITEMS.register("nexus_tracker", NexusTracker::new);
    public static final DeferredItem<BasicItem> TRANSMITTER = ITEMS.register("transmitter", BasicItem::new);
}