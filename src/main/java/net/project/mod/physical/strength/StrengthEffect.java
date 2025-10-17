package net.project.mod.physical.strength;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StrengthEffect {

    private final Map<UUID, Double> originalSpeed = new HashMap<>();
    private final Map<UUID, Double> originalJump = new HashMap<>();

    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        UUID uuid = serverPlayer.getUUID();
        double currentSpeed = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue();
        double currentJump = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).getBaseValue();

        originalSpeed.putIfAbsent(uuid, currentSpeed);
        originalJump.putIfAbsent(uuid, currentJump);

        if (serverPlayer.getData(StrengthSetup.LEGS_STRENGTH.get()) > 0) {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(originalSpeed.get(uuid) * 1.5);
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(originalJump.get(uuid) * 1.5);
        } else {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(originalSpeed.get(uuid));
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(originalJump.get(uuid));
        }
    }
}