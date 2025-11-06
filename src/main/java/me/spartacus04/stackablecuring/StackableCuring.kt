package me.spartacus04.stackablecuring

import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.config.FileBind
import me.spartacus04.stackablecuring.commands.MainCommand
import me.spartacus04.stackablecuring.config.Config
import me.spartacus04.stackablecuring.listeners.PlayerJoinEvent
import me.spartacus04.stackablecuring.listeners.VillagerEvent
import org.bstats.bukkit.Metrics

@Suppress("unused")
class StackableCuring : ColosseumPlugin() {
    override fun onEnable() {
        if(serverVersion < "1.20.2") {
            colosseumLogger.warn("This plugin is unnecessary on versions prior to 1.20.2, as such it will be disabled.")
            server.pluginManager.disablePlugin(this)
        }

        INSTANCE = this

        CONFIG = run {
            if (!dataFolder.exists()) dataFolder.mkdirs()
            FileBind.create(Config::class.java)
        }

        registerCommands {
            addCommand(MainCommand::class.java)
        }

        VillagerEvent(this).register()
        PlayerJoinEvent(this).register()

        if(CONFIG.ALLOW_METRICS)
            Metrics(this, 20757)

        if(CONFIG.CHECK_UPDATE)
            checkForUpdates("spartacus04/StackableCuring") {
                if(it != description.version) {
                    colosseumLogger.info("A new update is available!")
                    colosseumLogger.url("https://modrinth.com/plugin/stackablecuring")
                }
            }

        colosseumLogger.confirm("Enabled StackableCuring")
    }

    override fun onDisable() {
        colosseumLogger.warn("Disabled StackableCuring")
    }

    companion object {
        lateinit var INSTANCE: StackableCuring
        lateinit var CONFIG: Config
    }
}