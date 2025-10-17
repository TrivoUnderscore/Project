package net.project.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import static net.project.mod.KeybindSetup.SWITCH_MAINHAND_KEY;

public class KeybindManager {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null && SWITCH_MAINHAND_KEY.consumeClick()) {
            player.setMainArm(player.getMainArm().getOpposite());
        }
    }
}
