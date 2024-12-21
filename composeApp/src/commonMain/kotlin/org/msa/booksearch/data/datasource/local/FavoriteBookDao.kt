package org.msa.booksearch.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.msa.booksearch.data.datasource.local.modelEntity.BookEntity

/**
 * این اینترفیس برای تعامل با پایگاه داده محلی کتاب‌ها (Room) طراحی شده است.
 * وظیفه این کلاس انجام عملیات CRUD بر روی کتاب‌های مورد علاقه در پایگاه داده است.
 */
@Dao
interface FavoriteBookDao {

    /**
     * این متد برای درج یا بروزرسانی اطلاعات یک کتاب در پایگاه داده استفاده می‌شود.
     * اگر کتاب از قبل در پایگاه داده وجود داشته باشد، بروزرسانی می‌شود.
     * در غیر این صورت، کتاب جدید به پایگاه داده افزوده می‌شود.
     *
     * @param book کتابی که باید در پایگاه داده ذخیره یا بروزرسانی شود.
     */
    @Upsert
    suspend fun upsert(book: BookEntity)

    /**
     * این متد تمام کتاب‌های مورد علاقه موجود در پایگاه داده را بازیابی می‌کند.
     * داده‌ها در قالب یک لیست از `BookEntity` به صورت Flow باز می‌گردند.
     *
     * @return لیستی از کتاب‌ها که در پایگاه داده ذخیره شده‌اند.
     */
    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    /**
     * این متد یک کتاب خاص را بر اساس شناسه (id) آن از پایگاه داده بازیابی می‌کند.
     * در صورتی که کتاب مورد نظر پیدا شود، اطلاعات آن باز می‌گردد، در غیر این صورت `null` برمی‌گردد.
     *
     * @param id شناسه کتابی که می‌خواهیم اطلاعات آن را بازیابی کنیم.
     * @return کتاب مورد نظر یا `null` در صورتی که کتاب پیدا نشود.
     */
    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    /**
     * این متد کتاب مورد نظر را بر اساس شناسه (id) از پایگاه داده حذف می‌کند.
     * پس از فراخوانی این متد، کتاب مورد نظر دیگر در پایگاه داده موجود نخواهد بود.
     *
     * @param id شناسه کتابی که باید از پایگاه داده حذف شود.
     */
    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)
}
