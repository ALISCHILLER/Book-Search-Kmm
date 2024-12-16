package org.msa.booksearch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform