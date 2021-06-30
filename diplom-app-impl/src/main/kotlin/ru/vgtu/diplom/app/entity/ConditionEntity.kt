package ru.vgtu.diplom.app.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.vgtu.diplom.app.model.Condition

@Document(value = "condition")
data class ConditionEntity(
        @Id
        var clientId: String,
        var applicationId: String? = null,
        var condition: Condition
)

