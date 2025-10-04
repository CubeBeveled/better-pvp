package com.betterpvp.addon.modules;

import com.betterpvp.addon.BetterPvP;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.entity.Target;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MaceESP extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");
    private final Map<UUID, Boolean> playersHoldingMace = new HashMap<>();

    private final Setting<SettingColor> playerColor = sgRender.add(new ColorSetting.Builder()
        .name("player-color")
        .description("The color of the player.")
        .defaultValue(Color.fromHsv(233, 37, 77))
        .build()
    );

    private final Setting<Boolean> tracers = sgRender.add(new BoolSetting.Builder()
        .name("tracers")
        .description("If tracers should be rendered.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Target> tracerTarget = sgRender.add(new EnumSetting.Builder<Target>()
        .name("tracer-target")
        .description("What part of the player is the tracer going to.")
        .defaultValue(Target.Body)
        .visible(tracers::get)
        .build()
    );

    private final Setting<Boolean> chatNotify = sgGeneral.add(new BoolSetting.Builder()
        .name("chat-feedback")
        .description("If the module should warn you in chat about who has the mace.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> notifyWhenHolding = sgRender.add(new BoolSetting.Builder()
        .name("notify-when-holding")
        .description("If you should be notified via chat when someone is holding the mace.")
        .defaultValue(false)
        .visible(chatNotify::get)
        .build()
    );

    private final Setting<Boolean> onlyWhileHolding = sgGeneral.add(new BoolSetting.Builder()
        .name("only-while-holding")
        .description("If the players should be considered to have a mace only when holding it.")
        .defaultValue(true)
        .build()
    );

    public MaceESP() {
        super(BetterPvP.CATEGORY, "mace-esp", "Shows you who has the mace.");
    }

    // Get the players that have the mace
    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!tracers.get()) return;

        if (mc.world.getPlayers() == null) return;

        for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
            if (player == null || mc.player.getUuid() == null) continue;
            if (player.getUuid() == mc.player.getUuid() || Friends.get().isFriend(player)) continue;

            ItemStack handItem = player.getStackInHand(Hand.MAIN_HAND);
            UUID playerUuid = player.getUuid();

            if (handItem.getItem() == Items.MACE) {
                if (chatNotify.get()) {
                    if (notifyWhenHolding.get()) {
                        info(
                            "(highlight)%s(default) has the mace.",
                            player.getName(),
                            player.getX(),
                            player.getY(),
                            player.getZ()
                        );
                    } else if (!playersHoldingMace.containsKey(playerUuid)) {
                        info(
                            "(highlight)%s(default) has the mace.",
                            player.getName(),
                            player.getX(),
                            player.getY(),
                            player.getZ()
                        );
                    }
                }

                playersHoldingMace.put(playerUuid, true);
            } else {
                playersHoldingMace.put(playerUuid, false);
            }
        }
    }

    // Render the stuff
    @EventHandler
    private void onRender(Render3DEvent event) {
        if (mc.options.hudHidden) return;

        playersHoldingMace.forEach((playerUuid, holdingMace) -> {
            if (!holdingMace && onlyWhileHolding.get()) return;
            AbstractClientPlayerEntity player = getPlayerFromUUID(playerUuid);
            if (player == null) return;

            Color color = playerColor.get();

            double x = player.lastX + (player.getX() - player.lastX) * event.tickDelta;
            double y = player.lastY + (player.getY() - player.lastY) * event.tickDelta;
            double z = player.lastZ + (player.getZ() - player.lastZ) * event.tickDelta;

            double height = player.getBoundingBox().maxY - player.getBoundingBox().minY;
            if (tracerTarget.get() == Target.Head) y += height;
            else if (tracerTarget.get() == Target.Body) y += height / 2;

            event.renderer.line(RenderUtils.center.x, RenderUtils.center.y, RenderUtils.center.z, x, y, z, color);
        });
    }

    private @Nullable AbstractClientPlayerEntity getPlayerFromUUID(UUID uuid) {
        for (AbstractClientPlayerEntity p : mc.world.getPlayers()) {
            if (p.getUuid().equals(uuid)) return p;
        }

        return null;
    }
}
