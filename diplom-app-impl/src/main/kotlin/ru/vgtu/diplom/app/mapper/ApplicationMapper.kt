package ru.vgtu.diplom.app.mapper

import org.springframework.stereotype.Component
import ru.vgtu.diplom.app.entity.ApplicationEntity
import ru.vgtu.diplom.app.extensions.ApplicationStatus
import ru.vgtu.diplom.app.model.Application
import ru.vgtu.diplom.app.model.Condition
import ru.vgtu.diplom.app.model.PostApplication
import java.time.LocalDateTime

@Component
class ApplicationMapper {
    fun PostApplication.toEntity(clientId: String, condition: Condition): ApplicationEntity = ApplicationEntity(
        appId = null,
        clientId = clientId,
        applicationStatus = ApplicationStatus.DRAFT,
        application = Application(
            userName = userName,
            login = login,
            purchaseRegion = purchaseRegion,
            applicationType = applicationType,
            prescoringFlag = prescoringFlag,
            loanType = condition.loanType,
            productCode = condition.productCode,
            creditProgram = condition.creditProgram,
            programCode = condition.programCode,
            appCreateDate = LocalDateTime.now(),
            confirmationDate = confirmationDate,
            applicationStatus = applicationStatus,
            creditParam = condition.creditParam,
            creditProgramParam = condition.creditProgramParam,
            auto = condition.auto,
            insurances = condition.insurances,
            profiles = profiles,
            decision = decision,
            isBki = false,
            isClientValid = false,
            isIncomeValid = false,
            isPassportValid = false,
            isSolvency = false,
            isUnderwriting = Math.random() > Math.random()
        ),
        isSend = false
    )

    fun Application.toEntity(): ApplicationEntity = ApplicationEntity(
        appId = applicationId,
        clientId = profiles?.first()?.clientId,
        applicationStatus = applicationStatus?.let { ApplicationStatus.valueOf(it) },
        application = this,
        isSend = false
    )

}