package ru.vgtu.diplom.app.service

import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service
import ru.vgtu.diplom.app.exception.ConditionBodyIsNotExistException
import ru.vgtu.diplom.app.exception.ConditionNotFoundException
import ru.vgtu.diplom.app.mapper.ConditionMapper
import ru.vgtu.diplom.app.model.Condition
import ru.vgtu.diplom.app.repository.ConditionReactiveRepository

@Service
class ConditionService(
        private val repository: ConditionReactiveRepository,
        private val conditionMapper: ConditionMapper
) {
    suspend fun getCondition(clientId: String): Condition {
        return repository.findById(clientId)
                .map { it.condition }
                .awaitFirstOrElse { throw ConditionNotFoundException(clientId) }
    }

    fun writeCondition(clientId: String, condition: Condition?) {
        condition ?: throw ConditionBodyIsNotExistException(clientId)
        with(conditionMapper) {
            repository.save(condition.toEntity(clientId)).subscribe()
        }
    }

}