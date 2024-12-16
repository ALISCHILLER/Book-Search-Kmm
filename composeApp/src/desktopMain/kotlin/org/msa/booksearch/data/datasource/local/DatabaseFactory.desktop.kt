package org.msa.booksearch.data.datasource.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * این کلاس به‌طور مشترک (expect) برای پلتفرم‌های مختلف تعریف می‌شود.
 * هدف این است که یک کارخانه برای ساخت و راه‌اندازی پایگاه داده ایجاد کنیم.
 * این کلاس برای پلتفرم‌های مختلف (مانند Windows، macOS و Linux) تنظیمات خاص خود را دارد و
 * از آن برای ایجاد و دسترسی به پایگاه داده استفاده می‌شود.
 */
actual class DatabaseFactory {

    /**
     * این متد وظیفه ایجاد یک سازنده (Builder) برای پایگاه داده را دارد.
     * هر پلتفرم خاص (مثل اندروید، macOS، Windows و غیره) پیاده‌سازی خاص خود را خواهد داشت.
     *
     * این سازنده برای ایجاد پایگاه داده در مسیری که متناسب با سیستم‌عامل است، استفاده می‌شود.
     * بسته به سیستم‌عامل، مسیر ذخیره‌سازی متفاوت خواهد بود.
     *
     * @return سازنده پایگاه داده (RoomDatabase.Builder) برای پلتفرم مورد نظر که به‌طور خودکار مسیر پایگاه داده را تنظیم می‌کند.
     */
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        // شناسایی سیستم‌عامل جاری (Windows، macOS یا سایر سیستم‌ها)
        val os = System.getProperty("os.name").lowercase()

        // دریافت مسیر خانگی کاربر برای ساخت مسیرهای مناسب در سیستم‌عامل‌های مختلف
        val userHome = System.getProperty("user.home")

        // تعیین مسیر ذخیره‌سازی بر اساس سیستم‌عامل
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Bookpedia")  // مسیر برای ویندوز
            os.contains("mac") -> File(userHome, "Library/Application Support/Bookpedia")  // مسیر برای macOS
            else -> File(userHome, ".local/share/Bookpedia")  // مسیر برای لینوکس یا سایر سیستم‌ها
        }

        // در صورتی که دایرکتوری برای ذخیره‌سازی پایگاه داده وجود ندارد، آن را ایجاد می‌کنیم
        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        // ایجاد مسیر فایل پایگاه داده با استفاده از نام پایگاه داده
        val dbFile = File(appDataDir, AppDatabase.DB_NAME)

        // بازگشت سازنده پایگاه داده برای راه‌اندازی آن
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}
