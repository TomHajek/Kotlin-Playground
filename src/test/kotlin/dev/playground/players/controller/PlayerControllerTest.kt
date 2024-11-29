package dev.playground.players.controller

import dev.playground.players.model.Player
import dev.playground.players.model.Team
import dev.playground.players.service.PlayerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.context.annotation.Import
import org.springframework.graphql.test.tester.GraphQlTester

@Import(PlayerService::class)
@GraphQlTest(PlayerController::class)
class PlayerControllerTest {
    
    @Autowired
    private lateinit var tester: GraphQlTester
    
    @Autowired
    private lateinit var playerService: PlayerService
    
    @Test
    fun testFindAllPlayerShouldReturnAllPlayers() {
        val document = """
            query MyQuery {
              findAll {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .execute()
            .path("findAll")
            .entityList(Player::class.java)
            .hasSizeGreaterThan(3)
    }
    
    @Test
    fun testValidIdShouldReturnPlayer() {
        val document = """
            query findOne(${'$'}id: ID) {
              findOne(id: ${'$'}id) {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .variable("id", 1)
            .execute()
            .path("findOne")
            .entity(Player::class.java)
            .satisfies { player ->
                Assertions.assertEquals("MS Dhoni", player.name)
                Assertions.assertEquals(Team.CSK, player.team)
            }
    }
    
    @Test
    fun testInvalidIdShouldReturnNull() {
        val document = """
            query findOne(${'$'}id: ID) {
              findOne(id: ${'$'}id) {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .variable("id", 100)
            .execute()
            .path("findOne")
            .valueIsNull()
    }
    
    @Test
    fun testShouldCreateNewPlayer() {
        val currentCount = playerService.findAll().size
        val document = """
            mutation create(${'$'}name: String, ${'$'}team: Team) {
              create(name: ${'$'}name, team: ${'$'}team) {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .variable("name", "Virat Kohli")
            .variable("team", Team.RCB)
            .execute()
            .path("create")
            .entity(Player::class.java)
            .satisfies { player ->
                Assertions.assertEquals("Virat Kohli", player.name)
                Assertions.assertEquals(Team.RCB, player.team)
            }
        
        Assertions.assertEquals(currentCount + 1, playerService.findAll().size)
    }
    
    @Test
    fun testShouldUpdateExistingPlayer() {
        val document = """
            mutation update(${'$'}id: ID, ${'$'}name: String, ${'$'}team: Team) {
              update(id: ${'$'}id, name: ${'$'}name, team: ${'$'}team) {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .variable("id", 3)
            .variable("name", "Updated Jasprit Bumrah")
            .variable("team", Team.CSK)
            .execute()
            .path("update")
            .entity(Player::class.java)
        
        val updatedPlayer = playerService.findOne(3)!!
        Assertions.assertEquals("Updated Jasprit Bumrah", updatedPlayer.name)
        Assertions.assertEquals(Team.CSK, updatedPlayer.team)
    }
    
    @Test
    fun testShouldRemovePlayerWithValidId() {
        val currentCount = playerService.findAll().size
        val document = """
            mutation delete(${'$'}id: ID) {
              delete(id: ${'$'}id) {
                id
                name
                team
              }
            }
        """.trimIndent()
        
        tester.document(document)
            .variable("id", 3)
            .executeAndVerify()
        
        Assertions.assertEquals(currentCount - 1, playerService.findAll().size)
    }
    
}