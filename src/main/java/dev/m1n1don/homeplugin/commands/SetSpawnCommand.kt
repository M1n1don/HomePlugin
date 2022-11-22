package dev.m1n1don.homeplugin.commands

import dev.m1n1don.homeplugin.HomePlugin
import dev.m1n1don.homeplugin.table.UserData
import dev.m1n1don.homeplugin.user.User
import hazae41.minecraft.kutils.bukkit.msg
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class SetSpawnCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player

        transaction {
            UserData.update({ UserData.uniqueId eq sender.uniqueId }) {
                it[spawnLocation] = "${sender.world.name},${sender.location.x},${sender.location.y},${sender.location.z}"
            }
        }

        val user = HomePlugin.plugin.users[sender.uniqueId]!!
        HomePlugin.plugin.users.remove(sender.uniqueId)
        HomePlugin.plugin.users[sender.uniqueId] = User(user.id, user.uniqueId, sender.location)

        sender.msg("&eスポーンポイントを設定しました！")
        return true
    }
}