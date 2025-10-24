package xxrexraptorxx.nexus.main;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.magmacore.config.ConfigHelper;
import xxrexraptorxx.magmacore.main.ModRegistry;
import xxrexraptorxx.nexus.network.ModPackets;
import xxrexraptorxx.nexus.registries.CreativeTabs;
import xxrexraptorxx.nexus.registries.ModBlocks;
import xxrexraptorxx.nexus.registries.ModItems;
import xxrexraptorxx.nexus.utils.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage <a href="https://www.curseforge.com/minecraft/mc-mods/nexus-battle-mode">...</a>
 **/
@Mod(References.MODID)
public class Nexus {

    public static final Logger LOGGER = LogManager.getLogger();


    public Nexus(IEventBus bus, ModContainer container) {
        ModBlocks.init(bus);
        ModItems.init(bus);
        CreativeTabs.init(bus);

        bus.addListener(ModPackets::register);
        bus.addListener(this::addCreative);

        ConfigHelper.registerConfigs(container, References.MODID, false, Config.SERVER_CONFIG, null);
        ModRegistry.register(References.MODID, References.NAME, References.URL);
    }


    @Mod(value = References.MODID, dist = Dist.CLIENT)
    public static class BlockDetectiveClient {

        public BlockDetectiveClient(ModContainer container) {
            ConfigHelper.registerIngameConfig(container);
        }
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> key = event.getTabKey();

        if (key == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.TRANSMITTER);

        } else if (key == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.NEXUS_TRACKER);
            event.accept(ModItems.REPAIR_KIT);
        }
    }
}
