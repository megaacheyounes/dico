package com.megaache.dico.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.megaache.dico.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntity>)

    @Query("delete from wordinfoentity where word IN(:words)")
    suspend fun deleteWordinfos(words: List<String>)

    @Query("select * from wordinfoentity where word like '%' || :word || '%'")
    suspend fun getWordInfos(word: String): List<WordInfoEntity>
}