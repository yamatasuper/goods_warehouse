package com.goods.product.database.users

import com.goods.product.features.user.UserChangeReceiveRemote
import com.goods.product.features.user.UserChangeResponseRemote
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Users : Table() {
    private val password = Users.varchar("password", 25)
    private val username = Users.varchar("username", 30)
    private val email = Users.varchar("email", 25)
    private val token = Users.varchar("token", 50)

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

    fun userChangeData(userChangeReceiveRemote: UserChangeReceiveRemote): UserChangeResponseRemote {
        return try {
            transaction {
                val userToUpdate =
                    Users.select { email.eq(userChangeReceiveRemote.email) }.singleOrNull()
                userToUpdate?.let {
                    Users.update({ email.eq(userChangeReceiveRemote.email) }) {
                        it[password] = userChangeReceiveRemote.password
                        it[username] = userChangeReceiveRemote.username
                    }
                }
                UserChangeResponseRemote("200")
            }
        } catch (e: Exception) {
            UserChangeResponseRemote("400")
        }
    }
}