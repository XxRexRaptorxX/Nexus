package xxrexraptorxx.nexus.main;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.nexus.utils.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage https://www.curseforge.com/minecraft/mc-mods/nexus-battle-mode
 **/
@Mod(References.MODID)
public class Nexus {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation CREATIVE_TAB = new ResourceLocation(References.MODID, "tab");


    public Nexus() {
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(Nexus.class);

        Config.init();
        ModBlocks.init();
        ModItems.init();
    }


    //Creative Tab
    @SubscribeEvent
    public static void registerTabs(final CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(CREATIVE_TAB, (cf) -> cf.icon(() -> new ItemStack(ModBlocks.NEXUS_GREEN.get()))
                .title(Component.translatable("itemGroup." + References.MODID + ".tab")).displayItems((flagSet, output, ifSth) -> {
                    output.accept(ModBlocks.NEXUS_RED.get());
                    output.accept(ModBlocks.NEXUS_BLUE.get());
                    output.accept(ModBlocks.NEXUS_GREEN.get());
                    output.accept(ModBlocks.NEXUS_YELLOW.get());
                    output.accept(ModBlocks.NEXUS_WHITE.get());
                    output.accept(ModBlocks.NEXUS_BLACK.get());
                    output.accept(ModItems.REPAIR_KIT.get());
                })
        );
    }

}