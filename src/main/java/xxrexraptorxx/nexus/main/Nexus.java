package xxrexraptorxx.nexus.main;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.nexus.network.ModPackets;
import xxrexraptorxx.nexus.registries.CreativeModeTabs;
import xxrexraptorxx.nexus.registries.ModBlocks;
import xxrexraptorxx.nexus.registries.ModItems;
import xxrexraptorxx.nexus.utils.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage https://www.curseforge.com/minecraft/mc-mods/nexus-battle-mode
 **/
@Mod(References.MODID)
public class Nexus {

    public static final Logger LOGGER = LogManager.getLogger();


    public Nexus(IEventBus eventBus) {

        Config.init();
        ModBlocks.init(eventBus);
        ModItems.init(eventBus);
        CreativeModeTabs.init(eventBus);

        eventBus.addListener(ModPackets::register);
    }

}