package org.msa.booksearch.data.datasource.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * این کلاس مسئول تبدیل داده‌های نوع لیست رشته‌ای (`List<String>`) به رشته JSON و بالعکس است.
 * این تبدیل‌ها برای ذخیره‌سازی مجموعه‌های داده‌ای لیست‌وار در پایگاه داده Room به صورت رشته‌های JSON استفاده می‌شوند.
 * استفاده از `TypeConverter` به این معنی است که Room می‌تواند به‌طور خودکار داده‌ها را به فرمت مناسب تبدیل و از آن ذخیره‌سازی و بازیابی کند.
 *
 * این نوع تبدیل برای داده‌هایی مناسب است که نمی‌توانند به‌طور مستقیم در دیتابیس ذخیره شوند،
 * مانند لیست‌های شیء در پایگاه داده‌های رابطه‌ای.
 * در اینجا از `kotlinx.serialization` برای تبدیل داده‌های پیچیده به فرمت JSON استفاده می‌کنیم.
 */
object StringListTypeConverter {

    /**
     * این متد مسئول دسرسیال‌سازی (Deserialization) یک رشته JSON به لیست از رشته‌ها (`List<String>`) است.
     * زمانی که داده‌های JSON از پایگاه داده بارگذاری می‌شوند، این متد به Room کمک می‌کند تا داده‌های ذخیره‌شده در قالب JSON را به فرمت موردنظر تبدیل کند.
     *
     * @param value یک رشته JSON که حاوی داده‌های لیست از رشته‌ها است.
     * @return لیستی از رشته‌ها که از داده‌های JSON استخراج شده‌اند.
     *
     * @throws kotlinx.serialization.SerializationException اگر فرمت داده‌های JSON با ساختار موردانتظار سازگار نباشد، این خطا رخ می‌دهد.
     */
    @TypeConverter
    fun fromString(value: String): List<String> {
        // از متد `decodeFromString` برای تبدیل داده‌های JSON به لیست از رشته‌ها استفاده می‌شود.
        // در اینجا از `kotlinx.serialization` به‌طور مستقیم برای دسرسیال‌سازی استفاده شده است.
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            // در صورت بروز هرگونه خطا در دسرسیال‌سازی، یک استثنا پرتاب می‌شود
            throw kotlinx.serialization.SerializationException("خطا در دسرسیال‌سازی رشته به لیست: $value", e)
        }
    }

    /**
     * این متد مسئول سریال‌سازی (Serialization) یک لیست از رشته‌ها (`List<String>`) به یک رشته JSON است.
     * زمانی که داده‌ها باید به پایگاه داده Room ارسال شوند، این متد از آن استفاده می‌شود تا داده‌های لیست را به فرمت قابل‌ذخیره‌سازی تبدیل کند.
     *
     * @param list لیستی از رشته‌ها که باید به فرمت JSON تبدیل شود.
     * @return یک رشته JSON که نمایانگر داده‌های لیست است.
     *
     * @throws kotlinx.serialization.SerializationException اگر تبدیل لیست به JSON با مشکلی مواجه شود.
     */
    @TypeConverter
    fun fromList(list: List<String>): String {
        // از `encodeToString` برای سریال‌سازی لیست رشته‌ها به رشته JSON استفاده می‌شود.
        // این متد `kotlinx.serialization` به‌طور خودکار لیست ورودی را به فرمت JSON تبدیل می‌کند.
        return try {
            Json.encodeToString(list)
        } catch (e: Exception) {
            // در صورت بروز خطا در سریال‌سازی، یک استثنا پرتاب می‌شود.
            throw kotlinx.serialization.SerializationException("خطا در سریال‌سازی لیست به رشته JSON: $list", e)
        }
    }
}
