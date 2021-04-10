package me.user.common.di

import android.content.Context
import me.user.common.getDbClient

interface IDependencyProvider

class AndroidDependencies(val context: Context) : IDependencyProvider

fun createDbClient(
    dependencyProvider: IDependencyProvider = object : IDependencyProvider {}
) = getDbClient(dependencyProvider)