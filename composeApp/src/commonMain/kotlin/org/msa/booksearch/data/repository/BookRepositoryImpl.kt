package org.msa.booksearch.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.EmptyResult
import org.msa.booksearch.core.resources.ResultCustom
import org.msa.booksearch.core.resources.map
import org.msa.booksearch.data.datasource.local.FavoriteBookDao
import org.msa.booksearch.data.datasource.mapper.toBook
import org.msa.booksearch.data.datasource.mapper.toBookEntity
import org.msa.booksearch.domain.model.BookModel
import org.msa.booksearch.domain.repository.BookRepository
import org.msa.booksearch.data.datasource.remote.RemoteBookDataSource

/**
 * کلاس پیاده‌سازی شده BookRepository برای مدیریت عملیات مربوط به کتاب‌ها.
 * این کلاس تعامل بین منابع داده محلی و ریموت را فراهم می‌کند.
 *
 * @param remoteBookDataSource منبع داده‌ای برای ارتباط با سرویس‌های ریموت
 * @param favoriteBookDao منبع داده‌ای برای مدیریت کتاب‌های موردعلاقه
 */
class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {

    /**
     * جستجوی کتاب‌ها بر اساس یک کوئری
     *
     * @param query عبارت جستجو
     * @return لیست کتاب‌ها به همراه خطاهای احتمالی
     */
    override suspend fun searchBooks(query: String): ResultCustom<List<BookModel>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                // تبدیل نتایج دریافتی از DTO به مدل کتاب
                dto.results.map { it.toBook() }
            }
    }

    /**
     * دریافت توضیحات یک کتاب خاص
     *
     * @param bookId شناسه کتاب
     * @return توضیحات کتاب یا خطای مرتبط
     */
    override suspend fun getBookDescription(bookId: String): ResultCustom<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if (localResult == null) {
            // در صورتی که در دیتابیس محلی موجود نباشد، از منبع ریموت واکشی می‌شود
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            // بازگشت توضیحات از دیتابیس محلی
            ResultCustom.Success(localResult.description)
        }
    }

    /**
     * دریافت لیست کتاب‌های موردعلاقه از دیتابیس محلی
     *
     * @return جریان (Flow) شامل لیست کتاب‌ها
     */
    override fun getFavoriteBooks(): Flow<List<BookModel>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                // تبدیل موجودیت‌های دیتابیس به مدل کتاب
                bookEntities.map { it.toBook() }
            }
    }

    /**
     * بررسی اینکه آیا یک کتاب در لیست علاقه‌مندی‌ها وجود دارد یا خیر
     *
     * @param id شناسه کتاب
     * @return جریان (Flow) شامل مقدار بولی برای وضعیت علاقه‌مندی
     */
    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                // بررسی وجود کتاب در لیست موردعلاقه‌ها
                bookEntities.any { it.id == id }
            }
    }

    /**
     * اضافه کردن یک کتاب به لیست موردعلاقه‌ها
     *
     * @param book مدل کتاب
     * @return نتیجه عملیات به همراه خطای احتمالی
     */
    override suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local> {
        return try {
            // افزودن یا بروزرسانی کتاب در دیتابیس محلی
            favoriteBookDao.upsert(book.toBookEntity())
            ResultCustom.Success(Unit)
        } catch (e: SQLiteException) {
            // مدیریت خطا در صورت پر بودن دیسک
            ResultCustom.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * حذف یک کتاب از لیست موردعلاقه‌ها
     *
     * @param id شناسه کتاب
     */
    override suspend fun deleteFromFavorites(id: String) {
        // حذف کتاب از دیتابیس محلی
        favoriteBookDao.deleteFavoriteBook(id)
    }
}
