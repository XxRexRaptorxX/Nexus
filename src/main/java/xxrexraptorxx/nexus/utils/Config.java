package xxrexraptorxx.nexus.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_NEXUS = "nexus";
    public static final String CATEGORY_ITEMS = "items";

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue UPDATE_CHECKER;
    public static ForgeConfigSpec.BooleanValue ADD_VANILLA_ITEMS_TO_MOD_TABS;
    public static ForgeConfigSpec.BooleanValue BEDROCK_MINING_WITH_PULVERIZER;
    public static ForgeConfigSpec.BooleanValue NEXUS_EFFECT_WHEN_RIGHT_CLICKED;
    public static ForgeConfigSpec.IntValue NEXUS_XP_AMOUNT;
    public static ForgeConfigSpec.IntValue NEXUS_XP_STAGE_AMOUNT;
    public static ForgeConfigSpec.IntValue NEXUS_HARDNESS;


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
        ADD_VANILLA_ITEMS_TO_MOD_TABS = builder.comment("Enable that tools/weapons to be added to the mod tabs of the correct epoch").define("add_vanilla_items_to_mod_tabs", true);
        builder.pop();

        CLIENT_CONFIG = builder.build();
    }


    public static void initServer() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Items").push(CATEGORY_ITEMS);
        BEDROCK_MINING_WITH_PULVERIZER = builder.comment("Should the Industrial Pulverizer be able to mine bedrock?").define("bedrock_mining_with_pulverizer", true);
        builder.pop();

        builder.comment("Nexus").push(CATEGORY_NEXUS);
        NEXUS_EFFECT_WHEN_RIGHT_CLICKED = builder.comment("Should the Nexus spawn a area effect cloud with damage boost when right clicked?").define("nexus_effect_when_right_clicked", true);
        NEXUS_XP_AMOUNT = builder.comment("How many XP the Nexus drop").defineInRange("nexus_xp_amount", 500, 0, 1000);
        NEXUS_XP_STAGE_AMOUNT = builder.comment("How many XP the Nexus drop if it switches to another destruction level").defineInRange("nexus_xp_amount", 50, 0, 1000);
        NEXUS_HARDNESS = builder.comment("How hard the Nexus is").defineInRange("nexus_hardness", 100, 10, 1000);
        builder.pop();

        SERVER_CONFIG = builder.build();
    }

}