package me.victor.graalvm.service

import me.victor.graalvm.domain.Demo
import me.victor.graalvm.domain.Participant
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class DemoManagerService(private val demoRepository: DemoRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)


    fun registerDemo(demo: Demo) {
        logger.info("Registering new demo {}...", demo.name)

        demoRepository.saveDemo(demo)
    }

    fun registerParticipant(participant: Participant, demoId: UUID) {
        logger.info("Registering new participant {} in demo {}", participant.name, demoId)

        demoRepository.saveParticipant(participant)
        demoRepository.addAttendee(participant, demoId)
    }

    fun listDemos(page: Int, size: Int): Page<Demo> {
        logger.debug("Retrieving {} demos from page {}", size, page)
        return demoRepository.listDemos(page, size)
    }

    fun listParticipants(demoId: UUID): List<Participant> {
        logger.debug("Retrieving participants from demo {}", demoId)
        return demoRepository.listParticipants(demoId)
    }
}