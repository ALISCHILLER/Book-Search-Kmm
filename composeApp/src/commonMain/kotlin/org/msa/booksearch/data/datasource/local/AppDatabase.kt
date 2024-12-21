package org.msa.booksearch.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.msa.booksearch.data.datasource.local.modelEntity.BookEntity

/**
 * این کلاس نمایانگر پایگاه داده اپلیکیشن است که توسط Room ایجاد و مدیریت می‌شود.
 * در اینجا از Room برای مدیریت ارتباط با پایگاه داده و ذخیره‌سازی داده‌ها استفاده شده است.
 *
 * این کلاس مسئول تنظیمات پایگاه داده، لیست موجودیت‌ها (entities)، نسخه پایگاه داده و TypeConverter‌ها است.
 * در اینجا تنها یک موجودیت (Entity) به نام `BookEntity` برای ذخیره‌سازی اطلاعات کتاب در پایگاه داده داریم.
 *
 * @see [BookEntity] موجودیتی است که اطلاعات کتاب‌ها را ذخیره می‌کند.
 */
@Database(
    entities = [BookEntity::class], // جداول موجودیت‌های مرتبط با دیتابیس (در اینجا BookEntity)
    version = 1 // نسخه اول دیتابیس. این نسخه باید با هر تغییر در ساختار دیتابیس افزایش یابد.
)
@TypeConverters(
    StringListTypeConverter::class // از TypeConverter برای تبدیل داده‌های خاص استفاده می‌کنیم. در اینجا لیست رشته‌ها به JSON و بالعکس تبدیل می‌شود.
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * متد دسترسی به DAO مورد نظر برای عملیات‌های مربوط به جدول `BookEntity`
     * این DAO مسئول عملیات‌هایی مانند اضافه‌کردن، حذف، و دریافت کتاب‌های محبوب از پایگاه داده است.
     *
     * @return DAO که به عملیات‌های پایگاه داده در جدول BookEntity دسترسی می‌دهد.
     */
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        /**
         * نام پایگاه داده. این نام برای شناسایی و ذخیره پایگاه داده در دستگاه کاربر استفاده می‌شود.
         */
        const val DB_NAME = "book.db"
    }
}
