package me.user.common.di

import me.user.common.getDbClient

interface IDependencyProvider

fun createDbClient(
    dependencyProvider: IDependencyProvider = object : IDependencyProvider {}
) = getDbClient(dependencyProvider)