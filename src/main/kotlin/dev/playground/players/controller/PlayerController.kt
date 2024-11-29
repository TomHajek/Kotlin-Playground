package dev.playground.players.controller

import dev.playground.players.model.Player
import dev.playground.players.model.Team
import dev.playground.players.service.PlayerService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

/**
 * GraphQL endpoint
 * The endpoint "methods" are based on `schema.graphqls` file
 * -> https://docs.spring.io/spring-graphql/reference/controllers.html
 */
@Controller
class PlayerController(private val playerService: PlayerService) {
    
    /*
     * @QueryMapping is only for fetching the data
     */
    @QueryMapping
    fun findAll(): List<Player> {
        return playerService.findAll()
    }
    
    @QueryMapping
    fun findOne(@Argument id: Int): Player? {
        return playerService.findOne(id)
    }
    
    /*
     * @MutationMapping is for data manipulation like create, update or delete
     */
    @MutationMapping
    fun create(@Argument name: String, @Argument team: Team): Player {
        return playerService.create(name, team)
    }
    
    @MutationMapping
    fun update(@Argument id: Int, @Argument name: String, @Argument team: Team): Player {
        return playerService.update(id, name, team)
    }
    
    @MutationMapping
    fun delete(@Argument id: Int): Player {
        return playerService.delete(id)
    }
    
}