package ru.vgtu.diplom.app.exception

class AppBodyIsNotExistException(clientId: String) : Exception("Application body is not exist! clientId = $clientId")

class AppIdIsNotExistException(clientId: String) : Exception("Application id is not exist! clientId = $clientId")

class AppNotFoundException(appId: String? = null, clientId: String? = null) : Exception("Application not found! appId = $appId, clientId = $clientId")

class DecisionNotFoundException(appId: String?, clientId: String? = null) : Exception("Decision not found! appId = $appId, clientId = $clientId")

class ConditionNotFoundException(clientId: String) : Exception("Condition not found! clientId = $clientId")

class ConditionBodyIsNotExistException(clientId: String) : Exception("Condition body is not exist! clientId = $clientId")

class ProfileBodyIsNotExistException(appId: String?) : Exception("Profile body is not exist! appId = $appId")

data class BaseError(val errorCode: String, val errorMsg: String)