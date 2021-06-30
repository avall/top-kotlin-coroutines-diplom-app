package ru.vgtu.diplom.app.config

import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class MongoSequenceGenerator(val mongoOperations: MongoOperations) {

    fun generateSequence(seqName: String): Long {
        return mongoOperations.findAndModify(query(where("_id").`is`(seqName)),
                Update().inc("seq", 1),
                options().returnNew(true).upsert(true), MongoSequence::class.java)?.seq ?: MongoSequence("123", 1).seq
    }
}