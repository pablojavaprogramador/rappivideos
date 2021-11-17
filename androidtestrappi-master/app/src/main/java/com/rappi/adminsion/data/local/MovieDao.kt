package com.rappi.adminsion.data.local;

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import javax.sql.DataSource

@Dao
interface MovieDao {

    //@Query("SELECT * FROM FavoriteMovie where type IN (:type)")
  //  fun loadAllMovie(type: String): DataSource.Factory<Int, FavoriteMovie>

    @Query("SELECT * FROM FavoriteMovie where id IN (:movieId)")
    fun loadMoviewByIds(movieId: Int): LiveData<FavoriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetMovie(vararg favoriteMovies: FavoriteMovie)

    @Query("DELETE FROM FavoriteMovie where id IN (:movieId)")
    suspend fun delete(movieId: Int)
}