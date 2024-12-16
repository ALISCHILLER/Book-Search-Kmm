package org.msa.booksearch.data.datasource.local

import androidx.room.RoomDatabase
import org.msa.booksearch.data.datasource.local.AppDatabase

/**
 * این کلاس به‌طور مشترک (expect) برای پلتفرم‌های مختلف تعریف می‌شود.
 * هدف این است که یک کارخانه برای ساخت و راه‌اندازی پایگاه داده ایجاد کنیم.
 */
expect class DatabaseFactory {

    /**
     * این متد برای ایجاد یک سازنده (Builder) از پایگاه داده استفاده می‌شود.
     * هر پلتفرم خاص (مثل اندروید یا iOS) پیاده‌سازی خاص خود را خواهد داشت.
     *
     * @return سازنده پایگاه داده (RoomDatabase.Builder) برای پلتفرم مورد نظر.
     */
    fun create(): RoomDatabase.Builder<AppDatabase>
}
