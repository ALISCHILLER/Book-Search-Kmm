package org.msa.booksearch.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.ResultCustom
import org.msa.booksearch.core.resources.safeCall
import org.msa.booksearch.data.model.BookWorkDto
import org.msa.booksearch.data.model.SearchResponseDto

/**
 * ApiService یک پیاده‌سازی از RemoteBookDataSource است که به‌طور خاص مسئول برقراری ارتباط با API سرویس OpenLibrary برای جستجو و دریافت جزئیات کتاب‌ها است.
 * این سرویس از Ktor Client برای ارسال درخواست‌های HTTP به API استفاده می‌کند و نتایج را به‌صورت سازگار با ساختارهای داده‌ای داخلی پروژه باز می‌گرداند.
 */
private const val BASE_URL = "https://openlibrary.org"

class ApiService(
    private val httpClient: HttpClient // HttpClient برای ارسال درخواست‌های HTTP به API
): RemoteBookDataSource {

    /**
     * این متد جستجو برای کتاب‌ها را بر اساس یک عبارت جستجو انجام می‌دهد.
     * از API OpenLibrary برای جستجو در کتابخانه استفاده می‌کند.
     * می‌توانید تعداد نتایج جستجو را محدود کنید و زبان جستجو را نیز مشخص کنید.
     *
     * @param query عبارت جستجو برای پیدا کردن کتاب‌ها
     * @param resultLimit محدودیت تعداد نتایج جستجو (اختیاری)
     * @return نتیجه جستجو به‌صورت یک شی از نوع ResultCustom که شامل لیست نتایج جستجو در قالب SearchResponseDto است.
     *         در صورت بروز خطا، شی از نوع DataError.Remote برای نمایش خطای مربوطه ارسال خواهد شد.
     */
    override suspend fun searchBooks(
        query: String, // عبارت جستجو برای کتاب‌ها
        resultLimit: Int? // تعداد محدود نتایج جستجو (اختیاری)
    ): ResultCustom<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            // ارسال درخواست GET به API OpenLibrary برای جستجو در کتاب‌ها
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query) // پارامتر جستجو برای کتاب‌ها
                parameter("limit", resultLimit) // تعداد محدود نتایج
                parameter("language", "eng") // زبان جستجو (انگلیسی)
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count") // فیلدهایی که باید در نتیجه جستجو گنجانده شوند
            }
        }
    }

    /**
     * این متد جزئیات یک کتاب خاص را از API OpenLibrary بر اساس شناسه آن دریافت می‌کند.
     * از این متد برای دریافت اطلاعات کامل یک کتاب خاص استفاده می‌شود.
     *
     * @param bookWorkId شناسه منحصربه‌فرد کتاب برای دریافت جزئیات آن
     * @return نتیجه درخواست به‌صورت یک شی از نوع Result که شامل اطلاعات جزئیات کتاب در قالب BookWorkDto است.
     *         در صورت بروز خطا، شی از نوع DataError.Remote برای نمایش خطای مربوطه ارسال خواهد شد.
     */
    override suspend fun getBookDetails(bookWorkId: String): ResultCustom<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            // ارسال درخواست GET به API OpenLibrary برای دریافت جزئیات یک کتاب خاص
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }
}
