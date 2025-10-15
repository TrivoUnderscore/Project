package net.project.mod.heartbeat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.sound.ModSounds;
import net.project.mod.sound.SoundsManager;

public class HeartbeatEffect {
    public static final SoundsManager soundsManager = new SoundsManager();

    @SubscribeEvent
    public void heartbeatEffect(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            int heartbeat = serverPlayer.getData(HeartbeatSetup.HEART_BEAT);
            if (heartbeat > 0) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30));
                soundsManager.playSound(ModSounds.HEART_BEAT.get(), serverPlayer, 40);
            }
        }
    }
}
