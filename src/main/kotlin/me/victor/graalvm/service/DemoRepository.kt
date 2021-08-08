package me.victor.graalvm.service

import me.victor.graalvm.domain.Demo
import me.victor.graalvm.domain.Participant
import org.springframework.data.domain.Page
import java.util.UUID

interface DemoRepository {
    fun saveDemo(demo: Demo)
    fun saveParticipant(participant: Participant)
    fun addAttendee(participant: Participant, demo: UUID)
    fun listDemos(page: Int, size: Int): Page<Demo>
    fun listParticipants(demoId: UUID): List<Participant>
}