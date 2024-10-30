package org.example.sodam.domain.user.domain

import org.example.sodam.domain.user.enum.Allergy
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(unique = true, nullable = false)
    val accountId: String,

    @Column(nullable = false)
    val password: String,

    val foods: String? = null,

    var allergy: String? = null,

    var point: Int = 0
) {
    fun updateAllergy(allergies: List<Allergy>?) {
        this.allergy = allergies?.joinToString(",")
    }

    fun pointUp(point: Int) {
        this.point += point
    }
}
