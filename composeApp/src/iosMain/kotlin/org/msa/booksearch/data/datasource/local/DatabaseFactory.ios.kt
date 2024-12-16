package org.msa.booksearch.data.datasource.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * این کلاس به‌طور مشترک (expect) برای پلتفرم‌های مختلف (مثل iOS) تعریف می‌شود.
 * هدف این است که یک کارخانه برای ساخت و راه‌اندازی پایگاه داده ایجاد کنیم.
 * این کلاس به‌طور خاص برای پلتفرم iOS طراحی شده است و مسیر ذخیره‌سازی پایگاه داده را
 * در دایرکتوری مستندات (Documents) دستگاه iOS انتخاب می‌کند.
 */
actual class DatabaseFactory {

    /**
     * این متد برای ایجاد یک سازنده (Builder) از پایگاه داده استفاده می‌شود.
     * این سازنده برای پلتفرم‌های مختلف تنظیمات خاصی دارد و برای ایجاد پایگاه داده
     * در مسیر مناسب به کار می‌رود.
     *
     * در اینجا برای iOS، پایگاه داده در دایرکتوری مستندات دستگاه ذخیره می‌شود.
     *
     * @return RoomDatabase.Builder<AppDatabase> سازنده پایگاه داده که می‌تواند برای ساخت پایگاه داده استفاده شود.
     */
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        // ایجاد مسیر کامل فایل پایگاه داده در دایرکتوری مستندات
        val dbFile = documentDirectory() + "/${AppDatabase.DB_NAME}"

        // بازگشت سازنده پایگاه داده که از مسیر فایل پایگاه داده استفاده می‌کند
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        )
    }

    /**
     * این متد مسیر دایرکتوری مستندات (Documents) دستگاه را برمی‌گرداند.
     * این مسیر برای ذخیره‌سازی داده‌ها در iOS به کار می‌رود.
     *
     * @return مسیر دایرکتوری مستندات به‌صورت رشته (String).
     */
    private fun documentDirectory(): String {
        // دسترسی به مسیر دایرکتوری مستندات با استفاده از NSFileManager در iOS
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        // بازگشت مسیر دایرکتوری مستندات به‌صورت رشته
        return requireNotNull(documentDirectory?.path)
    }

}
