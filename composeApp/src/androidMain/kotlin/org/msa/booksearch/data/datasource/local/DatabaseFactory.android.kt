package org.msa.booksearch.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.msa.booksearch.data.datasource.local.AppDatabase

/**
 * این کلاس به‌طور مشترک (expect) برای پلتفرم‌های مختلف تعریف می‌شود.
 * هدف این است که یک کارخانه برای ساخت و راه‌اندازی پایگاه داده ایجاد کنیم.
 */
actual class DatabaseFactory(
    private val context: Context
) {

    /**
     * این متد برای ایجاد یک سازنده (Builder) از پایگاه داده استفاده می‌شود.
     * این سازنده باید برای ایجاد پایگاه داده از نوع Room در پلتفرم‌های مختلف استفاده شود.
     *
     * @return سازنده پایگاه داده (RoomDatabase.Builder) برای پلتفرم مورد نظر.
     */
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        // گرفتن context اپلیکیشن
        val appContext = context.applicationContext

        // تعیین مسیر ذخیره‌سازی پایگاه داده در دستگاه
        val dbFile = appContext.getDatabasePath(AppDatabase.DB_NAME)

        // ایجاد سازنده پایگاه داده با استفاده از Room
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            dbFile.absolutePath // استفاده از نام فایل به‌جای مسیر مستقیم
        ).fallbackToDestructiveMigration() // برای بازسازی پایگاه داده در صورت تغییرات نسخه
    }
}
