package org.msa.booksearch.core.resources

import io.github.oshai.kotlinlogging.KotlinLogging
import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.ResultCustom
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.msa.booksearch.core.utils.logError
import org.msa.booksearch.core.utils.logger
import kotlin.coroutines.coroutineContext

// تابعی که درخواست HTTP را ایمن انجام می‌دهد و خطاهای شبکه را مدیریت می‌کند
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): ResultCustom<T, DataError.Remote> {

    // تلاش برای اجرای درخواست HTTP
    val response = try {
        execute() // اجرای درخواست HTTP
    } catch (e: SocketTimeoutException) {
        // خطا در صورتی که زمان تایم‌اوت درخواست تمام شود
        logError(e, "Socket timeout occurred") // مدیریت خطا با لاگ
        return ResultCustom.Error(DataError.Remote.REQUEST_TIMEOUT) // برگشت خطای تایم‌اوت
    } catch (e: UnresolvedAddressException) {
        // خطا در صورتی که آدرس سرور حل نشود
        logError(e, "No internet connection") // مدیریت خطا با لاگ
        return ResultCustom.Error(DataError.Remote.NO_INTERNET) // برگشت خطای عدم اتصال به اینترنت
    } catch (e: Exception) {
        // خطای عمومی برای هر گونه مشکل غیر منتظره
        logError(e, "Unknown error occurred during the HTTP request") // مدیریت خطا با لاگ
        coroutineContext.ensureActive() // اطمینان از فعال بودن کرونتین
        return ResultCustom.Error(DataError.Remote.UNKNOWN) // برگشت خطای ناشناخته
    }

    // تبدیل پاسخ HTTP به نتیجه دلخواه
    return responseToResult(response)
}



// تابعی که پاسخ HTTP را به نتیجه مناسب تبدیل می‌کند
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): ResultCustom<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            // در صورتی که کد وضعیت HTTP بین 200 تا 299 باشد (درخواست موفق)
            try {
                ResultCustom.Success(response.body<T>()) // تبدیل بدنۀ پاسخ به مدل مورد نظر
            } catch (e: NoTransformationFoundException) {
                // خطا در صورتی که نتوان پاسخ را به مدل دلخواه تبدیل کرد
                logError(e, "Serialization error occurred") // ثبت خطا در لاگ
                ResultCustom.Error(DataError.Remote.SERIALIZATION) // برگشت خطای سریال‌سازی
            }
        }
        408 -> {
            // خطا در صورتی که کد وضعیت 408 (تایم‌اوت درخواست) باشد
            logError("Request timeout (408)") // ثبت خطا در لاگ
            ResultCustom.Error(DataError.Remote.REQUEST_TIMEOUT) // برگشت خطای تایم‌اوت
        }
        429 -> {
            // خطا در صورتی که کد وضعیت 429 (تعداد بیش از حد درخواست‌ها) باشد
            logError("Too many requests (429)") // ثبت خطا در لاگ
            ResultCustom.Error(DataError.Remote.TOO_MANY_REQUESTS) // برگشت خطای بیش از حد درخواست
        }
        in 500..599 -> {
            // خطا در صورتی که کد وضعیت HTTP بین 500 تا 599 باشد (خطای سرور)
            logError("Server error (5xx)") // ثبت خطا در لاگ
            ResultCustom.Error(DataError.Remote.SERVER) // برگشت خطای سرور
        }
        else -> {
            // خطا برای وضعیت‌های HTTP ناشناخته
            logError("Unknown error with status: ${response.status.value}") // ثبت خطا در لاگ
            ResultCustom.Error(DataError.Remote.UNKNOWN) // برگشت خطای ناشناخته
        }
    }
}
