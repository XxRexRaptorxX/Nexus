package xxrexraptorxx.nexus.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.nexus.items.NexusTracker;
import xxrexraptorxx.nexus.items.RepairKit;
import xxrexraptorxx.nexus.main.References;

public class ModItems {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(References.MODID);


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final DeferredItem<RepairKit> REPAIR_KIT = ITEMS.register("repair_kit", () -> new RepairKit(new Item.Properties().setId(itemId("repair_kit"))));
    public static final DeferredItem<NexusTracker> NEXUS_TRACKER = ITEMS.register("nexus_tracker", () -> new NexusTracker(new Item.Properties().setId(itemId("nexus_tracker"))));
    public static final DeferredItem<Item> TRANSMITTER = ITEMS.register("transmitter", () -> new Item(new Item.Properties().setId(itemId("transmitter"))));


    public static ResourceKey<Item> itemId(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(References.MODID, name));
    }
}