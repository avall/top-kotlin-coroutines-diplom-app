package ru.vgtu.diplom.app.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.vgtu.diplom.app.model.Decision

@Configuration
class KafkaConfig {

    @Value("\${kafka.bootstrap-address}")
    lateinit var bootstrapAddress: String

    @Bean
    fun producerFactory() =
        DefaultKafkaProducerFactory<String, Decision>(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
            )
        )

    @Bean
    fun kafkaTemplate() =
        KafkaTemplate(producerFactory())

    @Bean
    fun kafkaAdmin(): KafkaAdmin =
        KafkaAdmin(
            mapOf(
                Pair(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress)
            )
        )

    @Bean
    fun offerIn(): NewTopic =
        NewTopic("offer-in", 1, 1)


}