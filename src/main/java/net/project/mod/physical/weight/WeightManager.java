package net.project.mod.physical.weight;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.Mod;

public class WeightManager {

    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            int weight = 0;

            weight += serverPlayer.getOffhandItem().getCount();
            for (ItemStack stack : serverPlayer.getInventory().items) {
                weight += stack.getCount();
            }

            serverPlayer.setData(WeightSetup.WEIGHT.get(), weight);
            Mod.LOGGER.info("{}", weight);
        }
    }
}
