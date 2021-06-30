package ru.vgtu.diplom.app.schedule

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.vgtu.diplom.app.exception.DecisionNotFoundException
import ru.vgtu.diplom.app.messaging.MessageProducer
import ru.vgtu.diplom.app.service.ApplicationService
import ru.vgtu.diplom.common.logging.Loggable

@Component
@EnableScheduling
class ApplicationFinalStatusSchedule(
    private val messageProducer: MessageProducer,
    private val applicationService: ApplicationService
) {

    companion object : Loggable

    @Scheduled(fixedRateString = "\${schedule.application.fixedRate}")
    fun sendApplicationToOfferWithFinalStatus() {
        GlobalScope.launch {
            try {
                val decisionPairList = applicationService.findAllApplicationWithFinalStatus()
                decisionPairList.forEach {
                    val clientId = it.first
                    val applicationEntity = it.second
                    messageProducer.sendDecision(
                        applicationEntity.application.decision!!,
                        applicationEntity.appId!!,
                        clientId
                    )
                    applicationEntity.isSend = true
                    applicationService.updateApplicationEntity(clientId, applicationEntity)
                }
            } catch (ex: DecisionNotFoundException) {
                logger.info("decision list is null")
            } catch (ex: Exception) {
                logger.error(ex.message, ex)
            }
        }


    }

}