package me.victor.graalvm.domain

import java.util.UUID

data class Participant(
    val id: UUID,
    val name: String,
    val email: String
)