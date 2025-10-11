package xxrexraptorxx.nexus.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;
import xxrexraptorxx.magmacore.config.ConfigHelper;
import xxrexraptorxx.magmacore.config.ConfigListHelper;

import java.util.Arrays;
import java.util.List;

public class Config {

    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SERVER_CONFIG;

    private static ModConfigSpec.BooleanValue BARRIER_DAMAGE;
    private static ModConfigSpec.BooleanValue NEXUS_EFFECT_WHEN_RIGHT_CLICKED;
    private static ModConfigSpec.BooleanValue GLOWING_EFFECT_FROM_NEXUS;
    private static ModConfigSpec.BooleanValue NEXUS_UNDER_ATTACK_MESSAGE;
    private static ModConfigSpec.BooleanValue SPECTATOR_MODE_AFTER_LOST_NEXUS;
    private static ModConfigSpec.BooleanValue NEXUS_REPAIRING;
    private static ModConfigSpec.BooleanValue NEXUS_TRACKING;
    private static ModConfigSpec.IntValue TRACKING_COOLDOWN;
    private static ModConfigSpec.IntValue NEXUS_XP_AMOUNT;
    private static ModConfigSpec.IntValue REPAIR_COOLDOWN;
    private static ModConfigSpec.IntValue NEXUS_XP_STAGE_AMOUNT;
    private static ModConfigSpec.IntValue NEXUS_SAFE_ZONE;
    private static ModConfigSpec.ConfigValue<List<? extends String>> NEXUS_REWARDS;
    private static ModConfigSpec.ConfigValue<List<? extends String>> SUPPLY_CRATE_LOOT;
    private static ModConfigSpec.IntValue SUPPLY_CRATE_LOOT_AMOUNT;
    private static ModConfigSpec.IntValue SUPPLY_CRATE_XP_AMOUNT;

