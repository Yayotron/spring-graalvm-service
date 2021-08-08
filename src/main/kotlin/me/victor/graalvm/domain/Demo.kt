package me.victor.graalvm.domain

import java.time.LocalDateTime
import java.util.UUID

data class Demo(
    val id: UUID,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val attendees: List<Participant>
)