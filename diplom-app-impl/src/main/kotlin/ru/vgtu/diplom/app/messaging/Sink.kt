//package ru.vgtu.diplom.app.messaging
//
//import org.springframework.cloud.stream.annotation.Input
//import org.springframework.cloud.stream.annotation.Output
//import org.springframework.messaging.MessageChannel
//import org.springframework.messaging.SubscribableChannel
//import org.springframework.stereotype.Component
//
//@Component
//interface Sink {
//    companion object {
//        const val INPUT = "offer-out"
//        const val OUTPUT = "offer-in"
//    }
//
//    @Input(INPUT)
//    fun input() : SubscribableChannel
//
//    @Output(OUTPUT)
//    fun output(): MessageChannel
//}