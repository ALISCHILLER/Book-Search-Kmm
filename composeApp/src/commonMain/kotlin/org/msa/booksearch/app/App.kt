package org.msa.booksearch.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import booksearchkmm.composeapp.generated.resources.Res
import booksearchkmm.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.viewmodel.koinViewModel
import org.msa.booksearch.presentation.utils.BookDetailAction
import org.msa.booksearch.presentation.view.screen.BookDetailScreenRoot
import org.msa.booksearch.presentation.view.screen.BookListScreenRoot
import org.msa.booksearch.presentation.viewmodel.BookDetailViewModel
import org.msa.booksearch.presentation.viewmodel.BookListViewModel

// کامپوزبل اصلی اپلیکیشن که نمای کلی را تشکیل می‌دهد
@Composable
@Preview
fun App() {
    // استفاده از تم متریال برای طراحی رابط کاربری
    MaterialTheme {
        // تعریف کنترلر ناوبری که برای هدایت به صفحات مختلف استفاده می‌شود
        val navController = rememberNavController()

        // NavHost برای مدیریت مسیرها و هدایت به صفحه‌های مختلف
        NavHost(
            navController = navController,
            startDestination = Route.BookGraph // مسیر اولیه که صفحه BookGraph است
        ) {
            // تعریف مسیرهای مختلف مربوط به BookGraph
            navigation<Route.BookGraph>(
                startDestination = Route.BookList // شروع مسیر با صفحه BookList
            ) {
                // مسیر صفحه لیست کتاب‌ها
                composable<Route.BookList>(
                    exitTransition = { slideOutHorizontally() }, // انیمیشن خروج از صفحه
                    popEnterTransition = { slideInHorizontally() } // انیمیشن ورود به صفحه
                ) {
                    // ویو مدل برای صفحه لیست کتاب‌ها
                    val viewModel = koinViewModel<BookListViewModel>()
                    // ویو مدل برای کتاب انتخابی که در چند صفحه به اشتراک گذاشته می‌شود
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    // هنگامی که وارد صفحه می‌شویم، کتاب انتخابی را ریست می‌کنیم
                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }

                    // نمایش صفحه لیست کتاب‌ها
                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            // زمانی که روی یک کتاب کلیک می‌کنیم، آن را به عنوان کتاب انتخاب شده ذخیره می‌کنیم
                            selectedBookViewModel.onSelectBook(book)
                            // هدایت به صفحه جزئیات کتاب
                            navController.navigate(
                                Route.BookDetail(book.id) // مسیر صفحه جزئیات کتاب
                            )
                        }
                    )
                }

                // مسیر صفحه جزئیات کتاب
                composable<Route.BookDetail>(
                    enterTransition = { slideInHorizontally { initialOffset -> initialOffset } }, // انیمیشن ورود
                    exitTransition = { slideOutHorizontally { initialOffset -> initialOffset } } // انیمیشن خروج
                ) {
                    // ویو مدل کتاب انتخابی
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    // ویو مدل جزئیات کتاب
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    // کتاب انتخابی که باید در صفحه جزئیات نمایش داده شود
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    // زمانی که کتاب انتخابی تغییر کند، این اثر به روزرسانی‌های لازم را انجام می‌دهد
                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            // ارسال تغییرات به ویو مدل جزئیات کتاب
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                        }
                    }

                    // نمایش صفحه جزئیات کتاب
                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            // اگر کاربر دکمه برگشت را بزند، به صفحه قبلی برمی‌گردیم
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

// تابعی که ویو مدل‌های اشتراکی را بین صفحات مختلف در مسیرهای مختلف مدیریت می‌کند
@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    // اگر مسیر والد برای صفحه پیدا شد، از آن استفاده می‌کنیم، در غیر این صورت ویو مدل را مستقیماً ایجاد می‌کنیم
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        // پیدا کردن ورودی پدر در مسیر ناوبری
        navController.getBackStackEntry(navGraphRoute)
    }
    // استفاده از Koin برای دریافت ویو مدل از ورودی پدر
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
