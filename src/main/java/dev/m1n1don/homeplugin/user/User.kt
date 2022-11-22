package dev.m1n1don.homeplugin.user

import org.bukkit.Location
import java.util.UUID

data class User(val id: Int, val uniqueId: UUID, val spawnLocation: Location?)