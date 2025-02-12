package xxrexraptorxx.nexus.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_NEXUS_PROPERTIES = "nexus_properties";
    public static final String CATEGORY_NEXUS_FEATURES = "nexus_features";
    public static final String CATEGORY_SUPPLY_CRATE_PROPERTIES = "supply_crate_properties";
    public static final String CATEGORY_BARRIER_PROPERTIES = "barrier_properties";

    public static ModConfigSpec SERVER_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    public static ModConfigSpec.BooleanValue UPDATE_CHECKER;
    public static ModConfigSpec.BooleanValue BARRIER_DAMAGE;
    public static ModConfigSpec.BooleanValue NEXUS_EFFECT_WHEN_RIGHT_CLICKED;
    public static ModConfigSpec.BooleanValue GLOWING_EFFECT_FROM_NEXUS;
    public static ModConfigSpec.BooleanValue NEXUS_UNDER_ATTACK_MESSAGE;
    public static ModConfigSpec.BooleanValue SPECTATOR_MODE_AFTER_LOST_NEXUS;
    public static ModConfigSpec.BooleanValue NEXUS_REPAIRING;
    public static ModConfigSpec.BooleanValue NEXUS_TRACKING;
    public static ModConfigSpec.IntValue TRACKING_COOLDOWN;
    public static ModConfigSpec.IntValue NEXUS_XP_AMOUNT;
    public static ModConfigSpec.IntValue REPAIR_COOLDOWN;
    public static ModConfigSpec.IntValue NEXUS_XP_STAGE_AMOUNT;
    public static ModConfigSpec.IntValue NEXUS_HARDNESS;
    public static ModConfigSpec.IntValue NEXUS_SAFE_ZONE;
    public static ModConfigSpec.ConfigValue<List<String>> NEXUS_REWARDS;
    public static ModConfigSpec.ConfigValue<List<String>> SUPPLY_CRATE_LOOT;
    public static ModConfigSpec.IntValue SUPPLY_CRATE_LOOT_AMOUNT;
    public static ModConfigSpec.IntValue SUPPLY_CRATE_XP_AMOUNT;
    public static ModConfigSpec.BooleanValue PATREON_REWARDS;


    public static void init(ModContainer container) {
        initServer();
        initClient();

        container.registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }


    public static void initClient() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General").push(CATEGORY_GENERAL);
        UPDATE_CHECKER = builder.comment("Activate the Update-Checker").define("update-checker", true);
        builder.pop();

        CLIENT_CONFIG = builder.build();
    }


    public static void initServer() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General").push(CATEGORY_GENERAL);
        PATREON_REWARDS = builder.comment("Enables ingame rewards on first spawn for Patreons").define("patreon_rewards", true);
        builder.pop();

        builder.comment("Nexus Properties").push(CATEGORY_NEXUS_PROPERTIES);
        NEXUS_XP_AMOUNT = builder.comment("How many XP the Nexus drop").defineInRange("nexus_xp_amount", 500, 0, 1000);
        NEXUS_XP_STAGE_AMOUNT = builder.comment("How many XP the Nexus drop if it switches to another destruction level").defineInRange("nexus_stage_xp_amount", 50, 0, 1000);
        NEXUS_SAFE_ZONE = builder.comment("The radius of the zone around a Nexus where no block can be placed [0 = disabled] (large values cost performance)").defineInRange("nexus_safe_zone", 6, 0, 15);
        //NEXUS_HARDNESS = builder.comment("How hard the Nexus is").defineInRange("nexus_hardness", 100, 10, 1000);
        NEXUS_REWARDS = builder.comment("A list with all the rewards that drop when a Nexus is destroyed [amount*modid:item]").define("nexus_rewards", new ArrayList<>(Arrays.asList(
                "1*" + BuiltInRegistries.ITEM.getKey(Items.NETHER_STAR).toString()
        )));
        NEXUS_REPAIRING = builder.comment("Should the nexus be repairable?").define("nexus_repairing", true);
        REPAIR_COOLDOWN = builder.comment("How long should the Cooldown be after repair?").defineInRange("repair_cooldown", 1500, 0, 100000);
        NEXUS_TRACKING = builder.comment("Should the nexus be trackable?").define("nexus_tracking", true);
        TRACKING_COOLDOWN = builder.comment("How long should the Cooldown be after using a Nexus-Tracker?").defineInRange("tracking_cooldown", 1500, 0, 100000);
        builder.pop();

        builder.comment("Nexus Features").push(CATEGORY_NEXUS_FEATURES);
        NEXUS_UNDER_ATTACK_MESSAGE = builder.comment("Should a message be sent when a Nexus is attacked?").define("nexus_under_attack_message", true);
        NEXUS_EFFECT_WHEN_RIGHT_CLICKED = builder.comment("Should the Nexus spawn a area effect cloud with damage boost when right clicked?").define("nexus_effect_when_right_clicked", false);
        GLOWING_EFFECT_FROM_NEXUS = builder.comment("Should the Nexus spread a glowing effect when attacked?").define("glowing_effect_from_nexus", true);
        SPECTATOR_MODE_AFTER_LOST_NEXUS = builder.comment("Should the players of a lost team put into spectator mode?").define("spectator_mode_after_lost_nexus", true);
        builder.pop();

        builder.comment("Barrier Features").push(CATEGORY_BARRIER_PROPERTIES);
        BARRIER_DAMAGE = builder.comment("Should the security barrier cause damage when touched?").define("barrier_damage", true);
        builder.pop();

        builder.comment("Supply Crate Properties").push(CATEGORY_SUPPLY_CRATE_PROPERTIES);
        SUPPLY_CRATE_LOOT = builder.comment("A list with all the loot that can drop when a Supply Crate is destroyed [amount*modid:item]").define("supply_crate_loot", new ArrayList<>(Arrays.asList(
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SWORD).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.TRIDENT).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HELMET).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_CHESTPLATE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_LEGGINGS).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_BOOTS).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.CROSSBOW).toString(),
                "3*" + BuiltInRegistries.ITEM.getKey(Items.GOLDEN_APPLE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.ENCHANTED_GOLDEN_APPLE).toString(),
                "15*" + BuiltInRegistries.ITEM.getKey(Items.BREAD).toString(),
                "5*" + BuiltInRegistries.ITEM.getKey(Items.TNT).toString(),
                "5*" + BuiltInRegistries.ITEM.getKey(Items.ENDER_PEARL).toString(),
                "10*" + BuiltInRegistries.ITEM.getKey(Items.IRON_INGOT).toString(),
                "10*" + BuiltInRegistries.ITEM.getKey(Items.GOLD_INGOT).toString(),
                "3*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND).toString(),
                "4*" + BuiltInRegistries.ITEM.getKey(Items.EXPERIENCE_BOTTLE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_AXE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_PICKAXE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_AXE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE).toString(),
                "1*" + BuiltInRegistries.ITEM.getKey(Items.SHIELD).toString(),
                "10*" + BuiltInRegistries.ITEM.getKey(Items.WIND_CHARGE).toString()

        )));
        SUPPLY_CRATE_LOOT_AMOUNT = builder.comment("How many different items should be dropped from the list? [0 = no drops]").defineInRange("supply_crate_loot_amount", 3, 0, 10);
        SUPPLY_CRATE_XP_AMOUNT = builder.comment("How many XP a Supply Crate drop").defineInRange("supply_crate_xp_amount", 10, 0, 1000);
        builder.pop();

        SERVER_CONFIG = builder.build();
    }
}