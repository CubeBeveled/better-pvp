package com.betterpvp.addon;

import com.betterpvp.addon.hud.HudExample;
import com.betterpvp.addon.modules.MaceESP;
import com.betterpvp.addon.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class BetterPvP extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Better PvP");
    public static final HudGroup HUD_GROUP = new HudGroup("Better PvP");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Better PvP");

        // Modules
        Modules.get().add(new ModuleExample());
        //Modules.get().add(new ModuleExample());
        //Modules.get().add(new CrystalAuraMinus());
        Modules.get().add(new MaceESP());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.betterpvp.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("CubeBeveled", "better-pvp");
    }
}
