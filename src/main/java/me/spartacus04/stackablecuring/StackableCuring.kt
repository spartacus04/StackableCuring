package me.spartacus04.stackablecuring

import me.spartacus04.colosseum.utils.PluginUpdater
import me.spartacus04.stackablecuring.StackableCuringState.CONFIG
import me.spartacus04.stackablecuring.StackableCuringState.LOGGER
import me.spartacus04.stackablecuring.StackableCuringState.PLUGIN
import me.spartacus04.stackablecuring.StackableCuringState.VERSION
import me.spartacus04.stackablecuring.commands.MainCommand
import me.spartacus04.stackablecuring.listeners.PlayerJoinEvent
import me.spartacus04.stackablecuring.listeners.VillagerEvent
import org.bstats.bukkit.Metrics
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class StackableCuring : JavaPlugin(), Listener {
    override fun onEnable() {
        if(VERSION < "1.20.2") {
            LOGGER.warn("This plugin is unnecessary on versions prior to 1.20.2, as such it will be disabled.")
            server.pluginManager.disablePlugin(this)
        }

        getCommand("stackablecuring")!!.setExecutor(MainCommand())

        VillagerEvent(this).register()
        PlayerJoinEvent(this).register()

        if(CONFIG.ALLOW_METRICS)
            Metrics(this, 20757)

        if(CONFIG.CHECK_UPDATE)
            PluginUpdater(PLUGIN, "spartacus04/StackableCuring").getVersion {
                if(it != PLUGIN.description.version) {
                    LOGGER.info("A new update is available!")
                    LOGGER.url("https://modrinth.com/plugin/stackablecuring")
                }
            }

        LOGGER.confirm("Enabled StackableCuring")
    }
}

