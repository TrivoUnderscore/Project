package net.project.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.project.mod.combat.CombatManager;
import net.project.mod.combat.CombatSetup;
import net.project.mod.heartbeat.HeartbeatEffect;
import net.project.mod.heartbeat.HeartbeatManager;
import net.project.mod.heartbeat.HeartbeatSetup;
import net.project.mod.physical.endurance.EnduranceEffect;
import net.project.mod.physical.endurance.EnduranceManager;
import net.project.mod.physical.endurance.EnduranceSetup;
import net.project.mod.physical.strength.StrengthEffect;
import net.project.mod.physical.strength.StrengthManager;
import net.project.mod.physical.strength.StrengthSetup;
import net.project.mod.sound.ModSounds;
import net.project.mod.physical.weight.WeightEffect;
import net.project.mod.physical.weight.WeightManager;
import net.project.mod.physical.weight.WeightSetup;
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

        EnduranceSetup.register(modEventBus);
        StrengthSetup.register(modEventBus);
        WeightSetup.register(modEventBus);
        HeartbeatSetup.register(modEventBus);
        CombatSetup.register(modEventBus);
        ModSounds.register(modEventBus);
        NeoForge.EVENT_BUS.register(new EnduranceManager());
        NeoForge.EVENT_BUS.register(new EnduranceEffect());
        NeoForge.EVENT_BUS.register(new StrengthManager());
        NeoForge.EVENT_BUS.register(new StrengthEffect());
        NeoForge.EVENT_BUS.register(new WeightManager());
        NeoForge.EVENT_BUS.register(new WeightEffect());
        NeoForge.EVENT_BUS.register(new CombatManager());
        NeoForge.EVENT_BUS.register(new HeartbeatEffect());
        NeoForge.EVENT_BUS.register(new HeartbeatManager());
            }

    @SubscribeEvent
    public void onLivingSwapItems(LivingSwapItemsEvent.Hands event) {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        mc.options.mainHand().set(mc.player.getMainArm().getOpposite());
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.setMainArm(serverPlayer.getMainArm().getOpposite());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
