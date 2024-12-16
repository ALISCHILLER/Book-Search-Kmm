package org.msa.booksearch.core.uiKit

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * کامپوننت PulseAnimation
 *
 * این کامپوننت انیمیشنی با جلوه پالس زدن (افزایش و کاهش اندازه دایره) ایجاد می‌کند.
 * این انیمیشن می‌تواند برای جلب توجه کاربران یا نشان دادن فرآیندهای در حال اجرا در رابط کاربری استفاده شود.
 *
 * قابلیت‌های اصلی:
 * - تغییر مقیاس (Scale) و شفافیت (Alpha) به صورت تدریجی و انیمیشنی.
 * - قابلیت تنظیم رنگ، عرض مرز، مدت زمان انیمیشن و اندازه پایه دایره.
 *
 * @param modifier Modifier برای شخصی‌سازی ظاهر و مکان کامپوننت.
 * @param borderColor رنگ مرز انیمیشن (پیش‌فرض: زرد شنی).
 * @param borderWidth عرض مرز دایره (پیش‌فرض: 5dp).
 * @param duration مدت زمان یک چرخه انیمیشن به میلی‌ثانیه (پیش‌فرض: 1000ms).
 * @param minScale کوچک‌ترین اندازه دایره در انیمیشن (پیش‌فرض: 0.5f).
 * @param maxScale بزرگ‌ترین اندازه دایره در انیمیشن (پیش‌فرض: 1.5f).
 */
@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFFFFBD64), // رنگ پیش‌فرض: زرد شنی
    borderWidth: Dp = 5.dp, // عرض پیش‌فرض مرز
    duration: Int = 1000, // مدت زمان پیش‌فرض انیمیشن
    minScale: Float = 0.5f, // مقیاس کوچک‌ترین اندازه
    maxScale: Float = 1.5f // مقیاس بزرگ‌ترین اندازه
) {
    // ایجاد یک ترنزیشن بی‌نهایت برای اجرای انیمیشن
    val transition = rememberInfiniteTransition()

    // متغیر progress بین minScale و maxScale مقداردهی می‌شود
    val progress by transition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration), // مدت زمان انیمیشن
            repeatMode = RepeatMode.Restart // حالت تکرار
        )
    )

    // نمایش Box با انیمیشن پالسی
    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = progress // تغییر مقیاس افقی
                scaleY = progress // تغییر مقیاس عمودی
                alpha = maxScale - progress // شفافیت: مقدار معکوس مقیاس
            }
            .border(
                width = borderWidth, // عرض مرز
                color = borderColor, // رنگ مرز
                shape = CircleShape // شکل دایره‌ای
            )
    )
}