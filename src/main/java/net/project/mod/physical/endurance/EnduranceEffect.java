package net.project.mod.physical.endurance;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.physical.strength.StrengthSetup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EnduranceEffect {

    private final Map<UUID, Double> originalArmor = new HashMap<>();
    private final Map<UUID, Double> originalArmorToughness = new HashMap<>();

    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        UUID uuid = serverPlayer.getUUID();
        double currentArmor = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR)).getBaseValue();
        double currentArmorToughness = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR_TOUGHNESS)).getBaseValue();

        originalArmor.putIfAbsent(uuid, currentArmor);
        originalArmorToughness.putIfAbsent(uuid, currentArmorToughness);

        if (serverPlayer.getData(EnduranceSetup.ENDURANCE.get()) > 0) {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR)).setBaseValue(originalArmor.get(uuid) + 10);
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR_TOUGHNESS)).setBaseValue(originalArmorToughness.get(uuid) + 10);
        } else {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR)).setBaseValue(originalArmor.get(uuid));
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ARMOR_TOUGHNESS)).setBaseValue(originalArmorToughness.get(uuid));
        }
    }
}