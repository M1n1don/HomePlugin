package dev.m1n1don.homeplugin.commands

import dev.m1n1don.homeplugin.HomePlugin
import hazae41.minecraft.kutils.bukkit.msg
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player
        val location = HomePlugin.plugin.users[sender.uniqueId]?.spawnLocation

        if (location == null)
        {
            sender.msg("&cスポーンポイントが設定されていません。")
            return true
        }

        sender.teleport(location)
        sender.msg("&aテレポートが完了しました！")
        return true
    }
}