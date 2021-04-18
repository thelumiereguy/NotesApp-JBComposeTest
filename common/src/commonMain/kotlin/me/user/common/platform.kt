package me.user.common

import org.koin.core.module.Module

expect fun getPlatformName(): String

internal expect fun platformModule(): Module

expect fun runTest(block: suspend () -> Unit)