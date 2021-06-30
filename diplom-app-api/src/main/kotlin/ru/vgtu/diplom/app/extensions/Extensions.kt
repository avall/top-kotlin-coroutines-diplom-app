package ru.vgtu.diplom.app.extensions


enum class ApplicationStatus(
    val value: String
) {
    DRAFT("DRAFT"),
    ERROR("ERROR"),
    DECLINE("DECLINE"),
    PRE_APPROVE("PRE_APPROVE"),
    APPROVE("APPROVE");
}