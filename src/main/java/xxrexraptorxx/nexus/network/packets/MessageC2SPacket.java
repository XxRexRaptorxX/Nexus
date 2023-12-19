package xxrexraptorxx.nexus.network.packets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkEvent;
import xxrexraptorxx.nexus.items.NexusTracker;

public class MessageC2SPacket {

    public MessageC2SPacket() {
    }


    public MessageC2SPacket(FriendlyByteBuf buf) {
    }


    public void toBytes(FriendlyByteBuf buf) {
    }


    //Nexus Tracker function
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {

            //Serverside
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            BlockPos playerPos = player.getOnPos();
            ServerScoreboard scoreboard = (ServerScoreboard) level.getServer().getScoreboard();

            if(!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.nexus.tracking"), false);

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

        return true;
    }

}
