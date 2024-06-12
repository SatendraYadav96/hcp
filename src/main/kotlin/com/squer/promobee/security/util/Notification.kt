package com.squer.cme.utils

import java.io.Serializable

class Notification(
    var title: String? = null,
    var message: String? = null,
    var userToken: String? = null,
    var userTokenCC: String? = null,
    var type: EventTypeEnum? = null,
    var priority: Int = 2,
    var status: NotificationStatusEnum? = null,
    var failedReason: String? = null,
) : Serializable

enum class NotificationStatusEnum {
    NEW, SUCCESS, FAILED
}

enum class EventTypeEnum {
    PUSH, EMAIL, SMS
}
