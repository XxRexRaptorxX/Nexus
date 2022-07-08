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
    public static ForgeConfigSpec.BooleanValue GLOWING_EFFECT_FROM_NEXUS;
    public static ForgeConfigSpec.BooleanValue NEXUS_UNDER_ATTACK_MESSAGE;
    public static ForgeConfigSpec.BooleanValue SPECTATOR_MODE_AFTER_LOST_NEXUS;
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
        GLOWING_EFFECT_FROM_NEXUS = builder.comment("Should the Nexus spread a glowing effect when attacked?").define("glowing_effect_from_nexus", true);
        NEXUS_UNDER_ATTACK_MESSAGE = builder.comment("Should a message be sent when a Nexus is attacked?").define("nexus_under_attack_message", true);
        SPECTATOR_MODE_AFTER_LOST_NEXUS = builder.comment("Should the players of a lost team put into spectator mode?").define("spectator_mode_after_lost_nexus", true);
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