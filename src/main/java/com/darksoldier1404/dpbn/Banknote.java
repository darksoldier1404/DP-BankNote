package com.darksoldier1404.dpbn;

import com.darksoldier1404.dpbn.commands.DPBNCommand;
import com.darksoldier1404.dppc.annotation.DPPCoreVersion;
import com.darksoldier1404.dppc.data.DPlugin;
import com.darksoldier1404.dppc.utils.PluginUtil;
import com.darksoldier1404.dpbn.events.DPBNEvent;

@DPPCoreVersion(since = "5.3.3")
public class Banknote extends DPlugin {
    private static Banknote plugin;

    public static Banknote getInstance() {
        return plugin;
    }

    public Banknote() {
        super(true);
        plugin = this;
        init();
    }

    @Override
    public void onLoad() {
        PluginUtil.addPlugin(plugin, 28390);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DPBNEvent(), plugin);
        DPBNCommand.init();
    }

    @Override
    public void onDisable() {
        saveAllData();
    }
}
