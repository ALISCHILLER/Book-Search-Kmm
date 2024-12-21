package org.msa.booksearch

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.msa.booksearch.di.initKoin

class BookApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}