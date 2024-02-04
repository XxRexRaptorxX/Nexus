package xxrexraptorxx.nexus.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.network.packets.MessageC2SPacket;

public class ModPackets {

    private ModPackets() {}

    public static void register(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(References.MODID).versioned("1.0");

        registrar.play(MessageC2SPacket.ID, MessageC2SPacket::new, handler -> handler.server(MessageC2SPacket::handle));
    }


    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.SERVER.noArg().send(message);
    }


    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.PLAYER.with(player).send(message);
    }


    public static void sendToPlayerNear(CustomPacketPayload message, PacketDistributor.TargetPoint targetPoint) {
        PacketDistributor.NEAR.with(targetPoint).send(message);
    }


    public static void sendToPlayersWithinXBlocks(CustomPacketPayload message, BlockPos pos, ResourceKey dimension, int distance) {
        PacketDistributor.NEAR.with(new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), distance, dimension)).send(message);
    }


    public static void sendToAllPlayers(CustomPacketPayload message) {
        PacketDistributor.ALL.noArg().send(message);
    }
}
