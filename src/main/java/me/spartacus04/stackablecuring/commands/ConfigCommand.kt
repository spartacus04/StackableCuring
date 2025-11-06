package me.spartacus04.stackablecuring.commands

import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.commandHandling.argument.arguments.ArgumentBoolean
import me.spartacus04.colosseum.commandHandling.argument.arguments.ArgumentString
import me.spartacus04.colosseum.commandHandling.command.ColosseumBaseCommand
import me.spartacus04.colosseum.commandHandling.command.ColosseumCommand
import me.spartacus04.colosseum.commandHandling.command.ColosseumNestedCommand
import me.spartacus04.colosseum.commandHandling.exceptions.MalformedArgumentException
import me.spartacus04.colosseum.logging.sendConfirm
import me.spartacus04.stackablecuring.StackableCuring.Companion.CONFIG
import org.bukkit.command.CommandSender

class ConfigCommand(plugin: ColosseumPlugin) : ColosseumNestedCommand(plugin, "config", listOf(
    object : ColosseumCommand(plugin) {
        override val commandData = commandDescriptor("uninstallMode") {
            arguments.add(ArgumentBoolean())
        }

        override fun execute(ctx: CommandContext<CommandSender>) {
            val value = ctx.getArgument<Boolean>(0)

            CONFIG.UNINSTALL_MODE = value
            CONFIG.save()

            ctx.sender.sendConfirm(plugin, "Config saved!")
        }
    },
    object : ColosseumBaseCommand(plugin, "villagerBlackList", listOf(
        object : ColosseumCommand(plugin) {
            override val commandData = commandDescriptor("add") {
                val remainingVillagers = villagerList.map { it.lowercase() }

                arguments.add(ArgumentString(remainingVillagers))
            }

            override fun execute(ctx: CommandContext<CommandSender>) {
                val profession = ctx.getArgument<String>(0).uppercase()

                if(!CONFIG.VILLAGER_BLACKLIST.contains(profession) && villagerList.contains(profession)) {
                    CONFIG.VILLAGER_BLACKLIST.add(profession)
                    CONFIG.save()

                    ctx.sender.sendConfirm(plugin, "Config saved!")
                } else {
                    throw MalformedArgumentException(ctx.getArgument<String>(0), "not already blacklisted profession")
                }
            }
        },
        object : ColosseumCommand(plugin) {
            override val commandData = commandDescriptor("remove") {
                val blacklistedVillagers = villagerList.map { it.lowercase() }

                arguments.add(ArgumentString(blacklistedVillagers))
            }

            override fun execute(ctx: CommandContext<CommandSender>) {
                val profession = ctx.getArgument<String>(0).uppercase()

                if(CONFIG.VILLAGER_BLACKLIST.contains(profession) && villagerList.contains(profession)) {
                    CONFIG.VILLAGER_BLACKLIST.remove(profession)
                    CONFIG.save()

                    ctx.sender.sendConfirm(plugin, "Config saved!")
                } else {
                    throw MalformedArgumentException(ctx.getArgument<String>(0), "already blacklisted profession")
                }
            }
        },
        object : ColosseumCommand(plugin) {
            override val commandData = commandDescriptor("list") {  }

            override fun execute(ctx: CommandContext<CommandSender>) {
                ctx.sender.sendMessage(CONFIG.VILLAGER_BLACKLIST.joinToString(", "))
            }
        }
    )) {}
)) {
    override val commandData = commandDescriptor("config") {
        permissions = setOf("stackablecuring.config")
    }

    companion object {
        val villagerList = listOf(
            "ARMORER", "BUTCHER", "CARTOGRAPHER", "CLERIC", "FARMER", "FISHERMAN",
            "FLETCHER", "LEATHERWORKER", "LIBRARIAN", "MASON", "SHEPHERD", "TOOLSMITH",
            "WEAPONSMITH"
        )
    }
}