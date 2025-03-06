package com.betterpvp.addon.modules;

import com.betterpvp.addon.BetterPvP;
import meteordevelopment.meteorclient.events.packets.InventoryEvent;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.screen.ScreenHandler;

public class ContainerSearch extends Module {
    private final SettingGroup sgRender = this.settings.createGroup("Render");
    private final Setting<SettingColor> color = sgRender.add(new ColorSetting.Builder()
        .name("highlight-color")
        .description("The color in which to highlight the item.")
        .defaultValue(Color.YELLOW)
        .build()
    );

    @EventHandler
    private void onInventory(InventoryEvent event) {
        ScreenHandler handler = mc.player.currentScreenHandler;

    }

    public ContainerSearch() {
        super(BetterPvP.CATEGORY, "container-serch", "Searches for stuff in containers");
    }

    public ContainerSearch(Category category, String name, String desc) {
        super(category, name, desc);
    }
}
