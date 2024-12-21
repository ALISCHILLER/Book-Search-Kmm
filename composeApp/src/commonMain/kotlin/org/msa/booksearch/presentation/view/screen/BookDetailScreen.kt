@file:OptIn(ExperimentalLayoutApi::class)

package org.msa.booksearch.presentation.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import booksearchkmm.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.msa.booksearch.core.uiKit.SandYellow
import org.msa.booksearch.presentation.utils.BookDetailAction
import org.msa.booksearch.presentation.view.components.BlurredImageBackground
import org.msa.booksearch.presentation.view.components.BookChip
import org.msa.booksearch.presentation.view.components.ChipSize
import org.msa.booksearch.presentation.view.components.TitledContent
import org.msa.booksearch.presentation.model.BookDetailState
import org.msa.booksearch.presentation.viewmodel.BookDetailViewModel
import kotlin.math.round
// فایل صفحه جزئیات کتاب

// وارد کردن کتابخانه‌های مورد نیاز برای Jetpack Compose و Material3

// تابع ریشه برای نمایش صفحه جزئیات کتاب
@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel, // ویو مدل مرتبط با این صفحه
    onBackClick: () -> Unit // تابع بازگشت به صفحه قبلی
) {
    // جمع‌آوری وضعیت صفحه از ViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()

    // فراخوانی کامپوننت اصلی صفحه
    BookDetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is BookDetailAction.OnBackClick -> onBackClick() // مدیریت عملیات بازگشت
                else -> Unit
            }
            viewModel.onAction(action) // ارسال عملیات به ViewModel
        }
    )
}

// تابع اصلی صفحه جزئیات کتاب
@Composable
private fun BookDetailScreen(
    state: BookDetailState, // وضعیت فعلی صفحه
    onAction: (BookDetailAction) -> Unit // عملیات قابل انجام در صفحه
) {
    // پس‌زمینه محو شده با عکس کتاب
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl, // آدرس تصویر کتاب
        isFavorite = state.isFavorite, // وضعیت علاقه‌مندی
        onFavoriteClick = {
            onAction(BookDetailAction.OnFavoriteClick) // عملیات علاقه‌مندی
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick) // عملیات بازگشت
        },
        modifier = Modifier.fillMaxSize()
    ) {
        // اگر اطلاعات کتاب موجود باشد
        if (state.book != null) {
            // چیدمان ستون برای نمایش اطلاعات کتاب
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp) // حداکثر عرض ستون
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()), // فعال‌سازی اسکرول عمودی
                horizontalAlignment = Alignment.CenterHorizontally // تراز مرکزی برای افقی
            ) {
                // نمایش عنوان کتاب
                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                // نمایش نام نویسندگان کتاب
                Text(
                    text = state.book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                // نمایش اطلاعات تکمیلی مانند امتیاز و تعداد صفحات
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // اگر امتیاز وجود داشته باشد
                    state.book.averageRating?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.rating), // عنوان بخش
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(rating * 10) / 10.0}" // نمایش امتیاز
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow // رنگ زرد ستاره
                                )
                            }
                        }
                    }
                    // اگر تعداد صفحات وجود داشته باشد
                    state.book.numPages?.let { pageCount ->
                        TitledContent(
                            title = stringResource(Res.string.pages), // عنوان بخش
                        ) {
                            BookChip {
                                Text(text = pageCount.toString()) // نمایش تعداد صفحات
                            }
                        }
                    }
                }
                // نمایش زبان‌های کتاب
                if (state.book.languages.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.book.languages.forEach { language ->
                                BookChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(), // نمایش زبان به صورت حروف بزرگ
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
                // نمایش عنوان بخش توضیحات
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )
                // نمایش توضیحات کتاب یا پیام در حال بارگذاری
                if (state.isLoading) {
                    CircularProgressIndicator() // نمایش نوار بارگذاری
                } else {
                    Text(
                        text = if (state.book.description.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable) // متن پیش‌فرض در صورت نبود توضیحات
                        } else {
                            state.book.description // نمایش توضیحات کتاب
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f) // رنگ متن پیش‌فرض
                        } else Color.Black,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}
