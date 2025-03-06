package com.betterpvp.addon.modules;

import com.betterpvp.addon.BetterPvP;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");
    private final SettingGroup sgDistances = this.settings.createGroup("Distances");

    /**
     * Example setting.
     * The {@code name} parameter should be in kebab-case.
     * If you want to access the setting from another class, simply make the setting {@code public}, and use
     * {@link meteordevelopment.meteorclient.systems.modules.Modules#get(Class)} to access the {@link Module} object.
     */
    private final Setting<Double> reach = sgDistances.add(new DoubleSetting.Builder()
        .name("reach")
        .description("From how far its gonna place crystals")
        .defaultValue(5.0d)
        .range(1.0d, 10.0d)
        .build()
    );

    private final Setting<SettingColor> targetColor = sgRender.add(new ColorSetting.Builder()
        .name("target-color")
        .description("The color of the target.")
        .defaultValue(Color.RED)
        .build()
    );

    /**
     * The {@code name} parameter should be in kebab-case.
     */
    public ModuleExample() {
        super(BetterPvP.CATEGORY, "world-origin", "An example module that highlights the center of the world.");
    }


}
