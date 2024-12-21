package org.msa.booksearch.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.msa.booksearch.app.Route
import org.msa.booksearch.core.resources.onSuccess
import org.msa.booksearch.domain.repository.BookRepository
import org.msa.booksearch.presentation.utils.BookDetailAction
import org.msa.booksearch.presentation.model.BookDetailState

// کلاس BookDetailViewModel برای مدیریت وضعیت جزئیات کتاب و تعامل با لایه داده طراحی شده است.
class BookDetailViewModel(
    private val bookRepository: BookRepository, // وابستگی به مخزن داده‌های کتاب
    private val savedStateHandle: SavedStateHandle // مدیریت وضعیت ذخیره شده برای ناوبری
): ViewModel() {

    // دریافت شناسه کتاب از مسیر ذخیره شده
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    // وضعیت داخلی برای مدیریت جزئیات کتاب
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        // زمانی که جریان شروع می‌شود، جزئیات کتاب و وضعیت علاقه‌مندی‌ها بارگذاری می‌شوند.
        .onStart {
            fetchBookDescription()
            observeFavoriteStatus()
        }
        // به اشتراک‌گذاری وضعیت جریان در طول عمر ViewModel
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // مدیریت تعاملات کاربر از طریق اکشن‌ها
    fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> {
                // به‌روزرسانی وضعیت با کتاب انتخاب‌شده جدید
                _state.update { it.copy(
                    book = action.book
                ) }
            }
            is BookDetailAction.OnFavoriteClick -> {
                // افزودن یا حذف کتاب به علاقه‌مندی‌ها
                viewModelScope.launch {
                    if(state.value.isFavorite) {
                        bookRepository.deleteFromFavorites(bookId) // حذف از علاقه‌مندی‌ها
                    } else {
                        state.value.book?.let { book ->
                            bookRepository.markAsFavorite(book) // افزودن به علاقه‌مندی‌ها
                        }
                    }
                }
            }
            else -> Unit // سایر اکشن‌ها نیازی به مدیریت ندارند
        }
    }

    // مشاهده وضعیت علاقه‌مندی برای کتاب فعلی
    private fun observeFavoriteStatus() {
        bookRepository
            .isBookFavorite(bookId)
            .onEach { isFavorite ->
                _state.update { it.copy(
                    isFavorite = isFavorite
                ) }
            }
            .launchIn(viewModelScope) // اتصال جریان به محدوده ViewModel
    }

    // بارگذاری توضیحات کتاب از مخزن داده‌ها
    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update { it.copy(
                        book = it.book?.copy(
                            description = description
                        ),
                        isLoading = false // بارگذاری به پایان رسیده است
                    ) }
                }
        }
    }
}
