package org.msa.booksearch.presentation.model

import org.msa.booksearch.core.utils.UiText
import org.msa.booksearch.domain.model.BookModel

/**
 * مدل نمایشی برای نمایش وضعیت صفحه لیست کتاب
 *
 * این کلاس برای مدیریت وضعیت (State) صفحه لیست کتاب در معماری MVVM طراحی شده است.
 * شامل اطلاعاتی مانند کوئری جستجو، لیست نتایج، لیست کتاب‌های مورد علاقه و وضعیت بارگذاری می‌باشد.
 */
data class BookListState(
    /**
     * کوئری جستجوی فعلی وارد شده توسط کاربر
     * مقدار پیش‌فرض: "Kotlin"
     */
    val searchQuery: String = "Kotlin",

    /**
     * نتایج جستجوی برگشتی از دیتابیس یا API
     * مقدار پیش‌فرض: لیست خالی
     */
    val searchResults: List<BookModel> = emptyList(),

    /**
     * لیست کتاب‌های مورد علاقه کاربر
     * مقدار پیش‌فرض: لیست خالی
     */
    val favoriteBooks: List<BookModel> = emptyList(),

    /**
     * نشان‌دهنده وضعیت بارگذاری
     * مقدار پیش‌فرض: true
     */
    val isLoading: Boolean = true,

    /**
     * ایندکس تب انتخاب‌شده
     * مقدار پیش‌فرض: 0
     */
    val selectedTabIndex: Int = 0,

    /**
     * پیام خطا برای نمایش به کاربر (در صورت وجود)
     * از نوع UiText برای پشتیبانی چند زبانی
     * مقدار پیش‌فرض: null
     */
    val errorMessage: UiText? = null
)
