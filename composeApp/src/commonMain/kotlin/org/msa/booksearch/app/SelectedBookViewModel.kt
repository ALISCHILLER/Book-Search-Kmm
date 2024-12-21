// پکیج مربوط به مدیریت ViewModel در اپلیکیشن
package org.msa.booksearch.app

// وارد کردن کلاس‌های ViewModel و StateFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.msa.booksearch.domain.model.BookModel

// تعریف ViewModel برای مدیریت وضعیت کتاب انتخاب‌شده
class SelectedBookViewModel : ViewModel() {

    // یک StateFlow قابل تغییر برای نگهداری اطلاعات کتاب انتخاب‌شده
    private val _selectedBook = MutableStateFlow<BookModel?>(null)

    // نسخه غیرقابل تغییر StateFlow برای دسترسی از سایر بخش‌ها
    val selectedBook = _selectedBook.asStateFlow()

    // تابعی برای تغییر کتاب انتخاب‌شده
    fun onSelectBook(book: BookModel?) {
        _selectedBook.value = book
    }
}
