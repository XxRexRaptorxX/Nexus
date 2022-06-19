package xxrexraptorxx.nexus.main;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.nexus.utils.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage https://www.curseforge.com/minecraft/mc-mods/nexus
 **/
@Mod(References.MODID)
public class Nexus {

    public static final Logger LOGGER = LogManager.getLogger();

    public Nexus() {

        Config.init();
        ModBlocks.init();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}