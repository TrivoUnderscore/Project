package net.project.mod;

import com.mojang.blaze3d.platform.InputConstants;
import cpw.mods.util.Lazy;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeybindSetup {
    public static final KeyMapping SWITCH_MAINHAND_KEY = new KeyMapping(
            "key.projectmod.switch_mainhand",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "key.categories.projectmod"
    );
}
