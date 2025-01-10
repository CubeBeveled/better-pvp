package com.betterpvp.addon.modules;

import com.betterpvp.addon.BetterPvP;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class maceESP extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");
    private final SettingGroup sgDistances = this.settings.createGroup("Distances");
    private final Map<UUID, Boolean> playerHoldingState = new HashMap<>();
    private static final Logger LOG = LogUtils.getLogger();

    private final Setting<Double> reach = sgDistances.add(new DoubleSetting.Builder()
        .name("reach")
        .description("From how far its gonna place crystals")
        .defaultValue(5.0d)
        .range(1.0d, 10.0d)
        .build()
    );

    private final Setting<SettingColor> playerColor = sgRender.add(new ColorSetting.Builder()
        .name("player-color")
        .description("The color of the player.")
        .defaultValue(Color.RED)
        .build()
    );

    private final Setting<Boolean> tracers = sgRender.add(new BoolSetting.Builder()
        .name("tracers")
        .description("The color of the player.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> chatFeedback = sgRender.add(new BoolSetting.Builder()
        .name("chat-feedback")
        .description("If the module should warn you in chat about who has the mace.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> onlyWhileHolding = sgRender.add(new BoolSetting.Builder()
        .name("only-while-holding")
        .description("If the players should be considered to have a mace only when holding it.")
        .defaultValue(true)
        .build()
    );

    public maceESP() {
        super(BetterPvP.CATEGORY, "mace-esp", "Shows you who has the mace.");
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent event) {

        if (!(event.entity instanceof PlayerEntity)) return;
        LOG.info(((PlayerEntity) event.entity).getStackInHand(Hand.MAIN_HAND).toString());

        //playerHoldingState.put(event.entity.getUuid(), );
    }
}
