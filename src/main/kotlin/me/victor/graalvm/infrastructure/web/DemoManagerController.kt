package me.victor.graalvm.infrastructure.web

import me.victor.graalvm.domain.Demo
import me.victor.graalvm.domain.Participant
import me.victor.graalvm.service.DemoManagerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("/demos")
class DemoManagerController(private val demoManagerService: DemoManagerService) {

    @PostMapping
    fun registerDemo(@RequestBody registerDto: RegisterDemoDto) = demoManagerService.registerDemo(registerDto.toDemo())

    @GetMapping
    fun listDemos(
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = true, defaultValue = "20") size: Int
    ) = demoManagerService.listDemos(page, size)

    @PostMapping("/participants")
    fun addParticipant(
        @RequestBody registerDto: RegisterParticipantDto,
        @RequestParam(value = "demoId", required = true) demoId: UUID
    ) = demoManagerService.registerParticipant(registerDto.toParticipant(), demoId)

    @GetMapping("/participants")
    fun listParticipants(@RequestParam(value = "demoId", required = true) demoId: UUID) =
        demoManagerService.listParticipants(demoId)
}

data class RegisterDemoDto(var name: String, var startTime: LocalDateTime, var endTime: LocalDateTime) {
    fun toDemo() = Demo(UUID.randomUUID(), this.name, this.startTime, this.endTime, emptyList())
}

data class RegisterParticipantDto(var name: String, var email: String) {
    fun toParticipant() = Participant(UUID.randomUUID(), name, email)
}