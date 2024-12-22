package org.msa.booksearch.presentation.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import booksearchkmm.composeapp.generated.resources.Res
import booksearchkmm.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.msa.booksearch.core.uiKit.DarkBlue
import org.msa.booksearch.core.uiKit.DesertWhite
import org.msa.booksearch.core.uiKit.SandYellow

/**
 * یک کامپوننت قابل استفاده مجدد برای نمایش نوار جستجو.
 *
 * @param searchQuery مقدار فعلی جستجو.
 * @param onSearchQueryChange تابعی که با تغییر متن جستجو فراخوانی می‌شود.
 * @param onImeSearch تابعی که هنگام فشردن دکمه "جستجو" در کیبورد اجرا می‌شود.
 * @param modifier اصلاح‌کننده‌ای برای اعمال تغییرات ظاهری یا ابعادی اضافی.
 */
@Composable
fun BookSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    // تغییر رنگ‌های مربوط به انتخاب متن برای نوار جستجو
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow, // رنگ دستگیره انتخاب متن
            backgroundColor = SandYellow // رنگ پس‌زمینه متن انتخاب شده
        )
    ) {
        OutlinedTextField(
            value = searchQuery, // مقدار ورودی فعلی
            onValueChange = onSearchQueryChange, // مدیریت تغییرات متن ورودی
            shape = RoundedCornerShape(100), // گوشه‌های گرد برای کادر نوار جستجو
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue, // رنگ نشانگر متن
                focusedBorderColor = SandYellow // رنگ کادر زمانی که روی آن فوکوس شده است
            ),
            placeholder = {
                // نمایش متن جایگزین وقتی ورودی خالی است
                Text(
                    text = stringResource(Res.string.search_hint) // متن جایگزین از منابع
                )
            },
            leadingIcon = {
                // آیکون جستجو در ابتدای نوار
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null, // بدون توضیح اضافی
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f) // شفافیت آیکون
                )
            },
            singleLine = true, // محدود کردن ورودی به یک خط
            keyboardActions = KeyboardActions(
                onSearch = {
                    // اجرای تابع جستجو هنگام فشردن دکمه "جستجو" روی کیبورد
                    onImeSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // نوع ورودی: متن
                imeAction = ImeAction.Search // عمل کیبورد: جستجو
            ),
            trailingIcon = {
                // نمایش آیکون حذف زمانی که متن ورودی خالی نیست
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank() // اگر متن خالی نیست، آیکون را نشان بده
                ) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("") // پاک کردن متن ورودی
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.close_hint), // توضیح آیکون
                            tint = MaterialTheme.colorScheme.onSurface // رنگ آیکون
                        )
                    }
                }
            },
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(100), // گوشه‌های گرد برای پس‌زمینه
                    color = DesertWhite // رنگ پس‌زمینه نوار
                )
                .minimumInteractiveComponentSize() // حداقل اندازه برای تعامل کاربر
        )
    }
}
