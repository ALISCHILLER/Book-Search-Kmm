package org.msa.booksearch.core.resources

/**
 * یک اینترفیس کلی برای مدیریت نتیجه (Result) عملیات‌ها.
 * - D: نوع داده‌ای که در صورت موفقیت برگردانده می‌شود.
 * - E: نوع خطا که باید از نوع Error باشد.
 */
sealed interface ResultCustom<out D, out E : Error> {

    /**
     * کلاس برای نمایش حالت موفقیت.
     * @param data داده‌ای که در صورت موفقیت بازمی‌گردد.
     */
    data class Success<out D>(val data: D) : ResultCustom<D, Nothing>

    /**
     * کلاس برای نمایش حالت خطا.
     * @param error خطای رخ داده که باید از نوع Error باشد.
     */
    data class Error<out E : org.msa.booksearch.core.resources.Error>(val error: E) :
        ResultCustom<Nothing, E>
}

/**
 * یک تابع اکستنشن برای نگاشت (Map) داده‌های نوع Success به نوع جدید.
 * @param map تابعی که داده‌های نوع Success را نگاشت می‌کند.
 * @return نتیجه جدید با نوع داده تغییر یافته.
 */
inline fun <T, E : Error, R> ResultCustom<T, E>.map(map: (T) -> R): ResultCustom<R, E> {
    return when (this) {
        is ResultCustom.Error -> ResultCustom.Error(error) // اگر نتیجه خطا باشد، بدون تغییر برمی‌گردد.
        is ResultCustom.Success -> ResultCustom.Success(map(data)) // اگر موفق باشد، داده را نگاشت می‌کند.
    }
}

/**
 * تبدیل یک نتیجه به نتیجه‌ای با داده خالی (Unit).
 * این تابع برای زمانی که به داده نیازی نیست استفاده می‌شود.
 */
fun <T, E : Error> ResultCustom<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { } // داده به Unit تبدیل می‌شود.
}

/**
 * اجرای یک اکشن خاص در صورت موفقیت.
 * @param action عملی که در صورت موفقیت روی داده اجرا می‌شود.
 * @return همان نتیجه اصلی بدون تغییر.
 */
inline fun <T, E : Error> ResultCustom<T, E>.onSuccess(action: (T) -> Unit): ResultCustom<T, E> {
    return when (this) {
        is ResultCustom.Error -> this // اگر خطا باشد، همان نتیجه بازمی‌گردد.
        is ResultCustom.Success -> {
            action(data) // اکشن روی داده اجرا می‌شود.
            this
        }
    }
}

/**
 * اجرای یک اکشن خاص در صورت وقوع خطا.
 * @param action عملی که در صورت وقوع خطا اجرا می‌شود.
 * @return همان نتیجه اصلی بدون تغییر.
 */
inline fun <T, E : Error> ResultCustom<T, E>.onError(action: (E) -> Unit): ResultCustom<T, E> {
    return when (this) {
        is ResultCustom.Error -> {
            action(error) // اکشن روی خطا اجرا می‌شود.
            this
        }
        is ResultCustom.Success -> this // اگر موفق باشد، بدون تغییر بازمی‌گردد.
    }
}

/**
 * یک نوع نتیجه خالی (EmptyResult) که برای مواقعی استفاده می‌شود که نیازی به داده نیست.
 * - E: نوع خطا که باید از نوع Error باشد.
 */
typealias EmptyResult<E> = ResultCustom<Unit, E>
