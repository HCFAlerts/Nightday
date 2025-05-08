package me.andyreckt.holiday.core.util.redis.pubsub.packets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.andyreckt.holiday.api.server.IServer;
import me.andyreckt.holiday.core.HolidayAPI;
import me.andyreckt.holiday.core.server.Server;
import me.andyreckt.holiday.core.util.redis.messaging.Packet;

@Getter
@RequiredArgsConstructor
public class ServerKeepAlivePacket implements Packet {

    private final Server server;

    @Override
    public void onReceive() {
        IServer savedServer = HolidayAPI.getUnsafeAPI().getServerManager().getServer(server.getServerId());
        if (savedServer != null && savedServer.getLastKeepAlive() > server.getLastKeepAlive()) return;
        HolidayAPI.getUnsafeAPI().getServerManager().getServers().put(server.getServerId(), server);
    }
}
