package net.project.mod.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.core.jmx.Server;

public class SoundsManager {
    private boolean isPlayingSound = false;
    private int tickTime = 0;

    public void playSound(SoundEvent sound, ServerPlayer player, int loopTimeTicks) {
        Holder<SoundEvent> soundHolder = Holder.direct(sound);
        if (!isPlayingSound) {
            player.connection.send(new net.minecraft.network.protocol.game.ClientboundSoundPacket(
                    soundHolder,
                    SoundSource.PLAYERS,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    1.0F,
                    1.0F,
                    player.level().getRandom().nextLong()
            ));            isPlayingSound = true;
        } else tickTime++;

        if (tickTime >= loopTimeTicks) {
            isPlayingSound = false;
            tickTime = 0;
        }
    }
}
