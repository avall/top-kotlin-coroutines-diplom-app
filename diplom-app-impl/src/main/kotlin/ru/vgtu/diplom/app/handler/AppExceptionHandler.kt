package ru.vgtu.diplom.app.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.vgtu.diplom.app.exception.*
import ru.vgtu.diplom.common.logging.Loggable
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class AppExceptionHandler {

    companion object : Loggable

    private val EMPTY_MESSAGE: String = "Empty message"

    @ExceptionHandler(AppIdIsNotExistException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleAppIdIsNotExistException(request: HttpServletRequest,
                                       ex: AppIdIsNotExistException): ResponseEntity<BaseError> {
        logger.error("${ex.message}, url: ${request.requestURL}")
        return ResponseEntity.badRequest().body(BaseError("exception.ParameterError", ex.message ?: EMPTY_MESSAGE))
    }

    @ExceptionHandler(
            AppBodyIsNotExistException::class,
            ConditionBodyIsNotExistException::class,
            ProfileBodyIsNotExistException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleAppBodyIsNotExistException(request: HttpServletRequest,
                                         ex: AppBodyIsNotExistException): ResponseEntity<BaseError> {
        logger.error("${ex.message}, url: ${request.requestURL}")
        return ResponseEntity.badRequest().body(BaseError("exception.NotExistBody", ex.message ?: EMPTY_MESSAGE))
    }

    @ExceptionHandler(
            ConditionNotFoundException::class,
            DecisionNotFoundException::class,
            AppNotFoundException::class
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleConditionNotFoundException(request: HttpServletRequest,
                                         ex: ConditionNotFoundException): ResponseEntity<BaseError> {
        logger.error("${ex.message}, url: ${request.requestURL}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseError("exception.NotFound", ex.message ?: EMPTY_MESSAGE))
    }
}