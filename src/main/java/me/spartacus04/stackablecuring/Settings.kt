package me.spartacus04.stackablecuring

import com.google.gson.GsonBuilder
import org.bukkit.plugin.java.JavaPlugin

/**
 * Represents the settings for the application.
 *
 * @property villagerBlacklist The list of blacklisted villagers.
 * @property uninstallMode Indicates if the uninstall mode is enabled.
 * @property allowMetrics Indicates if metrics are allowed.
 */
data class Settings(
    var villagerBlacklist: ArrayList<String> = ArrayList(),
    var uninstallMode: Boolean = false,
    var allowMetrics: Boolean = true
)

internal class SettingsContainer {
    companion object {
        lateinit var CONFIG : Settings

        val villagerList = listOf(
            "ARMORER", "BUTCHER", "CARTOGRAPHER", "CLERIC", "FARMER", "FISHERMAN",
            "FLETCHER", "LEATHERWORKER", "LIBRARIAN", "MASON", "SHEPHERD", "TOOLSMITH",
            "WEAPONSMITH"
        )

        fun reloadConfig(plugin: JavaPlugin) {
            val gson = GsonBuilder().setPrettyPrinting().setLenient().create()

            val configFile = plugin.dataFolder.resolve("config.json")

            if(!configFile.exists()) {
                if(!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
                configFile.createNewFile()
                CONFIG = Settings()
                plugin.getResource("config.json")?.bufferedReader().use {
                    configFile.writeText(it?.readText()!!)
                }
            } else {
                if (configFile.readText().contains("Merchants")) {
                    configFile.delete()
                    return reloadConfig(plugin)
                }

                CONFIG = gson.fromJson(configFile.readText(), Settings::class.java)
            }
        }

        fun saveConfig(plugin: JavaPlugin) {
            val gson = GsonBuilder().setPrettyPrinting().setLenient().create()

            val configFile = plugin.dataFolder.resolve("config.json")

            configFile.delete()
            configFile.createNewFile()
            configFile.bufferedWriter().use {
                it.write(gson.toJson(CONFIG))
            }
        }
    }
}