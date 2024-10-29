package org.example.sodam.domain.user.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val name: String,
    @Column(unique = true, nullable = false)
    val accountId: String,
    @Column(nullable = false)
    val password: String

)
