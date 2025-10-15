package net.project.mod.heartbeat;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.project.mod.Mod;

import java.util.function.Supplier;

public class HeartbeatSetup {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mod.MODID);

    public static final Supplier<AttachmentType<Integer>> HEART_BEAT =
            ATTACHMENT_TYPES.register(
                    "heart_beat",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("heart_beat").codec())
                            .build()
            );

    public static final Supplier<AttachmentType<Integer>> TIME =
            ATTACHMENT_TYPES.register(
                    "time",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("time").codec())
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
