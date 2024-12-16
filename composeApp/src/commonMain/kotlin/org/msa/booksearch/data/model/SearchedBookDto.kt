package org.msa.booksearch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * مدل DTO برای کتاب‌های جستجو شده.
 * این کلاس اطلاعات کتاب‌ها را که از API جستجو دریافت می‌شود، نگهداری می‌کند.
 * هر فیلد معادل یک ویژگی از کتاب است که ممکن است در نتایج جستجو باشد.
 */
@Serializable
data class SearchedBookDto(
    /**
     * شناسه منحصربه‌فرد کتاب.
     * این شناسه معمولاً برای شناسایی کتاب‌ها در سیستم استفاده می‌شود.
     */
    @SerialName("key") val id: String,

    /**
     * عنوان کتاب.
     * این فیلد شامل نام کامل کتاب است که در نتایج جستجو نمایش داده می‌شود.
     */
    @SerialName("title") val title: String,

    /**
     * زبان‌های کتاب.
     * این فیلد شامل لیستی از زبان‌هایی است که کتاب در آن منتشر شده است.
     * این مقدار ممکن است null باشد اگر اطلاعات زبان در دسترس نباشد.
     */
    @SerialName("language") val languages: List<String>? = null,

    /**
     * کلید جایگزین جلد کتاب.
     * این فیلد شناسه‌ای است که به جلد کتاب در مخزن تصویر اشاره دارد.
     * ممکن است null باشد اگر جلد کتاب در دسترس نباشد.
     */
    @SerialName("cover_i") val coverAlternativeKey: Int? = null,

    /**
     * کلیدهای نویسنده کتاب.
     * این فیلد لیستی از شناسه‌های نویسندگان کتاب است که ممکن است شامل چندین نویسنده باشد.
     * این مقدار ممکن است null باشد اگر اطلاعات نویسنده در دسترس نباشد.
     */
    @SerialName("author_key") val authorKeys: List<String>? = null,

    /**
     * نام‌های نویسندگان کتاب.
     * این فیلد لیستی از نام‌های نویسندگان کتاب را شامل می‌شود.
     * ممکن است null باشد اگر اطلاعات نویسنده در دسترس نباشد.
     */
    @SerialName("author_name") val authorNames: List<String>? = null,

    /**
     * کلید جلد کتاب.
     * این فیلد شناسه‌ای است که به جلد کتاب مربوطه اشاره دارد.
     * ممکن است null باشد اگر جلد کتاب در دسترس نباشد.
     */
    @SerialName("cover_edition_key") val coverKey: String? = null,

    /**
     * سال انتشار اولیه کتاب.
     * این فیلد سالی را که کتاب برای اولین بار منتشر شده است، نشان می‌دهد.
     * ممکن است null باشد اگر این اطلاعات در دسترس نباشد.
     */
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,

    /**
     * میانگین رتبه‌بندی کتاب.
     * این فیلد میانگین امتیازات کاربران برای کتاب را نشان می‌دهد.
     * ممکن است null باشد اگر این اطلاعات در دسترس نباشد.
     */
    @SerialName("ratings_average") val ratingsAverage: Double? = null,

    /**
     * تعداد کل رتبه‌بندی‌ها برای کتاب.
     * این فیلد نشان‌دهنده تعداد نظرات و رتبه‌های ثبت‌شده برای کتاب است.
     * ممکن است null باشد اگر این اطلاعات در دسترس نباشد.
     */
    @SerialName("ratings_count") val ratingsCount: Int? = null,

    /**
     * تعداد صفحات کتاب.
     * این فیلد تعداد صفحات کتاب را نشان می‌دهد.
     * ممکن است null باشد اگر این اطلاعات در دسترس نباشد.
     */
    @SerialName("number_of_pages_median") val numPagesMedian: Int? = null,

    /**
     * تعداد ویرایش‌های موجود از کتاب.
     * این فیلد نشان‌دهنده تعداد ویرایش‌های مختلف کتاب است.
     * ممکن است null باشد اگر این اطلاعات در دسترس نباشد.
     */
    @SerialName("edition_count") val numEditions: Int? = null,
)
