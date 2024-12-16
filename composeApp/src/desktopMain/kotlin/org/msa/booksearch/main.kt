package org.msa.booksearch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Book Search Kmm",
    ) {
        App()
    }
}