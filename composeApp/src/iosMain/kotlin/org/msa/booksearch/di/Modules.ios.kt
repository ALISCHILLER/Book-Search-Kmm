package org.msa.booksearch.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin // استفاده از موتور Darwin برای ارتباطات شبکه‌ای در iOS
import org.koin.core.module.Module
import org.koin.dsl.module
import org.msa.booksearch.data.datasource.local.DatabaseFactory

// این ماژول مخصوص پلتفرم iOS است و وابستگی‌های خاص iOS را تزریق می‌کند.
actual val platformModule: Module
    get() = module {
        // ایجاد و تزریق وابستگی HttpClientEngine با استفاده از موتور Darwin برای iOS
        // موتور Darwin مخصوص ارتباطات شبکه‌ای در پلتفرم iOS است. از آن برای ارسال درخواست‌های HTTP استفاده می‌شود.
        single<HttpClientEngine> { Darwin.create() }

        // ایجاد و تزریق DatabaseFactory برای مدیریت دیتابیس محلی در پلتفرم iOS
        // این کلاس مسئول ارتباط با دیتابیس محلی (مثلاً SQLite) و دسترسی به داده‌ها است.
        single { DatabaseFactory() }
    }
