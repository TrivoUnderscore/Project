package net.project.mod.combat;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.project.mod.Mod;

import java.util.function.Supplier;

public class CombatSetup {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mod.MODID);

    public static final Supplier<AttachmentType<Boolean>> COMBAT =
            ATTACHMENT_TYPES.register(
                    "combat",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL.fieldOf("combat").codec())
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