    static {
        ConfigHelper.setCategory(SERVER_BUILDER, "nexus");
        NEXUS_XP_AMOUNT = SERVER_BUILDER.comment("How many XP the Nexus drop").defineInRange("nexus_xp_amount", 500, 0, 1000);
        NEXUS_XP_STAGE_AMOUNT = SERVER_BUILDER.comment("How many XP the Nexus drop if it switches to another destruction level").defineInRange("nexus_stage_xp_amount", 50, 0,
                1000);
        NEXUS_SAFE_ZONE = SERVER_BUILDER.comment("The radius of the zone around a Nexus where no block can be placed [0 = disabled] (large values cost performance)")
                .defineInRange("nexus_safe_zone", 6, 0, 15);
        NEXUS_REWARDS = SERVER_BUILDER.comment("A list with all the rewards that drop when a Nexus is destroyed [amount*modid:item]").defineListAllowEmpty("nexus_rewards",
                Arrays.asList("1*" + BuiltInRegistries.ITEM.getKey(Items.NETHER_STAR).toString()), () -> "amount*modid:item",
                obj -> obj instanceof String string && ConfigListHelper.isValidItemWithCount(string));
        NEXUS_REPAIRING = SERVER_BUILDER.comment("Should the nexus be repairable?").define("nexus_repairing", true);
        REPAIR_COOLDOWN = SERVER_BUILDER.comment("How long should the Cooldown be after repair?").defineInRange("repair_cooldown", 1500, 0, 100000);
        NEXUS_TRACKING = SERVER_BUILDER.comment("Should the nexus be trackable?").define("nexus_tracking", true);
        TRACKING_COOLDOWN = SERVER_BUILDER.comment("How long should the Cooldown be after using a Nexus-Tracker?").defineInRange("tracking_cooldown", 1500, 0, 100000);
        SERVER_BUILDER.pop();

        ConfigHelper.setCategory(SERVER_BUILDER, "game_events");
        NEXUS_UNDER_ATTACK_MESSAGE = SERVER_BUILDER.comment("Should a message be sent when a Nexus is attacked?").define("nexus_under_attack_message", true);
        NEXUS_EFFECT_WHEN_RIGHT_CLICKED = SERVER_BUILDER.comment("Should the Nexus spawn a area effect cloud with damage boost when right clicked?")
                .define("nexus_effect_when_right_clicked", false);
        GLOWING_EFFECT_FROM_NEXUS = SERVER_BUILDER.comment("Should the Nexus spread a glowing effect when attacked?").define("glowing_effect_from_nexus", true);
        SPECTATOR_MODE_AFTER_LOST_NEXUS = SERVER_BUILDER.comment("Should the players of a lost team put into spectator mode?").define("spectator_mode_after_lost_nexus", true);
        SERVER_BUILDER.pop();

        ConfigHelper.setCategory(SERVER_BUILDER, "barrier");
        BARRIER_DAMAGE = SERVER_BUILDER.comment("Should the security barrier cause damage when touched?").define("barrier_damage", true);
        SERVER_BUILDER.pop();

        ConfigHelper.setCategory(SERVER_BUILDER, "supply_crate");
        SUPPLY_CRATE_LOOT = SERVER_BUILDER.comment("A list with all the loot that can drop when a Supply Crate is destroyed [amount*modid:item]").defineListAllowEmpty(
                "supply_crate_loot",
                Arrays.asList("1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SWORD).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.TRIDENT).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HELMET).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_CHESTPLATE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_LEGGINGS).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_BOOTS).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.CROSSBOW).toString(),
                        "3*" + BuiltInRegistries.ITEM.getKey(Items.GOLDEN_APPLE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.ENCHANTED_GOLDEN_APPLE).toString(),
                        "15*" + BuiltInRegistries.ITEM.getKey(Items.BREAD).toString(), "5*" + BuiltInRegistries.ITEM.getKey(Items.TNT).toString(),
                        "5*" + BuiltInRegistries.ITEM.getKey(Items.ENDER_PEARL).toString(), "10*" + BuiltInRegistries.ITEM.getKey(Items.IRON_INGOT).toString(),
                        "10*" + BuiltInRegistries.ITEM.getKey(Items.GOLD_INGOT).toString(), "3*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND).toString(),
                        "4*" + BuiltInRegistries.ITEM.getKey(Items.EXPERIENCE_BOTTLE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_AXE).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.DIAMOND_PICKAXE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_AXE).toString(),
                        "1*" + BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE).toString(), "1*" + BuiltInRegistries.ITEM.getKey(Items.SHIELD).toString(),
                        "10*" + BuiltInRegistries.ITEM.getKey(Items.WIND_CHARGE).toString()),
                () -> "amount*modid:item", obj -> obj instanceof String string && ConfigListHelper.isValidItemWithCount(string));
        SUPPLY_CRATE_LOOT_AMOUNT = SERVER_BUILDER.comment("How many different items should be dropped from the list? [0 = no drops]").defineInRange("supply_crate_loot_amount", 3,
                0, 10);
        SUPPLY_CRATE_XP_AMOUNT = SERVER_BUILDER.comment("How many XP a Supply Crate drop").defineInRange("supply_crate_xp_amount", 10, 0, 1000);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }


    public static boolean getBarrierDamage() {
        return BARRIER_DAMAGE.get();
    }


    public static boolean getNexusEffectWhenRightClicked() {
        return NEXUS_EFFECT_WHEN_RIGHT_CLICKED.get();
    }


    public static boolean getGlowingEffectFromNexus() {
        return GLOWING_EFFECT_FROM_NEXUS.get();
    }


    public static boolean getNexusUnderAttackMessage() {
        return NEXUS_UNDER_ATTACK_MESSAGE.get();
    }


    public static boolean getSpectatorModeAfterLostNexus() {
        return SPECTATOR_MODE_AFTER_LOST_NEXUS.get();
    }


    public static boolean getNexusRepairingEnabled() {
        return NEXUS_REPAIRING.get();
    }


    public static boolean getNexusTrackingEnabled() {
        return NEXUS_TRACKING.get();
    }


    public static int getTrackingCooldown() {
        return TRACKING_COOLDOWN.get();
    }


    public static int getNexusXpAmount() {
        return NEXUS_XP_AMOUNT.get();
    }


    public static int getRepairCooldown() {
        return REPAIR_COOLDOWN.get();
    }


    public static int getNexusXpPerStageAmount() {
        return NEXUS_XP_STAGE_AMOUNT.get();
    }


    public static int getNexusSafeZone() {
        return NEXUS_SAFE_ZONE.get();
    }


    public static List<String> getNexusRewards() {
        return (List<String>) NEXUS_REWARDS.get();
    }


    public static List<String> getSupplyCrateLoot() {
        return (List<String>) SUPPLY_CRATE_LOOT.get();
    }


    public static int getSupplyCrateXpAmount() {
        return SUPPLY_CRATE_XP_AMOUNT.get();
    }


    public static int getSupplyCrateLootAmount() {
        return SUPPLY_CRATE_LOOT_AMOUNT.get();
    }
}
