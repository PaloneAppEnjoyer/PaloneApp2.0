package com.palone.paloneapp.utils.htmlParser

import com.palone.paloneapp.substitutions.data.models.SubstitutionData

interface HtmlParserInterface {
    fun getSubstitutionsFromHtml(rawData: String?): List<SubstitutionData>
}