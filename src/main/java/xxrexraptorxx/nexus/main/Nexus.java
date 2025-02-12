package xxrexraptorxx.nexus.main;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

        Config.init(container);
        ModBlocks.init(eventBus);
        ModItems.init(eventBus);
        CreativeModeTabs.init(eventBus);

        eventBus.addListener(ModPackets::register);
    }

}