package org.msa.booksearch.presentation.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import booksearchkmm.composeapp.generated.resources.*

import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter

import org.jetbrains.compose.resources.painterResource
import org.msa.booksearch.core.uiKit.*
import org.msa.booksearch.domain.model.BookModel
import kotlin.math.round

/**
 * کامپوزبلی برای نمایش یک کتاب در لیست.
 * این کامپوزبل شامل تصویر کتاب، عنوان، نویسنده، امتیاز متوسط و دکمه‌ای برای مشاهده جزئیات کتاب است.
 *
 * @param book مدل کتاب که اطلاعات آن باید نمایش داده شود.
 * @param onClick تابعی که هنگام کلیک بر روی کتاب باید فراخوانی شود.
 * @param modifier اصلاح‌گری برای تنظیم ویژگی‌های ظاهری کامپوزبل.
 */
@Composable
fun BookListItem(
    book: BookModel, // مدل کتاب که باید اطلاعات آن نمایش داده شود.
    onClick: () -> Unit, // تابعی که هنگام کلیک روی کتاب باید فراخوانی شود.
    modifier: Modifier = Modifier // اصلاح‌گر اختیاری برای تنظیم ویژگی‌های ظاهری
) {
    // تعریف Surface برای تنظیم شکل و رنگ پس‌زمینه کامپوزبل
    Surface(
        shape = RoundedCornerShape(32.dp), // تنظیم گوشه‌های گرد برای کامپوزبل
        modifier = modifier
            .clickable(onClick = onClick), // اضافه کردن قابلیت کلیک به کامپوزبل
        color = LightBlue.copy(alpha = 0.2f) // رنگ پس‌زمینه با شفافیت کم
    ) {
        // استفاده از Row برای قرار دادن تصویر کتاب و اطلاعات آن در کنار هم
        Row(
            modifier = Modifier
                .padding(16.dp) // تنظیم فاصله داخلی
                .fillMaxWidth() // عرض کامپوزبل به طور کامل پر می‌شود
                .height(IntrinsicSize.Min), // ارتفاع کامپوزبل به حداقل اندازه لازم
            verticalAlignment = Alignment.CenterVertically, // تراز عمودی مرکز
            horizontalArrangement = Arrangement.spacedBy(16.dp) // فاصله افقی بین اجزا
        ) {
            // نمایش تصویر کتاب
            Box(
                modifier = Modifier
                    .height(100.dp), // ارتفاع تصویر کتاب
                contentAlignment = Alignment.Center // تراز کردن محتوا در مرکز
            ) {
                // متغیر برای ذخیره نتیجه بارگذاری تصویر
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                // بارگذاری تصویر از URL کتاب
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl, // URL تصویر کتاب
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter) // تصویر با ابعاد معتبر بارگذاری شده
                            } else {
                                Result.failure(Exception("Invalid image size")) // ابعاد نامعتبر
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace() // چاپ خطا در صورت شکست بارگذاری
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                val painterState by painter.state.collectAsStateWithLifecycle() // دریافت وضعیت بارگذاری تصویر
                val transition by animateFloatAsState(
                    targetValue = if(painterState is AsyncImagePainter.State.Success) {
                        1f // نمایش انتقال به حالت موفقیت
                    } else {
                        0f // نمایش انتقال به حالت شکست
                    },
                    animationSpec = tween(durationMillis = 800) // تنظیم مدت زمان انیمیشن
                )

                when (val result = imageLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp) // نمایش انیمیشن ضربان برای بارگذاری تصویر
                    )
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.book_error_2) // تصویر پیش‌فرض در صورت شکست بارگذاری
                            },
                            contentDescription = book.title, // توضیحات تصویر
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop // مقیاس تصویر برای پر کردن فضای تصویر
                            } else {
                                ContentScale.Fit // مقیاس تصویر برای تناسب با فضای تصویر
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f, // تنظیم نسبت ابعاد تصویر
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer {
                                    rotationX = (1f - transition) * 30f // چرخش تصویر
                                    val scale = 0.8f + (0.2f * transition) // مقیاس تصویر
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                }
            }
            // نمایش اطلاعات کتاب در ستون
            Column(
                modifier = Modifier
                    .fillMaxHeight() // ارتفاع ستون پر می‌شود
                    .weight(1f), // ستون وزن می‌گیرد تا فضای بیشتری را اشغال کند
                verticalArrangement = Arrangement.Center // تراز عمودی در مرکز
            ) {
                // نمایش عنوان کتاب
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium, // سبک متن عنوان کتاب
                    maxLines = 2, // حداکثر تعداد خطوط عنوان
                    overflow = TextOverflow.Ellipsis // استفاده از علامت ... برای نمایش عنوان طولانی
                )
                // نمایش نام نویسنده اگر موجود باشد
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = authorName, // نام نویسنده
                        style = MaterialTheme.typography.bodyLarge, // سبک متن نام نویسنده
                        maxLines = 1, // حداکثر تعداد خطوط نام نویسنده
                        overflow = TextOverflow.Ellipsis // استفاده از علامت ... در صورت طولانی بودن
                    )
                }
                // نمایش امتیاز کتاب اگر موجود باشد
                book.averageRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically // تراز عمودی در مرکز
                    ) {
                        // نمایش امتیاز کتاب
                        Text(
                            text = "${round(rating * 10) / 10.0}", // نمایش امتیاز با دقت یک رقم اعشاری
                            style = MaterialTheme.typography.bodyMedium // سبک متن امتیاز
                        )
                        // نمایش آیکن ستاره
                        Icon(
                            imageVector = Icons.Default.Star, // آیکن ستاره
                            contentDescription = null, // توضیح اضافی برای آیکن
                            tint = SandYellow // رنگ آیکن
                        )
                    }
                }
            }
            // نمایش آیکن برای مشاهده جزئیات بیشتر کتاب
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // آیکن فلش برای نمایش جزئیات
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp) // تنظیم اندازه آیکن
            )
        }
    }
}
