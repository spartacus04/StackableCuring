package me.spartacus04.stackablecuring.commands

import me.spartacus04.stackablecuring.StackableCuringState.CONFIG
import me.spartacus04.stackablecuring.StackableCuringState.LOGGER
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

internal class MainCommand : CommandExecutor, TabCompleter {
    private val villagerList = listOf(
        "ARMORER", "BUTCHER", "CARTOGRAPHER", "CLERIC", "FARMER", "FISHERMAN",
        "FLETCHER", "LEATHERWORKER", "LIBRARIAN", "MASON", "SHEPHERD", "TOOLSMITH",
        "WEAPONSMITH"
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            when(args[0]) {
                "reload" -> {
                    if(sender.hasPermission("stackablecuring.reload")) {
                        sender.sendMessage(
                            LOGGER.messageFormatter.info("Reloading StackableCuring...")
                        )
                        CONFIG.read()

                        sender.sendMessage(
                            LOGGER.messageFormatter.confirm("Reloaded StackableCuring!")
                        )
                    } else {
                        sender.sendMessage(
                            LOGGER.messageFormatter.error("You don't have permission to do that!")
                        )
                    }
                }
                "config" -> {
                    if(sender.hasPermission("stackablecuring.config")) {
                        when(args[1]) {
                            "uninstallMode" -> {
                                CONFIG.UNINSTALL_MODE = args[2].lowercase().toBooleanStrict()
                                CONFIG.save()

                                sender.sendMessage(
                                    LOGGER.messageFormatter.confirm("Set uninstallMode to ${args[2]}")
                                )
                            }
                            "villagerBlacklist" -> {
                                when(args[2]) {
                                    "add" -> {
                                        if(villagerList.contains(args[3].uppercase()) && !CONFIG.VILLAGER_BLACKLIST.contains(args[3].uppercase())) {
                                            CONFIG.VILLAGER_BLACKLIST.add(args[3].uppercase())
                                            CONFIG.save()
                                        }
                                    }
                                    "remove" -> {
                                        if(villagerList.contains(args[3].uppercase()) && CONFIG.VILLAGER_BLACKLIST.contains(args[3].uppercase())) {
                                            CONFIG.VILLAGER_BLACKLIST.remove(args[3].uppercase())
                                            CONFIG.save()
                                        }
                                    }
                                    "list" -> {
                                        sender.sendMessage(CONFIG.VILLAGER_BLACKLIST.joinToString { ", " })
                                    }
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(
                            LOGGER.messageFormatter.error("You don't have permission to do that!")
                        )
                    }
                }
            }
        }
        catch (_: Exception) {
            sender.sendMessage(
                LOGGER.messageFormatter.error("Invalid command usage!")
            )
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        val list = ArrayList<String>()

        when(args.size) {
            1 -> {
                if(sender.hasPermission("instantrestock.reload"))
                    list.add("reload")

                if(sender.hasPermission("instantrestock.config"))
                    list.add("config")
            }
            2 -> {
                if(args[0] == "config") {
                    list.addAll(listOf(
                        "villagerBlacklist",
                        "uninstallMode",
                    ))
                }
            }
            3 -> {
                if(args[0] == "config") {
                    when(args[1]) {
                        "villagerBlacklist" -> {
                            list.addAll(listOf("add", "remove", "list"))
                        }
                        else -> {
                            list.addAll(listOf("true", "false"))
                        }
                    }
                }
            }
            4 -> {
                if(args[0] == "config" && args[1] == "villagerBlacklist") {
                    when(args[2]) {
                        "add", "remove" -> list.addAll(villagerList)
                    }
                }
            }
        }

        return list
    }
}