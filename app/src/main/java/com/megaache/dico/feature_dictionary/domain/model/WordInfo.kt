package com.megaache.dico.feature_dictionary.domain.model

import com.megaache.dico.feature_dictionary.data.remote.dto.MeaningDto
import com.megaache.dico.feature_dictionary.data.remote.dto.PhoneticDto

data class WordInfo(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val word: String
)