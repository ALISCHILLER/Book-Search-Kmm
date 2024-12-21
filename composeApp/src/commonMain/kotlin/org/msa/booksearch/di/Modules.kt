package org.msa.booksearch.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.msa.booksearch.core.resources.HttpClientFactory
import org.msa.booksearch.data.datasource.local.AppDatabase
import org.msa.booksearch.data.datasource.local.DatabaseFactory
import org.msa.booksearch.data.datasource.remote.RemoteBookDataSource
import org.msa.booksearch.domain.repository.BookRepository


expect val platformModule: Module
/**
 * ماژول Koin برای تعریف و راه‌اندازی وابستگی‌های مشترک در پروژه.
 * این وابستگی‌ها در تمام پلتفرم‌ها (مانند Android و iOS) به اشتراک گذاشته می‌شوند.
 */
val sharedModule = module {
    // ایجاد یک نمونه از HttpClient با استفاده از فکتوری HttpClientFactory.
    single { HttpClientFactory.create(get()) }

    // وابستگی به داده‌های ریموت کتاب‌ها (از طریق کلاس KtorRemoteBookDataSource)
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    // ایجاد نمونه‌ای از BookRepository با استفاده از DefaultBookRepository
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    // پیکربندی پایگاه داده با استفاده از SQLite
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver()) // استفاده از SQLite به عنوان درایور
            .build()
    }

    // گرفتن DAO مربوط به کتاب‌های مورد علاقه از پایگاه داده
    single { get<AppDatabase>().favoriteBookDao }

    // تعریف ViewModel‌ها برای مدیریت داده‌ها و تعامل با UI
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}
