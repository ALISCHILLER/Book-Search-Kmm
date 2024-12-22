package org.msa.booksearch.presentation.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * یک کامپوننت ترکیبی که عنوان و محتوای دلخواه را در یک ساختار عمودی نمایش می‌دهد.
 *
 * @param title عنوانی که در بالای محتوا نمایش داده می‌شود.
 * @param modifier اصلاح‌کننده‌ای برای اعمال تغییرات ظاهری یا ابعادی اضافی.
 * @param content محتوای ترکیبی (Composable) که در زیر عنوان نمایش داده می‌شود.
 */
@Composable
fun TitledContent(
    title: String, // عنوانی که نمایش داده می‌شود
    modifier: Modifier = Modifier, // اصلاح‌کننده پیش‌فرض
    content: @Composable () -> Unit // محتوایی که توسط کاربر تعریف می‌شود
) {
    // یک ستون که عناصر را به صورت عمودی مرتب می‌کند
    Column(
        modifier = modifier, // استفاده از اصلاح‌کننده برای تغییرات ظاهری
        horizontalAlignment = Alignment.CenterHorizontally // تنظیم ترازبندی افقی به صورت مرکزی
    ) {
        // نمایش عنوان
        Text(
            text = title, // متن عنوان
            style = MaterialTheme.typography.titleSmall // استفاده از استایل مشخص شده در تم
        )
        // نمایش محتوای دلخواه که به صورت ترکیبی ارسال شده است
        content()
    }
}
