package me.spartacus04.stackablecuring

import com.github.Anon8281.universalScheduler.UniversalScheduler
import de.tr7zw.nbtapi.NBT
import de.tr7zw.nbtapi.NBTEntity
import me.spartacus04.stackablecuring.SettingsContainer.Companion.CONFIG
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.entity.Villager
import org.bukkit.entity.ZombieVillager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTransformEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class StackableCuring : JavaPlugin(), Listener {
    private val uuidPattern =  """(\[I;-?\d+,-?\d+,-?\d+,-?\d+])""".toRegex()

    override fun onEnable() {
        SettingsContainer.reloadConfig(this)

        getCommand("stackablecuring")!!.setExecutor(MainCommand(this))

        server.pluginManager.registerEvents(this, this)

        if(CONFIG.allowMetrics)
            Metrics(this, 20757)

        if(CONFIG.updateCheck) {
            Updater(this).getVersion {
                if(it != description.version) {
                    Bukkit.getConsoleSender().sendMessage(
                        "[§aStackableVillagerCuring§f] A new update is available!"
                    )
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInteractAtEntityEvent(e: PlayerInteractAtEntityEvent) {
        if(!CONFIG.uninstallMode) return

        val villager = e.rightClicked as? Villager ?: return

        NBT.modify(villager) {
            val gossips = it.getCompoundList("Gossips")

            gossips.forEach {gossip ->
                if(gossip.getString("Type") != "major_positive") return@forEach
                if(gossip.getInteger("Value") > 20) gossip.setInteger("Value", 20)
            }
        }
    }

    @EventHandler
    fun onVillagerTransformEvent(e: EntityTransformEvent) {
        if(CONFIG.uninstallMode) return
        if(e.transformReason != EntityTransformEvent.TransformReason.CURED) return

        val villager = e.entity as? ZombieVillager ?: return
        val curedVillager = e.transformedEntity as? Villager ?: return

        if(villager.villagerProfession?.name in CONFIG.villagerBlacklist) return

        UniversalScheduler.getScheduler(this).runTaskLater({
            val nbtzombie = NBTEntity(villager)
            val oldGossips = nbtzombie.getCompoundList("Gossips")


            NBT.modify(curedVillager) {
                val newGossips = it.getCompoundList("Gossips")

                newGossips.forEach {newGossip ->
                    if(newGossip.getString("Type") != "major_positive") return@forEach

                    val target = newGossip.getUUID("Target")
                    if(!newGossips.any {gossip ->
                            gossip.getString("Type") == "minor_positive" &&
                                    gossip.getUUID("Target") == target &&
                                    gossip.getInteger("Value") >= 25
                        }) return@forEach

                    if(oldGossips.any { old -> old.getString("Type") == "major_positive" && old.getUUID("Target") == target }) {
                        newGossip.setInteger("Value", newGossip.getInteger("Value") + 20)
                    }
                }
            }
        }, 1)
    }
}

