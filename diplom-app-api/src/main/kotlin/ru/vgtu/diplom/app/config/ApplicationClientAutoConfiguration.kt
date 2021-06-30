package ru.vgtu.diplom.app.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vgtu.diplom.app.client.ApplicationClient
import ru.vgtu.diplom.common.webclient.MdcLoggingWebClientCustomizer
import ru.vgtu.diplom.common.webclient.MdcWebClientFactory

@Configuration
@ConditionalOnProperty("service.app.base-url")
@EnableConfigurationProperties(ApplicationClientProperty::class)
class ApplicationClientAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun applicationReactiveClient(
        clientProperty: ApplicationClientProperty
    ): ApplicationClient =
        with(clientProperty) {
            val builder = MdcWebClientFactory.builder(
                readTimeout = readTimeout,
                connectionTimeout = connectionTimeout
            ).baseUrl(baseUrl)
            MdcLoggingWebClientCustomizer().customize(builder)

            ApplicationClient(builder.build())
        }



}