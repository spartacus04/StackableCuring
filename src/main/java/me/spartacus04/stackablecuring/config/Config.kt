package me.spartacus04.stackablecuring.config

import com.google.gson.annotations.SerializedName
import me.spartacus04.colosseum.config.ConfigField
import me.spartacus04.colosseum.config.FileBind
import me.spartacus04.stackablecuring.StackableCuringState.PLUGIN

/**
 * Represents the settings for the plugin.
 *
 * @property VILLAGER_BLACKLIST The list of blacklisted villagers.
 * @property UNINSTALL_MODE Indicates if the uninstall mode is enabled.
 * @property CHECK_UPDATE Indicates if checking for updates is enabled.
 * @property ALLOW_METRICS Indicates if metrics are allowed.
 */
data class Config(
    @ConfigField(
        "Villager Blacklist",
        "The list of blacklisted villagers",
        "[]"
    )
    @SerializedName("villagerBlacklist")
    var VILLAGER_BLACKLIST: ArrayList<String> = ArrayList(),

    @ConfigField(
        "Uninstall Mode",
        "If enabled, the plugin will revert all it's changes",
        "false"
    )
    @SerializedName("uninstallMode")
    var UNINSTALL_MODE: Boolean = false,

    @ConfigField(
        "Check for Updates",
        "Allows the plugin to check for updates",
        "true"
    )
    @SerializedName("updateCheck")
    var CHECK_UPDATE: Boolean = true,

    @ConfigField(
        "Allow Metrics",
        "Allows the plugin to send metrics data",
        "true"
    )
    @SerializedName("allowMetrics")
    var ALLOW_METRICS: Boolean = true
) : FileBind("config.json", Config::class.java, PLUGIN) {
    @Suppress("unused")
    @SerializedName("\$schema")
    private val schema = "https://raw.githubusercontent.com/spartacus04/StackableCuring/master/configSchema.json"
}
