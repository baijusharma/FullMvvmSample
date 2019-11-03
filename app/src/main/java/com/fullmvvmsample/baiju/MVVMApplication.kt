package com.fullmvvmsample.baiju

import android.app.Application
import com.fullmvvmsample.baiju.data.db.AppDatabase
import com.fullmvvmsample.baiju.data.network.MyApi
import com.fullmvvmsample.baiju.data.network.NetworkConnectionInterceptor
import com.fullmvvmsample.baiju.data.prefrences.PrefrenceProvider
import com.fullmvvmsample.baiju.data.repository.QuotesRepository
import com.fullmvvmsample.baiju.data.repository.UserRepository
import com.fullmvvmsample.baiju.ui.auth.AuthViewModelFactory
import com.fullmvvmsample.baiju.ui.profile.ProfileViewModelFactory
import com.fullmvvmsample.baiju.ui.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PrefrenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }


}