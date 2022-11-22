package dev.m1n1don.homeplugin

import dev.m1n1don.homeplugin.commands.*
import dev.m1n1don.homeplugin.listeners.*
import dev.m1n1don.homeplugin.table.UserData
import dev.m1n1don.homeplugin.user.User
import org.bukkit.Bukkit
import org.bukkit.Location
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.Connection
import java.util.UUID

class HomePlugin : AbstractHomePlugin()
{
    companion object
    {
        lateinit var plugin: HomePlugin
    }

    val users: MutableMap<UUID, User> = mutableMapOf()

    override fun onEnable()
    {
        plugin = this

        val dbFolder = File(dataFolder, "/database")
        if (!dbFolder.exists()) dbFolder.mkdirs()

        val dbFile = File(dataFolder, "/database/icecat.db")
        if (!dbFile.exists()) dbFile.createNewFile()

        Database.connect("jdbc:sqlite:${dbFile.path}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(
                UserData
            )

            UserData.selectAll().forEach {
                val data = it[UserData.spawnLocation].split(",")
                users[it[UserData.uniqueId]] = User(it[UserData.id], it[UserData.uniqueId], Location(Bukkit.getWorld(data[0]), data[1].toDouble(), data[2].toDouble(), data[3].toDouble()))
            }
        }

        registerListeners(
            PlayerConnectionListener()
        )

        registerCommands(
            "setspawn" to SetSpawnCommand(),
            "spawn" to SpawnCommand()
        )
    }
}