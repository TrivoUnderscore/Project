package net.project.mod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.project.mod.combat.CombatManager;
import net.project.mod.combat.CombatSetup;
import net.project.mod.heartbeat.HeartbeatEffect;
import net.project.mod.heartbeat.HeartbeatManager;
import net.project.mod.heartbeat.HeartbeatSetup;
import net.project.mod.sound.ModSounds;
import net.project.mod.weight.WeightEffect;
import net.project.mod.weight.WeightManager;
import net.project.mod.weight.WeightSetup;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@net.neoforged.fml.common.Mod(Mod.MODID)
public class Mod {
    public static final String MODID = "projectmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Mod(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        WeightSetup.register(modEventBus);
        HeartbeatSetup.register(modEventBus);
        CombatSetup.register(modEventBus);
        ModSounds.register(modEventBus);
        NeoForge.EVENT_BUS.register(KeybindManager.class);
        NeoForge.EVENT_BUS.register(new WeightManager());
        NeoForge.EVENT_BUS.register(new WeightEffect());
        NeoForge.EVENT_BUS.register(new CombatManager());
        NeoForge.EVENT_BUS.register(new HeartbeatEffect());
        NeoForge.EVENT_BUS.register(new HeartbeatManager());
        modEventBus.register(KeybindRegistry.class);
    }

    @SubscribeEvent
    public void onLivingSwapItems(LivingSwapItemsEvent.Hands event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.setMainArm(serverPlayer.getMainArm().getOpposite());
        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
