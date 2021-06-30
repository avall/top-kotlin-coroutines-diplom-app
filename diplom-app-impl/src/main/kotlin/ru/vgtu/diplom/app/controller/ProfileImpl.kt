package ru.vgtu.diplom.app.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.vgtu.diplom.app.api.ProfileApi
import ru.vgtu.diplom.app.model.Profile
import ru.vgtu.diplom.app.service.ProfileService

@RestController
class ProfileImpl(
        private val profileService: ProfileService
) : ProfileApi {
    override suspend fun getProfileByAppId(appId: String): ResponseEntity<Profile> {
        return ResponseEntity.ok(profileService.getProfileByAppId(appId))
    }

    override suspend fun updateProfileByAppId(appId: String, profile: Profile?): ResponseEntity<Unit> {
        profileService.updateProfileByAppId(appId, profile)
        return ResponseEntity.ok().build()
    }
}