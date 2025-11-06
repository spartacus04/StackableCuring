package me.spartacus04.stackablecuring.listeners

import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.listeners.ColosseumListener
import me.spartacus04.colosseum.logging.sendInfo
import me.spartacus04.colosseum.logging.sendUrl
import me.spartacus04.stackablecuring.StackableCuring.Companion.CONFIG
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

internal class PlayerJoinEvent(val plugin: ColosseumPlugin) : ColosseumListener(plugin) {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if(CONFIG.CHECK_UPDATE && player.hasPermission("stackablecuring.notifyupdate"))
            plugin.checkForUpdates("spartacus04/StackableCuring") {
                if(it != plugin.description.version) {
                    player.sendInfo(plugin, "A new update is available!")
                    player.sendUrl(plugin, "https://modrinth.com/plugin/stackablecuring")
                }
            }
    }
}