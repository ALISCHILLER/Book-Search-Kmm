package org.msa.booksearch.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.msa.booksearch.core.uiKit.LightBlue

// تعریف Enum برای سایز Chip
enum class ChipSize {
    SMALL, REGULAR // اندازه‌های مختلف چپ: کوچک (SMALL) و معمولی (REGULAR)
}

/**
 * یک تابع کامپوزبل برای نمایش یک Chip با اندازه‌های مختلف.
 * این تابع یک نمای کارت مانند نمایش می‌دهد که محتوا را به صورت افقی در یک ردیف مرتب می‌کند.
 *
 * @param modifier اصلاح‌گری که به عنصر اصلی اعمال می‌شود.
 * @param size سایز Chip که می‌تواند SMALL یا REGULAR باشد. در صورتی که انتخاب نشود، اندازه پیش‌فرض REGULAR است.
 * @param chipContent محتوای داخلی Chip که به صورت یک کامپوزبل درون Row قرار می‌گیرد.
 */
@Composable
fun BookChip(
    modifier: Modifier = Modifier, // اصلاح‌گر پیش‌فرض برای تنظیم ظاهر
    size: ChipSize = ChipSize.REGULAR, // اندازه پیش‌فرض Chip
    chipContent: @Composable RowScope.() -> Unit // محتوای داخلی Chip که درون Row قرار می‌گیرد
) {
    Box(
        modifier = modifier
            .widthIn( // تعیین حداقل عرض Chip براساس اندازه
                min = when(size) {
                    ChipSize.SMALL -> 50.dp // در صورت انتخاب SMALL، عرض حداقل 50dp است
                    ChipSize.REGULAR -> 80.dp // در صورت انتخاب REGULAR، عرض حداقل 80dp است
                }
            )
            .clip(RoundedCornerShape(16.dp)) // شکل گوشه‌های Chip به صورت گرد با شعاع 16dp
            .background(LightBlue) // رنگ پس‌زمینه Chip
            .padding( // فاصله داخلی Chip
                vertical = 8.dp, // فاصله عمودی
                horizontal = 12.dp // فاصله افقی
            ),
        contentAlignment = Alignment.Center // محاذات محتوا در مرکز
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // محاذات عمودی در مرکز
            horizontalArrangement = Arrangement.Center // محاذات افقی در مرکز
        ) {
            chipContent() // محتوای داخلی که در Row قرار می‌گیرد
        }
    }
}
