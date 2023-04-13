package me.victor.graalvm.infrastructure.database.entities

import me.victor.graalvm.domain.Demo
import me.victor.graalvm.infrastructure.database.DatabaseConstants
import java.time.LocalDateTime
import java.util.UUID
import jakarta.persistence.*

@Entity
@Table(schema = DatabaseConstants.SCHEMA, name = DatabaseConstants.DEMO_TABLE)
data class DemoEntity(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Column(name = "name", nullable = false)
    var name: String = "",
    @Column(name = "start_time", nullable = false)
    var startTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "end_time", nullable = false)
    var endTime: LocalDateTime = LocalDateTime.now(),
    @OneToMany
    @JoinColumn(name = "demo_id", referencedColumnName = "id")
    var participants: List<ParticipantEntity> = emptyList()
) {
    companion object {
        fun fromDemo(demo: Demo) = DemoEntity(
            demo.id,
            demo.name,
            demo.startTime,
            demo.endTime,
            demo.attendees.map { ParticipantEntity.fromParticipant(it) }
        )
    }

    fun toDemo() = Demo(this.id, this.name, this.startTime, this.endTime, this.participants.map { it.toParticipant() })
}