package org.msa.booksearch.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import org.msa.booksearch.data.datasource.local.DatabaseFactory

// این ماژول برای پلتفرم خاص اندروید است که وابستگی‌ها را به سیستم تزریق می‌کند.
actual val platformModule: Module
    get() = module {
        // ایجاد یک نمونه از HttpClientEngine با استفاده از موتور OkHttp
        // OkHttp به عنوان یک موتور HTTP برای Ktor استفاده می‌شود که در اینجا برای ارتباطات شبکه‌ای استفاده می‌شود
        single<HttpClientEngine> { OkHttp.create() }

        // ایجاد نمونه‌ای از DatabaseFactory که به دیتابیس محلی مربوط به اپلیکیشن متصل می‌شود
        // این کد از androidApplication() برای دسترسی به اپلیکیشن اندروید استفاده می‌کند
        single { DatabaseFactory(androidApplication()) }
    }
