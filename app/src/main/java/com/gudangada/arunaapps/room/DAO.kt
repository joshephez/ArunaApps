package com.gudangada.arunaapps.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gudangada.arunaapps.entity.Model
import io.reactivex.Single

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(postsItem: Model)


    //@Query("SELECT * FROM return_value WHERE title LIKE :query_")
    @Query("SELECT * FROM return_value WHERE title LIKE '%' || :query_ || '%'")
    fun getPostWithQuery(query_: String): Single<List<Model>>

    @Query("SELECT * FROM return_value")
    fun getPosts(): Single<List<Model>>

    @Query("SELECT * FROM return_value WHERE id = :id_")
    fun getPost(id_: Int): Single<Model>

    @Query("DELETE FROM return_value")
    fun deletePosts()

}