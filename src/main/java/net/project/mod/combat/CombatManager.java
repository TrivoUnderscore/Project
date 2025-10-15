package net.project.mod.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class CombatManager {
    @SubscribeEvent
    public void inCombat(LivingDamageEvent.Post event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.setData(CombatSetup.TIME, 0);
            if (!serverPlayer.getData(CombatSetup.COMBAT)) {
                serverPlayer.setData(CombatSetup.COMBAT, true);
                serverPlayer.sendSystemMessage(Component.literal("You've entered combat."));
            }
        }
    }

    @SubscribeEvent
    public void inCombatTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            boolean inCombat = serverPlayer.getData(CombatSetup.COMBAT);
            if (inCombat) {
                int combatTime = serverPlayer.getData(CombatSetup.TIME);
                serverPlayer.setData(CombatSetup.TIME, combatTime + 1);
                if (combatTime >= (10 * 20)) {
                    serverPlayer.setData(CombatSetup.COMBAT, false);
                    serverPlayer.sendSystemMessage(Component.literal("You're out of combat."));
                }
            }
        }
    }
}
