package net.project.mod.heartbeat;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.combat.CombatSetup;

public class HeartbeatManager {
    @SubscribeEvent
    public void heartbeatController(PlayerTickEvent.Post event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getData(CombatSetup.COMBAT)) {
                if (serverPlayer.getData(HeartbeatSetup.HEART_BEAT) < 1) {
                    serverPlayer.setData(HeartbeatSetup.HEART_BEAT, serverPlayer.getData(HeartbeatSetup.HEART_BEAT) + 1);
                }
            } else
            if (serverPlayer.getData(HeartbeatSetup.HEART_BEAT) > 0) {
                serverPlayer.setData(HeartbeatSetup.HEART_BEAT, serverPlayer.getData(HeartbeatSetup.HEART_BEAT) - 1);
            }
        }
    }
}
