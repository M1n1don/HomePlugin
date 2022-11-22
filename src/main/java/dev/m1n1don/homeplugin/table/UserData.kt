package dev.m1n1don.homeplugin.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object UserData : Table()
{
    val id: Column<Int> = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val uniqueId: Column<UUID> = uuid("uniqueId")

    val spawnLocation: Column<String> = text("spawnLocation")
}