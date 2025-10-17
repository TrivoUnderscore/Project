package net.project.mod.physical.strength;

import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.project.mod.Mod;
import net.project.mod.physical.weight.WeightSetup;

import java.util.function.Supplier;

public class StrengthManager {
    @SubscribeEvent
    public void perTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getInventory().getArmor(1).is(Items.DIAMOND_LEGGINGS)) {
                serverPlayer.setData(StrengthSetup.LEGS_STRENGTH.get(), 1);
            } else
                serverPlayer.setData(StrengthSetup.LEGS_STRENGTH.get(), 0);
        }
    }
}
