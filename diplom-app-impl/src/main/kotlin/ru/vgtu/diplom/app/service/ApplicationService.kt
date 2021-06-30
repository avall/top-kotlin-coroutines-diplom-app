package ru.vgtu.diplom.app.service

import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import ru.vgtu.diplom.app.entity.ApplicationEntity
import ru.vgtu.diplom.app.exception.AppBodyIsNotExistException
import ru.vgtu.diplom.app.exception.AppIdIsNotExistException
import ru.vgtu.diplom.app.exception.AppNotFoundException
import ru.vgtu.diplom.app.exception.DecisionNotFoundException
import ru.vgtu.diplom.app.extensions.ApplicationStatus
import ru.vgtu.diplom.app.mapper.ApplicationMapper
import ru.vgtu.diplom.app.model.*
import ru.vgtu.diplom.app.repository.ApplicationReactiveRepository
import ru.vgtu.diplom.common.logging.Loggable
import java.time.LocalDateTime

@Service
class ApplicationService(
        private val repository: ApplicationReactiveRepository,
        private val applicationMapper: ApplicationMapper,
        private val conditionService: ConditionService
) {

    companion object : Loggable

    suspend fun createApplication(application: PostApplication?, clientId: String): PostApplicationInfo {
        application ?: throw AppBodyIsNotExistException(clientId)
        return with(applicationMapper) {
            val condition = conditionService.getCondition(clientId)
            repository.findByClientIdAndApplicationStatus(clientId, ApplicationStatus.DRAFT)
                    .map { PostApplicationInfo(it.appId, it.application.appCreateDate) }
                    .switchIfEmpty(
                            repository.save(application.toEntity(clientId, condition))
                                    .map { PostApplicationInfo(it.appId, it.application.appCreateDate) }
                    )
                    .awaitFirstOrElse { PostApplicationInfo() }
        }
    }

    suspend fun getApplication(appId: String?, clientId: String): Application {
        appId ?: throw AppIdIsNotExistException(clientId)
        return repository.findById(appId)
                .map { it.application }
                .awaitFirstOrElse { throw AppNotFoundException(appId, clientId) }
    }

    suspend fun getApplicationInfo(clientId: String): GetApplicationInfo {
        return repository.findAllByClientId(clientId)
                .filter { it.applicationStatus == ApplicationStatus.DRAFT }
                .map {
                    val app = it.application
                    GetApplicationInfo(
                            app.applicationId,
                            app.appCreateDate,
                            app.confirmationDate,
                            app.applicationStatus
                    )
                }
                .awaitFirstOrElse { throw AppNotFoundException(clientId = clientId) }
    }

    suspend fun getDecision(appId: String?, clientId: String): Decision {
        appId ?: throw AppIdIsNotExistException(clientId)
        return repository.findById(appId)
                .map { it.application.decision ?: throw DecisionNotFoundException(appId, clientId) }
                .awaitFirstOrElse { throw AppNotFoundException(appId) }
    }

    suspend fun updateStatus(appId: String, status: Status?) {
        repository.findById(appId)
                .map {
                    it.applicationStatus = ApplicationStatus.valueOf(status?.newApplicationStatus.toString())
                    it.application.applicationStatus = status?.newApplicationStatus
                    when (it.applicationStatus) {
                        ApplicationStatus.APPROVE,
                        ApplicationStatus.DECLINE,
                        ApplicationStatus.PRE_APPROVE -> it.application.confirmationDate = LocalDateTime.now()
                        else -> logger.info("change status to ${it.applicationStatus} for $appId")
                    }
                    repository.save(it).subscribe()
                }
                .awaitFirstOrElse { throw AppNotFoundException(appId) }

    }

    suspend fun writeDecision(appId: String, decision: Decision?) {
        repository.findById(appId)
                .map {
                    it.application.decision = decision
                    repository.save(it).subscribe()
                }
                .awaitFirstOrElse { throw AppNotFoundException(appId) }
    }

    suspend fun updateApplication(clientId: String, application: Application?) {
        application ?: throw AppBodyIsNotExistException(clientId)
        with(applicationMapper) {
            repository.save(application.toEntity()).subscribe()
        }
    }

    suspend fun updateApplicationEntity(clientId: String, applicationEntity: ApplicationEntity?) {
        applicationEntity ?: throw AppBodyIsNotExistException(clientId)
        repository.save(applicationEntity).subscribe()
    }

    suspend fun findAllApplicationWithFinalStatus(): MutableList<Pair<String, ApplicationEntity>> =
            repository.findByApplicationStatusAndIsSend(ApplicationStatus.DECLINE)
                    .mergeWith(repository.findByApplicationStatusAndIsSend(ApplicationStatus.APPROVE))
                    .mergeWith(repository.findByApplicationStatusAndIsSend(ApplicationStatus.PRE_APPROVE))
                    .map { it.clientId!! to it }
                    .collectList()
                    .awaitFirstOrNull() ?: throw DecisionNotFoundException("not found")


}