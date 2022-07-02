package xxrexraptorxx.nexus.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import xxrexraptorxx.nexus.main.ModBlocks;
import xxrexraptorxx.nexus.main.References;

public class CreativeTabs {

    public static final CreativeModeTab BASE_TAB = new CreativeModeTab(References.MODID + "_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.NEXUS_GREEN.get());
        }
    };

}
