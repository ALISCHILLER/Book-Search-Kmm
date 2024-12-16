package org.msa.booksearch.core.utils

import booksearchkmm.composeapp.generated.resources.Res
import booksearchkmm.composeapp.generated.resources.*
import org.msa.booksearch.core.resources.DataError

/**
 * این تابع خطاهای موجود در لایه داده (`DataError`) را به متن‌های قابل نمایش در رابط کاربری (`UiText`) تبدیل می‌کند.
 * این تبدیل به توسعه‌دهنده کمک می‌کند تا بدون نیاز به مدیریت مستقیم پیام‌ها در رابط کاربری، خطاها را به صورت استاندارد نمایش دهد.
 */
fun DataError.toUiText(): UiText {
    // انتخاب رشته مناسب برای نمایش بر اساس نوع خطا
    val stringRes = when (this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full // خطای پر شدن فضای دیسک
        DataError.Local.UNKNOWN -> Res.string.error_unknown // خطای ناشناخته محلی
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout // خطای تایم‌اوت درخواست
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests // خطای تعداد زیاد درخواست‌ها
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet // خطای عدم دسترسی به اینترنت
        DataError.Remote.SERVER -> Res.string.error_unknown // خطای ناشناخته سمت سرور
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization // خطای سریال‌سازی داده‌ها
        DataError.Remote.UNKNOWN -> Res.string.error_unknown // خطای ناشناخته عمومی
        else -> Res.string.error_unknown // حالت پیش‌فرض برای خطاهای پیش‌بینی‌نشده
    }

    // بازگشت رشته به صورت منبع متنی رابط کاربری
    return UiText.StringResourceId(stringRes)
}
