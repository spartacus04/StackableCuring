# StackableCuring

[![Build](https://github.com/spartacus04/StackableCuring/actions/workflows/gradle.yml/badge.svg)](https://github.com/spartacus04/StackableCuring/actions/workflows/gradle.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/spartacus04/stackablecuring/badge)](https://www.codefactor.io/repository/github/spartacus04/stackablecuring)

![bStats Players](https://img.shields.io/bstats/players/20757)
![bStats Servers](https://img.shields.io/bstats/servers/20757)

![Spigot Downloads](https://img.shields.io/spiget/downloads/88098?label=Spigot%20Downloads)
![Hangar Downloads](https://img.shields.io/hangar/dt/StackableCuring?label=Hangar%20downloads)
![Modrinth Downloads](https://img.shields.io/modrinth/dt/Fsy9NpXv?label=Modrinth%20downloads&color=00cc00)
![GitHub all releases](https://img.shields.io/github/downloads/spartacus04/stackablecuring/total?label=Github%20downloads)

>A spigot plugin to restore the old villager curing behavior.

## Overview

The **StackableCuring** plugin is designed to restore the old villager curing behavior where the discounts would stack with each cure. This was fixed in Minecraft 1.20.1 with [MC-181190](https://bugs.mojang.com/browse/MC-181190). The fix was extremely controversial in the minecraft community, as such this plugin was created to restore the old behavior. 

## Dependencies

- NTB-API ([Spigot](https://www.spigotmc.org/resources/nbt-api.7939/)-[Hangar](https://hangar.papermc.io/tr7zw/NBTAPI)-[Modrinth](https://modrinth.com/plugin/nbtapi)-[Github](https://github.com/tr7zw/Item-NBT-API))

## Features

- **Villager Blacklist**: Add, remove, or list villagers in the blacklist using the `/stackablecuring config villagerBlacklist <add/remove/list> <villagertype>` command.

## Usage

### Configuration

Use the following commands to configure the plugin:

- `/stackablecuring config villagerBlacklist <add/remove/list> <villagertype>`: Manage the villager blacklist.

- `/stackablecuring config uninstallMode <true/false>`: Toggle uninstall mode on or off.

### Reloading the Plugin

- `/stackablecuring reload`: Reload the plugin from the config file.

## Permissions

- `stackablecuring.reload`: Allows players to use the `/stackablecuring reload` command.

- `stackablecuring.config`: Allows players to use the configuration commands listed above.

## Support

If you encounter any issues or have questions about the **StackableCuring** plugin, please reach out to us on the [Issue tracker](https://github.com/spartacus04/StackableCuring/issues).

## License

This plugin is distributed under the [MIT License](https://github.com/spartacus04/StackableCuring/blob/master/LICENSE). Feel free to modify and share it as needed.

Enjoy customizing your villager trade experience with the **StackableCuring** plugin on your Minecraft server!
