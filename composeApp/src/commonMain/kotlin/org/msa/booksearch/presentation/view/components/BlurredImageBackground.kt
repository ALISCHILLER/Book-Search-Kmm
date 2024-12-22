package org.msa.booksearch.presentation.view.components


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import booksearchkmm.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.msa.booksearch.core.uiKit.DarkBlue
import org.msa.booksearch.core.uiKit.*

/**
 * یک تابع کامپوزبل که یک تصویر پس‌زمینه با افکت بلور (تار) نمایش می‌دهد و به کاربر این امکان را می‌دهد
 * که به صفحه قبلی برگردد یا آیتم را به عنوان مورد علاقه علامت‌گذاری کند.
 *
 * @param imageUrl آدرس URL تصویری که به عنوان پس‌زمینه نمایش داده می‌شود.
 * @param isFavorite متغیری بولی که نشان می‌دهد آیا آیتم به عنوان مورد علاقه علامت‌گذاری شده است یا خیر.
 * @param onFavoriteClick تابع بازگشتی که زمانی که کاربر روی دکمه "مورد علاقه" کلیک می‌کند، اجرا می‌شود.
 * @param onBackClick تابع بازگشتی که زمانی که کاربر روی دکمه "بازگشت" کلیک می‌کند، اجرا می‌شود.
 * @param modifier اصلاح‌گری که به عنصر اصلی (container) اعمال می‌شود.
 * @param content یک تابع کامپوزبل که به محتوای اضافی این پس‌زمینه امکان اضافه شدن می‌دهد.
 */
@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // یک حالت برای ذخیره نتیجه بارگذاری تصویر (موفق یا ناموفق).
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    // Painter برای بارگذاری تصویر به صورت غیرهمزمان.
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            // بررسی ابعاد تصویر پس از بارگذاری برای تایید صحت تصویر.
            val size = it.painter.intrinsicSize
            imageLoadResult = if(size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("ابعاد تصویر نامعتبر"))
            }
        },
        onError = {
            // چاپ جزئیات خطا در صورت ناموفق بودن بارگذاری تصویر.
            it.result.throwable.printStackTrace()
        }
    )

    // کانتینر اصلی که تصویر پس‌زمینه و سایر عناصر UI را در خود جای می‌دهد.
    Box(modifier = modifier) {
        // یک ستون که صفحه را به دو بخش تقسیم می‌کند: یکی برای تصویر بلور شده و دیگری برای کارت.
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // بخش بالایی که تصویر پس‌زمینه بلور شده را نمایش می‌دهد.
            Box(
                modifier = Modifier
                    .weight(0.3f) // 30 درصد ارتفاع صفحه را اشغال می‌کند
                    .fillMaxWidth()
                    .background(DarkBlue) // رنگ پس‌زمینه را تنظیم می‌کند
            ) {
                // نمایش تصویر با افکت بلور (تار) اعمال شده.
                Image(
                    painter = painter,
                    contentDescription = stringResource(Res.string.book_cover),
                    contentScale = ContentScale.Crop, // تصویر را برش می‌دهد تا فضا را پر کند
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp) // اعمال افکت بلور
                )
            }

            // بخش پایینی صفحه که رنگ پس‌زمینه تنظیم شده را دارد.
            Box(
                modifier = Modifier
                    .weight(0.7f) // 70 درصد ارتفاع صفحه را اشغال می‌کند
                    .fillMaxWidth()
                    .background(DesertWhite) // رنگ پس‌زمینه را تنظیم می‌کند
            )
        }

        // دکمه بازگشت در گوشه بالا سمت چپ برای بازگشت به صفحه قبلی.
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding() // پدینگ برای جلوگیری از همپوشانی با نوار وضعیت
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White // رنگ آیکون را سفید می‌کند
            )
        }

        // ستون مرکزی برای نمایش محتوای اضافی.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f)) // فضای خالی برای ایجاد فاصله
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp) // ارتفاع کارت
                    .aspectRatio(2 / 3f), // نسبت ابعاد کارت
                shape = RoundedCornerShape(8.dp), // شکل گوشه‌های گرد کارت
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 15.dp // سایه کارت
                )
            ) {
                // محتوای متحرک برای بارگذاری تصویر
                AnimatedContent(
                    targetState = imageLoadResult
                ) { result ->
                    when(result) {
                        null -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            // نمایش انیمیشن در صورت بارگذاری نشدن تصویر
                            PulseAnimation(
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }
                        else -> {
                            Box {
                                // نمایش تصویر یا تصویر خطا در صورت بارگذاری موفق یا ناموفق
                                Image(
                                    painter = if(result.isSuccess) painter else {
                                        painterResource(Res.drawable.book_error_2)
                                    },
                                    contentDescription = stringResource(Res.string.book_cover),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    contentScale = if(result.isSuccess) {
                                        ContentScale.Crop
                                    } else {
                                        ContentScale.Fit
                                    }
                                )
                                // دکمه مورد علاقه در پایین راست کارت
                                IconButton(
                                    onClick = onFavoriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    SandYellow, Color.Transparent
                                                ),
                                                radius = 70f
                                            )
                                        )
                                ) {
                                    // انتخاب آیکون مورد علاقه بسته به وضعیت
                                    Icon(
                                        imageVector = if(isFavorite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        tint = Color.Red,
                                        contentDescription = if(isFavorite) {
                                            stringResource(Res.string.remove_from_favorites)
                                        } else {
                                            stringResource(Res.string.mark_as_favorite)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            // محتوای اضافی که به تابع ورودی ارسال شده است.
            content()
        }
    }
}
