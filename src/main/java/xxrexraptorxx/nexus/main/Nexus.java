package xxrexraptorxx.nexus.main;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.magmacore.config.ConfigHelper;
import xxrexraptorxx.magmacore.main.ModRegistry;
import xxrexraptorxx.nexus.network.ModPackets;
import xxrexraptorxx.nexus.registries.CreativeModeTabs;
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


    public Nexus(IEventBus eventBus, ModContainer container) {
        ModBlocks.init(eventBus);
        ModItems.init(eventBus);
        CreativeModeTabs.init(eventBus);

        eventBus.addListener(ModPackets::register);

        ConfigHelper.registerConfigs(container, References.MODID, true, Config.SERVER_CONFIG, Config.CLIENT_CONFIG);
        ModRegistry.register(References.MODID, References.NAME, References.URL);
    }


    @Mod(value = References.MODID, dist = Dist.CLIENT)
    public static class BlockDetectiveClient {

        public BlockDetectiveClient(ModContainer container) {
            ConfigHelper.registerIngameConfig(container);
        }
    }
}
