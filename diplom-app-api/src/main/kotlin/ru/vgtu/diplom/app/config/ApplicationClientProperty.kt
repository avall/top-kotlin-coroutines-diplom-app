package ru.vgtu.diplom.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties("service.app")
data class ApplicationClientProperty(
        val baseUrl: String,
        val readTimeout: Duration = Duration.ofMillis(15000),
        val connectionTimeout: Duration = Duration.ofMillis(15000)
)