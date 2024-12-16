package org.msa.booksearch.data.repository

import kotlinx.coroutines.flow.Flow
import org.msa.booksearch.core.resources.DataError
import org.msa.booksearch.core.resources.EmptyResult
import org.msa.booksearch.core.resources.ResultCustom
import org.msa.booksearch.data.model.BookModel
import org.msa.booksearch.domain.repository.BookRepository

class BookRepositoryImpl(): BookRepository {

    override suspend fun searchBooks(query: String): ResultCustom<List<BookModel>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookDescription(bookId: String): ResultCustom<String?, DataError> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteBooks(): Flow<List<BookModel>> {
        TODO("Not yet implemented")
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(id: String) {
        TODO("Not yet implemented")
    }


}