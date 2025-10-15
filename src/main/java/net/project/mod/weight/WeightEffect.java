package net.project.mod.weight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.Mod;
import java.util.Objects;


public class WeightEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Mod.MODID, "weight_speed");

    @SubscribeEvent
    public void tick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        int weight = serverPlayer.getData(WeightSetup.WEIGHT.get());
        var speedAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED));

        if (speedAttr.hasModifier(MODIFIER_ID)) {
            speedAttr.removeModifier(MODIFIER_ID);
        }

        double adjustment = -0.001 * weight;

        AttributeModifier modifier = new AttributeModifier(
                MODIFIER_ID,
                adjustment,
                AttributeModifier.Operation.ADD_VALUE
        );

        speedAttr.addOrUpdateTransientModifier(modifier);
    }
}