package net.project.mod;

import net.project.mod.combat.CombatManager;
import net.project.mod.combat.CombatSetup;
import net.project.mod.heartbeat.HeartbeatEffect;
import net.project.mod.heartbeat.HeartbeatManager;
import net.project.mod.heartbeat.HeartbeatSetup;
import net.project.mod.sound.ModSounds;
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
        NeoForge.EVENT_BUS.register(new CombatManager());
        NeoForge.EVENT_BUS.register(new HeartbeatEffect());
        NeoForge.EVENT_BUS.register(new HeartbeatManager());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
