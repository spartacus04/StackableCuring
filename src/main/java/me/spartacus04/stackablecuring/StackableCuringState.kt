package me.spartacus04.stackablecuring

import me.spartacus04.colosseum.config.FileBind
import me.spartacus04.colosseum.logging.PluginLogger
import me.spartacus04.colosseum.scheduler.ColosseumScheduler
import me.spartacus04.colosseum.utils.version.MinecraftServerVersion
import me.spartacus04.stackablecuring.config.Config
import org.bukkit.plugin.java.JavaPlugin

/**
 * The object `StackableCuringState` is a singleton object that holds all the global variables and objects used by the plugin.
 *
 * @property PLUGIN The main plugin instance.
 * @property CONFIG The configuration object that holds the plugin's settings.
 */
object StackableCuringState {
    val PLUGIN = JavaPlugin.getPlugin(StackableCuring::class.java)
    internal val VERSION = MinecraftServerVersion.current

    val CONFIG = run {
        if(!PLUGIN.dataFolder.exists()) PLUGIN.dataFolder.mkdirs()
        FileBind.create(Config::class.java)
    }

    internal val LOGGER = PluginLogger(false, "StackableCuring")
    internal val SCHEDULER = ColosseumScheduler.getScheduler(PLUGIN)
}