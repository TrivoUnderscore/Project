package net.project.mod.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoundsManager {
    private static class PlayerSoundData {
        boolean isPlayingSound = false;
        int tickTime = 0;
    }

    private static final Map<UUID, PlayerSoundData> playerData = new HashMap<>();

    public void playSound(SoundEvent sound, ServerPlayer player, int loopTimeTicks) {
        UUID id = player.getUUID();
        PlayerSoundData data = playerData.computeIfAbsent(id, uuid -> new PlayerSoundData());

        Holder<SoundEvent> soundHolder = Holder.direct(sound);
        if (!data.isPlayingSound) {
            player.connection.send(new net.minecraft.network.protocol.game.ClientboundSoundPacket(
                    soundHolder,
                    SoundSource.PLAYERS,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    1.0F,
                    1.0F,
                    player.level().getRandom().nextLong()
            ));            data.isPlayingSound = true;
        } else data.tickTime++;

        if (data.tickTime >= loopTimeTicks) {
            data.isPlayingSound = false;
            data.tickTime = 0;
        }
    }
}
