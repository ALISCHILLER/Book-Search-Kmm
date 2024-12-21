package org.msa.booksearch.domain.model

/**
 * مدل داده‌ای کتاب که شامل اطلاعات مختلفی در مورد هر کتاب می‌باشد.
 * این کلاس برای نمایش اطلاعات کتاب‌ها در برنامه استفاده می‌شود.
 *
 * @property id شناسه منحصربه‌فرد کتاب
 * @property title عنوان کتاب
 * @property imageUrl URL تصویر کتاب
 * @property authors لیستی از نویسندگان کتاب
 * @property description توضیحات مربوط به کتاب (اختیاری)
 * @property languages لیستی از زبان‌هایی که کتاب به آن‌ها ترجمه شده است
 * @property firstPublishYear سال انتشار اولیه کتاب (اختیاری)
 * @property averageRating میانگین امتیاز کتاب از نظر کاربران (اختیاری)
 * @property ratingCount تعداد کل امتیازهایی که کتاب دریافت کرده است (اختیاری)
 * @property numPages تعداد صفحات کتاب (اختیاری)
 * @property numEditions تعداد ویرایش‌های مختلف کتاب
 */
data class BookModel(
    val id: String, // شناسه منحصربه‌فرد کتاب
    val title: String, // عنوان کتاب
    val imageUrl: String, // URL تصویر کتاب
    val authors: List<String>, // لیستی از نویسندگان کتاب
    val description: String?, // توضیحات کتاب (اختیاری)
    val languages: List<String>, // لیستی از زبان‌هایی که کتاب به آن‌ها ترجمه شده است
    val firstPublishYear: String?, // سال انتشار اولیه کتاب (اختیاری)
    val averageRating: Double?, // میانگین امتیاز کتاب (اختیاری)
    val ratingCount: Int?, // تعداد کل امتیازهای دریافتی کتاب (اختیاری)
    val numPages: Int?, // تعداد صفحات کتاب (اختیاری)
    val numEditions: Int // تعداد ویرایش‌های مختلف کتاب
)
