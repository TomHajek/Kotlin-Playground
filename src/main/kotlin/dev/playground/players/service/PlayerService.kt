package dev.playground.players.service

import dev.playground.players.model.Player
import dev.playground.players.model.Team
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

/**
 * Business layer that is still not part of graphql
 * Simplified service for crud operations
 */
@Service
class PlayerService {
    
    private val players = mutableListOf<Player>()
    private val id = AtomicInteger(0)
    
    fun findAll(): List<Player> = players
    
    fun findOne(id: Int): Player? = players.find { it.id == id }
    
    fun create(name: String, team: Team): Player {
        val player = Player(id.incrementAndGet(), name, team)
        players.add(player)
        return player
    }
    
    fun update(id: Int, name: String, team: Team): Player {
        val updatedPlayer = Player(id, name, team)
        val index = players.indexOfFirst { it.id == id }
        if (index != -1) {
            players[index] = updatedPlayer
        } else {
            throw IllegalArgumentException("Invalid Player")
        }
        return updatedPlayer
    }
    
    fun delete(id: Int): Player {
        val player = players.find { it.id == id }
            ?: throw IllegalArgumentException("Player not found")
        players.remove(player)
        return player
    }
    
    @PostConstruct
    private fun init() {
        players.apply {
            add(Player(id.incrementAndGet(), "MS Dhoni", Team.CSK))
            add(Player(id.incrementAndGet(), "Rohit Sharma", Team.MI))
            add(Player(id.incrementAndGet(), "Jasprit Bumrah", Team.MI))
            add(Player(id.incrementAndGet(), "Rishabh Pant", Team.DC))
            add(Player(id.incrementAndGet(), "Suresh Raina", Team.CSK))
        }
    }
    
}