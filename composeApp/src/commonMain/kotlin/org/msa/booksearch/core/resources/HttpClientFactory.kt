package org.msa.booksearch.core.resources

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * یک آبجکت حرفه‌ای برای ساخت و پیکربندی HttpClient با استفاده از Ktor.
 * این کلاس تمامی تنظیمات ضروری و رایج برای ارتباط با API‌ها را فراهم می‌کند.
 */
object HttpClientFactory {

    /**
     * تابع ساخت HttpClient با تنظیمات بهینه‌سازی شده برای موارد زیر:
     * - سریال‌سازی/دی‌سریال‌سازی JSON
     * - مدیریت تایم‌اوت‌ها
     * - ثبت لاگ برای دیباگ و عیب‌یابی
     * - تنظیم هدرها و درخواست‌های پیش‌فرض
     *
     * @param engine موتور شبکه (HttpClientEngine) برای اجرای درخواست‌ها
     * @return نمونه‌ای از HttpClient با پیکربندی کامل
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {

            // تنظیم سریال‌سازی JSON با نادیده گرفتن کلیدهای ناشناخته در پاسخ
            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true // پاسخ‌ها به صورت خوانا چاپ می‌شوند
                        isLenient = true // حالت انعطاف‌پذیر در پذیرش فرمت‌های JSON
                        ignoreUnknownKeys = true // نادیده گرفتن کلیدهای ناشناخته
                        coerceInputValues = true // تبدیل مقادیر نامعتبر به مقدار پیش‌فرض
                    }
                )
            }

            // مدیریت تایم‌اوت‌ها برای درخواست‌ها
            install(HttpTimeout) {
                socketTimeoutMillis = 30_000L // حداکثر زمان برای برقراری اتصال
                requestTimeoutMillis = 30_000L // حداکثر زمان برای انجام درخواست
                connectTimeoutMillis = 15_000L // حداکثر زمان برای اتصال به سرور
            }

            // تنظیمات لاگینگ برای ثبت درخواست‌ها و پاسخ‌ها
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("[HTTP LOG]: $message") // ثبت لاگ‌ها با فرمت مشخص
                    }
                }
                level = LogLevel.BODY // نمایش بدنۀ درخواست‌ها و پاسخ‌ها
            }

            // تنظیم پیش‌فرض‌های تمامی درخواست‌ها
            defaultRequest {
                contentType(ContentType.Application.Json) // نوع محتوا به صورت JSON
                headers.append("Accept", ContentType.Application.Json.toString()) // تعریف نوع داده‌های مورد پذیرش
                headers.append("User-Agent", "Bookpedia-App/1.0") // اضافه کردن User-Agent برای شناسایی درخواست
            }
        }
    }
}
