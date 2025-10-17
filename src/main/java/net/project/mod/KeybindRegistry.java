package net.project.mod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import static net.project.mod.KeybindSetup.SWITCH_MAINHAND_KEY;

public class KeybindRegistry {
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SWITCH_MAINHAND_KEY);
    }
}
