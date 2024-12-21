@file:OptIn(FlowPreview::class)

package org.msa.booksearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.msa.booksearch.core.resources.onError
import org.msa.booksearch.core.resources.onSuccess
import org.msa.booksearch.core.utils.toUiText
import org.msa.booksearch.data.datasource.local.modelEntity.BookEntity
import org.msa.booksearch.domain.model.BookModel
import org.msa.booksearch.domain.repository.BookRepository
import org.msa.booksearch.presentation.utils.BookListAction
import org.msa.booksearch.presentation.model.BookListState

// ViewModel برای مدیریت وضعیت و عملیات مربوط به لیست کتاب‌ها
class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    // متغیر برای ذخیره کتاب‌های کش‌شده
    private var cachedBooks = emptyList<BookModel>()
    // متغیر برای مدیریت کارهای جستجو
    private var searchJob: Job? = null
    // متغیر برای مدیریت وظایف مربوط به کتاب‌های مورد علاقه
    private var observeFavoriteJob: Job? = null

    // وضعیت فعلی لیست کتاب‌ها با استفاده از MutableStateFlow
    private val _state = MutableStateFlow(BookListState())
    // وضعیت نمایش داده شده به UI
    val state = _state
        .onStart {
            // اگر کتاب‌های کش‌شده خالی بودند، جستجو و کتاب‌های مورد علاقه را مشاهده می‌کنیم
            if(cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // تابعی برای پردازش اقدامات مختلف در ویو مدل
    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {
                // اینجا می‌توان کدهای مربوط به کلیک روی کتاب را اضافه کرد
            }

            is BookListAction.OnSearchQueryChange -> {
                // بروزرسانی وضعیت جستجو با عبارت جستجوی جدید
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                // بروزرسانی وضعیت با تب انتخابی جدید
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    // تابع برای مشاهده تغییرات در کتاب‌های مورد علاقه
    private fun observeFavoriteBooks() {
        // لغو وظیفه قبلی مشاهده کتاب‌های مورد علاقه
        observeFavoriteJob?.cancel()
        observeFavoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { favoriteBooks ->
                // بروزرسانی وضعیت با لیست کتاب‌های مورد علاقه
                _state.update { it.copy(
                    favoriteBooks = favoriteBooks
                ) }
            }
            .launchIn(viewModelScope)
    }

    // تابع برای مشاهده تغییرات در عبارت جستجو
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged() // فقط تغییرات جدید جستجو را پردازش می‌کند
            .debounce(500L) // تأخیر برای جلوگیری از جستجوهای مکرر
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        // اگر عبارت جستجو خالی باشد، نتایج جستجو به کتاب‌های کش‌شده برمی‌گردد
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        // اگر طول عبارت جستجو بیشتر یا مساوی ۲ باشد، جستجو آغاز می‌شود
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    // تابع برای انجام جستجو در کتاب‌ها
    private fun searchBooks(query: String) = viewModelScope.launch {
        // بروزرسانی وضعیت به حالت بارگذاری
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        bookRepository
            .searchBooks(query)
            .onSuccess { searchResults ->
                // اگر جستجو موفقیت‌آمیز باشد، نتایج به وضعیت اضافه می‌شود
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                // اگر خطا رخ دهد، وضعیت به حالت خطا تغییر می‌کند
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

}
