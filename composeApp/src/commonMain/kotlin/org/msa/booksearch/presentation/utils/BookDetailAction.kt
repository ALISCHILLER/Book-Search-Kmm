package org.msa.booksearch.presentation.utils

import org.msa.booksearch.domain.model.BookModel

// تعریف یک sealed interface برای مدیریت انواع مختلف اقداماتی که می‌توان در صفحه جزئیات کتاب انجام داد.
sealed interface BookDetailAction {

    // این یک داده‌ای از نوع "آبجکت" است که نشان‌دهنده اقدام برگشت (Back) از صفحه جزئیات کتاب است.
    data object OnBackClick: BookDetailAction

    // این یک داده‌ای از نوع "آبجکت" است که نشان‌دهنده اقدام "افزودن به علاقه‌مندی" (Favorite) است.
    data object OnFavoriteClick: BookDetailAction

    // این یک داده‌ای از نوع "دیتا کلاس" است که وقتی کتاب انتخاب می‌شود، یک تغییر در داده‌های کتاب را شامل می‌شود.
    // این مدل اطلاعات جدید کتاب را دریافت می‌کند و برای پردازش این تغییرات به‌کار می‌رود.
    data class OnSelectedBookChange(val book: BookModel): BookDetailAction
}
