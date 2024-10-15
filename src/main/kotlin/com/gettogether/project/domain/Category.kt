package com.gettogether.project.domain

import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id", nullable = false)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val name: String
)
