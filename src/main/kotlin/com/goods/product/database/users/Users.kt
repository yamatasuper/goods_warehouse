package com.goods.product.database.users

import com.goods.product.utils.UserDatabaseColumns
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val password = varchar(UserDatabaseColumns.PASSWORD, 25)
    private val username = varchar(UserDatabaseColumns.USERNAME, 30)
    private val email = varchar(UserDatabaseColumns.EMAIL, 25)
    private val token = varchar(UserDatabaseColumns.TOKEN, 50)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email ?: ""
                it[token] = userDTO.token
            }
        }
    }

    fun  fetchUserUsingToken(tokenSearch: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { token.eq(tokenSearch) }.single()
                UserDTO(
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email],
                    token = userModel[token]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchUserUsingEmail(emailSearch: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { email.eq(emailSearch) }.single()
                UserDTO(
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email],
                    token = userModel[token]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun removeUserUsingToken(token: String): Int? {
        return try {
            transaction {
                Users.deleteWhere { Users.token.eq(token) }
            }
        } catch (e: Exception) {
            null
        }
    }
}