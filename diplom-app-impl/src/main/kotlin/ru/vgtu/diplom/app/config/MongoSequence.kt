package ru.vgtu.diplom.app.config

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "database_sequences")
data class MongoSequence(
        @Id
        val seqId: String,
        val seq: Long
)