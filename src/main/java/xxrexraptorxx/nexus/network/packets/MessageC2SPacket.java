package xxrexraptorxx.nexus.network.packets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;
import xxrexraptorxx.nexus.items.NexusTracker;
import xxrexraptorxx.nexus.main.References;

public record MessageC2SPacket() implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(References.MODID, "nexus_coords_sync");


    public MessageC2SPacket(FriendlyByteBuf buffer) {
        this();
    }


    @Override
    public void write(final FriendlyByteBuf buffer) { }


    @Override
    @NotNull
    public ResourceLocation id() {
        return ID;
    }


    //Nexus Tracker function
    public static void handle(final MessageC2SPacket data, final PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            if(context.level().isEmpty())
                return;


            //Serverside
            ServerPlayer player = (ServerPlayer) context.player().get();
            ServerLevel level = player.serverLevel();
            BlockPos playerPos = player.getOnPos();
            ServerScoreboard scoreboard = (ServerScoreboard) level.getServer().getScoreboard();

            if(!level.isClientSide) {
                if (scoreboard.getObjectiveNames().contains("RED_NEXUS") || scoreboard.getObjectiveNames().contains("BLUE_NEXUS") || scoreboard.getObjectiveNames().contains("GREEN_NEXUS") ||
                        scoreboard.getObjectiveNames().contains("YELLOW_NEXUS") || scoreboard.getObjectiveNames().contains("BLACK_NEXUS") || scoreboard.getObjectiveNames().contains("WHITE_NEXUS")) {
                    player.displayClientMessage(Component.translatable("message.nexus.tracking").withStyle(ChatFormatting.GRAY), false);

                } else {
                    player.displayClientMessage(Component.translatable("message.nexus.tracking_failed").withStyle(ChatFormatting.GRAY), false);
                }

                if (scoreboard.getObjectiveNames().contains("RED_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("RED_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.RED), false);
                }
                if (scoreboard.getObjectiveNames().contains("BLUE_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("BLUE_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.BLUE), false);
                }
                if (scoreboard.getObjectiveNames().contains("GREEN_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("GREEN_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.GREEN), false);
                }
                if (scoreboard.getObjectiveNames().contains("YELLOW_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("YELLOW_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.YELLOW), false);
                }
                if (scoreboard.getObjectiveNames().contains("BLACK_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("BLACK_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.DARK_GRAY), false);
                }
                if (scoreboard.getObjectiveNames().contains("WHITE_NEXUS")) {
                    player.displayClientMessage(Component.literal("Nexus ").append(Component.literal(String.valueOf(
                                    NexusTracker.distance(playerPos, scoreboard.getObjective("WHITE_NEXUS").getFormattedDisplayName().getString()))))
                            .append(Component.translatable("message.nexus.tracker_distance")).withStyle(ChatFormatting.WHITE), false);
                }
            }
        });
    }

}
