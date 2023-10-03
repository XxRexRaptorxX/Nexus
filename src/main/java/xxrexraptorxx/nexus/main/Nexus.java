package xxrexraptorxx.nexus.main;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.nexus.network.ModPackets;
import xxrexraptorxx.nexus.utils.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage https://www.curseforge.com/minecraft/mc-mods/nexus-battle-mode
 **/
@Mod(References.MODID)
public class Nexus {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation CREATIVE_TAB = new ResourceLocation(References.MODID, "main_tab");


    public Nexus() {
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(Nexus.class);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.init();
        ModBlocks.init();
        ModItems.init();

        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModPackets.register();
        });
    }


    //Creative Tab
    @SubscribeEvent
    public static void registerTabs(final CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(CREATIVE_TAB, (cf) -> cf.icon(() -> new ItemStack(ModBlocks.NEXUS_GREEN.get()))
                .title(Component.translatable("itemGroup." + References.MODID + "_tab")).displayItems((params, output) -> {
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
                })
        );
    }

}