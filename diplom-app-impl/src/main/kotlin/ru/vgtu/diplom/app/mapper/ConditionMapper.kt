package ru.vgtu.diplom.app.mapper

import org.springframework.stereotype.Component
import ru.vgtu.diplom.app.entity.ConditionEntity
import ru.vgtu.diplom.app.model.Condition

@Component
class ConditionMapper {
    fun Condition.toEntity(clientId: String): ConditionEntity = ConditionEntity(clientId = clientId, condition = this)
}