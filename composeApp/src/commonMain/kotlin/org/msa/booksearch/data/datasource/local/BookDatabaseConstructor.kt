package org.msa.booksearch.data.datasource.local

// برای اعلام یک شیء مشترک (expect) که در پلتفرم‌های مختلف پیاده‌سازی خواهد شد
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor {

    /**
     * این متد برای راه‌اندازی پایگاه داده استفاده می‌شود.
     * پیاده‌سازی این متد باید برای هر پلتفرم مختلف (مثل اندروید و iOS) انجام شود.
     *
     * @return پایگاه داده راه‌اندازی شده از نوع AppDatabase
     */
    fun initialize(): AppDatabase
}
