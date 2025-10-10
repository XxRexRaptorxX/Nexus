package xxrexraptorxx.nexus.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.network.packets.MessageC2SPacket;

public class ModPackets {

    private ModPackets() {
    }


    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(References.MODID).versioned("1.0");

        registrar.playToServer(MessageC2SPacket.ID, MessageC2SPacket.STREAM_CODEC, MessageC2SPacket::handle);
    }

    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }


    public static void sendToPlayersWithinXBlocks(CustomPacketPayload message, BlockPos pos, ServerLevel level, int distance) {
        PacketDistributor.sendToPlayersNear(level, null, pos.getX(), pos.getY(), pos.getZ(), distance, message);
    }


    public static void sendToAllPlayers(CustomPacketPayload message) {
        PacketDistributor.sendToAllPlayers(message);
    }

}
