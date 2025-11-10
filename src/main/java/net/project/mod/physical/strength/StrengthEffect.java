package net.project.mod.physical.strength;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.project.mod.Mod;
import net.project.mod.combat.CombatSetup;

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

        AttributeInstance moveAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED));
        AttributeInstance jumpAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.JUMP_STRENGTH));
        AttributeInstance attackAttr = Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE));

        double currentSpeed = moveAttr.getBaseValue();
        double currentJump = jumpAttr.getBaseValue();
        serverPlayer.getData(CombatSetup.COMBAT);
        originalSpeed.putIfAbsent(uuid, currentSpeed);
        originalJump.putIfAbsent(uuid, currentJump);

        if (serverPlayer.getData(StrengthSetup.LEGS_STRENGTH.get()) > 0) {
            moveAttr.setBaseValue(originalSpeed.get(uuid) * 1.5);
            jumpAttr.setBaseValue(originalJump.get(uuid) * 1.5);
        } else {
            moveAttr.setBaseValue(originalSpeed.get(uuid));
            jumpAttr.setBaseValue(originalJump.get(uuid));
        }

        double base = attackAttr.getBaseValue();
        double rightStrength = serverPlayer.getData(StrengthSetup.R_ARM_STRENGTH.get());
        double leftStrength = serverPlayer.getData(StrengthSetup.L_ARM_STRENGTH.get());

        double rightExtra = base * 0.1 * rightStrength;
        double leftExtra = base * 0.1 * leftStrength;

        if (serverPlayer.getMainArm() == HumanoidArm.LEFT && leftStrength > 0) {
            AttributeModifier leftMod = new AttributeModifier(
                    LEFT_ARM_MODIFIER,
                    leftExtra,
                    AttributeModifier.Operation.ADD_VALUE
            );
            attackAttr.addOrUpdateTransientModifier(leftMod);
            attackAttr.removeModifier(RIGHT_ARM_MODIFIER);
        } else if (serverPlayer.getMainArm() == HumanoidArm.RIGHT && rightStrength > 0) {
            AttributeModifier rightMod = new AttributeModifier(
                    RIGHT_ARM_MODIFIER,
                    rightExtra,
                    AttributeModifier.Operation.ADD_VALUE
            );
            attackAttr.addOrUpdateTransientModifier(rightMod);
            attackAttr.removeModifier(LEFT_ARM_MODIFIER);
        }
    }
}