package me.spartacus04.stackablecuring.listeners

import de.tr7zw.nbtapi.NBT
import de.tr7zw.nbtapi.NBTCompoundList
import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.listeners.ColosseumListener
import me.spartacus04.stackablecuring.StackableCuring.Companion.CONFIG
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Villager
import org.bukkit.entity.ZombieVillager
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityTransformEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent

internal class VillagerEvent(val plugin: ColosseumPlugin) : ColosseumListener(plugin) {

    // This event is called when a player right-clicks a villager
    // It's called when the uninstallMode is enabled
    // It caps the gossip value to 20
    @EventHandler
    fun onPlayerInteractAtEntityEvent(e: PlayerInteractAtEntityEvent) {
        if(!CONFIG.UNINSTALL_MODE) return

        val villager = e.rightClicked as? Villager ?: return

        NBT.modify(villager) {
            val gossips = it.getCompoundList("Gossips")

            gossips.forEach {gossip ->
                if(gossip.getString("Type") != "major_positive") return@forEach
                if(gossip.getInteger("Value") > 20) gossip.setInteger("Value", 20)
            }
        }
    }

    // This event is called when a zombie villager is cured
    // It handles the logic of adding the gossip to the cured villager
    @EventHandler
    fun onVillagerTransformEvent(e: EntityTransformEvent) {
        if(CONFIG.UNINSTALL_MODE) return
        if(e.transformReason != EntityTransformEvent.TransformReason.CURED) return

        val zombieVillager = e.entity as? ZombieVillager ?: return
        val curedVillager = e.transformedEntity as? Villager ?: return
        val player = zombieVillager.conversionPlayer ?: return

        if(getProfessionKey(zombieVillager).uppercase() in CONFIG.VILLAGER_BLACKLIST) return


        val lastMajorGossipValue = getMajorPositiveGossip(zombieVillager, player)

        // If the last major gossip value is 0, we don't need to do anything as the villager was never cured before
        if(lastMajorGossipValue == 0) return

        plugin.scheduler.runTaskLater({
            NBT.modify(curedVillager) {
                val gossips = it.getCompoundList("Gossips")

                for(i in 0 until gossips.size()) {
                    if(gossips[i].getString("Type") == "major_positive" && gossips[i].getUUID("Target") == player.uniqueId) {
                        gossips[i].setInteger("Value", lastMajorGossipValue + 20)
                    }
                }
            }
        }, 1)
    }

    fun getMajorPositiveGossip(villager: Entity, offlinePlayer: OfflinePlayer): Int =
         NBT.get<Int>(villager) { nbt ->
             val zombieGossips = nbt.getCompoundList("Gossips") as NBTCompoundList

             val majorGossip = zombieGossips.find {
                 it.getString("Type") == "major_positive" &&
                         it.getUUID("Target") == offlinePlayer.uniqueId
             }

             return@get majorGossip?.getInteger("Value") ?: 0
        }
    

    /**
     * In API versions 1.20.6 and earlier, Villager.Profession is a class.
     * In versions 1.21 and later, it is an interface.
     * This method uses reflection to get the profession.key.key field of the villager.
     * @param villager The villager to get the profession of.
     * @return The profession name of the villager.
     */
    private fun getProfessionKey(villager: ZombieVillager): String {
        val profession = villager::class.java.getMethod("getVillagerProfession").invoke(villager)
        val key = profession::class.java.getMethod("getKey").invoke(profession)
        val keyKey = key::class.java.getMethod("getKey").invoke(key)
        return keyKey.toString()
    }
}