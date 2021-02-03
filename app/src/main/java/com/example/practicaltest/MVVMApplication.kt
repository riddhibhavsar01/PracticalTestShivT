package com.example.practicaltest

import android.app.Application
import com.example.practicaltest.data.db.AppDatabase
import com.example.practicaltest.data.network.NetworkConnectionInterceptor
import com.example.practicaltest.data.network.RetrofitApiCall
import com.example.practicaltest.data.repository.UserRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { RetrofitApiCall(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }



    }

}