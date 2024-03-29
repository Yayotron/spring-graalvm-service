package me.victor.graalvm.infrastructure.database.entities

import jakarta.persistence.*
import me.victor.graalvm.domain.Participant
import me.victor.graalvm.infrastructure.database.DatabaseConstants
import java.util.UUID

@Entity
@Table(schema = DatabaseConstants.SCHEMA, name = DatabaseConstants.PARTICIPANT_TABLE)
data class ParticipantEntity(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Column(name = "name", nullable = false)
    var name: String = "",
    @Column(name = "email", nullable = true)
    var email: String = "",
    @ManyToOne(optional = true)
    @JoinColumn(name = "demo_id", referencedColumnName = "id")
    var demoEntity: DemoEntity? = null
) {
    companion object {
        fun fromParticipant(participant: Participant) = ParticipantEntity(
            participant.id,
            participant.name,
            participant.email
        )
    }

    fun toParticipant() = Participant(this.id, this.name, this.email)

}