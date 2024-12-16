package org.msa.booksearch.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * این شیء Serializer برای دسرسیال‌سازی و سریال‌سازی شیء BookWorkDto به فرمت JSON و برعکس استفاده می‌شود.
 * این کد به‌طور دقیق‌تری اطلاعات را از JSON استخراج و به‌طور خودکار به BookWorkDto تبدیل می‌کند.
 */
object BookWorkDtoSerializer : KSerializer<BookWorkDto> {

    // توصیف‌کننده‌ای برای فیلدهای داده‌ای BookWorkDto
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(BookWorkDto::class.simpleName!!) {
        // فیلد description که می‌تواند null باشد
        element<String?>("description")
    }

    /**
     * دسرسیال‌سازی (Deserialization) داده‌ها از JSON به شیء BookWorkDto.
     * در اینجا بررسی می‌شود که داده‌های ورودی به صورت رشته‌ای (String) یا شیء JSON باشند.
     */
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String? = null

        // پردازش تمام فیلدهای موجود در JSON
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "این دسرسیالیزر فقط با JSON کار می‌کند."
                    )
                    val element = jsonDecoder.decodeJsonElement()

                    // بررسی نوع داده برای فیلد description
                    description = when {
                        element is JsonObject -> {
                            // اگر داده‌ها شیء JSON باشد، آن را با استفاده از DescriptionDto دسرسیالیز می‌کنیم
                            decoder.json.decodeFromJsonElement<DescriptionDto>(element).value
                        }
                        element is JsonPrimitive && element.isString -> {
                            // اگر داده‌ها از نوع رشته (String) باشند
                            element.content
                        }
                        else -> null // اگر نوع داده‌ها نامشخص یا غیرمنتظره باشند
                    }
                }
                CompositeDecoder.DECODE_DONE -> break // پایان پردازش فیلدهای JSON
                else -> throw SerializationException("شاخص غیرمنتظره $index در هنگام دسرسیال‌سازی.")
            }
        }

        // بازگشت شیء BookWorkDto با مقدار description که استخراج شده است
        return@decodeStructure BookWorkDto(description)
    }

    /**
     * سریال‌سازی (Serialization) شیء BookWorkDto به فرمت JSON.
     * در اینجا اگر فیلد description وجود داشته باشد، آن را به فرمت JSON تبدیل می‌کند.
     */
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(descriptor) {
        // اگر description موجود باشد، آن را در قالب JSON قرار می‌دهیم
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }
}
