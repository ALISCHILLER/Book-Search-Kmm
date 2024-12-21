package org.msa.booksearch.domain.repository


import kotlinx.coroutines.flow.Flow
import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.EmptyResult
import org.msa.booksearch.core.resources.ResultCustom
import org.msa.booksearch.domain.model.BookModel

interface BookRepository {
    suspend fun searchBooks(query: String): ResultCustom<List<BookModel>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): ResultCustom<String?, DataError>

    fun getFavoriteBooks(): Flow<List<BookModel>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}