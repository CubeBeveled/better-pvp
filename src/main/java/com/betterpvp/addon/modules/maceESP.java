package com.betterpvp.addon.modules;

import com.betterpvp.addon.BetterPvP;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class maceESP extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");
    private final SettingGroup sgDistances = this.settings.createGroup("Distances");
    private final Map<UUID, Boolean> playerHoldingState = new HashMap<>();

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

    @Override
    public void onInitialize() {
        // Track players' hand item changes
        ServerTickEvents.END_PLAYER_TICK.register(this::onPlayerTick);
    }

    private void onPlayerTick(PlayerEntity player) {
        // Get the player's UUID
        UUID playerId = player.getUuid();

        // Get the item in their main and off hand
        ItemStack mainHandItem = player.getMainHandStack();
        ItemStack offHandItem = player.getOffHandStack();

        // Check if the player is holding the desired item
        boolean isHoldingItem = mainHandItem.getItem() == Items.DIAMOND_SWORD || offHandItem.getItem() == Items.DIAMOND_SWORD;

        // Only update the state if it has changed
        if (playerHoldingState.getOrDefault(playerId, false) != isHoldingItem) {
            playerHoldingState.put(playerId, isHoldingItem);

            // Log or perform an action
            if (isHoldingItem) {
                System.out.println(player.getName().getString() + " started holding a diamond sword!");
            } else {
                System.out.println(player.getName().getString() + " stopped holding a diamond sword!");
            }
        }
    }
}
