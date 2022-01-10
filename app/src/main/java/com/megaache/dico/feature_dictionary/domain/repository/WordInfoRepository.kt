package com.megaache.dico.feature_dictionary.domain.repository

import com.megaache.dico.core.util.Resource
import com.megaache.dico.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}