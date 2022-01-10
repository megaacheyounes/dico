package com.megaache.dico.feature_dictionary.domain.model

import com.megaache.dico.feature_dictionary.data.remote.dto.DefinitionDto

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)
