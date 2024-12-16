package org.msa.booksearch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * مدل DTO برای پاسخ جستجو در کتابخانه.
 * این کلاس اطلاعات جستجوی کتاب‌ها را از API دریافت می‌کند.
 *
 * فیلد "results" شامل لیستی از کتاب‌هایی است که در نتایج جستجو یافت شده‌اند.
 */
@Serializable
data class SearchResponseDto(
    /**
     * لیست نتایج جستجو که شامل مجموعه‌ای از کتاب‌های جستجو شده است.
     * این لیست از شیءهای `SearchedBookDto` ساخته شده است.
     */
    @SerialName("docs") val results: List<SearchedBookDto>
)
