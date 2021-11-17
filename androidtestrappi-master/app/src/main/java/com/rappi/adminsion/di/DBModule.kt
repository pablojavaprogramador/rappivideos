package com.rappi.adminsion.di

import androidx.room.Room

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.security.AccessController

val DbModule = module {
    single {
            androidContext()
    }

}