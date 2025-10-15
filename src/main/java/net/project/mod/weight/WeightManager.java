package net.project.mod.weight;

import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class WeightManager {
    @SubscribeEvent
    public void perTick(PlayerTickEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            int total = 0;
            for (ItemStack stack : serverPlayer.getInventory().items) {
                
            }
        }
    }
}
