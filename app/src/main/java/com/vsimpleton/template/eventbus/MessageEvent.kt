package com.vsimpleton.template.eventbus

class MessageEvent @JvmOverloads constructor(
    var eventId: EventId,
    var msg: String = "",
    var arg1: Int = 0,
    var arg2: Int = 0,
    var obj: Any? = null
)