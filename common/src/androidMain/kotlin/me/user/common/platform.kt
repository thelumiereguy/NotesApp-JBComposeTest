package me.user.common

import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

actual fun getPlatformName(): String {
    return "Android"
}

actual fun getStompClient(): StompClient {
    return StompClient(OkHttpWebSocketClient())
}