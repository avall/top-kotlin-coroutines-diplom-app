package ru.vgtu.diplom.app.config

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component
import ru.vgtu.diplom.app.entity.ApplicationEntity

@Component
class ApplicationModelListener(
        private val mongoSequenceGenerator: MongoSequenceGenerator
) : AbstractMongoEventListener<ApplicationEntity>() {
    override fun onBeforeConvert(event: BeforeConvertEvent<ApplicationEntity>) {
        val appId = event.source.appId
        if (appId == null) {
            event.source.appId = "AU${mongoSequenceGenerator.generateSequence(event.source.SEQUENCE_NAME)}"
            event.source.application.applicationId = event.source.appId
        }
    }
}