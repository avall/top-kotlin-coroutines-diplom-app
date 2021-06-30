package ru.vgtu.diplom.app.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.vgtu.diplom.app.api.ConditionApi
import ru.vgtu.diplom.app.model.Condition
import ru.vgtu.diplom.app.service.ConditionService

@RestController
class ConditionImpl(
        private val conditionService: ConditionService
) : ConditionApi {
    override suspend fun getCondition(clientId: String): ResponseEntity<Condition> {
        return ResponseEntity.ok(conditionService.getCondition(clientId))
    }

    override suspend fun writeCondition(clientId: String, condition: Condition?): ResponseEntity<Unit> {
        conditionService.writeCondition(clientId, condition)
        return ResponseEntity.ok().build()
    }
}