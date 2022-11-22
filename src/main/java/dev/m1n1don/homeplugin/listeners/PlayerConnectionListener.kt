package dev.m1n1don.homeplugin.listeners

import dev.m1n1don.homeplugin.HomePlugin
import dev.m1n1don.homeplugin.table.UserData
import dev.m1n1don.homeplugin.user.User
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PlayerConnectionListener : Listener
{
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent)
    {
        val player = e.player

        transaction {
            if (UserData.select { UserData.uniqueId eq player.uniqueId }.count() == 1L) return@transaction

            val id = UserData.insert {
                it[uniqueId] = player.uniqueId
                it[spawnLocation] = String()
            }[UserData.id]

            HomePlugin.plugin.users[player.uniqueId] = User(id, player.uniqueId, null)
        }
    }
}