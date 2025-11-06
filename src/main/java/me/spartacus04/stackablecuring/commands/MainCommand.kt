package me.spartacus04.stackablecuring.commands

import me.spartacus04.colosseum.ColosseumPlugin
import me.spartacus04.colosseum.commandHandling.command.ColosseumBaseCommand

class MainCommand(plugin: ColosseumPlugin) : ColosseumBaseCommand(plugin, "stackablecuring", listOf(
    ConfigCommand(plugin),
    ReloadCommand(plugin)
))