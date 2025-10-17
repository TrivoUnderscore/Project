package net.project.mod.physical.strength;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.project.mod.Mod;

import java.util.function.Supplier;

public class StrengthSetup {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mod.MODID);

    public static final Supplier<AttachmentType<Integer>> LEGS_STRENGTH =
            ATTACHMENT_TYPES.register(
                    "legs_strength",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("legs_strength").codec())
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> L_ARM_STRENGTH =
            ATTACHMENT_TYPES.register(
                    "l_arm_strength",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("l_arm_strength").codec())
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> R_ARM_STRENGTH =
            ATTACHMENT_TYPES.register(
                    "r_arm_strength",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("r_arm_strength").codec())
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
