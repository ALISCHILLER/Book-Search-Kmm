package org.msa.booksearch.data.datasource.remote

import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.ResultCustom
import org.msa.booksearch.data.model.BookWorkDto
import org.msa.booksearch.data.model.SearchResponseDto

/**
 * RemoteBookDataSource یک واسط است که به‌طور خاص برای تعامل با منابع داده‌ای از راه دور (مثل API) طراحی شده است.
 * این کلاس مسئول برقراری ارتباط با منابع داده‌ای خارجی است و اطلاعات کتاب‌ها را از طریق متدهای مختلف دریافت می‌کند.
 */
interface RemoteBookDataSource {

    /**
     * این متد جستجوی کتاب‌ها را انجام می‌دهد.
     * بر اساس جستجوی کاربر (query)، کتاب‌هایی که با این عبارت جستجو همخوانی دارند را بازمی‌گرداند.
     * همچنین می‌توان تعداد نتایج محدود (resultLimit) را تعیین کرد.
     *
     * @param query عبارت جستجو برای پیدا کردن کتاب‌ها
     * @param resultLimit محدودیت تعداد نتایج (اختیاری)
     * @return نتیجه جستجو به‌صورت یک شی از نوع ResultCustom که شامل لیست نتایج جستجو در قالب SearchResponseDto است.
     *         در صورت بروز خطا، شی از نوع DataError.Remote برای نمایش خطای مربوطه ارسال خواهد شد.
     */
    suspend fun searchBooks(
        query: String, // عبارت جستجو برای کتاب‌ها
        resultLimit: Int? = null // تعداد محدود نتایج جستجو (اختیاری)
    ): ResultCustom<SearchResponseDto, DataError.Remote>

    /**
     * این متد جزئیات یک کتاب خاص را بر اساس شناسه کتاب (bookWorkId) دریافت می‌کند.
     * اطلاعات مربوط به کتاب مانند عنوان، نویسندگان، رتبه‌بندی و غیره را از منابع داده‌ای خارجی بازیابی می‌کند.
     *
     * @param bookWorkId شناسه منحصربه‌فرد کتاب برای دریافت جزئیات آن
     * @return نتیجه درخواست به‌صورت یک شی از نوع ResultCustom که شامل اطلاعات جزئیات کتاب در قالب BookWorkDto است.
     *         در صورت بروز خطا، شی از نوع DataError.Remote برای نمایش خطای مربوطه ارسال خواهد شد.
     */
    suspend fun getBookDetails(bookWorkId: String): ResultCustom<BookWorkDto, DataError.Remote>
}
