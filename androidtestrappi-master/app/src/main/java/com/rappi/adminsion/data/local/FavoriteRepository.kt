package com.rappi.adminsion.data.local;




class FavoriteRepository(private val movieDao: MovieDao) {

    fun movieById(movieId: Int) = movieDao.loadMoviewByIds(movieId)
   // fun movies(type: String) = movieDao.loadAllMovie(type)
    suspend fun insert(favoriteMovie: FavoriteMovie) =
        movieDao.insetMovie(favoriteMovie)

    suspend fun deleteById(movieId: Int) = movieDao.delete(movieId)
}