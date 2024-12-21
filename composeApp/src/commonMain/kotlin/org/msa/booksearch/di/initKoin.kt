package org.msa.booksearch.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * این تابع برای راه‌اندازی Koin و بارگذاری ماژول‌ها استفاده می‌شود.
 * از آنجا که Koin وابستگی‌ها را به صورت مرکزی مدیریت می‌کند، این تابع باید در ابتدای اپلیکیشن فراخوانی شود.
 *
 * @param config تنظیمات اختیاری برای پیکربندی Koin.
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        // اعمال تنظیمات سفارشی اگر موجود باشد.
        config?.invoke(this)

        // بارگذاری ماژول‌های مشترک و پلتفرمی
        modules(sharedModule, platformModule)
    }
}
