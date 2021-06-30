package ru.vgtu.diplom.app.service

import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service
import ru.vgtu.diplom.app.exception.AppNotFoundException
import ru.vgtu.diplom.app.exception.ProfileBodyIsNotExistException
import ru.vgtu.diplom.app.model.Profile
import ru.vgtu.diplom.app.repository.ApplicationReactiveRepository

@Service
class ProfileService(
        private val repository: ApplicationReactiveRepository
) {
    suspend fun getProfileByAppId(appId: String): Profile {
        return repository.findById(appId)
                .map { it.application.profiles?.first() ?: throw AppNotFoundException(appId) }
                .awaitFirstOrElse { throw AppNotFoundException(appId) }
    }

    fun updateProfileByAppId(appId: String, profile: Profile?) {
        profile ?: throw ProfileBodyIsNotExistException(appId)
        repository.findById(appId)
                .map {
                    it.application.profiles = listOf(profile)
                    repository.save(it).subscribe()
                }.subscribe()
    }

}