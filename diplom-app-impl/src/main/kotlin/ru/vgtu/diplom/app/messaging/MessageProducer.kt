package ru.vgtu.diplom.app.messaging

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import ru.vgtu.diplom.app.model.Decision
import ru.vgtu.diplom.common.logging.Loggable

@Service
class MessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, Decision>,
    @Qualifier("offerIn") private val newTopic: NewTopic
) {

    companion object : Loggable

    fun sendDecision(decision: Decision, appId: String, clientId: String) {
        val headers = MessageHeaders(
            mapOf(
                KafkaHeaders.CORRELATION_ID to appId,
                "M-Client-Id" to clientId,
                KafkaHeaders.TOPIC to newTopic.name()
            )
        )
        kafkaTemplate.send(MessageBuilder.createMessage(decision, headers))
        logger.info("message sent appId: $appId, clientId: $clientId")
    }

}