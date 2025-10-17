package net.project.mod.physical.endurance;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.physical.strength.StrengthSetup;

public class EnduranceManager {
    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getOffhandItem().is(Items.SHIELD)) {
                serverPlayer.setData(EnduranceSetup.ENDURANCE.get(), 1);
            } else
                serverPlayer.setData(EnduranceSetup.ENDURANCE.get(), 0);
        }
    }
}
