package org.msa.booksearch.presentation.model

import org.msa.booksearch.domain.model.BookModel

/**
 * این کلاس برای مدیریت وضعیت صفحه جزئیات کتاب استفاده می‌شود.
 * @property isLoading نشان‌دهنده وضعیت بارگذاری داده‌ها (true اگر در حال بارگذاری است).
 * @property isFavorite مشخص می‌کند آیا کتاب به لیست علاقه‌مندی‌ها اضافه شده است یا خیر.
 * @property book شامل جزئیات کتاب مورد نظر است (null اگر هنوز بارگذاری نشده باشد).
 */
data class BookDetailState(
    val isLoading: Boolean = true, // نشان‌دهنده وضعیت بارگذاری اطلاعات کتاب
    val isFavorite: Boolean = false, // مشخص می‌کند آیا کتاب علاقه‌مند شده است یا خیر
    val book: BookModel? = null // اطلاعات کتاب که ممکن است هنوز بارگذاری نشده باشد
)
