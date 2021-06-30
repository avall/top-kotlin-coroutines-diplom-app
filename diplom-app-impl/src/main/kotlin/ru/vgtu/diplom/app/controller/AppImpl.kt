package ru.vgtu.diplom.app.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.vgtu.diplom.app.api.AppApi
import ru.vgtu.diplom.app.model.*
import ru.vgtu.diplom.app.service.ApplicationService
import ru.vgtu.diplom.common.logging.Loggable

@RestController
class AppImpl(
        val applicationService: ApplicationService
) : AppApi {

    companion object : Loggable

    override suspend fun createApplication(hClientId: String, postApplication: PostApplication?): ResponseEntity<PostApplicationInfo> =
            ResponseEntity.ok(applicationService.createApplication(postApplication, hClientId))


    override suspend fun getApplicationByAppId(appId: String, hClientId: String): ResponseEntity<Application> {
        runCatching {
            return ResponseEntity.ok(applicationService.getApplication(appId, hClientId))
        }.onFailure {
            logger.error(it.message, it)
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.notFound().build()
    }

    override suspend fun getApplicationInfo(clientId: String): ResponseEntity<GetApplicationInfo> {
        return ResponseEntity.ok(applicationService.getApplicationInfo(clientId))
    }

    override suspend fun getDecision(appId: String, hClientId: String): ResponseEntity<Decision> {
        return ResponseEntity.ok(applicationService.getDecision(appId, hClientId))
    }

    override suspend fun updateStatus(appId: String, status: Status?): ResponseEntity<Unit> {
        applicationService.updateStatus(appId, status)
        return ResponseEntity.ok().build()
    }

    override suspend fun writeDecision(appId: String, decision: Decision?): ResponseEntity<Unit> {
        applicationService.writeDecision(appId, decision)
        return ResponseEntity.ok().build()
    }

    override suspend fun updateApplication(hClientId: String, application: Application?): ResponseEntity<Unit> {
        applicationService.updateApplication(hClientId, application)
        return ResponseEntity.ok().build()
    }
}