package xxrexraptorxx.nexus.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.nexus.main.References;

public class CreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.MODID) ;

    public static void init(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(References.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + References.MODID + "_tab"))
            .icon(() -> ModBlocks.NEXUS_GREEN_BLOCKITEM.get().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(ModBlocks.NEXUS_RED.get());
                output.accept(ModBlocks.NEXUS_BLUE.get());
                output.accept(ModBlocks.NEXUS_GREEN.get());
                output.accept(ModBlocks.NEXUS_YELLOW.get());
                output.accept(ModBlocks.NEXUS_WHITE.get());
                output.accept(ModBlocks.NEXUS_BLACK.get());
                output.accept(ModBlocks.SUPPLY_CRATE.get());
                output.accept(ModBlocks.SECURTIY_WALL.get());
                output.accept(ModBlocks.SECURTIY_BARRIER.get());
                output.accept(ModItems.NEXUS_TRACKER.get());
                output.accept(ModItems.REPAIR_KIT.get());
                output.accept(ModItems.TRANSMITTER.get());
            }).build());
}
