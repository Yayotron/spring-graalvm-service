package me.victor.graalvm.infrastructure.database.repositories

import me.victor.graalvm.domain.Demo
import me.victor.graalvm.domain.Participant
import me.victor.graalvm.infrastructure.database.entities.DemoEntity
import me.victor.graalvm.infrastructure.database.entities.ParticipantEntity
import me.victor.graalvm.service.DemoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DemoSqlRepository(
    private val demoRepository: DemoJpaRepository,
    private val participantRepository: ParticipantJpaRepository
) : DemoRepository {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun saveDemo(demo: Demo) {
        logger.debug("Saving new demo {} into database", demo.name)

        demoRepository.save(DemoEntity.fromDemo(demo))
    }

    override fun saveParticipant(participant: Participant) {
        logger.debug("Saving new participant {} into database", participant.name)

        participantRepository.save(ParticipantEntity.fromParticipant(participant))
    }

    override fun addAttendee(participant: Participant, demoId: UUID) {
        demoId.let { demoRepository.getOne(it) }
            .let { it.copy(participants = withExtraParticipant(it, participant)) }
            .run { demoRepository.save(this) }
    }

    override fun listDemos(page: Int, size: Int): Page<Demo> {
        return demoRepository.findAll(PageRequest.of(page, size)).map { it.toDemo() }
    }

    override fun listParticipants(demoId: UUID): List<Participant> {
        return demoRepository.findById(demoId)
            .map { demo ->
                demo.participants
                    .map { participant -> participant.toParticipant() }
            }.orElseThrow()
    }


    private fun withExtraParticipant(
        it: DemoEntity,
        participant: Participant
    ) = it.participants.plus(ParticipantEntity.fromParticipant(participant))
}

interface DemoJpaRepository : JpaRepository<DemoEntity, UUID>

interface ParticipantJpaRepository : JpaRepository<ParticipantEntity, UUID>