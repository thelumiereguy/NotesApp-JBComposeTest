package me.user.common

import org.hildan.krossbow.stomp.StompClient

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun getStompClient(): StompClient {
    return StompClient()
}