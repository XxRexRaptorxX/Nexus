package xxrexraptorxx.nexus.utils;

import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_NEXUS = "nexus";

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue UPDATE_CHECKER;
    public static ForgeConfigSpec.BooleanValue NEXUS_EFFECT_WHEN_RIGHT_CLICKED;
    public static ForgeConfigSpec.BooleanValue NEXUS_REPAIRING;
    public static ForgeConfigSpec.IntValue NEXUS_XP_AMOUNT;
    public static ForgeConfigSpec.IntValue NEXUS_XP_STAGE_AMOUNT;
    public static ForgeConfigSpec.IntValue NEXUS_HARDNESS;
    public static ForgeConfigSpec.ConfigValue<List<String>> NEXUS_REWARDS;


    public static void init() {
        initServer();
        initClient();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }


    public static void initClient() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("General").push(CATEGORY_GENERAL);
        UPDATE_CHECKER = builder.comment("Activate the Update-Checker").define("update-checker", true);
        builder.pop();

        CLIENT_CONFIG = builder.build();
    }


    public static void initServer() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Nexus").push(CATEGORY_NEXUS);
        NEXUS_EFFECT_WHEN_RIGHT_CLICKED = builder.comment("Should the Nexus spawn a area effect cloud with damage boost when right clicked?").define("nexus_effect_when_right_clicked", true);
        //NEXUS_REPAIRING = builder.comment("should the nexus be repairable?").define("nexus_repairing", true);     TODO
        NEXUS_XP_AMOUNT = builder.comment("How many XP the Nexus drop").defineInRange("nexus_xp_amount", 500, 0, 1000);
        NEXUS_XP_STAGE_AMOUNT = builder.comment("How many XP the Nexus drop if it switches to another destruction level").defineInRange("nexus_stage_xp_amount", 50, 0, 1000);
        NEXUS_HARDNESS = builder.comment("How hard the Nexus is").defineInRange("nexus_hardness", 100, 10, 1000);
        NEXUS_REWARDS = builder.comment("A list with all the rewards that drop when a Nexus is destroyed [amount*modid:item]").define("nexus_rewards", new ArrayList<>(Arrays.asList(
                "1*" + ForgeRegistries.ITEMS.getKey(Items.NETHER_STAR).toString()
        )));
        builder.pop();

        SERVER_CONFIG = builder.build();
    }
}