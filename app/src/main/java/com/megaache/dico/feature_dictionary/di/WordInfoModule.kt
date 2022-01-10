package com.megaache.dico.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.megaache.dico.feature_dictionary.data.local.Converters
import com.megaache.dico.feature_dictionary.data.local.WordInfoDao
import com.megaache.dico.feature_dictionary.data.local.WordInfoDatabase
import com.megaache.dico.feature_dictionary.data.remote.DictionaryApi
import com.megaache.dico.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.megaache.dico.feature_dictionary.data.util.GsonParser
import com.megaache.dico.feature_dictionary.domain.repository.WordInfoRepository
import com.megaache.dico.feature_dictionary.domain.use_case.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.Signed
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        dictionaryApi: DictionaryApi
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(
            dao = db.dao,
            api = dictionaryApi
        )
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun providesDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }


}