package com.megaache.dico.feature_dictionary.data.repository

import com.megaache.dico.core.util.Resource
import com.megaache.dico.feature_dictionary.data.local.WordInfoDao
import com.megaache.dico.feature_dictionary.data.remote.DictionaryApi
import com.megaache.dico.feature_dictionary.domain.model.WordInfo
import com.megaache.dico.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordinfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            //no internet
            emit(
                Resource.Error(
                    message = "Oops, Something went wrong!",
                    data = wordInfos
                )
            )
        } catch (e: IOException) {
            //parsing exception
            emit(
                Resource.Error(
                    message = "No Internet!",
                    data = wordInfos
                )
            )
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))

    }
}