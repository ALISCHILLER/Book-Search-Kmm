package org.msa.booksearch.core.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * این کلاس برای مدیریت متن‌های قابل نمایش در رابط کاربری طراحی شده است.
 * هدف اصلی، ارائه متدی است که بتواند بین متن‌های داینامیک (ثابت یا تغییرپذیر) و منابع متنی (مانند فایل‌های رشته‌ای ترجمه‌شده) تمایز ایجاد کند.
 */
sealed interface UiText {

    /**
     * نمایش یک رشته داینامیک (متن ساده که مستقیماً به صورت مقدار رشته‌ای تعریف می‌شود).
     *
     * @property value مقدار رشته‌ای که باید نمایش داده شود.
     */
    data class DynamicString(val value: String) : UiText

    /**
     * نمایش یک رشته با استفاده از منابع متنی برنامه (مانند `strings.xml` یا منابع قابل ترجمه).
     *
     * @property id شناسه منبع متنی که باید بازیابی شود.
     * @property args آرگومان‌های قالب‌بندی که در متن منبع استفاده می‌شوند.
     */
    class StringResourceId(
        val id: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText

    /**
     * تبدیل نوع `UiText` به رشته‌ای قابل نمایش برای رابط کاربری.
     * این تابع کامپوزبل است و به صورت بلادرنگ رشته مورد نظر را از نوع مناسب (داینامیک یا منبع متنی) استخراج می‌کند.
     *
     * @return رشته‌ای که می‌توان آن را در رابط کاربری نمایش داد.
     */
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value // بازگشت مقدار مستقیم در صورت داینامیک بودن متن
            is StringResourceId -> stringResource(resource = id, formatArgs = args) // بازیابی متن از منابع
        }
    }
}
