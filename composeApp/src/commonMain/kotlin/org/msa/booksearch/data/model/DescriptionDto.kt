package org.msa.booksearch.data.model

import kotlinx.serialization.Serializable

/**
 * مدل DTO برای توصیف شرح یک کتاب.
 * این کلاس تنها یک فیلد "value" را که شامل شرح کتاب است نگهداری می‌کند.
 */
@Serializable
data class DescriptionDto(
    /**
     * توصیف شرح کتاب. این فیلد یک رشته (String) است که می‌تواند شرح کتاب را در خود داشته باشد.
     * این فیلد به‌طور مستقیم از پاسخ JSON دریافت می‌شود.
     */
    val value: String
)
