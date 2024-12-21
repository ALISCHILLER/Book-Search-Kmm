// پکیج اصلی مربوط به اپلیکیشن مدیریت کتاب
package org.msa.booksearch.app

// وارد کردن کتابخانه Serialization برای قابلیت سریال‌سازی داده‌ها
import kotlinx.serialization.Serializable

// تعریف یک اینترفیس sealed برای مدیریت مسیرهای مختلف در اپلیکیشن
sealed interface Route {

    // مسیر مربوط به گراف کتاب
    @Serializable
    data object BookGraph : Route

    // مسیر مربوط به لیست کتاب‌ها
    @Serializable
    data object BookList : Route

    // مسیر جزئیات یک کتاب مشخص که با شناسه کتاب مدیریت می‌شود
    @Serializable
    data class BookDetail(val id: String) : Route
}
