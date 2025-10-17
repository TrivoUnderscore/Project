package net.project.mod.physical.weight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.Mod;
import java.util.Objects;


public class WeightEffect {

    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Mod.MODID, "weighted_speed_modifier");
    private static final ResourceLocation JUMP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Mod.MODID, "weighted_jump_modifier");

    @SubscribeEvent
    public void tick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        int weight = serverPlayer.getData(WeightSetup.WEIGHT.get());
        var speedAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED));
        var jumpAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH));

        AttributeModifier speedModifier = new AttributeModifier(
                SPEED_MODIFIER_ID,
                -0.01 * weight,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        AttributeModifier jumpModifier = new AttributeModifier(
                JUMP_MODIFIER_ID,
                -0.01 * weight,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        speedAttr.addOrUpdateTransientModifier(speedModifier);
        jumpAttr.addOrUpdateTransientModifier(jumpModifier);
    }
}