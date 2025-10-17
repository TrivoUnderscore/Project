package net.project.mod.physical.weight;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.project.mod.Mod;

import java.util.function.Supplier;

public class WeightSetup {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mod.MODID);

    public static final Supplier<AttachmentType<Integer>> WEIGHT =
            ATTACHMENT_TYPES.register(
                    "weight",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT.fieldOf("weight").codec())
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
