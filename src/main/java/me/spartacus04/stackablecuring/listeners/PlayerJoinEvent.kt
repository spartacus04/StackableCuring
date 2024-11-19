package me.spartacus04.stackablecuring.listeners

import me.spartacus04.colosseum.listeners.ColosseumListener
import me.spartacus04.colosseum.utils.PluginUpdater
import me.spartacus04.stackablecuring.StackableCuringState.CONFIG
import me.spartacus04.stackablecuring.StackableCuringState.LOGGER
import me.spartacus04.stackablecuring.StackableCuringState.PLUGIN
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.player.PlayerJoinEvent

internal class PlayerJoinEvent(plugin: JavaPlugin) : ColosseumListener(plugin) {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if(CONFIG.CHECK_UPDATE && player.hasPermission("stackablecuring.notifyupdate"))
            PluginUpdater(PLUGIN, "spartacus04/StackableCuring").getVersion {
                if(it != PLUGIN.description.version) {
                    player.sendMessage(
                        LOGGER.messageFormatter.info("A new update is available!")
                    )
                    player.sendMessage(
                        LOGGER.messageFormatter.url("https://modrinth.com/plugin/stackablecuring")
                    )
                }
            }
    }
}