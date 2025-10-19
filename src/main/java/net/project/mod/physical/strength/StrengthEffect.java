package net.project.mod.physical.strength;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StrengthEffect {

    private final Map<UUID, Double> originalSpeed = new HashMap<>();
    private final Map<UUID, Double> originalJump = new HashMap<>();

    private static final ResourceLocation RIGHT_ARM_MODIFIER = ResourceLocation.fromNamespaceAndPath(Mod.MODID, "right_arm_modifier");
    private static final ResourceLocation LEFT_ARM_MODIFIER = ResourceLocation.fromNamespaceAndPath(Mod.MODID, "left_arm_modifier");

    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        UUID uuid = serverPlayer.getUUID();
        double currentSpeed = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue();
        double currentJump = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).getBaseValue();

        var attackAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE));

        originalSpeed.putIfAbsent(uuid, currentSpeed);
        originalJump.putIfAbsent(uuid, currentJump);

        AttributeModifier rightArmModifier = new AttributeModifier(
                RIGHT_ARM_MODIFIER,
                0.01 * serverPlayer.getData(StrengthSetup.R_ARM_STRENGTH.get()),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        AttributeModifier leftArmModifier = new AttributeModifier(
                    LEFT_ARM_MODIFIER,
                0.1 * serverPlayer.getData(StrengthSetup.L_ARM_STRENGTH.get()),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        if (serverPlayer.getData(StrengthSetup.LEGS_STRENGTH.get()) > 0) {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(originalSpeed.get(uuid) * 1.5);
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(originalJump.get(uuid) * 1.5);
        } else {
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(originalSpeed.get(uuid));
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(originalJump.get(uuid));
        }

        if (serverPlayer.getMainArm() == HumanoidArm.LEFT) {
            if (serverPlayer.getData(StrengthSetup.L_ARM_STRENGTH.get()) > 0) {
                attackAttr.addOrUpdateTransientModifier(leftArmModifier);
            }
        } else
        if (serverPlayer.getData(StrengthSetup.R_ARM_STRENGTH.get()) > 0) {
            attackAttr.addOrUpdateTransientModifier(rightArmModifier);
        }
    }
}