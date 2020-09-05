package com.katalis.branchapi.dto

data class PageJsonBranchDto(
        var totalPages: Int? = 0,
        var totalElements: Int? = 0,
        var elements: List<BranchDto>? = null
)