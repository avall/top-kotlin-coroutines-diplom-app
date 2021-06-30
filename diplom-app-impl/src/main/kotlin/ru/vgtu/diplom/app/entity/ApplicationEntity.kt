package ru.vgtu.diplom.app.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.vgtu.diplom.app.extensions.ApplicationStatus
import ru.vgtu.diplom.app.model.Application

@Document(value = "application")
class ApplicationEntity(
        val SEQUENCE_NAME: String = "app_sequence",
        @Id
        var appId: String?,
        var clientId: String?,
        var applicationStatus: ApplicationStatus?,
        var application: Application,
        var isSend: Boolean
)