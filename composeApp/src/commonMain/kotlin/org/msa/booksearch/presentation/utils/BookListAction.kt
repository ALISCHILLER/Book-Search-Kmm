package org.msa.booksearch.presentation.utils

import org.msa.booksearch.data.datasource.local.modelEntity.BookEntity
import org.msa.booksearch.domain.model.BookModel

/**
 * اینترفیس مهروموم شده (Sealed Interface) برای مدیریت اکشن‌های مختلف لیست کتاب
 *
 * این اینترفیس تمام اقدامات ممکن را که در صفحه لیست کتاب رخ می‌دهند،
 * به صورت انواع مشخص مدیریت می‌کند.
 */
sealed interface BookListAction {

    /**
     * تغییر در کوئری جستجو
     * @param query - رشته کوئری که کاربر وارد کرده است
     */
    data class OnSearchQueryChange(val query: String) : BookListAction

    /**
     * کلیک روی یک کتاب خاص
     * @param book - شیء کتاب انتخاب شده از نوع BookModel
     */
    data class OnBookClick(val book: BookModel) : BookListAction

    /**
     * انتخاب یک تب (Tab) خاص
     * @param index - ایندکس تب انتخاب شده
     */
    data class OnTabSelected(val index: Int) : BookListAction
}
