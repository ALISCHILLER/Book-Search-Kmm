package org.msa.booksearch.data.model

import kotlinx.serialization.Serializable

/**
 * نمایه جزئیات کتاب. این کلاس شامل اطلاعات مختلف کتاب از جمله توضیحات کتاب است.
 * در صورت نیاز می‌توان سایر اطلاعات مرتبط مانند عنوان، نویسنده و تاریخ انتشار را به این کلاس اضافه کرد.
 *
 * @property description توضیحات مربوط به کتاب. ممکن است خالی باشد.
 */
@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    /**
     * توضیحات مربوط به کتاب.
     * ممکن است این فیلد خالی باشد، بنابراین مقدار پیش‌فرض آن null است.
     */
    val description: String? = null
)
