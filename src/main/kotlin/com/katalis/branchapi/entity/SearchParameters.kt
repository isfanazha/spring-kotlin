package com.katalis.branchapi.entity

data class SearchParameters(
        var page: Int,
        var size: Int,
        var keyword: String? = ""
)
