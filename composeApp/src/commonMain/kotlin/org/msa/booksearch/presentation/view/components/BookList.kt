package org.msa.booksearch.presentation.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.msa.booksearch.domain.model.BookModel

/**
 * یک کامپوزابل برای نمایش لیست کتاب‌ها در قالب یک LazyColumn.
 * این کامپوزابل به شما امکان می‌دهد که یک لیست افقی یا عمودی از کتاب‌ها را نمایش دهید
 * و هر کتاب را با کلیک بر روی آن باز کنید.
 *
 * @param books لیستی از مدل‌های کتاب که باید در لیست نمایش داده شوند.
 * @param onBookClick یک تابع که زمانی که کاربر بر روی یک کتاب کلیک کند، فراخوانی می‌شود.
 * @param modifier اصلاح‌گر برای تنظیم ویژگی‌های ظاهری کامپوزبل.
 * @param scrollState وضعیت اسکرول که برای نگهداری وضعیت اسکرول لیست استفاده می‌شود.
 */
@Composable
fun BookList(
    books: List<BookModel>, // لیست کتاب‌ها که باید در نمای لیست نمایش داده شوند.
    onBookClick: (BookModel) -> Unit, // تابعی که زمانی که کاربر بر روی یک کتاب کلیک می‌کند فراخوانی می‌شود.
    modifier: Modifier = Modifier, // اصلاح‌گر اختیاری برای تنظیم ویژگی‌های ظاهری کامپوزبل
    scrollState: LazyListState = rememberLazyListState() // وضعیت اسکرول لیست
) {
    // استفاده از LazyColumn برای نمایش لیست به صورت بهینه.
    LazyColumn(
        modifier = modifier, // اصلاح‌گر که به LazyColumn اعمال می‌شود.
        state = scrollState, // وضعیت اسکرول لیست.
        verticalArrangement = Arrangement.spacedBy(12.dp), // فاصله عمودی بین آیتم‌ها در لیست.
        horizontalAlignment = Alignment.CenterHorizontally // محاذات افقی آیتم‌ها در مرکز.
    ) {
        // حلقه‌ای برای نمایش هر کتاب از لیست
        items(
            items = books, // داده‌های ورودی (لیست کتاب‌ها)
            key = { it.id } // کلید منحصر به فرد برای هر کتاب که معمولاً از شناسه کتاب استفاده می‌شود.
        ) { book ->
            // نمایش هر آیتم کتاب در لیست
            BookListItem(
                book = book, // داده کتاب
                modifier = Modifier
                    .widthIn(max = 700.dp) // عرض کتاب محدود به 700dp
                    .fillMaxWidth() // پر کردن عرض صفحه
                    .padding(horizontal = 16.dp), // فاصله افقی از لبه‌های صفحه
                onClick = {
                    // فراخوانی تابع onBookClick زمانی که کاربر بر روی کتاب کلیک می‌کند
                    onBookClick(book)
                }
            )
        }
    }
}
