package xxrexraptorxx.nexus.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;
import xxrexraptorxx.nexus.main.References;
import xxrexraptorxx.nexus.network.packets.MessageC2SPacket;

public class ModPackets {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = ChannelBuilder.named(new ResourceLocation(References.MODID, "messages")).
                networkProtocolVersion(1).
                simpleChannel();

        INSTANCE = net;

        net.messageBuilder(MessageC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MessageC2SPacket::new)
                .encoder(MessageC2SPacket::toBytes)
                .consumerMainThread(MessageC2SPacket::handle)
                .add();
    }


    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }


    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }

}
