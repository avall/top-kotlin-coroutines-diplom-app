package ru.vgtu.diplom.app.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.vgtu.diplom.app.entity.ApplicationEntity
import ru.vgtu.diplom.app.entity.ConditionEntity
import ru.vgtu.diplom.app.extensions.ApplicationStatus

interface ApplicationReactiveRepository : ReactiveCrudRepository<ApplicationEntity, String> {
    fun findAllByClientId(clientId: String) : Flux<ApplicationEntity>
    fun findByClientIdAndApplicationStatus(clientId: String, status: ApplicationStatus?): Mono<ApplicationEntity>
    fun findByApplicationStatusAndIsSend(applicationStatus: ApplicationStatus, boolean: Boolean = false): Flux<ApplicationEntity>
}

interface ConditionReactiveRepository : ReactiveCrudRepository<ConditionEntity, String>