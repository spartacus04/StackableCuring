package me.spartacus04.stackablecuring.commands

import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.commandHandling.command.ColosseumCommand
import me.spartacus04.colosseum.i18n.sendI18nConfirm
import me.spartacus04.colosseum.i18n.sendI18nWarn
import me.spartacus04.colosseum.logging.sendConfirm
import me.spartacus04.colosseum.logging.sendWarn
import me.spartacus04.stackablecuring.StackableCuring.Companion.CONFIG
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: ColosseumPlugin) : ColosseumCommand(plugin) {
    override val commandData = commandDescriptor("reload") {
        description = "Reloads the plugin configuration"
        permissions = setOf("stackablecuring.reload")
    }

    override fun execute(ctx: CommandContext<CommandSender>) {
        ctx.sender.sendWarn(plugin, "Reloading StackableCuring...")
        CONFIG.read()
        ctx.sender.sendConfirm(plugin, "Reloaded StackableCuring!")
    }
}