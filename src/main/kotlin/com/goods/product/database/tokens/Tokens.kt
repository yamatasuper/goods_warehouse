package com.goods.product.database.tokens

import com.goods.product.utils.TokenDatabaseColumns
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    private val id = varchar(TokenDatabaseColumns.ID, 50)
    private val email = varchar(TokenDatabaseColumns.EMAIL, 25)
    private val token = varchar(TokenDatabaseColumns.TOKEN, 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[email] = tokenDTO.email
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            id = it[Tokens.id],
                            token = it[token],
                            email = it[email]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun removeUserUsingToken(token: String): Int? {
        return try {
            transaction {
                Tokens.deleteWhere { Tokens.token.eq(token) }
            }
        } catch (e: Exception) {
            null
        }
    }
}