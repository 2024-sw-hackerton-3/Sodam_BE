package org.example.sodam.domain.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByAccountId(accountId: String): Boolean
    fun findByAccountId(accountId: String): User?
}
