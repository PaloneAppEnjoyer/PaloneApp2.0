package com.palone.paloneapp.domain.htmlParser

import com.palone.paloneapp.feature_screen_substitutions.data.models.SubstitutionData

interface HtmlParserInterface {
    fun getSubstitutionsFromHtml(rawData: String?): List<SubstitutionData>
}