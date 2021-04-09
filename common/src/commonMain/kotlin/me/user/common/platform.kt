package me.user.common

import org.hildan.krossbow.stomp.StompClient

expect fun getPlatformName(): String

expect fun getStompClient(): StompClient