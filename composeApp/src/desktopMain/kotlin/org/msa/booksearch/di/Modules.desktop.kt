package org.msa.booksearch.di

import org.koin.core.module.Module
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module
import org.msa.booksearch.data.datasource.local.DatabaseFactory

// این ماژول برای پلتفرم خاص (در اینجا اندروید) استفاده می‌شود تا وابستگی‌ها را در سیستم تزریق کند.
actual val platformModule: Module
    get() = module {
        // ایجاد و تزریق وابستگی HttpClientEngine با استفاده از موتور OkHttp
        // OkHttp یک موتور HTTP قدرتمند است که برای ارسال درخواست‌های HTTP استفاده می‌شود.
        // این وابستگی برای استفاده در پروژه‌های شبکه‌ای (مثل ارسال درخواست‌های API) ضروری است.
        single<HttpClientEngine> { OkHttp.create() }

        // ایجاد و تزریق DatabaseFactory که مسئول مدیریت دیتابیس محلی است
        // این کلاس به شما کمک می‌کند تا دیتابیس محلی را برای ذخیره‌سازی داده‌ها ایجاد و مدیریت کنید.
        single { DatabaseFactory() }
    }
